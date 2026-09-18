package com.shadow.shadowthings.block.entity;

import com.shadow.shadowthings.block.entity.base.AbstractSoulEntity;
import com.shadow.shadowthings.item.ModItems;
import com.shadow.shadowthings.screen.custom.SoulFurnaceMenu;
import com.shadow.shadowthings.sound.ModSounds;
import com.shadow.shadowthings.util.ModTags;
import com.shadow.shadowthings.util.UpgradeType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.Optional;

public class SoulFurnaceEntity extends AbstractSoulEntity implements MenuProvider {

    public boolean isFormed = false;

    // --- INVENTORY ---
    // Slot 0: Input Item, Slot 1: Output Item
    public final ItemStackHandler mainInventory = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (level != null && !level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return slot == 0; // Only allow items to be inserted into the Input slot!
        }
    };
    public final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> SoulFurnaceEntity.this.getSouls();
                case 1 -> SoulFurnaceEntity.this.getMaxSouls();
                case 2 -> SoulFurnaceEntity.this.progress;
                case 3 -> SoulFurnaceEntity.this.maxProgress;
                case 4 -> (int) SoulFurnaceEntity.this.momentum;
                case 5 -> (int) SoulFurnaceEntity.this.maxMomentum;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> SoulFurnaceEntity.this.setSouls(value);
                case 1 -> SoulFurnaceEntity.this.setMaxSouls(value);
                case 2 -> SoulFurnaceEntity.this.progress = value;
                case 3 -> SoulFurnaceEntity.this.maxProgress = value;
                case 4 -> SoulFurnaceEntity.this.momentum = value;
                case 5 -> SoulFurnaceEntity.this.maxMomentum = value;
            }
        }

        @Override
        public int getCount() {
            return 6; // Syncs 6 variables
        }
    };

    // --- MOMENTUM & SMELTING ---
    public float momentum = 0.0f;
    public float maxMomentum = 100.0f;
    public float momentumGainRate = 0.5f;
    public float momentumDecayRate = 0.2f;

    public int progress = 0;
    public int maxProgress = 200; // Base 10 seconds to smelt

    public float orbitAngle = 0.0f;
    public float prevOrbitAngle = 0.0f;

    public boolean isSmelting = false;

    public SoulFurnaceEntity(BlockPos pos, BlockState state) {
        // Starts with 0 souls, 10,000 max capacity, and a standard transfer rate
        super(ModBlockEntities.SOUL_FURNACE_BE.get(), pos, state, 0, 5000, 0, 0);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (!this.isFormed) return;

        // --- ORBIT & LAP DETECTION MATH ---
        this.prevOrbitAngle = this.orbitAngle;
        float normalizedMomentum = this.momentum / 100.0f;
        float currentSpeedMultiplier = Math.min(15.0f, 1.0f + (normalizedMomentum * normalizedMomentum * 1.5f));


        // --- EXPONENTIAL SUB-LAP TRIGGER MATH ---
        float normalizedMom = this.momentum / 500.0f;
        float exponentialFactor = normalizedMom * normalizedMom;

        // Scale triggers per lap: 1 trigger at low speed, up to 6 triggers per lap at max overload!
        int triggersPerLap = 1 + (int)(exponentialFactor * 5.0f);

        float twoPi = (float) (Math.PI * 2);
        float segmentSize = twoPi / triggersPerLap /2;
        float oldSegment = this.orbitAngle / segmentSize;

        // Advance the angle (using your existing exponential speed multiplier)
        this.orbitAngle += 0.05f * currentSpeedMultiplier;

        float newSegment = this.orbitAngle / segmentSize;

        // If we crossed a segment threshold, play the sound!
        // --- 3-TIER ORBIT SOUND LOGIC ---
        if ((int) oldSegment < (int) newSegment) {
            SoundEvent soundToPlay;
            float soundPitch;
            float soundVolume;
            float dynamicPitch = 0.25f + (exponentialFactor * 1.8f);
            float randomPitch = dynamicPitch * (float)(level.random.nextFloat() * 0.15);
            float currentSpeedPercent = momentum/maxMomentum;
            if (currentSpeedPercent < 0.25) {
                // Tier 1: Low / Standard (Heavy, deep mechanical hum)
                soundToPlay = ModSounds.SOUL_FURNACE_CRAFT.get(); // Or a base sound
                soundPitch = randomPitch - 0.25f;
                soundVolume = 0.10f;
            } else if (currentSpeedPercent < 0.5) {
                // Tier 2: Medium / Accelerated (Higher whir)
                soundToPlay = ModSounds.SOUL_FURNACE_CRAFT.get();
                soundPitch = randomPitch;
                soundVolume = 0.10f;
            } else if (currentSpeedPercent < 0.75){
                // Tier 3: Overload / Critical Mass (Screaming turbine)
                soundToPlay = ModSounds.SOUL_FURNACE_CRAFT.get(); // Or a separate intense sound event
                soundPitch = randomPitch + 0.25f;
                soundVolume = 0.10f;
            } else {
                soundToPlay = ModSounds.SOUL_FURNACE_CRAFT.get(); // Or a separate intense sound event
                soundPitch = randomPitch + 0.35f;
                soundVolume = 0.10f;
            }

            level.playSound(
                    null,
                    pos,
                    soundToPlay,
                    net.minecraft.sounds.SoundSource.BLOCKS,
                    soundVolume,
                    soundPitch
            );
        }


        if (level.isClientSide()) {
            this.tickClientVisuals();
            return;
        }

        if (this.getSouls() < this.maxSouls) {
            if (this.transferTickCounter > 0) {
                this.transferTickCounter--;
            } else {
                int needed = this.maxSouls - this.getSouls();
                if (requestSoulsFromCore(needed)) {
                } else {
                    this.transferTickCounter = 100;
                }
            }
        }

        if (level.getGameTime() % 10 == 0) {
            tickAutoIO(level, pos);
        }
        ItemStack inputStack = mainInventory.getStackInSlot(0);

        // 1. Check if we have an item and enough souls to run
        if (!inputStack.isEmpty() && this.getSouls() > 0) {

            // Wrap our input in a vanilla container to ask the RecipeManager if it can be smelted
            SingleRecipeInput inventoryWrapper = new SingleRecipeInput(inputStack);
            Optional<RecipeHolder<SmeltingRecipe>> match = level.getRecipeManager()
                    .getRecipeFor(RecipeType.SMELTING, inventoryWrapper, level);

            if (match.isPresent()) {
                ItemStack result = match.get().value().getResultItem(level.registryAccess());
                ItemStack outputSlot = mainInventory.getStackInSlot(1);

                // Ensure the output slot is empty, or matches the result and isn't full
                if (outputSlot.isEmpty() || (outputSlot.is(result.getItem()) && outputSlot.getCount() + result.getCount() <= outputSlot.getMaxStackSize())) {
                    isSmelting = true;

                    // --- MOMENTUM MATH ---
                    // Increase momentum
                    this.momentum = Math.min(this.momentum + this.momentumGainRate, this.maxMomentum);

                    // Calculate speed: At 0 momentum = 1x speed. At 100 momentum = 6x speed!
                    int speedMultiplier = 1 + (int)(this.momentum / 20.0f);
                    this.progress += speedMultiplier;

                    // Calculate soul cost: Base 5 per tick, reduces to 1 per tick at max momentum!
                    int soulCost = Math.max(1, 5 - (int)(this.momentum / 25.0f));
                    this.removeSouls(soulCost);

                    // --- CRAFTING ---
                    if (this.progress >= this.maxProgress) {
                        this.progress = 0;
                        mainInventory.extractItem(0, 1, false); // Consume 1 input

                        if (outputSlot.isEmpty()) {
                            mainInventory.setStackInSlot(1, result.copy());
                        } else {
                            outputSlot.grow(result.getCount());
                        }
                    }
                }
            }
        }

        // 2. If we aren't actively smelting, momentum decays!
        if (!isSmelting) {
            this.progress = 0;
            if (this.momentum > 0) {
                // --- DYNAMIC DECAY MATH ---
                float dynamicMultiplier = 0.15f + (this.momentum / 50.0f);
                float actualDecay = this.momentumDecayRate * dynamicMultiplier;

                this.momentum = Math.max(0, this.momentum - actualDecay);
            }
        }

        // 3. Sync to client so the renderer knows how fast to spin the crystal
        // We only send updates every 5 ticks to avoid flooding the network, unless momentum is 0 or Max
        if (level.getGameTime() % 5 == 0 || this.momentum == 0 || this.momentum == this.maxMomentum) {
            this.setChanged();
            level.sendBlockUpdated(pos, state, state, 3);
        }
    }

    private void tickAutoIO(Level level, BlockPos pos) {
        // 1. SCALE SPEED WITH UPGRADES:
        // Base interval is 10 ticks. Each Speed upgrade reduces it by 2 ticks (down to a minimum of 2 ticks).
        int stackTier = this.getUpgradeLevel(UpgradeType.SOUL_SMELT_SPEED);
        int checkInterval = 10;

        if (level.getGameTime() % checkInterval != 0) return;

        // Check all 6 directions around the controller block
        for (Direction direction : Direction.values()) {
            BlockPos targetPos = pos.relative(direction);

            IItemHandler adjacentHandler = level.getCapability(Capabilities.ItemHandler.BLOCK, targetPos, direction.getOpposite());
            if (adjacentHandler == null) continue;

            // --- 2. PUSH OUTPUT (Slot 1 -> Adjacent Inventory) ---
            ItemStack outputStack = mainInventory.getStackInSlot(1);
            if (!outputStack.isEmpty()) {
                for (int i = 0; i < adjacentHandler.getSlots(); i++) {
                    ItemStack remainder = adjacentHandler.insertItem(i, outputStack, false);
                    if (remainder.getCount() < outputStack.getCount()) {
                        mainInventory.setStackInSlot(1, remainder);
                        setChanged();
                        level.sendBlockUpdated(pos, getBlockState(), getBlockState(), 3);
                        break;
                    }
                }
            }

            // --- 3. PULL INPUT (Adjacent Inventory -> Slot 0) ---
            ItemStack inputSlot = mainInventory.getStackInSlot(0);
            int maxStackSize = 64; // Default max stack size fallback

            // Check if input slot has room for more items
            if (inputSlot.isEmpty() || inputSlot.getCount() < maxStackSize) {
                for (int i = 0; i < adjacentHandler.getSlots(); i++) {
                    // Simulate extraction to check what the item is
                    ItemStack candidate = adjacentHandler.extractItem(i, maxStackSize, true);

                    if (!candidate.isEmpty()) {
                        // Verify if it can be smelted by the furnace
                        SingleRecipeInput inventoryWrapper = new SingleRecipeInput(candidate);
                        boolean canSmelt = level.getRecipeManager()
                                .getRecipeFor(RecipeType.SMELTING, inventoryWrapper, level).isPresent();

                        if (canSmelt) {
                            // If slot already has items, make sure the candidate matches the exact same item type
                            if (!inputSlot.isEmpty() && !ItemStack.isSameItemSameComponents(inputSlot, candidate)) {
                                continue;
                            }

                            // Calculate exact space available in the slot
                            int upgradeBatchLimit = Math.max(1, stackTier * 16);
                            int spaceLeft = inputSlot.isEmpty() ? candidate.getMaxStackSize() : inputSlot.getMaxStackSize() - inputSlot.getCount();
                            int extractAmount = Math.min(candidate.getCount(), Math.min(spaceLeft, upgradeBatchLimit));
                            // Perform the actual extraction for the full batch size
                            ItemStack realExtracted = adjacentHandler.extractItem(i, extractAmount, false);
                            if (!realExtracted.isEmpty()) {
                                if (inputSlot.isEmpty()) {
                                    mainInventory.setStackInSlot(0, realExtracted);
                                } else {
                                    inputSlot.grow(realExtracted.getCount());
                                }
                                setChanged();
                                level.sendBlockUpdated(pos, getBlockState(), getBlockState(), 3);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void onUpgradesChanged() {
        super.onUpgradesChanged();
        if (this.level != null && !this.level.isClientSide()) {
            int speedTier = this.getUpgradeLevel(UpgradeType.SOUL_SMELT_SPEED);
            int efficiencyTier = this.getUpgradeLevel(UpgradeType.SOUL_USAGE_EFFICIENCY);

            if (this.hasUpgrade(ModItems.SOUL_UPGRADE_OVERLOAD.get())) {
                this.maxMomentum = 750.0f; // Insane max speed
                this.momentumGainRate = 3.0f; // Revs up instantly
                this.momentumDecayRate = 0.25f; // Cools down quickly without efficiency!
            } else {
                this.maxMomentum = 100.0f + (speedTier * 50.0f);
                this.momentumGainRate = 0.5f + (speedTier * 0.25f);

                // Math.max guarantees the decay rate never drops below 0.02, even with max upgrades!
                this.momentumDecayRate = Math.max(0.02f, 0.2f - (efficiencyTier * 0.04f));
            }

            // Force momentum down if we remove an upgrade and max capacity shrinks
            if (this.momentum > this.maxMomentum) {
                this.momentum = this.maxMomentum;
                this.setChanged();
                this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
            }
        }
    }

    @Override
    public TagKey<Item> getAllowedUpgradeTag() {
        return ModTags.Items.FURNACE_UPGRADES; // Make sure to create this JSON tag!
    }

    // --- SAVING AND SYNCING ---
    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putBoolean("IsFormed", this.isFormed);
        tag.putFloat("Momentum", this.momentum);
        tag.putFloat("MaxMomentum", this.maxMomentum);
        tag.putInt("Progress", this.progress);
        tag.put("MainInventory", mainInventory.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.isFormed = tag.getBoolean("IsFormed");
        this.momentum = tag.getFloat("Momentum");
        this.maxMomentum = tag.getFloat("MaxMomentum");
        this.progress = tag.getInt("Progress");
        mainInventory.deserializeNBT(registries, tag.getCompound("MainInventory"));
    }
    @Override
    public Component getDisplayName() {
        return Component.literal("Soul Furnace");
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new SoulFurnaceMenu(containerId, playerInventory, this, this.data);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        tag.putBoolean("IsFormed", this.isFormed);
        tag.putFloat("Momentum", this.momentum);
        tag.put("MainInventory", mainInventory.serializeNBT(registries)); // Syncs items for the floating renderer!
        return tag;
    }
}