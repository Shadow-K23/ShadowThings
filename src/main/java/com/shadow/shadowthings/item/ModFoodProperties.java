package com.shadow.shadowthings.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoodProperties {

    public static final FoodProperties SOUL_FRUIT_1 = new FoodProperties.Builder()
            .nutrition(3)
            .saturationModifier(0.25f)
            .fast()
            .alwaysEdible()
            .build();

    public static final FoodProperties SHADOW_BERRY = new FoodProperties.Builder()
            .nutrition(1)
            .saturationModifier(0.15f)
            .fast()
            .build();

}
