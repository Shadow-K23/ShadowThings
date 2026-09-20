package com.shadow.shadowthings.datagen;

import com.shadow.shadowthings.ShadowThings;
import com.shadow.shadowthings.block.ModBlocks;
import com.shadow.shadowthings.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,@Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, ShadowThings.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.SHADOW_BLOCK.get())
                .add(ModBlocks.SHADOW_ORE.get())
                .add(ModBlocks.SHADOW_DEEPSLATE_ORE.get())
                .add(ModBlocks.SHADOW_NETHER_ORE.get())
                .add(ModBlocks.SHADOW_END_ORE.get())

                .add(ModBlocks.SOUL_CORE.get())
                .add(ModBlocks.SOUL_STRUCTURE_BLOCK.get())
                .add(ModBlocks.SOUL_CRAFTER.get())
                .add(ModBlocks.SOUL_PEDESTAL.get())
                .add(ModBlocks.SOUL_FURNACE_CONTROLLER.get());

        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.SHADOW_ORE.get())
                .add(ModBlocks.SHADOW_DEEPSLATE_ORE.get())
                .add(ModBlocks.SHADOW_NETHER_ORE.get())
                .add(ModBlocks.SHADOW_END_ORE.get());

        tag(BlockTags.FENCES)
                .add(ModBlocks.SHADOW_FENCE.get());

        tag(BlockTags.FENCE_GATES)
                .add(ModBlocks.SHADOW_FENCE_GATE.get());

        tag(BlockTags.WALLS)
                .add(ModBlocks.SHADOW_WALL.get());


        tag(ModTags.Blocks.NEEDS_SHADOW_TOOL)
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL);

        tag(ModTags.Blocks.INCORRECT_FOR_SHADOW_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_DIAMOND_TOOL)
                .remove(ModTags.Blocks.NEEDS_SHADOW_TOOL);

        this.tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.SHADOWWOOD_LOG.get())
                .add(ModBlocks.STRIPPED_SHADOWWOOD_LOG.get())
                .add(ModBlocks.SHADOWWOOD_WOOD.get())
                .add(ModBlocks.STRIPPED_SHADOWWOOD_WOOD.get());


    }
}
