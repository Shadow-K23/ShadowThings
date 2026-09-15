package com.shadow.shadowthings.server;

import com.shadow.shadowthings.block.entity.SoulCoreEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class CoreMeltdownManager {
    private final SoulCoreEntity core;
    public final int DETONATION_TIME = 1800;

    public double gravityRadius = 100.0;
    public double maxGravityStrength = 0.15;

    public CoreMeltdownManager(SoulCoreEntity core) {
        this.core = core;
    }

    // Called every frame by the Core once the meltdown begins
    public void tick(Level level, BlockPos pos) {
        this.core.meltdownTimer++;
        this.core.setChanged();
        level.sendBlockUpdated(pos, this.core.getBlockState(), this.core.getBlockState(), 3);

        if (level.isClientSide()) {
            this.tickClientVisuals(level, pos);
            return;
        }

        // --- PHASE 1: The Warning (0-60 ticks) ---
        if (this.core.meltdownTimer < 600) {
            if (this.core.meltdownTimer % 100 == 0) {
                level.playSound(null, pos, SoundEvents.BEACON_DEACTIVATE, SoundSource.BLOCKS, 3.0f, 0.5f);
                level.playSound(null, pos, SoundEvents.BEACON_DEACTIVATE, SoundSource.BLOCKS, 3.5f, 0.25f);
                level.playSound(null, pos, SoundEvents.BEACON_DEACTIVATE, SoundSource.BLOCKS, 4.0f, 0.15f);
            }
        }
        // --- PHASE 2: The Gravity Well (60-180 ticks) ---
        else if (this.core.meltdownTimer < 1000) {
            if (this.core.meltdownTimer % 25 == 0) {
                level.playSound(null, pos, SoundEvents.BEACON_POWER_SELECT, SoundSource.BLOCKS, 3.5f, 0.35f);
            }
            if (this.core.meltdownTimer % 50 == 0) {
                level.playSound(null, pos, SoundEvents.BEACON_POWER_SELECT, SoundSource.BLOCKS, 3.5f, 1.5f);
            }

            // 1. Define the center of the black hole (e.g., 3 blocks ABOVE the core)
            double coreX = pos.getX() + 0.5;
            double coreY = pos.getY() + 3.0;
            double coreZ = pos.getZ() + 0.5;

            // 2. Find all entities within the customizable radius
           AABB pullArea = new AABB(pos).inflate(this.gravityRadius);
            List<Entity> entities = level.getEntitiesOfClass(Entity.class, pullArea);

            for (Entity target : entities) {
                // Ignore players in creative or spectator mode
                if (target instanceof Player player && (player.isCreative() || player.isSpectator())) {
                    continue;
                }
                // 3. Calculate distance to the hovering BLACK HOLE center
                double dx = coreX - target.getX();
                double dy = coreY - target.getY();
                double dz = coreZ - target.getZ();
                double distanceSq = dx * dx + dy * dy + dz * dz;

                if (distanceSq > 0 && distanceSq <= (this.gravityRadius * this.gravityRadius)) {
                    double distance = Math.sqrt(distanceSq);

                    // 4. Calculate standard inward gravity
                    double distanceFactor = 1.0 - (distance / this.gravityRadius);
                    double currentPull = this.maxGravityStrength * distanceFactor;

                    double pullX = (dx / distance) * currentPull;
                    double pullY = (dy / distance) * currentPull;
                    double pullZ = (dz / distance) * currentPull;

                    double orbitX = 0;
                    double orbitY = 0;
                    double orbitZ = 0;

                    // 5. THE STRICT ORBIT TRAP (Only kicks in within 5 blocks)
                    if (distance <= 5.0) {
                        // Severely weaken the inward X/Z pull so they stay in a ring instead of crashing into the exact center pixel
                        pullX *= 0.1;
                        pullZ *= 0.1;

                        // Calculate strict flat tangent for the orbit
                        double tangentDist = Math.sqrt(dz * dz + dx * dx);
                        if (tangentDist > 0) {
                            double orbitSpeed = 0.5; // Fast, violent spin
                            orbitX = (dz / tangentDist) * orbitSpeed;
                            orbitZ = (-dx / tangentDist) * orbitSpeed;
                        }

                        // Counteract vanilla Minecraft gravity so they don't fall out of the ring
                        orbitY = 0.08;
                    }

                    // 6. Apply Movement with Damping
                    // We multiply their current velocity by 0.85 (drag) to prevent them from slingshotting out of the orbit!
                    net.minecraft.world.phys.Vec3 currentVel = target.getDeltaMovement();
                    target.setDeltaMovement(
                            currentVel.x * 0.85 + pullX + orbitX,
                            currentVel.y * 0.85 + pullY + orbitY,
                            currentVel.z * 0.85 + pullZ + orbitZ
                    );

                    target.hurtMarked = true;
                }
            }
        }
        // --- PHASE 3A: COLLAPSE START AND SOUND ---
        else if (this.core.meltdownTimer < DETONATION_TIME) {
            if(this.core.meltdownTimer < 1600){
                if (this.core.meltdownTimer % 10 == 0){
                    level.playSound(null, pos, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 4f, 0.5f);
                }else if (this.core.meltdownTimer % 20 == 0){
                    level.playSound(null, pos, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 5f, 0.35f);
                }else if (this.core.meltdownTimer % 30 == 0){
                    level.playSound(null, pos, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 6f, 0.15f);
                }else if (this.core.meltdownTimer % 40 == 0) {
                    level.playSound(null, pos, SoundEvents.WARDEN_HEARTBEAT, SoundSource.BLOCKS, 8.0f, 0.25f);
                }

            }
            //PHASE 3B -- COLLAPSE AND NO SOUND
        }else if (this.core.meltdownTimer < DETONATION_TIME) {

        }
        // --- PHASE 4: DETONATION ---
        else if (this.core.meltdownTimer >= DETONATION_TIME) {
            detonate(level, pos);
        }
    }

    private void tickClientVisuals(Level level, BlockPos pos) {
        // We will put the crazy particle math here later!
    }

    private void detonate(Level level, BlockPos pos) {
        // 1. Physical crater
        level.explode(null, pos.getX(), pos.getY(), pos.getZ(), 20f, Level.ExplosionInteraction.TNT);

        // 2. The 200-Block Soul Shockwave
        AABB nukeRadius = new AABB(pos).inflate(100);
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, nukeRadius);
        for (LivingEntity target : entities) {
            target.hurt(level.damageSources().magic(), 1000f);
        }

        // 3. Destroy the core
        level.removeBlock(pos, false);
    }
}
