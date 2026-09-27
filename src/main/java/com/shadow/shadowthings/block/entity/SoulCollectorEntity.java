package com.shadow.shadowthings.block.entity;

import com.shadow.shadowthings.block.entity.base.AbstractSoulEntity;
import com.shadow.shadowthings.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SoulCollectorEntity extends AbstractSoulEntity {

    public int collectionRadius = 5;
    private int networkTickCounter = 0;

    // Timer to show the particle radius when the player toggles it
    public int showRadiusTimer = 0;
    public float clientSpinAngle = 0f;


    public SoulCollectorEntity(BlockPos pos, BlockState state) {
        // Base stats: 0 souls, 10,000 capacity, standard transfer rules
        super(ModBlockEntities.SOUL_COLLECTOR_BE.get(), pos, state, 0, 2500, 0, 0);
    }

    // Tracks the physical location of souls flying towards the block
    public final List<Vector3f> flyingSouls = new ArrayList<>();

    // Call this from your Event when a mob dies!
    public void addIncomingVisualSoul(BlockPos deathPos) {
        this.flyingSouls.add(new Vector3f(deathPos.getX() + 0.5f, deathPos.getY() + 1.0f, deathPos.getZ() + 0.5f));
    }

    // Inside your existing tick() method, add this to the SERVER-SIDE block:
    public void tick(Level level, BlockPos pos, BlockState state) {
        if (level.isClientSide()) {
            tickClientVisuals();
            return;
        }

        // --- FLYING SOUL PARTICLES (Server Side) ---
        if (!this.flyingSouls.isEmpty() && level instanceof ServerLevel serverLevel) {
            Vector3f targetPos = new Vector3f(pos.getX() + 0.5f, pos.getY() + 1.5f, pos.getZ() + 0.5f);
            Iterator<Vector3f> iterator = this.flyingSouls.iterator();

            while (iterator.hasNext()) {
                Vector3f soulPos = iterator.next();
                Vector3f direction = new Vector3f(targetPos).sub(soulPos);

                float distance = direction.length();

                // If it reached the orb, remove it and play a tiny sound!
                if (distance < 0.5f) {
                    iterator.remove();
                    serverLevel.playSound(null, pos, net.minecraft.sounds.SoundEvents.SOUL_ESCAPE.value(), net.minecraft.sounds.SoundSource.BLOCKS, 0.1f, 1.5f);
                } else {
                    // Move the soul 0.6 blocks towards the collector per tick
                    direction.normalize().mul(0.6f);
                    soulPos.add(direction);

                    // Spawn a server particle at this exact flying location.
                    // (Server particles are automatically sent to all nearby clients and ignore block collisions!)
                    serverLevel.sendParticles(ParticleTypes.SOUL_FIRE_FLAME,
                            soulPos.x(), soulPos.y(), soulPos.z(), 1, 0, 0, 0, 0);
                }
            }
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
                        int amountToPush = Math.min(this.souls, Math.min(spaceInCore, 1000));
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
        super.tickClientVisuals();

        // Increment the spin angle based on current souls
        float fillRatio = this.getMaxSouls() > 0 ? (float) this.getSouls() / (float) this.getMaxSouls() : 0f;
        this.clientSpinAngle += 2.0F + (fillRatio * 6.0F);

        if (this.clientSpinAngle >= 360f) {
            this.clientSpinAngle -= 360f;
        }

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