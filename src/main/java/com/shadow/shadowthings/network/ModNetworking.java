package com.shadow.shadowthings.network;

import com.shadow.shadowthings.server.ModDataAttachments;
import com.shadow.shadowthings.soul.ModManaSyncPayload;
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
                            manaData.setMana(payload.mana());
                        }
                    });
                }
        );
    }
}
