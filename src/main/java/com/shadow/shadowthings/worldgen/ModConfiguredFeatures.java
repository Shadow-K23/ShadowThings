package com.shadow.shadowthings.worldgen;

import com.shadow.shadowthings.Config;
import com.shadow.shadowthings.ShadowThings;
import com.shadow.shadowthings.block.ModBlocks;
import com.shadow.shadowthings.block.custom.ShadowBerryBushBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.CherryFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.SpruceFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RandomizedIntStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.CherryTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.neoforged.fml.common.Mod;

import java.util.List;

public class ModConfiguredFeatures {
    //ORE GEN
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_SHADOW_ORE_KEY = registerKey("shadow_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_SHADOW_ORE_KEY = registerKey("nether_shadow_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_SHADOW_ORE_KEY = registerKey("end_shadow_ore");


    //TREE
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHADOWWOOD_KEY = registerKey("shadowwood");

    public static final ResourceKey<ConfiguredFeature<?, ?>> SHADOW_BERRY_BUSH_KEY = registerKey("sahdow_berry_bush");

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

        register(context, SHADOWWOOD_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.SHADOWWOOD_LOG.get()),
                new CherryTrunkPlacer(3, 4, 6, UniformInt.of(1,3), UniformInt.of(4,7),UniformInt.of(-3,0),UniformInt.of(1,3)),
                BlockStateProvider.simple(ModBlocks.SHADOWWOOD_LEAVES.get()),
                new CherryFoliagePlacer(UniformInt.of(3,4),ConstantInt.of(0),UniformInt.of(4,6),
                        0.2f,0.15f,0.65f,0.35f),
                new TwoLayersFeatureSize(1,0,2)).build());

        register(context,SHADOW_BERRY_BUSH_KEY, Feature.RANDOM_PATCH,
                FeatureUtils.simplePatchConfiguration(
                        Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(
                                BlockStateProvider.simple(ModBlocks.SHADOW_BERRY_BUSH.get().defaultBlockState().setValue(ShadowBerryBushBlock.AGE,
                                        Integer.valueOf(3)))),
                        List.of(Blocks.GRASS_BLOCK)));

    }




    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(ShadowThings.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
