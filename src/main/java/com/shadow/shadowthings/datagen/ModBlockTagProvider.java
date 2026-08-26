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
                .add(ModBlocks.MAGIC_BLOCK.get());

        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.SHADOW_ORE.get())
                .add(ModBlocks.SHADOW_DEEPSLATE_ORE.get());

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
    }
}
