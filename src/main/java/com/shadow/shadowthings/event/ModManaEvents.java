package com.shadow.shadowthings.event;

import com.shadow.shadowthings.ShadowThings;
import com.shadow.shadowthings.enchantment.ModEnchantments;
import com.shadow.shadowthings.server.ModDataAttachments;
import com.shadow.shadowthings.soul.ModManaSyncPayload;
import com.shadow.shadowthings.soul.ModPlayerSoulMana;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.valueproviders.UniformInt;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.HashSet;
import java.util.Set;

@EventBusSubscriber(modid = ShadowThings.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ModManaEvents {
    private static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();


    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.getEntity().level().isClientSide() && event.getEntity() instanceof ServerPlayer player) {

            // Grab the data on login
            var manaData = player.getData(ModDataAttachments.PLAYER_SOUL_MANA);

            // Immediately sync it to the client so the HUD updates!
            PacketDistributor.sendToPlayer(player, new ModManaSyncPayload(manaData.getMana()));
        }
    }
    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (!event.getEntity().level().isClientSide() && event.getEntity() instanceof ServerPlayer player) {

            // Grab the data on login
            var manaData = player.getData(ModDataAttachments.PLAYER_SOUL_MANA);

            // Immediately sync it to the client so the HUD updates!
            PacketDistributor.sendToPlayer(player, new ModManaSyncPayload(manaData.getMana()));
        }
    }
    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (!event.getEntity().level().isClientSide() && event.getEntity() instanceof ServerPlayer player) {

            // Grab the data on login
            var manaData = player.getData(ModDataAttachments.PLAYER_SOUL_MANA);

            // Immediately sync it to the client so the HUD updates!
            PacketDistributor.sendToPlayer(player, new ModManaSyncPayload(manaData.getMana()));
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
                PacketDistributor.sendToPlayer(player, new ModManaSyncPayload(manaData.getMana()));

            }
        }
    }

    //MANA GATHER
    @SubscribeEvent
    public static void onMobKill(LivingDeathEvent event){
        if (!event.getEntity().level().isClientSide() && event.getSource().getEntity() instanceof ServerPlayer player){
            if(event.getEntity() instanceof net.minecraft.world.entity.monster.Monster){
                var manaData = player.getData(ModDataAttachments.PLAYER_SOUL_MANA);

                net.minecraft.world.item.ItemStack weapon = player.getMainHandItem();
                var registry = player.level().registryAccess().registryOrThrow(net.minecraft.core.registries.Registries.ENCHANTMENT);
                var soulStealerHolder = registry.getHolder(ModEnchantments.SOUL_STEALER).orElse(null);
                int enchantLevel = 0;
                if (soulStealerHolder != null) {
                    enchantLevel = weapon.getEnchantmentLevel(soulStealerHolder) + 1;
                }

                int souls = player.getRandom().nextIntBetweenInclusive(5, 15) * enchantLevel;

                manaData.addMana(souls);

                PacketDistributor.sendToPlayer(player, new ModManaSyncPayload(manaData.getMana()));
            }
        }
    }
}