package com.shadow.shadowthings.worldgen;

import com.shadow.shadowthings.ShadowThings;
import com.shadow.shadowthings.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {
    //ORE GEN
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_SHADOW_ORE_KEY = registerKey("shadow_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_SHADOW_ORE_KEY = registerKey("nether_shadow_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_SHADOW_ORE_KEY = registerKey("end_shadow_ore");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        //ORE GEN
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherrackReplaceables = new BlockMatchTest(Blocks.NETHERRACK);
        RuleTest endStoneReplaceables = new BlockMatchTest(Blocks.END_STONE);

        List<OreConfiguration.TargetBlockState> overworldShadowOres = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.SHADOW_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.SHADOW_DEEPSLATE_ORE.get().defaultBlockState()));

        register(context, OVERWORLD_SHADOW_ORE_KEY, Feature.ORE, new OreConfiguration(
                overworldShadowOres, 5));
        register(context, NETHER_SHADOW_ORE_KEY, Feature.ORE, new OreConfiguration(
                netherrackReplaceables, ModBlocks.SHADOW_NETHER_ORE.get().defaultBlockState(), 7));
        register(context, END_SHADOW_ORE_KEY, Feature.ORE, new OreConfiguration(
                endStoneReplaceables, ModBlocks.SHADOW_END_ORE.get().defaultBlockState(), 9));


    }




    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(ShadowThings.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
