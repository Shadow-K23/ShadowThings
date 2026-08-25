package com.shadow.shadowthings.datagen;

import com.shadow.shadowthings.ShadowThings;
import com.shadow.shadowthings.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
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
        //ORES
        blockWithItem(ModBlocks.SHADOW_ORE);
        blockWithItem(ModBlocks.SHADOW_DEEPSLATE_ORE);
        //ADVANCED BLOCKS
        blockWithItem(ModBlocks.MAGIC_BLOCK);
    }

    private void blockWithItem(DeferredBlock<?> deferredBlock){
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }
}
