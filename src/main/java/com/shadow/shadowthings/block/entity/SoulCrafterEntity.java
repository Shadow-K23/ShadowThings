package com.shadow.shadowthings.block.entity;

import com.mojang.logging.LogUtils;
import com.shadow.shadowthings.block.entity.base.AbstractSoulEntity;
import com.shadow.shadowthings.recipe.ModRecipes;
import com.shadow.shadowthings.recipe.SoulInfusionRecipe;
import com.shadow.shadowthings.screen.custom.SoulCrafterMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
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
            sync(); // Uses the helper method from AbstractSoulEntity!
        }
    };

    // --- CRAFTING ANIMATION VARIABLES ---
    public boolean isCrafting = false;
    public int craftingProgress = 0;
    public int requiredSouls = 0;
    private int transferTickCounter = 0; // Tracks the delay from transferRate
    public int maxCraftingTime = 100; // 5 seconds (20 ticks * 5)


    public SoulCrafterEntity(BlockPos pos, BlockState state) {
        // Passes the BlockEntity type, position, state, and a Max Soul capacity of 10,000!
        super(ModBlockEntities.SOUL_CRAFTER_BE.get(), pos, state,0, 10000, 0 ,0);
    }



    public void tick(Level level, BlockPos pos, BlockState state) {
        if (level.isClientSide()) return; // Server-side only

        if (this.isCrafting) {
            // 1. If we don't have enough souls, request them from the Core
            if (this.getSouls() < this.requiredSouls) {
                if (this.transferTickCounter > 0) {
                    this.transferTickCounter--;
                } else {
                    int needed = this.requiredSouls - this.getSouls();
                    LOGGER.info("Soul Crafter at {} requesting {} souls from core...", this.worldPosition, needed);

                    // Call the request method from AbstractSoulEntity
                    if (requestSoulsFromCore(needed)) {
                        LOGGER.info("Transfer successful! Crafter now has {} souls.", this.getSouls());
                        this.transferTickCounter = this.getTransferRate();
                    } else {
                        LOGGER.warn("Soul request failed! Check if core is linked, in range, and has enough souls.");
                    }
                }
            } else {
                // 2. We have enough souls, progress the crafting animation!
                this.craftingProgress++;
                setChanged();

                if (this.craftingProgress >= this.maxCraftingTime) {
                    finishCrafting();
                }
            }
        }
    }

    private void finishCrafting() {
        // 1. Get the current recipe (we will write the matching logic next)
        var recipe = getMatchingRecipe();
        if (recipe == null) {
            this.isCrafting = false;
            return;
        }

        // 2. Consume the Catalyst and Soul Mana
        this.inventory.extractItem(0, 1, false);
        this.setSouls(this.getSouls() - recipe.soulCost());

        // 3. Clear the Pedestals
        for (SoulPedestalEntity pedestal : getNearbyPedestals()) {
            if (!pedestal.inventory.getStackInSlot(0).isEmpty()) {
                pedestal.inventory.extractItem(0, 1, false);
            }
        }

        // 4. Output the Result
        this.inventory.setStackInSlot(0, recipe.result().copy());

        // 5. Reset the machine
        this.isCrafting = false;
        this.craftingProgress = 0;
        sync();
    }


    // Triggers when you press the "Craft" button in the UI
    public void startCrafting() {
        var recipe = getMatchingRecipe();
        if (recipe != null) {
            this.isCrafting = true;
            this.craftingProgress = 0;
            this.requiredSouls = recipe.soulCost(); // Lock in the cost!
            LOGGER.info("Started crafting recipe. Required souls set to: {}", this.requiredSouls);
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
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries); // Loads the souls!
        inventory.deserializeNBT(registries, tag.getCompound("inventory"));
        this.isCrafting = tag.getBoolean("IsCrafting");
        this.craftingProgress = tag.getInt("CraftingProgress");
        this.requiredSouls = tag.getInt("RequiredSouls");
        this.transferTickCounter = tag.getInt("TransferTickCounter");
    }
}