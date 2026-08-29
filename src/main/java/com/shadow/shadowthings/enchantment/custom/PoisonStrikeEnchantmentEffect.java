package com.shadow.shadowthings.enchantment.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

import java.util.concurrent.ThreadLocalRandom;

public record PoisonStrikeEnchantmentEffect() implements EnchantmentEntityEffect {
    public static final MapCodec<PoisonStrikeEnchantmentEffect> CODEC = MapCodec.unit(PoisonStrikeEnchantmentEffect::new);

    @Override
    public void apply(ServerLevel serverLevel, int enchantmentLevel, EnchantedItemInUse enchantedItemInUse, Entity entity, Vec3 vec3) {
        if (enchantmentLevel == 1) {
            if (entity instanceof LivingEntity livingTarget) {
                float chance = 0.50f;
                if (ThreadLocalRandom.current().nextFloat() < chance) {
                    MobEffectInstance effectInstance = new MobEffectInstance(
                            MobEffects.POISON, 100, 0, false, false
                    );
                    livingTarget.addEffect(effectInstance);
                }
            }
        }

        if (enchantmentLevel == 2) {
            if (entity instanceof LivingEntity livingTarget) {
                float chance = 0.65f;
                if (ThreadLocalRandom.current().nextFloat() < chance) {
                    MobEffectInstance effectInstance = new MobEffectInstance(
                            MobEffects.POISON, 100, 1, false, false
                    );
                    livingTarget.addEffect(effectInstance);
                }
            }
        }

        if (enchantmentLevel == 3) {
            if (entity instanceof LivingEntity livingTarget) {
                float chance = 0.85f;
                if (ThreadLocalRandom.current().nextFloat() < chance) {
                    MobEffectInstance effectInstance = new MobEffectInstance(
                            MobEffects.POISON, 100, 2, false, false
                    );
                    livingTarget.addEffect(effectInstance);
                }
            }
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
