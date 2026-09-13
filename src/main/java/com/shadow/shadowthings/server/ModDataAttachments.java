package com.shadow.shadowthings.server;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.shadow.shadowthings.soul.ModPlayerSoulMana;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModDataAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, "shadowthings");

    public static final Codec<ModPlayerSoulMana> SOUL_MANA_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    // Save the current mana
                    Codec.INT.fieldOf("mana").forGetter(ModPlayerSoulMana::getMana),
                    // Save the max mana
                    Codec.INT.fieldOf("max_mana").forGetter(ModPlayerSoulMana::getMaxMana)
            ).apply(instance, (mana, maxMana) -> {
                // This runs when the player logs back in to rebuild their data
                ModPlayerSoulMana pm = new ModPlayerSoulMana();
                pm.setMaxMana(maxMana);
                pm.setMana(mana);
                return pm;
            })
    );
    public static final Supplier<AttachmentType<ModPlayerSoulMana>> PLAYER_SOUL_MANA = ATTACHMENT_TYPES.register(
            "player_mana",
            () -> AttachmentType.builder(ModPlayerSoulMana::new)
                    .serialize(SOUL_MANA_CODEC) // Point to the Codec we just made!
                    .copyOnDeath()
                    .build()
    );


    public static void register(IEventBus eventBus){
        ATTACHMENT_TYPES.register(eventBus);
    }
}