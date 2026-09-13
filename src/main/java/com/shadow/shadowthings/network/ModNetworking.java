package com.shadow.shadowthings.network;

import com.shadow.shadowthings.server.ModDataAttachments;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = "shadowthings", bus = EventBusSubscriber.Bus.MOD)
public class ModNetworking {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        // Create a registrar for your mod
        final PayloadRegistrar registrar = event.registrar("shadowthings");

        // Register the Server-to-Client packet
        registrar.playToClient(
                ModManaSyncPayload.TYPE,
                ModManaSyncPayload.STREAM_CODEC,
                (payload, context) -> {
                    // This block runs on the Client when the packet arrives
                    context.enqueueWork(() -> {
                        // Get the local player and update their mana data
                        if (context.player() != null) {
                            var manaData = context.player().getData(ModDataAttachments.PLAYER_SOUL_MANA);

                            // Update BOTH values on the client HUD!
                            manaData.setMaxMana(payload.maxMana());
                            manaData.setMana(payload.mana());
                            manaData.hasSynced = true;
                        }
                    });
                }
        );
        // Register the payload blueprint
        registrar.playToServer(
                ModStartCraftingPayload.TYPE,
                ModStartCraftingPayload.STREAM_CODEC,
                (payload, context) -> {
                    context.enqueueWork(() -> {
                        // This code runs on the Server!
                        if (context.player() != null && context.player().level() != null) {
                            var level = context.player().level();
                            // Find the block entity at the coordinates the client sent
                            if (level.getBlockEntity(payload.pos()) instanceof com.shadow.shadowthings.block.entity.SoulCrafterEntity crafter) {
                                crafter.startCrafting(); // Trigger the logic we wrote earlier!
                            }
                        }
                    });
                }
        );
    }
}
