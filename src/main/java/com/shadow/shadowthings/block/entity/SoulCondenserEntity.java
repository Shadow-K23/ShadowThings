package com.shadow.shadowthings.block.entity;

import com.shadow.shadowthings.block.entity.base.AbstractSoulEntity;
import com.shadow.shadowthings.world.SoulCoreData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;

import java.util.UUID;

public class SoulCondenserEntity extends AbstractSoulEntity {

    public UUID ownerUUID;
    private int tickCounter = 0;

    public SoulCondenserEntity(BlockPos pos, BlockState state) {
        // Starts with 0 souls, 1000 capacity, 0 transfer in, 100 transfer out
        super(ModBlockEntities.SOUL_CONDENSER_BE.get(), pos, state, 0, 1000, 0, 0);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (level.isClientSide()) return;

        this.tickCounter++;

        // Run generation math once every second (30 ticks) to save server performance
        if (this.tickCounter >= 30) {
            this.tickCounter = 0;

            // 1. BIOME CHECK & GENERATION
            int generationAmount = 3; // Base rate: 3 soul per second

            Holder<Biome> currentBiome = level.getBiome(pos);

            if (currentBiome.unwrapKey().map(key -> key.location().getPath().equals("shadowwood_forest")).orElse(false)) {
                generationAmount = 15; // 15x Bonus for custom biome!
            }

            // 2. STORE SOULS
            if (this.souls < this.maxSouls) {
                this.souls = Math.min(this.souls + generationAmount, this.maxSouls);
                this.setChanged();
            }

            // 3. WIRELESS TRANSMISSION TO CORE
            if (this.ownerUUID != null && this.souls > 0 && level instanceof ServerLevel serverLevel) {
                SoulCoreData data = SoulCoreData.get(serverLevel);

                // Assuming your SoulCoreData has a getter like this. If not, you'll need to add one to SoulCoreData!
                BlockPos corePos = data.getCorePosition(this.ownerUUID);

                if (corePos != null && level.getBlockEntity(corePos) instanceof SoulCoreEntity core) {
                    int spaceInCore = core.getMaxSouls() - core.getSouls();

                    if (spaceInCore > 0) {
                        // Push a batch of souls (up to 50 per second)
                        int amountToPush = Math.min(this.souls, Math.min(spaceInCore, 50));

                        core.addSouls(amountToPush);
                        this.souls -= amountToPush;
                        this.setChanged();
                    }
                }
            }
        }
    }

    @Override
    public TagKey<Item> getAllowedUpgradeTag() {
        return null;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (this.ownerUUID != null) {
            tag.putUUID("OwnerUUID", this.ownerUUID);
        }
        tag.putInt("StoredSouls",this.souls);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.hasUUID("OwnerUUID")) {
            this.ownerUUID = tag.getUUID("OwnerUUID");
        }
        this.souls = tag.getInt("StoredSouls");
    }
}