package com.shadow.shadowthings.datagen;

import com.shadow.shadowthings.ShadowThings;
import com.shadow.shadowthings.item.ModItems;
import com.shadow.shadowthings.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider{



    public
        ModItemTagProvider(PackOutput output, CompletableFuture < HolderLookup.Provider > lookupProvider, CompletableFuture < TagLookup < Block >> blockTags, @Nullable ExistingFileHelper existingFileHelper)
        {
            super(output, lookupProvider, blockTags, ShadowThings.MODID, existingFileHelper);
        }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModTags.Items.TRANSFORMABLE_ITEMS)
                .add(ModItems.SHADOWINGOT.get())
                .add(ModItems.RAWSHADOWINGOT.get());
    }
}