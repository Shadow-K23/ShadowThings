package com.shadow.shadowthings.util;

import com.shadow.shadowthings.item.ModItems;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class ModToolTiers {


    public static final Tier SHADOW = new SimpleTier(
            ModTags.Blocks.INCORRECT_FOR_SHADOW_TOOL,
            2650,
            15f,
            12f,
            28,
            () -> Ingredient.of(ModItems.SHADOW_INGOT)
    );


}
