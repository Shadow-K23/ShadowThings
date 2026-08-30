package com.shadow.shadowthings.worldgen.Tree;

import com.shadow.shadowthings.ShadowThings;
import com.shadow.shadowthings.worldgen.ModConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

public class ModTreeGrowers {
    public static final TreeGrower SHADOWWOOD = new TreeGrower(ShadowThings.MODID + ":shadowwood",
            Optional.empty(), Optional.of(ModConfiguredFeatures.SHADOWWOOD_KEY),Optional.empty());
}
