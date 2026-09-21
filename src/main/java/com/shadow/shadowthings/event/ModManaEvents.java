package com.shadow.shadowthings.event;

import com.shadow.shadowthings.ShadowThings;
import com.shadow.shadowthings.block.entity.SoulCollectorEntity;
import com.shadow.shadowthings.enchantment.ModEnchantments;
import com.shadow.shadowthings.item.custom.SoulScytheItem;
import com.shadow.shadowthings.server.ModDataAttachments;
import com.shadow.shadowthings.network.ModManaSyncPayload;
import com.shadow.shadowthings.soul.ModPlayerSoulMana;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.*;

@EventBusSubscriber(modid = ShadowThings.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ModManaEvents {
    private static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();


    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.getEntity().level().isClientSide() && event.getEntity() instanceof ServerPlayer player) {

            // Grab the data on login
            var manaData = player.getData(ModDataAttachments.PLAYER_SOUL_MANA);

            // Immediately sync it to the client so the HUD updates!
            PacketDistributor.sendToPlayer(player, new ModManaSyncPayload(manaData.getMana(), manaData.getMaxMana()));
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (!event.getEntity().level().isClientSide() && event.getEntity() instanceof ServerPlayer player) {

            // Grab the data on login
            var manaData = player.getData(ModDataAttachments.PLAYER_SOUL_MANA);

            // Immediately sync it to the client so the HUD updates!
            PacketDistributor.sendToPlayer(player, new ModManaSyncPayload(manaData.getMana(), manaData.getMaxMana()));
        }
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (!event.getEntity().level().isClientSide() && event.getEntity() instanceof ServerPlayer player) {

            // Grab the data on login
            var manaData = player.getData(ModDataAttachments.PLAYER_SOUL_MANA);

            // Immediately sync it to the client so the HUD updates!
            PacketDistributor.sendToPlayer(player, new ModManaSyncPayload(manaData.getMana(), manaData.getMaxMana()));
        }
    }

    //MANA REGEN
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        // Only run logic on the server side


        if (!event.getEntity().level().isClientSide() && event.getEntity() instanceof ServerPlayer player) {
            ModPlayerSoulMana manaData = player.getData(ModDataAttachments.PLAYER_SOUL_MANA);
            // Regenerate 1 mana every second (20 ticks)
            if (player.tickCount % 20 == 0 && manaData.getMana() < manaData.getMaxMana()) {
                manaData.addMana(manaData.getManaRegen());

                // Sync to client so the HUD updates
                PacketDistributor.sendToPlayer(player, new ModManaSyncPayload(manaData.getMana(), manaData.getMaxMana()));

            }
        }
    }

    //MANA GATHER
    @SubscribeEvent
    public static void onMobKill(LivingDeathEvent event) {
        if (event.getEntity().level().isClientSide()) return;

        LivingEntity dyingEntity = event.getEntity();
        ServerLevel level = (ServerLevel) dyingEntity.level();
        BlockPos deathPos = dyingEntity.blockPosition();

        // --- 1. SOUL COLLECTOR LOGIC (Automated Mob Farms) ---
        int collectorSouls = 2;
        if (dyingEntity instanceof Monster) collectorSouls = 15;
        if (dyingEntity.getMaxHealth() >= 100) collectorSouls = 500;

        // Define the 15-block radius corners
        BlockPos minPos = BlockPos.containing(deathPos.getX() - 15, deathPos.getY() - 15, deathPos.getZ() - 15);
        BlockPos maxPos = BlockPos.containing(deathPos.getX() + 15, deathPos.getY() + 15, deathPos.getZ() + 15);

        List<SoulCollectorEntity> nearbyCollectors = new ArrayList<>();

        // Scan all block positions in that radius
        for (BlockPos p : BlockPos.betweenClosed(minPos, maxPos)) {
            // Check if the chunk is actually loaded before polling the entity to prevent lag
            if (level.isLoaded(p) && level.getBlockEntity(p) instanceof SoulCollectorEntity collector) {
                // Verify the mob is inside this specific collector's radius
                if (collector.getCollectionArea().contains(dyingEntity.position())) {
                    nearbyCollectors.add(collector);
                }
            }
        }
        // Sort the list so the closest collector gets priority
        nearbyCollectors.sort(Comparator.comparingDouble(c -> c.getBlockPos().distSqr(deathPos)));

        // Give souls to the closest valid collector
        if (!nearbyCollectors.isEmpty()) {
            com.shadow.shadowthings.block.entity.SoulCollectorEntity closest = nearbyCollectors.get(0);
            if (closest.getSouls() < closest.getMaxSouls()) {
                closest.addSouls(collectorSouls);
                closest.addIncomingVisualSoul(deathPos);
            }
        }

        // --- 2. PLAYER SOUL STEALER LOGIC (Manual Kills) ---
        if (event.getSource().getEntity() instanceof ServerPlayer player) {
            if (dyingEntity instanceof Monster) {
                var manaData = player.getData(ModDataAttachments.PLAYER_SOUL_MANA);
                ItemStack weapon = player.getMainHandItem();
                var registry = player.level().registryAccess().registryOrThrow(Registries.ENCHANTMENT);
                var soulStealerHolder = registry.getHolder(ModEnchantments.SOUL_STEALER).orElse(null);
                int scytheBonus = 0;
                int enchantLevel = 0;
                if (soulStealerHolder != null) {
                    enchantLevel = weapon.getEnchantmentLevel(soulStealerHolder) + 1;
                }
                if (weapon.getItem() instanceof SoulScytheItem scytheItem){
                    scytheBonus = player.getRandom().nextIntBetweenInclusive(150,300);
                }

                int souls = (player.getRandom().nextIntBetweenInclusive(5, 15) + scytheBonus) * enchantLevel;

                manaData.addMana(souls);
                PacketDistributor.sendToPlayer(player, new ModManaSyncPayload(manaData.getMana(), manaData.getMaxMana()));
            }
        }
    }
}