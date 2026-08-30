package com.shadow.shadowthings.enchantment;

import com.shadow.shadowthings.ShadowThings;
import com.shadow.shadowthings.enchantment.custom.LightningStrikeEnchantmentEffect;
import com.shadow.shadowthings.enchantment.custom.PoisonStrikeEnchantmentEffect;
import com.shadow.shadowthings.enchantment.custom.ShadowStrikeEnchantmentEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentTarget;
import org.spongepowered.asm.mixin.Shadow;

public class ModEnchantments {
    public static final ResourceKey<Enchantment> LIGHTNING_STRIKE = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(ShadowThings.MODID, "lightning_strike"));

    public static final ResourceKey<Enchantment> POISON_STRIKE = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(ShadowThings.MODID, "poison_strike"));

    public static final ResourceKey<Enchantment> SHADOW_STRIKE = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(ShadowThings.MODID, "shadow_strike"));


    public static void bootstrap(BootstrapContext<Enchantment> context){
        var enchantments = context.lookup(Registries.ENCHANTMENT);
        var items = context.lookup(Registries.ITEM);


        register(context,LIGHTNING_STRIKE,Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                items.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                5,
                3,
                Enchantment.dynamicCost(5,7),
                Enchantment.dynamicCost(25,8),
                2,
                EquipmentSlotGroup.MAINHAND))
                .exclusiveWith(enchantments.getOrThrow(EnchantmentTags.DAMAGE_EXCLUSIVE))
                .withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER,
                        EnchantmentTarget.VICTIM, new LightningStrikeEnchantmentEffect())
        );

        register(context,POISON_STRIKE,Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                items.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                3,
                3,
                Enchantment.dynamicCost(5,7),
                Enchantment.dynamicCost(25,8),
                3,
                EquipmentSlotGroup.MAINHAND))
                .exclusiveWith(enchantments.getOrThrow(EnchantmentTags.DAMAGE_EXCLUSIVE))
                .withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER,
                        EnchantmentTarget.VICTIM, new PoisonStrikeEnchantmentEffect())
        );

        register(context, SHADOW_STRIKE,Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                items.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                1,
                5,
                Enchantment.dynamicCost(6,8),
                Enchantment.dynamicCost(30,10),
                5,
                EquipmentSlotGroup.MAINHAND))
                .exclusiveWith(enchantments.getOrThrow(EnchantmentTags.DAMAGE_EXCLUSIVE))
                .withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER,
                        EnchantmentTarget.VICTIM, new ShadowStrikeEnchantmentEffect())
        );

    }





    private static void register(BootstrapContext<Enchantment> registry, ResourceKey<Enchantment> key,
                                 Enchantment.Builder builder) {
        registry.register(key, builder.build(key.location()));
    }
}
