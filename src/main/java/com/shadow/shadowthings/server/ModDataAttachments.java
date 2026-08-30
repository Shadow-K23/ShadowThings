package com.shadow.shadowthings.server;

import com.mojang.serialization.Codec;
import com.shadow.shadowthings.soul.ModPlayerSoulMana;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModDataAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, "shadowthings");

    public static final Supplier<AttachmentType<ModPlayerSoulMana>> PLAYER_SOUL_MANA = ATTACHMENT_TYPES.register(
            "player_mana",
            () -> AttachmentType.builder(ModPlayerSoulMana::new)
                    .serialize(Codec.INT.xmap(
                            mana -> {
                                ModPlayerSoulMana pm = new ModPlayerSoulMana();
                                pm.setMana(mana);
                                return pm;
                            },
                            ModPlayerSoulMana::getMana
                    ))
                    .copyOnDeath() // Keeps mana when the player dies and respawns
                    .build()
    );



    public static void register(IEventBus eventBus){
        ATTACHMENT_TYPES.register(eventBus);
    }
}