package com.shadow.shadowthings.worldgen;

import com.shadow.shadowthings.ShadowThings;
import com.shadow.shadowthings.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModPlacedFeatures {
    //ORE GEN
    public static final ResourceKey<PlacedFeature> SHADOW_ORE_PLACED_KEY = registerKey("shadow_ore_placed");
    public static final ResourceKey<PlacedFeature> NETHER_SHADOW_ORE_PLACED_KEY = registerKey("nether_shadow_ore_placed");
    public static final ResourceKey<PlacedFeature> END_SHADOW_ORE_PLACED_KEY = registerKey("end_shadow_ore_placed");

    //TREE
    public static final ResourceKey<PlacedFeature> SHADOWWOOD_PLACED_KEY = registerKey("shadowwood_placed");

    public static final ResourceKey<PlacedFeature> SHADOW_BERRY_PLACED_KEY = registerKey("shadow_berry_placed");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
    //ORE GEN
        register(context,SHADOW_ORE_PLACED_KEY,configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_SHADOW_ORE_KEY),
                ModOrePlacement.commonOrePlacement(8, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64),VerticalAnchor.absolute(75))));
        register(context,NETHER_SHADOW_ORE_PLACED_KEY,configuredFeatures.getOrThrow(ModConfiguredFeatures.NETHER_SHADOW_ORE_KEY),
                ModOrePlacement.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.absolute(20),VerticalAnchor.absolute(120))));
        register(context,END_SHADOW_ORE_PLACED_KEY,configuredFeatures.getOrThrow(ModConfiguredFeatures.END_SHADOW_ORE_KEY),
                ModOrePlacement.commonOrePlacement(12, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64),VerticalAnchor.absolute(75))));

    //TREES
        register(context,SHADOWWOOD_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.SHADOWWOOD_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(1,0.01f,1),
                        ModBlocks.SHADOWWOOD_SAPLING.get()));

        register(context,SHADOW_BERRY_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.SHADOW_BERRY_BUSH_KEY),
                List.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));

    }



    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(ShadowThings.MODID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }



}
