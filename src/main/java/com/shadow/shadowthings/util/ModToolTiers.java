package com.shadow.shadowthings.util;

import com.shadow.shadowthings.item.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.SimpleTier;

public class ModToolTiers {

    public static final TagKey<Block> INCORRECT_FOR_CUSTOM = BlockTags.create(
            ResourceLocation.fromNamespaceAndPath("shadowthings", "incorrect_for_custom"));

    public static final SimpleTier SHADOW = new SimpleTier(
            INCORRECT_FOR_CUSTOM,
            2650,
            15f,
            12f,
            30,
            () -> Ingredient.of(ModItems.SHADOWINGOT.get())
    );



}
