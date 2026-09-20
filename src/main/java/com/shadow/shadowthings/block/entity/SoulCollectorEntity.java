package com.shadow.shadowthings.block.entity;

import com.shadow.shadowthings.block.entity.base.AbstractSoulEntity;
import com.shadow.shadowthings.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class SoulCollectorEntity extends AbstractSoulEntity {

    public int collectionRadius = 5;
    private int networkTickCounter = 0;

    // Timer to show the particle radius when the player toggles it
    public int showRadiusTimer = 0;

    public SoulCollectorEntity(BlockPos pos, BlockState state) {
        // Base stats: 0 souls, 10,000 capacity, standard transfer rules
        super(ModBlockEntities.SOUL_COLLECTOR_BE.get(), pos, state, 0, 10000, 0, 0);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (level.isClientSide()) {
            tickClientVisuals();
            return;
        }

        // --- WIRELESS TRANSMISSION TO CORE ---
        this.networkTickCounter++;
        if (this.networkTickCounter >= 20) {
            this.networkTickCounter = 0;

            BlockPos targetCore = this.getLinkedCorePos(); // Inherited from AbstractSoulEntity

            if (targetCore != null && this.souls > 0 && level instanceof ServerLevel) {
                if (level.isLoaded(targetCore) && level.getBlockEntity(targetCore) instanceof SoulCoreEntity core) {
                    int spaceInCore = core.getMaxSouls() - core.getSouls();

                    if (spaceInCore > 0) {
                        // Push souls to the core! (Amount scales with capacity/upgrades)
                        int amountToPush = Math.min(this.souls, Math.min(spaceInCore, 200));
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

    // Called by the Block when a player shift-right-clicks it with an empty hand
    public void cycleRadius(Player player) {
        this.collectionRadius += 4;
        if (this.collectionRadius > 13) {
            this.collectionRadius = 5;
        }
        this.showRadiusTimer = 60; // Show particles for 3 seconds
        this.sync();

        player.displayClientMessage(Component.literal("§bCollection Radius set to: " + this.collectionRadius + "x" + this.collectionRadius), true);
    }

    public AABB getCollectionArea() {
        return new AABB(this.getBlockPos()).inflate(this.collectionRadius);
    }

    @Override
    public void tickClientVisuals() {
        super.tickClientVisuals(); // Keeps the transfer particles running!

        if (this.showRadiusTimer > 0) {
            this.showRadiusTimer--;

            // TODO: Spawn a beautiful box of particles around the collection area here!
            // using this.collectionRadius to determine how far out the corners are.
        }
    }

    @Override
    public TagKey<Item> getAllowedUpgradeTag() {
        return ModTags.Items.COLLECTOR_UPGRADES; // E.g., Speed Upgrades to push more souls, or Range Upgrades
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("CollectionRadius", this.collectionRadius);
        tag.putInt("ShowRadiusTimer", this.showRadiusTimer);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.collectionRadius = tag.getInt("CollectionRadius");
        this.showRadiusTimer = tag.getInt("ShowRadiusTimer");
    }
}