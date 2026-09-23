package com.shadow.shadowthings.block.entity;

import com.shadow.shadowthings.block.entity.base.AbstractSoulEntity;
import com.shadow.shadowthings.screen.custom.SoulCrucibleMenu;
import com.shadow.shadowthings.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.UUID;

public class SoulCrucibleEntity extends AbstractSoulEntity implements MenuProvider {

    // --- BURNING MECHANICS ---
    public int progress = 0;
    public int maxProgress = 60; // Takes 3 seconds (60 ticks) to burn 1 item
    public int currentYield = 0; // How many souls the currently burning item will give
    private int networkTickCounter = 0;

    // --- INVENTORY ---
    // Slot 0: Input Item
    public final ItemStackHandler mainInventory = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (level != null && !level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    public final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> SoulCrucibleEntity.this.getSouls();
                case 1 -> SoulCrucibleEntity.this.getMaxSouls();
                case 2 -> SoulCrucibleEntity.this.progress;
                case 3 -> SoulCrucibleEntity.this.maxProgress;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> SoulCrucibleEntity.this.setSouls(value);
                case 1 -> SoulCrucibleEntity.this.setMaxSouls(value);
                case 2 -> SoulCrucibleEntity.this.progress = value;
                case 3 -> SoulCrucibleEntity.this.maxProgress = value;
            }
        }
        @Override
        public int getCount() { return 4; }
    };

    public SoulCrucibleEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SOUL_CRUCIBLE_BE.get(), pos, state, 0, 2500, 0, 0);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (level.isClientSide()) return;

        // --- 1. BURN ITEMS ---
        if (this.currentYield > 0) {
            // An item is currently being burned
            this.progress++;
            if (this.progress >= this.maxProgress) {
                this.souls = Math.min(this.souls + this.currentYield, this.maxSouls);
                this.progress = 0;
                this.currentYield = 0;
                this.setChanged();
            }
        } else {
            // Not burning anything. Check if we have valid items and room in the tank!
            ItemStack inputStack = mainInventory.getStackInSlot(0);
            if (!inputStack.isEmpty() && this.souls < this.maxSouls) {
                int yieldAmount = getSoulYield(inputStack);

                if (yieldAmount > 0) {
                    this.currentYield = yieldAmount;
                    mainInventory.extractItem(0, 1, false); // Consume 1 item
                    this.setChanged();
                }
            }
        }

        // --- TRANSMISSION TO CORE ---
        this.networkTickCounter++;
        if (this.networkTickCounter >= 20) {
            this.networkTickCounter = 0;

            BlockPos targetCore = this.getLinkedCorePos();

            if (targetCore != null && this.souls > 0 && level instanceof ServerLevel) {
                if (level.isLoaded(targetCore)) {
                    if (level.getBlockEntity(targetCore) instanceof SoulCoreEntity core) {
                        int spaceInCore = core.getMaxSouls() - core.getSouls();
                        if (spaceInCore > 0) {
                            int amountToPush = Math.min(this.souls, Math.min(spaceInCore, 150));
                            core.addSouls(amountToPush);
                            this.removeSouls(amountToPush);
                            this.setChanged();
                        }
                    } else {
                        // Core is missing/broken, clear it
                        this.setLinkedCorePos(null);
                        this.setChanged();
                    }
                }
            }
        }
    }

    // --- ITEM VALUES ---
    private int getSoulYield(ItemStack stack) {
        if (stack.is(Items.ROTTEN_FLESH)) return 25;
        if (stack.is(Items.BONE)) return 35;
        if (stack.is(Items.SPIDER_EYE)) return 50;
        if (stack.is(Items.SOUL_SAND) || stack.is(Items.SOUL_SOIL)) return 75;

        return 0; // Not a valid soul fuel
    }

    @Override
    public TagKey<Item> getAllowedUpgradeTag() {
        return ModTags.Items.CRUCIBLE_UPGRADES;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("Progress", this.progress);
        tag.putInt("CurrentYield", this.currentYield);
        tag.put("MainInventory", mainInventory.serializeNBT(registries));

    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.progress = tag.getInt("Progress");
        this.currentYield = tag.getInt("CurrentYield");
        mainInventory.deserializeNBT(registries, tag.getCompound("MainInventory"));

    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.shadowthings.soul_crucible");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new SoulCrucibleMenu(containerId, playerInventory, this, this.data);
    }
}