package com.shadow.shadowthings.datagen;

import com.shadow.shadowthings.ShadowThings;
import com.shadow.shadowthings.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

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

        //ORES

        blockWithItem(ModBlocks.SHADOW_ORE);
        blockWithItem(ModBlocks.SHADOW_DEEPSLATE_ORE);

        //ADVANCED BLOCKS

        blockWithItem(ModBlocks.MAGIC_BLOCK);
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
