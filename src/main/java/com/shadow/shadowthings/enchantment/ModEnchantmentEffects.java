package com.shadow.shadowthings.enchantment;

import com.mojang.serialization.MapCodec;
import com.shadow.shadowthings.ShadowThings;
import com.shadow.shadowthings.enchantment.custom.LightningStrikeEnchantmentEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEnchantmentEffects {
    public static final DeferredRegister<MapCodec<? extends EnchantmentEntityEffect>> ENTITY_ENCHANTMENT_EFFECTS =
            DeferredRegister.create(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, ShadowThings.MODID);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> LIGHTNING_STRIKE =
            ENTITY_ENCHANTMENT_EFFECTS.register("lightning_strike", () -> LightningStrikeEnchantmentEffect.CODEC);


    public static void register(IEventBus eventBus){
        ENTITY_ENCHANTMENT_EFFECTS.register(eventBus);
    }
}
