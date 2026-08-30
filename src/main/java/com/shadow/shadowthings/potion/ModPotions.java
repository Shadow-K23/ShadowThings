package com.shadow.shadowthings.potion;

import com.shadow.shadowthings.ShadowThings;
import com.shadow.shadowthings.effect.ModEffects;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(BuiltInRegistries.POTION, ShadowThings.MODID);

    public static final Holder<Potion> SLIMEY_POTION = POTIONS.register("slimey_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.SLIMEY_EFFECT, 1200,0)));

    public static final Holder<Potion> SHADOW_CURSE_POTION = POTIONS.register("shadow_curse_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.SHADOW_CURSE_EFFECT, 1200,0)));


    public static void register(IEventBus eventBus){
        POTIONS.register(eventBus);
    }
}
