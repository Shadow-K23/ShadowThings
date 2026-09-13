package com.shadow.shadowthings.block.entity.base;

import com.mojang.logging.LogUtils;
import com.shadow.shadowthings.block.entity.SoulCoreEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.logging.log4j.core.jmx.Server;
import org.slf4j.Logger;

public abstract class AbstractSoulEntity extends BlockEntity {
    protected int souls;
    protected int maxSouls;

    protected boolean soulTransferEnabled = true;
    protected int transferRate = 0; //Amount of ticks between transfers
    protected int transferAmount = 0;
    protected int transferTickCounter = 0;
    protected BlockPos linkedCorePos = null;

    public AbstractSoulEntity(BlockEntityType<?> type, BlockPos pos, BlockState state,int souls, int maxSouls, int transferRate, int transferAmount) {
        super(type, pos, state);
        this.souls = souls;
        this.maxSouls = maxSouls;
        this.transferRate = transferRate;
        this.transferAmount = transferAmount;
    }

    public int getSouls() { return souls; }
    public int getMaxSouls() { return maxSouls; }
    public int getTransferRate() {return transferRate;}
    public int getTransferAmount() {return transferAmount;}

    // Paste all your soul math methods here!
    public void setSouls(int amount) {
        this.souls = Math.clamp(amount, 0, this.maxSouls);
        sync();
    }

    public void addSouls(int amount) {
        this.souls = Math.clamp(this.souls + amount, 0, this.maxSouls);
        sync();
    }

    public void removeSouls(int amount) {
        this.souls = Math.clamp(this.souls - amount, 0, this.maxSouls);
        sync();
    }

    public void setMaxSouls(int amount) {
        this.maxSouls = amount;
        if (this.souls > amount) this.souls = amount;
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


    public boolean requestSoulsFromCore(int requestedAmount) {
        if (this.level == null || this.level.isClientSide()) return false;

        // If the machine hasn't been linked to a core yet, fail early
        if (this.linkedCorePos == null) return false;

        // Check if the linked block is actually a Soul Core
        if (this.level.getBlockEntity(this.linkedCorePos) instanceof SoulCoreEntity core) {

            // Verify it's still within range
            if (this.worldPosition.distSqr(this.linkedCorePos) > (core.getCoreRadius() * core.getCoreRadius())) {
                return false;
            }

            // STRICTLY use the Core's transfer amount for this packet
            int amountToRequest = Math.min(requestedAmount, core.getTransferAmount());

            if (core.getSouls() >= amountToRequest) {
                core.removeSouls(amountToRequest);
                this.addSouls(amountToRequest);

                // Set our cooldown using the Core's transfer rate!
                this.transferTickCounter = core.getTransferRate();
                return true;
            }
        } else {
            // Core was broken/removed, clear the dead link
            this.linkedCorePos = null;
        }

        return false;
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

    // Update your saveAdditional and loadAdditional methods in AbstractSoulEntity:
    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (linkedCorePos != null) {
            tag.putLong("LinkedCorePos", linkedCorePos.asLong());
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("LinkedCorePos")) {
            linkedCorePos = BlockPos.of(tag.getLong("LinkedCorePos"));
        }
    }
}