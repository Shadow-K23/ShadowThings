package com.shadow.shadowthings.block.entity;

import com.shadow.shadowthings.block.entity.base.AbstractSoulEntity;
import com.shadow.shadowthings.screen.custom.SoulCrucibleMenu;
import com.shadow.shadowthings.util.ModTags;
import com.shadow.shadowthings.util.UpgradeType;
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

public class SoulCrucibleEntity extends AbstractSoulEntity implements MenuProvider {

    // --- BURNING & GENERATION MECHANICS ---
    public int progress = 0;
    public int maxProgress = 60;

    public int burnTime = 0;
    public int totalBurnTime = 0;

    private final int baseSoulsPerCycle = 10;
    private int networkTickCounter = 0;

    int efficiencyUpgradeTier = 0;
    int speedUpgradeTier = 0;
    int amountUpgradeTier = 0;

    // --- INVENTORY ---
    public final ItemStackHandler mainInventory = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (level != null && !level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    // Zwiększono do 6, aby zsynchronizować burnTime z GUI (płomykiem)
    public final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> SoulCrucibleEntity.this.getSouls();
                case 1 -> SoulCrucibleEntity.this.getMaxSouls();
                case 2 -> SoulCrucibleEntity.this.progress;
                case 3 -> SoulCrucibleEntity.this.maxProgress;
                case 4 -> SoulCrucibleEntity.this.burnTime;
                case 5 -> SoulCrucibleEntity.this.totalBurnTime;
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
                case 4 -> SoulCrucibleEntity.this.burnTime = value;
                case 5 -> SoulCrucibleEntity.this.totalBurnTime = value;
            }
        }
        @Override
        public int getCount() { return 6; }
    };

    public SoulCrucibleEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SOUL_CRUCIBLE_BE.get(), pos, state, 0, 2500, 0, 0);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (level.isClientSide()) return;
        boolean isDirty = false;


        if (this.burnTime > 0) {
            this.burnTime--;
            isDirty = true;
        }


        if (this.burnTime <= 0 && this.souls < this.maxSouls) {
            ItemStack inputStack = mainInventory.getStackInSlot(0);
            int rawBurnTime = getFuelBurnTime(inputStack);

            if (rawBurnTime > 0) {

                this.burnTime = (int) (rawBurnTime * getEfficiencyModifier());
                this.totalBurnTime = this.burnTime;

                mainInventory.extractItem(0, 1, false);
                isDirty = true;
            }
        }

        if (this.burnTime > 0) {
            if (this.souls < this.maxSouls) {

                this.progress += getSpeedModifier();

                if (this.progress >= this.maxProgress) {

                    int soulsToGenerate = (int) (this.baseSoulsPerCycle * getStackModifier());
                    this.souls = Math.min(this.souls + soulsToGenerate, this.maxSouls);

                    this.progress = 0;
                }
                isDirty = true;
            }
        } else {
            if (this.progress > 0) {
                this.progress = Math.max(0, this.progress - 2);
                isDirty = true;
            }
        }

        if (isDirty) {
            this.setChanged();
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
                        this.setLinkedCorePos(null);
                        this.setChanged();
                    }
                }
            }
        }
    }

    // --- UPGRADES) ---

    private int getSpeedModifier() {
        return 1 + speedUpgradeTier;
    }

    private float getStackModifier() {
        return 1.0f + (amountUpgradeTier * 1.0f);
    }

    private float getEfficiencyModifier() {
        return 1.0f + (efficiencyUpgradeTier * 0.25f);
    }

    // --- FUEL VALUES ---
    private int getFuelBurnTime(ItemStack stack) {
        if (stack.is(Items.ROTTEN_FLESH)) return 200;
        if (stack.is(Items.BONE)) return 300;
        if (stack.is(Items.SPIDER_EYE)) return 400;
        if (stack.is(Items.SOUL_SAND) || stack.is(Items.SOUL_SOIL)) return 800;
        return 0;
    }

    @Override
    public TagKey<Item> getAllowedUpgradeTag() {
        return ModTags.Items.CRUCIBLE_UPGRADES;
    }

    @Override
    protected void onUpgradesChanged() {
        super.onUpgradesChanged();
        this.efficiencyUpgradeTier = this.getUpgradeLevel(UpgradeType.SOUL_USAGE_EFFICIENCY);
        this.amountUpgradeTier = this.getUpgradeLevel(UpgradeType.SOUL_SMELT_AMOUNT);
        this.speedUpgradeTier = this.getUpgradeLevel(UpgradeType.SOUL_SMELT_SPEED);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("Progress", this.progress);
        tag.putInt("BurnTime", this.burnTime);
        tag.putInt("TotalBurnTime", this.totalBurnTime);
        tag.putInt("SpeedTier", this.speedUpgradeTier);
        tag.putInt("EfficiencyTier", this.efficiencyUpgradeTier);
        tag.putInt("AmountTier", this.amountUpgradeTier);
        tag.put("MainInventory", mainInventory.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.progress = tag.getInt("Progress");
        this.burnTime = tag.getInt("BurnTime");
        this.totalBurnTime = tag.getInt("TotalBurnTime");
        this.speedUpgradeTier = tag.getInt("SpeedTier");
        this.efficiencyUpgradeTier = tag.getInt("EfficiencyTier");
        this.amountUpgradeTier = tag.getInt("AmountTier");
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