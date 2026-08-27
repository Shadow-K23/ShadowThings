package com.shadow.shadowthings.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoodProperties {

    public static final FoodProperties DRAGON_FRUIT = new FoodProperties.Builder()
            .nutrition(3)
            .saturationModifier(0.25f)
            .effect(()-> new MobEffectInstance(MobEffects.REGENERATION, 300), 1f)
            .build();

    public static final FoodProperties SHADOW_BERRY = new FoodProperties.Builder()
            .nutrition(1)
            .saturationModifier(0.15f)
            //.effect(()-> new MobEffectInstance(MobEffects.REGENERATION, 100), 1f)
            .fast()
            .build();

}
