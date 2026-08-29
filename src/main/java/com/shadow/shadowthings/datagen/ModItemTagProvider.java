package com.shadow.shadowthings.datagen;

import com.shadow.shadowthings.ShadowThings;
import com.shadow.shadowthings.item.ModItems;
import com.shadow.shadowthings.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.common.Mod;
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

        tag(ItemTags.WEAPON_ENCHANTABLE);


        tag(ItemTags.SWORDS)
                .add(ModItems.SHADOWSWORD.get())
                .add(ModItems.SHADOWHAMMER.get());
        tag(ItemTags.PICKAXES)
                .add(ModItems.SHADOWPICKAXE.get())
                .add(ModItems.SHADOWHAMMER.get());
        tag(ItemTags.AXES)
                .add(ModItems.SHADOWAXE.get());
        tag(ItemTags.HOES)
                .add(ModItems.SHADOWHOE.get());
        tag(ItemTags.SHOVELS)
                .add(ModItems.SHADOWSHOVEL.get());

        this.tag(ItemTags.TRIMMABLE_ARMOR)
                        .add(ModItems.SHADOW_HELMET.get())
                        .add(ModItems.SHADOW_CHESTPLATE.get())
                        .add(ModItems.SHADOW_LEGGINGS.get())
                        .add(ModItems.SHADOW_BOOTS.get());

        tag(ItemTags.HEAD_ARMOR)
            .add(ModItems.SHADOW_HELMET.get());
        tag(ItemTags.CHEST_ARMOR)
                .add(ModItems.SHADOW_CHESTPLATE.get());
        tag(ItemTags.LEG_ARMOR)
                .add(ModItems.SHADOW_LEGGINGS.get());
        tag(ItemTags.FOOT_ARMOR)
                .add(ModItems.SHADOW_BOOTS.get());
    }
}