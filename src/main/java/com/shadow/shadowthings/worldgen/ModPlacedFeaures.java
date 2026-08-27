package com.shadow.shadowthings.worldgen;

import com.shadow.shadowthings.ShadowThings;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class ModPlacedFeaures {
    //ORE GEN
    public static final ResourceKey<PlacedFeature> SHADOW_ORE_PLACED_KEY = registerKey("shadow_ore_placed");
    public static final ResourceKey<PlacedFeature> NETHER_SHADOW_ORE_PLACED_KEY = registerKey("nether_shadow_ore_placed");
    public static final ResourceKey<PlacedFeature> END_SHADOW_ORE_PLACED_KEY = registerKey("end_shadow_ore_placed");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
    //ORE GEN
        register(context,SHADOW_ORE_PLACED_KEY,configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_SHADOW_ORE_KEY),
                ModOrePlacement.commonOrePlacement(8, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64),VerticalAnchor.absolute(75))));
        register(context,NETHER_SHADOW_ORE_PLACED_KEY,configuredFeatures.getOrThrow(ModConfiguredFeatures.NETHER_SHADOW_ORE_KEY),
                ModOrePlacement.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.absolute(20),VerticalAnchor.absolute(120))));
        register(context,END_SHADOW_ORE_PLACED_KEY,configuredFeatures.getOrThrow(ModConfiguredFeatures.END_SHADOW_ORE_KEY),
                ModOrePlacement.commonOrePlacement(12, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64),VerticalAnchor.absolute(75))));

    }



    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(ShadowThings.MODID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }



}
