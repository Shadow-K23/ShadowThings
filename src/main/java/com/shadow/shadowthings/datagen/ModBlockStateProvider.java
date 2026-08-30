package com.shadow.shadowthings.datagen;

import com.shadow.shadowthings.ShadowThings;
import com.shadow.shadowthings.block.ModBlocks;
import com.shadow.shadowthings.block.custom.RadishCropBlock;
import com.shadow.shadowthings.block.custom.ShadowBerryBushBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.function.Function;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper ){
         super(output, ShadowThings.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        //BLOCKS

        blockWithItem(ModBlocks.SHADOW_BLOCK);

        //NON-BLOCK BLOCKS

        stairsBlock(ModBlocks.SHADOW_STAIRS.get(),blockTexture(ModBlocks.SHADOW_BLOCK.get()));
        slabBlock(ModBlocks.SHADOW_SLAB.get(),blockTexture(ModBlocks.SHADOW_BLOCK.get()),blockTexture(ModBlocks.SHADOW_BLOCK.get()));

        buttonBlock(ModBlocks.SHADOW_BUTTON.get(),blockTexture(ModBlocks.SHADOW_BLOCK.get()));
        pressurePlateBlock(ModBlocks.SHADOW_PRESSURE_PLATE.get(),blockTexture(ModBlocks.SHADOW_BLOCK.get()));

        fenceBlock(ModBlocks.SHADOW_FENCE.get(),blockTexture(ModBlocks.SHADOW_BLOCK.get()));
        fenceGateBlock(ModBlocks.SHADOW_FENCE_GATE.get(),blockTexture(ModBlocks.SHADOW_BLOCK.get()));
        wallBlock(ModBlocks.SHADOW_WALL.get(),blockTexture(ModBlocks.SHADOW_BLOCK.get()));

        doorBlockWithRenderType(ModBlocks.SHADOW_DOOR.get(), modLoc("block/shadow_door_bottom"), modLoc("block/shadow_door_top"), "cutout");
        trapdoorBlockWithRenderType(ModBlocks.SHADOW_TRAPDOOR.get(), modLoc("block/shadow_trapdoor"),true, "cutout");

        blockItem(ModBlocks.SHADOW_STAIRS);
        blockItem(ModBlocks.SHADOW_SLAB);
        blockItem(ModBlocks.SHADOW_PRESSURE_PLATE);
        blockItem(ModBlocks.SHADOW_BUTTON);
        blockItem(ModBlocks.SHADOW_FENCE_GATE);
        blockItem(ModBlocks.SHADOW_WALL);
        blockItem(ModBlocks.SHADOW_TRAPDOOR, "_bottom");

        //CROPS

        makeCrop (((CropBlock) ModBlocks.RADISH_CROP.get()), "radish_crop_stage","radish_crop_stage");

        makeBush(((SweetBerryBushBlock) ModBlocks.SHADOW_BERRY_BUSH.get()),"shadow_berry_bush_stage","shadow_berry_bush_stage");

        //ORES

        blockWithItem(ModBlocks.SHADOW_ORE);
        blockWithItem(ModBlocks.SHADOW_DEEPSLATE_ORE);
        blockWithItem(ModBlocks.SHADOW_NETHER_ORE);
        blockWithItem(ModBlocks.SHADOW_END_ORE);

        //ADVANCED BLOCKS

        blockWithItem(ModBlocks.MAGIC_BLOCK);

        //TREES
        logBlock((RotatedPillarBlock) ModBlocks.SHADOWWOOD_LOG.get());
        axisBlock(((RotatedPillarBlock) ModBlocks.SHADOWWOOD_WOOD.get()),blockTexture(ModBlocks.SHADOWWOOD_LOG.get()),blockTexture(ModBlocks.SHADOWWOOD_LOG.get()));


        logBlock((RotatedPillarBlock) ModBlocks.STRIPPED_SHADOWWOOD_LOG.get());
        axisBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_SHADOWWOOD_WOOD.get()),blockTexture(ModBlocks.STRIPPED_SHADOWWOOD_LOG.get()),blockTexture(ModBlocks.STRIPPED_SHADOWWOOD_LOG.get()));

        blockItem(ModBlocks.SHADOWWOOD_LOG);
        blockItem(ModBlocks.SHADOWWOOD_WOOD);
        blockItem(ModBlocks.STRIPPED_SHADOWWOOD_LOG);
        blockItem(ModBlocks.STRIPPED_SHADOWWOOD_WOOD);

        blockWithItem(ModBlocks.SHADOWWOOD_PLANKS);

        leavesBlock(ModBlocks.SHADOWWOOD_LEAVES);
        saplingBlock(ModBlocks.SHADOWWOOD_SAPLING);

    }

    private void saplingBlock(DeferredBlock<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
                models().cross(BuiltInRegistries.BLOCK.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    private void leavesBlock(DeferredBlock<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(),
                models().singleTexture(BuiltInRegistries.BLOCK.getKey(blockRegistryObject.get()).getPath(), ResourceLocation.parse("minecraft:block/leaves"),
                        "all", blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }


    public void makeBush(SweetBerryBushBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> states(state, modelName, textureName);

        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] states(BlockState state, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().cross(modelName + state.getValue(ShadowBerryBushBlock.AGE),
                ResourceLocation.fromNamespaceAndPath(ShadowThings.MODID, "block/" + textureName + state.getValue(ShadowBerryBushBlock.AGE))).renderType("cutout"));

        return models;
    }

    public void makeCrop(CropBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> states(state, block, modelName, textureName);

        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] states(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().crop(modelName + state.getValue(((RadishCropBlock) block).getAgeProperty()),
                ResourceLocation.fromNamespaceAndPath(ShadowThings.MODID, "block/" + textureName + state.getValue(((RadishCropBlock) block).getAgeProperty()))).renderType("cutout"));

        return models;
    }

    private void blockWithItem(DeferredBlock<?> deferredBlock){
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }
    private void blockItem(DeferredBlock<?> deferredBlock) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("shadowthings:block/" + deferredBlock.getId().getPath()));
    }

    private void blockItem(DeferredBlock<?> deferredBlock, String appendix) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("shadowthings:block/" + deferredBlock.getId().getPath() + appendix));
    }
}
