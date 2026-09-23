package com.shadow.shadowthings.item.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class SoulScytheItem extends SwordItem {

    public SoulScytheItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        // 1. Process normal single-target damage and durability loss
        boolean result = super.hurtEnemy(stack, target, attacker);

        // 2. Define the AoE Sweep Hitbox (Expands 1.5 blocks around the primary target)
        AABB sweepBox = target.getBoundingBox().inflate(2.15D, 0.25D, 2.15D);

        // 3. Find all living entities inside that box (excluding the attacker and the primary target)
        List<LivingEntity> caughtEntities = attacker.level().getEntitiesOfClass(
                LivingEntity.class, sweepBox, entity -> entity != attacker && entity != target
        );

        // 4. Damage everything caught in the sweep!
        for (LivingEntity caughtEntity : caughtEntities) {
            // Apply flat sweep damage, or calculate based on your tier
            caughtEntity.hurt(attacker.damageSources().mobAttack(attacker), 4.5f);
        }
        return result;
    }
}