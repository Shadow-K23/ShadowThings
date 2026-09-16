package com.shadow.shadowthings.server;

import com.shadow.shadowthings.block.entity.SoulCoreEntity;
import com.shadow.shadowthings.effect.ModEffects;
import com.shadow.shadowthings.sound.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CoreMeltdownManager {
    private final SoulCoreEntity core;
    public final int DETONATION_TIME = 1800;

    public int craterRadius = 50; // 50 blocks is a massive 100-block wide crater!
    private boolean mathStarted = false;
    public boolean mathDone = false;
    public boolean warningSent = false;
    public List<BlockPos> craterBlocks = new ArrayList<>();

    public CoreMeltdownManager(SoulCoreEntity core) {
        this.core = core;
    }

    // Called every frame by the Core once the meltdown begins
    public void tick(Level level, BlockPos pos) {
        this.core.meltdownTimer++;
        // No network updates here yet!

        if (level.isClientSide()) {
            this.tickClientVisuals(level, pos);
            return; // The client stops here
        }

        // ONLY THE SERVER DOES THIS:
        this.core.setChanged();
        level.sendBlockUpdated(pos, this.core.getBlockState(), this.core.getBlockState(), 3);

        Component warningMessage = Component.literal("[CRITICAL ALARM] Soul Core containment has failed. Evacuate immediately.")
                .withStyle(ChatFormatting.RED, ChatFormatting.BOLD);
        if(!warningSent) {
            warningSent = true;
            level.getServer().getPlayerList().broadcastSystemMessage(warningMessage, false);
        };

        if (this.core.meltdownTimer == 1 && level instanceof net.minecraft.server.level.ServerLevel serverLevel) {
            net.minecraft.world.level.ChunkPos chunkPos = new net.minecraft.world.level.ChunkPos(pos);
            serverLevel.setChunkForced(chunkPos.x, chunkPos.z, true);
        }

        // --- PHASE 1: The Warning ---
        if (this.core.meltdownTimer < 600) {
            if (this.core.meltdownTimer % 100 == 0) {
                level.playSound(null, pos, SoundEvents.BEACON_DEACTIVATE, SoundSource.BLOCKS, 3.0f, 0.5f);
                level.playSound(null, pos, SoundEvents.BEACON_DEACTIVATE, SoundSource.BLOCKS, 3.5f, 0.25f);
                level.playSound(null, pos, SoundEvents.BEACON_DEACTIVATE, SoundSource.BLOCKS, 4.0f, 0.15f);
            }
        }
        // --- PHASE 2 ---
        else if (this.core.meltdownTimer < 1000) {
            if (!this.mathStarted) {
                this.mathStarted = true;

                CompletableFuture.runAsync(() -> {
                    List<BlockPos> calculatedBlocks = new ArrayList<>();
                    int radiusSq = this.craterRadius * this.craterRadius;

                    // Do the heavy 3D sphere math off the main thread
                    for (int x = -this.craterRadius; x <= this.craterRadius; x++) {
                        for (int y = -this.craterRadius; y <= this.craterRadius; y++) {
                            for (int z = -this.craterRadius; z <= this.craterRadius; z++) {
                                if (x * x + y * y + z * z <= radiusSq) {
                                    calculatedBlocks.add(pos.offset(x, y, z));
                                }
                            }
                        }
                    }
                    // Save the list and flag it as complete!
                    this.craterBlocks = calculatedBlocks;
                    this.mathDone = true;
                });
            }
            if (this.core.meltdownTimer % 25 == 0) {
                level.playSound(null, pos, SoundEvents.BEACON_POWER_SELECT, SoundSource.BLOCKS, 15f, 0.35f);
            }
            if (this.core.meltdownTimer % 50 == 0) {
                level.playSound(null, pos, SoundEvents.BEACON_POWER_SELECT, SoundSource.BLOCKS, 15f, 1.5f);
            }
            // --- ANTI-GRAVITY LEVITATION ---
            AABB liftArea = new AABB(pos).inflate(25.0);
            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, liftArea);

            for (LivingEntity target : entities) {
                if (target instanceof Player player && (player.isCreative() || player.isSpectator())) continue;

                double dx = target.getX() - (pos.getX() + 0.5);
                double dz = target.getZ() - (pos.getZ() + 0.5);
                double distance = Math.sqrt(dx * dx + dz * dz);

                double outwardPush = 0.0;
                if (distance > 0) {
                    outwardPush = 0.05 / distance; // Pushes them away gently
                }

                // Override their velocity: Add outward drift, and a constant upward lift
                net.minecraft.world.phys.Vec3 currentVel = target.getDeltaMovement();
                target.setDeltaMovement(
                        currentVel.x + (dx * outwardPush),
                        currentVel.y * 0.8 + 0.08, // The 0.08 perfectly counteracts standard falling gravity!
                        currentVel.z + (dz * outwardPush)
                );

                // Tell the server to sync the new crazy movement to the clients
                target.hurtMarked = true;
            }

        }
        else if (this.core.meltdownTimer < DETONATION_TIME) {

            // 3A. Crackling Sounds
            if (this.core.meltdownTimer < 1600) {
                if (this.core.meltdownTimer % 10 == 0) level.playSound(null, pos, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 5f, 0.5f);
                else if (this.core.meltdownTimer % 20 == 0) level.playSound(null, pos, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 7f, 0.35f);
                else if (this.core.meltdownTimer % 30 == 0) level.playSound(null, pos, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 9f, 0.15f);
            }

            // 3B. The 5-Second Buildup Sound
            if (this.core.meltdownTimer == 1680) {
                level.playSound(null, pos, com.shadow.shadowthings.sound.ModSounds.CORE_EXPLOSION_BUILDUP.get(), SoundSource.BLOCKS, 25.0f, 1f);
            }

            // 3C. The Final Push
            if (this.core.meltdownTimer == 1700) {

                AABB pushArea = new AABB(pos).inflate(30.0);
                List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, pushArea);
                for (LivingEntity target : entities) {
                    if (target instanceof Player player && (player.isCreative() || player.isSpectator())) continue;
                    double dx = target.getX() - (pos.getX() + 0.5);
                    double dz = target.getZ() - (pos.getZ() + 0.5);
                    target.setDeltaMovement(dx * 0.2, 1.5, dz * 0.2);
                    target.hurtMarked = true;
                }
            }
        }
        // --- PHASE 4: DETONATION (1800+ ticks) ---
        else if (this.core.meltdownTimer >= DETONATION_TIME) {
            if (this.mathDone) {
                level.playSound(null, pos, com.shadow.shadowthings.sound.ModSounds.CORE_EXPLOSION.get(), SoundSource.BLOCKS, 10.0f, 1.0f);
                level.playSound(null, pos, com.shadow.shadowthings.sound.ModSounds.CORE_EXPLOSION_SHOCKWAVE.get(), SoundSource.BLOCKS, 35.0f, 1f);
                detonate(level, pos);
                // 4. Destroy the core itself
                level.removeBlock(pos, false);
                // 5. Release the chunk back to normal Minecraft behavior
                if (level instanceof net.minecraft.server.level.ServerLevel serverLevel) {
                    net.minecraft.world.level.ChunkPos chunkPos = new net.minecraft.world.level.ChunkPos(pos);
                    serverLevel.setChunkForced(chunkPos.x, chunkPos.z, false);
                }
            } else {
                this.core.meltdownTimer = DETONATION_TIME;
                this.core.setChanged();
            }
        }
    }

    private void tickClientVisuals(Level level, BlockPos pos) {
        int currentTimer = this.core.meltdownTimer;

        // --- PHASE 2 VISUALS: Escaping Souls ---
        if (currentTimer >= 600 && currentTimer < 1500) {
            // 1. Calculate progress from 0.0 (start) to 1.0 (end of phase)
            float phaseProgress = (currentTimer - 600) / 400.0f;

            // 2. Exponential buildup!
            // Math.pow(phaseProgress, 2) means it starts slow, but skyrockets at the end.
            // Starts at 1 particle per tick, ramps up to 25 per tick right before Phase 3!
            int particleCount = 1 + (int)(24 * Math.pow(phaseProgress, 2));

            // 3. Make them shoot out faster and more violently over time
            double baseSpeed = 0.1;
            double maxSpeedMultiplier = 0.8 * phaseProgress;

            for (int i = 0; i < particleCount; i++) {
                // MOVE THE SPAWN POINT UP! (e.g., +2.0 blocks high, adjust this to match your crystal height)
                // Add a random offset so they spawn all over the crystal, not just one pixel
                double originX = pos.getX() + 0.5 + (level.random.nextDouble() - 0.5);
                double originY = pos.getY() + 3.0 + (level.random.nextDouble() - 0.5);
                double originZ = pos.getZ() + 0.5 + (level.random.nextDouble() - 0.5);

                // Calculate aggressive random vectors
                double speed = baseSpeed + maxSpeedMultiplier;
                double vx = (level.random.nextDouble() - 0.5) * speed;
                double vy = (level.random.nextDouble() - 0.5) * speed;
                double vz = (level.random.nextDouble() - 0.5) * speed;

                // Spawn them
                level.addParticle(ParticleTypes.SOUL, originX, originY, originZ, vx, vy, vz);

                // Only spawn the fire flames occasionally to keep it looking spectral, not like a campfire
                if (level.random.nextFloat() < 0.3f) {
                    level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, originX, originY, originZ, vx, vy, vz);
                }
            }
        }
    }

    private void detonate(Level level, BlockPos pos) {
        // 1. Erase the pre-calculated sphere
        for (BlockPos targetPos : this.craterBlocks) {
            // Leave bedrock alone so we don't drop players into the void
            if (!level.getBlockState(targetPos).is(net.minecraft.world.level.block.Blocks.BEDROCK)) {
                // Flag '2' updates the client, but skips heavy block-update physics to prevent lag!
                level.setBlock(targetPos, net.minecraft.world.level.block.Blocks.AIR.defaultBlockState(), 2);
            }
        }

        // --- THE PARTICLE SHOCKWAVE ---
        if (level instanceof net.minecraft.server.level.ServerLevel serverLevel) {
            double cX = pos.getX() + 0.5;
            double cY = pos.getY() + 3.0;
            double cZ = pos.getZ() + 0.5;

            // 1. THE FLASH: This is the massive vanilla mushroom-cloud flash.
            // It overrides client settings and guarantees a blinding visual.
            serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.EXPLOSION_EMITTER,
                    cX, cY, cZ, 2, 0.0, 0.0, 0.0, 0.0);

            // 2. THE SOUL BLAST: We increased the spread (4.0) and doubled the speed (5.0)
            // so they aggressively shoot across the entire room instead of clustering.
            serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.SOUL,
                    cX, cY, cZ, 1000, 4.0, 4.0, 4.0, 5.0);

            // 3. THE FIRE: Increased spread to fill the crater radius
            serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.SOUL_FIRE_FLAME,
                    cX, cY, cZ, 500, 5.0, 5.0, 5.0, 3.0);

            // 4. THE SMOKE: A lingering, massive dust cloud covering a 10-block area
            serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.LARGE_SMOKE,
                    cX, cY, cZ, 800, 5.0, 5.0, 5.0, 2.0);
        }

        // 3. Shockwave damage to living entities
        net.minecraft.world.phys.AABB nukeRadius = new net.minecraft.world.phys.AABB(pos).inflate(300);
        List<net.minecraft.world.entity.LivingEntity> entities = level.getEntitiesOfClass(net.minecraft.world.entity.LivingEntity.class, nukeRadius);
        for (net.minecraft.world.entity.LivingEntity target : entities) {
            target.addEffect(new MobEffectInstance(ModEffects.SHADOW_CURSE_EFFECT, 1200, 10),target);
            target.addEffect(new MobEffectInstance(MobEffects.WITHER, 100, 1),target);
            target.addEffect(new MobEffectInstance(MobEffects.POISON, 600, 3),target);
            target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 300, 5),target);
        }

        // 4. Destroy the core itself
        level.removeBlock(pos, false);
    }
}
