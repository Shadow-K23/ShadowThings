package com.shadow.shadowthings.block.entity.base;

import com.mojang.logging.LogUtils;
import com.shadow.shadowthings.block.entity.ModBlockEntities;
import com.shadow.shadowthings.block.entity.SoulCoreEntity;
import com.shadow.shadowthings.item.custom.SoulUpgradeItem;
import com.shadow.shadowthings.util.UpgradeType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.apache.logging.log4j.core.jmx.Server;
import org.slf4j.Logger;

import javax.annotation.Nullable;

public abstract class AbstractSoulEntity extends BlockEntity {
    protected int souls;
    protected int maxSouls;

    protected boolean soulTransferEnabled = true;
    protected int transferRate = 0; //Amount of ticks between transfers
    protected int transferAmount = 0;
    protected int transferTickCounter = 0;
    protected BlockPos linkedCorePos = null;

    protected int visualTransferTimer = 0; // Tracks how long to spawn particles



    public abstract TagKey<Item> getAllowedUpgradeTag();

    // The Universal Upgrade Inventory (Available to all machines)
    public final ItemStackHandler upgradeInventory = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            // 2. Call our new trigger method!
            onUpgradesChanged();

            if (level != null && !level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }

        }
        @Override
        public int getSlotLimit(int slot) {
            return 1; // Forces a maximum of 1 item per slot for ALL inherited blocks
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            // 1. TAG CHECK: Must be allowed in this specific machine type
            if (!stack.is(getAllowedUpgradeTag())) {
                return false;
            }

            // 2. DUPLICATE TYPE CHECK: Prevent stacking the same upgrade type
            if (stack.getItem() instanceof SoulUpgradeItem newUpgrade) {

                for (int i = 0; i < getSlots(); i++) {
                    if (i == slot) continue; // Skip the slot we are actively clicking on

                    ItemStack existingStack = getStackInSlot(i);
                    if (existingStack.getItem() instanceof SoulUpgradeItem existingUpgrade) {
                        // If the types match (e.g., both are SPEED), reject the new one!
                        if (existingUpgrade.getUpgradeType() == newUpgrade.getUpgradeType()) {
                            return false;
                        }
                    }
                }
            }
            return true; // Passed all checks, let it in!
        }
    };

    public AbstractSoulEntity(BlockEntityType<?> type, BlockPos pos, BlockState state,int souls, int maxSouls, int transferRate, int transferAmount) {
        super(type, pos, state);
        this.souls = souls;
        this.maxSouls = maxSouls;
        this.transferRate = transferRate;
        this.transferAmount = transferAmount;

    }

    public AABB getRenderBoundingBox() {
        return AABB.INFINITE;
    }

    public int getSouls() { return souls; }
    public int getMaxSouls() { return maxSouls; }
    public int getTransferRate() {return transferRate;}
    public int getTransferAmount() {return transferAmount;}

    // Paste all your soul math methods here!
    public void setSouls(int amount) {
        this.souls = Math.max( 0, amount);
        sync();
    }

    public void addSouls(int amount) {
        if (this.souls < this.maxSouls) {
            this.souls = Math.min(this.souls + amount, this.maxSouls);
        }
        sync();
    }

    public void removeSouls(int amount) {
        this.souls = Math.max(this.souls - amount, 0);
        sync();
    }

    public void setMaxSouls(int amount) {
        this.maxSouls = amount;
        sync();
    }

    public void addMaxSouls(int amount) {
        this.maxSouls += amount;
        sync();
    }

    public void removeMaxSouls(int amount) {
        this.maxSouls = Math.clamp(this.maxSouls - amount, 0, this.maxSouls);
        sync();
    }


    //UPGRADES LOGIC

    public boolean hasUpgrade(Item upgradeItem) {
        for (int i = 0; i < upgradeInventory.getSlots(); i++) {
            if (upgradeInventory.getStackInSlot(i).is(upgradeItem)) {
                return true;
            }
        }
        return false;
    }

    public int getUpgradeLevel(UpgradeType typeToFind) {
        int highestTier = 0; // If we want they to overwrite each other (max)
        // OR: int totalTier = 0; // If you want players to stack multiple Tier 1s together!

        for (int i = 0; i < upgradeInventory.getSlots(); i++) {
            ItemStack stack = upgradeInventory.getStackInSlot(i);

            // Check if the item in the slot is our custom Upgrade item
            if (stack.getItem() instanceof SoulUpgradeItem upgradeItem) {

                // Does it match the type we are looking for?
                if (upgradeItem.getUpgradeType() == typeToFind) {
                    highestTier = Math.max(highestTier, upgradeItem.getTier());
                }
            }
        }
        return highestTier;
    }

    public void dropAllUpgrades() {
        if (this.level != null && !this.level.isClientSide()) {
            // Drop upgrades
            for (int i = 0; i < this.upgradeInventory.getSlots(); i++) {
                ItemStack stack = this.upgradeInventory.getStackInSlot(i);
                if (!stack.isEmpty()) {
                    net.minecraft.world.Containers.dropItemStack(this.level, this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), stack);
                }
            }
        }
    }

    public boolean requestSoulsFromCore(int requestedAmount) {
        if (this.level == null || this.level.isClientSide()) return false;

        if (this.linkedCorePos == null) return false;

        if (this.level.getBlockEntity(this.linkedCorePos) instanceof SoulCoreEntity core) {

            if (this.worldPosition.distSqr(this.linkedCorePos) > (core.getCoreRadius() * core.getCoreRadius())) {
                return false;
            }

            // 1. Get the standard chunk request
            int amountToRequest = Math.min(requestedAmount, core.getTransferAmount());

            // 2. FIX: Clamp it down to whatever the core actually has left!
            amountToRequest = Math.min(amountToRequest, core.getSouls());

            // 3. If the core has anything left to give, drain it!
            if (amountToRequest > 0) {
                core.removeSouls(amountToRequest);
                this.addSouls(amountToRequest);

                this.transferTickCounter = core.getTransferRate();
                this.visualTransferTimer = core.getTransferRate();
                this.sync();

                return true;
            }
        } else {
            this.linkedCorePos = null;
        }

        return false;
    }

    public void tickClientVisuals() {
        if (this.level == null || !this.level.isClientSide()) return;
    }

    // A handy helper method so you don't repeat the sync logic 6 times!
    protected void sync() {
        this.setChanged();
        if (this.level != null && !this.level.isClientSide()) {
            this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
        }
    }

    public BlockPos getLinkedCorePos() { return linkedCorePos; }
    public void setLinkedCorePos(BlockPos pos) {
        this.linkedCorePos = pos;
        sync();
    }

    protected void onUpgradesChanged() {
        // Does nothing by default, but machines can override it!
    }

    // Update your saveAdditional and loadAdditional methods in AbstractSoulEntity:
    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("Souls", this.souls);
        tag.putInt("MaxSouls", this.maxSouls);
        if (linkedCorePos != null) {
            tag.putLong("LinkedCorePos", linkedCorePos.asLong());
        }
        tag.putInt("VisualTimer", this.visualTransferTimer); // ADD THIS
        tag.put("UpgradeInventory", upgradeInventory.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.souls = tag.getInt("Souls");
        this.maxSouls = tag.getInt("MaxSouls");
        if (tag.contains("LinkedCorePos")) {
            linkedCorePos = BlockPos.of(tag.getLong("LinkedCorePos"));
        }
        this.visualTransferTimer = tag.getInt("VisualTimer"); // ADD THIS
        upgradeInventory.deserializeNBT(registries, tag.getCompound("UpgradeInventory"));
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