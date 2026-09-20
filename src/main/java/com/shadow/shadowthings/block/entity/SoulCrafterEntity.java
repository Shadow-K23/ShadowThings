package com.shadow.shadowthings.block.entity;

import com.mojang.logging.LogUtils;
import com.shadow.shadowthings.block.entity.base.AbstractSoulEntity;
import com.shadow.shadowthings.recipe.ModRecipes;
import com.shadow.shadowthings.recipe.SoulInfusionRecipe;
import com.shadow.shadowthings.screen.custom.SoulCrafterMenu;
import com.shadow.shadowthings.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.joml.Vector3f;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class SoulCrafterEntity extends AbstractSoulEntity implements MenuProvider {
    private static final Logger LOGGER = LogUtils.getLogger();

    // 1-slot inventory for the center Catalyst/Result item
    public final ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            sync();
            if (level != null && !level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            // Prevent putting items in while crafting
            return !isCrafting && super.isItemValid(slot, stack);
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            // Prevent players from pulling the item out while crafting!
            if (isCrafting) {
                return ItemStack.EMPTY;
            }
            return super.extractItem(slot, amount, simulate);
        }
    };

    // --- CRAFTING ANIMATION VARIABLES ---
    public boolean isCrafting = false;
    public int craftingProgress = 0;
    public int requiredSouls = 0;
    public int maxCraftingTime = 100; // 5 seconds (20 ticks * 5)

    public SoulInfusionRecipe cachedRecipe = null;
    public ItemStack cachedResult = ItemStack.EMPTY;

    public int requiredPedestals = 0;
    public int pedestalsConsumed = 0;
    public float renderYOffset = 0.0f;
    public float spinAngle = 2f;

    DustParticleOptions tinySoulDust = new DustParticleOptions(new Vector3f(0.1f, 0.7f, 0.9f), 0.5f);


    public SoulCrafterEntity(BlockPos pos, BlockState state) {
        // Passes the BlockEntity type, position, state, and a Max Soul capacity of 10,000!
        super(ModBlockEntities.SOUL_CRAFTER_BE.get(), pos, state,0, 10000, 0 ,0);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        // 1. CLIENT SIDE ONLY: Visuals and Animation Math
        if (level != null && level.isClientSide()) {
            this.tickClientVisuals();
            float currentSpeed = 2f; // Default idle speed (so it doesn't freeze when stopped)

            if (this.isCrafting && this.requiredSouls > 0) {
                // Cast to float FIRST, then divide!
                float progress = (float) this.getSouls() / (float) this.requiredSouls;
                currentSpeed = 4f + (progress * 128f);
            }

            this.spinAngle += currentSpeed;

            if (this.spinAngle >= 360f) {
                this.spinAngle -= 360f;
            }

            // --- 2. LEVITATION MATH ---
            if (this.isCrafting) {
                if (this.renderYOffset < 1.5f) {
                    this.renderYOffset += 0.05f;
                }
            } else {
                if (this.renderYOffset > 0.0f) {
                    this.renderYOffset -= 0.05f;
                }
            }
            this.renderYOffset = Math.clamp(this.renderYOffset, 0.0f, 1.5f);

            // -- SPHERE AROUND ITEM ---

            if (this.isCrafting && this.requiredSouls > 0) {
                float progress = (float) this.getSouls() / (float) this.requiredSouls;

                // Maximum 6 particles per tick at 100% completion.
                // Using Math.random() ensures smooth fractional buildup (e.g., 0.5 particles per tick).
                float particlesToSpawn = progress * 10f;
                for (int i = 0; i < (int) particlesToSpawn; i++) {
                    // 1. Generate random directions using Gaussian math
                    double d0 = level.random.nextGaussian();
                    double d1 = level.random.nextGaussian();
                    double d2 = level.random.nextGaussian();

                    // 2. Normalize the vector (forces the point to be on the edge of a sphere)
                    double distance = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                    double radius = 0.35; // The size of the sphere

                    double x = (d0 / distance) * radius;
                    double y = (d1 / distance) * radius;
                    double z = (d2 / distance) * radius;

                    // 3. Center the sphere perfectly around the levitating item
                    double centerX = this.getBlockPos().getX() + 0.5;
                    // We use the exact same height math as the Renderer!
                    double centerY = this.getBlockPos().getY() + 0.75 + (this.renderYOffset * 0.75);
                    double centerZ = this.getBlockPos().getZ() + 0.5;

                    // 4. Spawn the particle with 0 velocity so it stays in the sphere shape
                    level.addParticle(tinySoulDust,
                            centerX + x, centerY + y, centerZ + z,
                            0, 0, 0);
                }

                // Handle fractional particle chances (for when progress is very low)
                if (level.random.nextFloat() < (particlesToSpawn - (int) particlesToSpawn)) {
                    double d0 = level.random.nextGaussian();
                    double d1 = level.random.nextGaussian();
                    double d2 = level.random.nextGaussian();
                    double distance = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                    double radius = 0.35;

                    double centerX = this.getBlockPos().getX() + 0.5;
                    double centerY = this.getBlockPos().getY() + 0.75 + (this.renderYOffset * 0.75);
                    double centerZ = this.getBlockPos().getZ() + 0.5;

                    level.addParticle(tinySoulDust,
                            centerX + (d0 / distance) * radius,
                            centerY + (d1 / distance) * radius,
                            centerZ + (d2 / distance) * radius,
                            0, 0, 0);
                }
            }

            // --- THE BURNING BRAZIERS ---
            if (this.isCrafting) {
                for (SoulPedestalEntity ped : getNearbyPedestals()) {
                    // Only burn if there is an item on it!
                    if (!ped.inventory.getStackInSlot(0).isEmpty()) {
                        // Spawn 1-2 particles per tick to create a nice campfire effect
                        if (level.random.nextInt(2) == 0) {
                            double pX = ped.getBlockPos().getX() + 0.5 + (level.random.nextDouble() - 0.5) * 0.3;
                            double pY = ped.getBlockPos().getY() + 0.75 + (level.random.nextDouble() * 0.2);
                            double pZ = ped.getBlockPos().getZ() + 0.5 + (level.random.nextDouble() - 0.5) * 0.3;

                            level.addParticle(net.minecraft.core.particles.ParticleTypes.SOUL_FIRE_FLAME,
                                    pX, pY, pZ, 0, 0.05, 0);
                        }
                    }
                }
            }
            return; // Stop the client here!
        }

        // SERVER SIDE ONLY:
        if (this.visualTransferTimer > 0) {
            this.visualTransferTimer--;
        }

        // Do the actual math and crafting
        if (this.isCrafting) {
            if (this.level.getGameTime() % 10 == 0) {
                // Start with the items the laser already vaporized
                int intactItems = this.pedestalsConsumed;

                // Add the items still waiting safely on their pedestals
                for (SoulPedestalEntity ped : getNearbyPedestals()) {
                    if (!ped.inventory.getStackInSlot(0).isEmpty()) {
                        intactItems++;
                    }
                }

                // If the total is less than what the recipe requires, something was destroyed!
                if (intactItems < this.requiredPedestals) {
                    LOGGER.warn("Ritual interrupted! A pedestal or item was destroyed.");
                    this.cancelCrafting();
                    return; // Stop running the rest of the tick!
                }
            }
            // If we don't have enough souls, request them from the Core
            if (this.getSouls() < this.requiredSouls ) {

                if (this.transferTickCounter > 0) {
                    this.transferTickCounter--;
                } else {
                    int needed = this.requiredSouls - this.getSouls();

                    if (requestSoulsFromCore(needed)) {

                        if (this.requiredPedestals > 0) {
                            float soulPercentage = (float) this.getSouls() / (float) this.requiredSouls;
                            int expectedConsumed = (int) (soulPercentage * this.requiredPedestals);

                            // If we crossed a threshold, zap an item! (Using a while-loop in case we cross multiple thresholds at once)
                            while (expectedConsumed > this.pedestalsConsumed) {
                                boolean consumedThisLoop = false;

                                // Find the first available pedestal with an item
                                for (SoulPedestalEntity ped : getNearbyPedestals()) {
                                    if (!ped.inventory.getStackInSlot(0).isEmpty()) {
                                        ped.inventory.extractItem(0, 1, false); // Zap!
                                        this.pedestalsConsumed++;
                                        consumedThisLoop = true;


                                        if (level instanceof net.minecraft.server.level.ServerLevel serverLevel) {

                                            double pX = ped.getBlockPos().getX() + 0.5;
                                            double pY = ped.getBlockPos().getY() + 1.0;
                                            double pZ = ped.getBlockPos().getZ() + 0.5;

                                            serverLevel.sendParticles(ParticleTypes.SOUL_FIRE_FLAME,
                                                    pX, pY, pZ, 100, 0.3, 0.75, 0.3, 0.02);
                                            level.playSound(null,ped.getBlockPos(), SoundEvents.AMETHYST_CLUSTER_BREAK, SoundSource.BLOCKS,0.65f,0.35f);
                                            level.playSound(null,ped.getBlockPos(), SoundEvents.ENDER_EYE_DEATH, SoundSource.BLOCKS,0.25f,1.5f);
                                            level.playSound(null,ped.getBlockPos(), SoundEvents.CANDLE_EXTINGUISH, SoundSource.BLOCKS,0.25f,1f);

                                        }
                                        break; // Break the for-loop so we only zap one pedestal at a time
                                    }
                                }

                                // Safety check: if pedestals were empty, break the while-loop to prevent infinite freezing
                                if (!consumedThisLoop) break;
                            }
                        }
                    } else {
                        this.transferTickCounter = 20;
                    }

                }
            } else {
                // We have enough souls! Progress the crafting animation
                this.craftingProgress++;
                setChanged();

                if (this.craftingProgress >= this.maxCraftingTime) {
                    finishCrafting();
                }
            }
        }
    }

    private void finishCrafting() {
        if (this.cachedRecipe == null) {
            this.isCrafting = false;
            return;
        }

        // 1. Consume the Center Catalyst item
        this.inventory.extractItem(0, 1, false);
        this.setSouls(this.getSouls() - this.cachedRecipe.soulCost());

        // 2. Clear Pedestals
        for (SoulPedestalEntity pedestal : getNearbyPedestals()) {
            if (!pedestal.inventory.getStackInSlot(0).isEmpty()) {
                pedestal.inventory.extractItem(0, 1, false);
            }
        }

        // 3. Output the Result
        this.inventory.setStackInSlot(0, this.cachedResult.copy());

        // --- NEW: FINISH PARTICLES ---
        if (this.level instanceof ServerLevel serverLevel) {
            double pX = this.worldPosition.getX() + 0.5;
            double pY = this.worldPosition.getY() + 1.75; // Right where the levitating item is
            double pZ = this.worldPosition.getZ() + 0.5;

            // A huge burst of souls and flash particles!
            serverLevel.sendParticles(ParticleTypes.SOUL_FIRE_FLAME,
                    pX, pY, pZ, 75, 0.35, 0.35, 0.35, 0.25);
            serverLevel.sendParticles(tinySoulDust,
                    pX, pY, pZ, 150, 0.35, 0.35, 0.35, 0.25);

            level.playSound(null,worldPosition,SoundEvents.GENERIC_EXPLODE.value(),SoundSource.BLOCKS,0.15f,0.25f);
            level.playSound(null,worldPosition,SoundEvents.SCULK_SHRIEKER_SHRIEK,SoundSource.BLOCKS,0.35f,1.5f);
            level.playSound(null,worldPosition,SoundEvents.SCULK_SHRIEKER_SHRIEK,SoundSource.BLOCKS,0.25f,0.8f);
            level.playSound(null,worldPosition,SoundEvents.SCULK_SHRIEKER_SHRIEK,SoundSource.BLOCKS,0.15f,0.35f);
        }

        // 4. Reset the machine
        this.craftingProgress = 0;
        this.cachedRecipe = null;
        this.cachedResult = ItemStack.EMPTY;
        this.renderYOffset = 0;
        this.spinAngle = 2f;
        this.isCrafting = false;
        sync();
    }

    public void cancelCrafting() {
        this.isCrafting = false;
        this.craftingProgress = 0;
        this.cachedRecipe = null;
        this.cachedResult = ItemStack.EMPTY;
        this.pedestalsConsumed = 0;

        this.sync();

        if (this.level != null && !this.level.isClientSide()) {
            // Play a harsh fizzle/failure sound!
            this.level.playSound(null, this.worldPosition, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 0.35f, 0.5f);
            this.level.playSound(null, this.worldPosition, SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 0.5f, 1.5f);
        }
    }

    // Triggers when you press the "Craft" button in the UI
    public void startCrafting() {
        var recipe = getMatchingRecipe();
        if (recipe != null) {
            int totalNeeded = recipe.soulCost() - this.getSouls();

            // 1. Check if we have a valid linked core
            if (this.linkedCorePos == null || !(this.level.getBlockEntity(this.linkedCorePos) instanceof SoulCoreEntity core)) {
                LOGGER.warn("Cannot start craft: No valid Soul Core linked!");
                return;
            }

            // 2. Check if the core has enough total souls to finish the ENTIRE craft
            if (core.getSouls() < totalNeeded) {
                LOGGER.warn("Cannot start craft: Core only has {} souls, but recipe needs {} more!", core.getSouls(), totalNeeded);
                return; // STOP! Do not start the craft.
            }

            // 3. If we passed the check, start the craft safely!
            this.isCrafting = true;
            this.craftingProgress = 0;
            this.requiredSouls = recipe.soulCost();
            this.requiredPedestals = recipe.pedestalItems().size();
            this.pedestalsConsumed = 0;


            this.cachedRecipe = recipe;
            this.cachedResult = cachedRecipe.result().copy();

            LOGGER.info("Started crafting recipe. Required souls: {}", this.requiredSouls);
            sync();
        } else {
            LOGGER.warn("Tried to start crafting, but no valid recipe matched!");
        }
    }

    private List<SoulPedestalEntity> getNearbyPedestals() {
        List<SoulPedestalEntity> pedestals = new ArrayList<>();
        int radius = 2; // 5x5 area
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                if (x == 0 && z == 0) continue; // Skip the center block

                BlockPos checkPos = this.worldPosition.offset(x, 0, z);
                if (this.level.getBlockEntity(checkPos) instanceof SoulPedestalEntity pedestal) {
                    pedestals.add(pedestal);
                }
            }
        }
        return pedestals;
    }

    private SoulInfusionRecipe getMatchingRecipe() {
        if (this.level == null) return null;

        // 1. Get the center item (Catalyst)
        ItemStack centerItem = this.inventory.getStackInSlot(0);
        if (centerItem.isEmpty()) return null;

        // 2. Gather all the items currently sitting on nearby pedestals
        List<ItemStack> pedestalItems = new ArrayList<>();
        for (SoulPedestalEntity pedestal : getNearbyPedestals()) {
            ItemStack stack = pedestal.inventory.getStackInSlot(0);
            if (!stack.isEmpty()) {
                pedestalItems.add(stack); // Only add pedestals that actually have items!
            }
        }

        // 3. Ask the server for every registered Soul Infusion Recipe
        var recipeManager = this.level.getRecipeManager();
        var allRecipes = recipeManager.getAllRecipesFor(ModRecipes.SOUL_INFUSION_TYPE.get());

        // 4. Test each recipe to see if we have a winner
        for (var recipeHolder : allRecipes) {
            SoulInfusionRecipe recipe = recipeHolder.value();

            // A. Does the center item match the catalyst?
            if (!recipe.catalyst().test(centerItem)) continue;

            // B. Do we have the exact right number of pedestal items?
            if (recipe.pedestalItems().size() != pedestalItems.size()) continue;

            // C. Match the pedestal items (ignoring order!)
            boolean allMatch = true;

            // We make a copy of the recipe's ingredients so we can cross them off one by one
            List<Ingredient> remainingIngredients = new ArrayList<>(recipe.pedestalItems());

            for (ItemStack physicalItem : pedestalItems) {
                boolean foundMatchForThisItem = false;

                for (int i = 0; i < remainingIngredients.size(); i++) {
                    if (remainingIngredients.get(i).test(physicalItem)) {
                        remainingIngredients.remove(i); // Cross this ingredient off the list
                        foundMatchForThisItem = true;
                        break;
                    }
                }

                // If even one physical item doesn't belong in the recipe, it's a fail
                if (!foundMatchForThisItem) {
                    allMatch = false;
                    break;
                }
            }

            // If we found a match for every item, and no required ingredients are left over
            if (allMatch && remainingIngredients.isEmpty()) {
                return recipe;
            }
        }

        return null; // No recipes matched
    }

    @Override
    public TagKey<Item> getAllowedUpgradeTag() {
        return ModTags.Items.CRAFTER_UPGRADES;
    }


    @Override
    public Component getDisplayName() {
        return Component.translatable("block.shadowthings.soul_crafter"); // Title of your UI
    }


    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
        return new SoulCrafterMenu(id, playerInventory, this);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries); // Saves the souls!
        tag.put("inventory", inventory.serializeNBT(registries));
        tag.putBoolean("IsCrafting", this.isCrafting);
        tag.putInt("CraftingProgress", this.craftingProgress);
        tag.putInt("RequiredSouls", this.requiredSouls);
        tag.putInt("TransferTickCounter", this.transferTickCounter);
        tag.putInt("CurrentSouls",this.souls);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries); // Loads the souls!
        inventory.deserializeNBT(registries, tag.getCompound("inventory"));
        this.isCrafting = tag.getBoolean("IsCrafting");
        this.craftingProgress = tag.getInt("CraftingProgress");
        this.requiredSouls = tag.getInt("RequiredSouls");
        this.transferTickCounter = tag.getInt("TransferTickCounter");
        this.souls = tag.getInt("CurrentSouls");
    }

    public void sync() {
        if (this.level != null && !this.level.isClientSide()) {
            this.setChanged();
            this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
        }
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        saveAdditional(tag, registries);
        return tag;
    }
}