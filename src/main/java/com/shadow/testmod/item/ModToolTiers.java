package com.shadow.testmod.item;

import com.mojang.serialization.Decoder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.SimpleTier;

public class ModToolTiers {

    public static final TagKey<Block> INCORRECT_FOR_CUSTOM = BlockTags.create(
            ResourceLocation.fromNamespaceAndPath("testmod", "incorrect_for_custom"));

    public static final SimpleTier SHADOW = new SimpleTier(
            INCORRECT_FOR_CUSTOM,
            2650,
            15f,
            12f,
            30,
            () -> Ingredient.of(ModItems.SHADOWINGOT.get())
    );



}
