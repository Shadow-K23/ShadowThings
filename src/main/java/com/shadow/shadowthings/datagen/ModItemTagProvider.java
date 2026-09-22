package com.shadow.shadowthings.datagen;

import com.shadow.shadowthings.ShadowThings;
import com.shadow.shadowthings.block.ModBlocks;
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

        this.tag(ItemTags.WEAPON_ENCHANTABLE)
                .add(ModItems.SHADOW_SCYTHE.get());

        this.tag(ItemTags.SWORDS)
                .add(ModItems.SHADOW_SWORD.get())
                .add(ModItems.SHADOW_HAMMER.get())
                .add(ModItems.SHADOW_SCYTHE.get());

        tag(ItemTags.PICKAXES)
                .add(ModItems.SHADOW_PICKAXE.get())
                .add(ModItems.SHADOW_HAMMER.get());
        tag(ItemTags.AXES)
                .add(ModItems.SHADOW_AXE.get());
        tag(ItemTags.HOES)
                .add(ModItems.SHADOW_HOE.get());
        tag(ItemTags.SHOVELS)
                .add(ModItems.SHADOW_SHOVEL.get());


        this.tag(ItemTags.TRIMMABLE_ARMOR)
                        .add(ModItems.SHADOW_HELMET.get())
                        .add(ModItems.SHADOW_CHESTPLATE.get())
                        .add(ModItems.SHADOW_LEGGINGS.get())
                        .add(ModItems.SHADOW_BOOTS.get());


        //TOOLTIPS
        tag(ModTags.Items.ADVANCED_ITEM_TOOLTIP)
                .add(ModItems.SHADOW_SCYTHE.get())
                .add(ModItems.SOUL_MATRIX.get())
                .add(ModItems.SHADOW_HAMMER.get())
                .add(ModItems.SOUL_UPGRADE_OVERLOAD.get())
                .add(ModItems.SOUL_LINKER.get())
                .add(ModItems.SOUL_FRUIT_1.get())
                .add(ModItems.SOUL_FRUIT_2.get())
                .add(ModItems.SOUL_FRUIT_3.get());

        //UPGRADE TAGS

        tag(ModTags.Items.CORE_UPGRADES)
                .add(ModItems.SOUL_UPGRADE_CAPACITY_1.get())
                .add(ModItems.SOUL_UPGRADE_CAPACITY_2.get())
                .add(ModItems.SOUL_UPGRADE_CAPACITY_3.get())
                .add(ModItems.SOUL_UPGRADE_CAPACITY_4.get())
                .add(ModItems.SOUL_UPGRADE_TRANSFER_RATE_1.get())
                .add(ModItems.SOUL_UPGRADE_TRANSFER_RATE_2.get())
                .add(ModItems.SOUL_UPGRADE_TRANSFER_RATE_3.get())
                .add(ModItems.SOUL_UPGRADE_TRANSFER_RATE_4.get())
                .add(ModItems.SOUL_UPGRADE_TRANSFER_AMOUNT_1.get())
                .add(ModItems.SOUL_UPGRADE_TRANSFER_AMOUNT_2.get())
                .add(ModItems.SOUL_UPGRADE_TRANSFER_AMOUNT_3.get())
                .add(ModItems.SOUL_UPGRADE_TRANSFER_AMOUNT_4.get())

                .add(ModItems.SOUL_UPGRADE_REDSTONE_CONTROL.get())
                .add(ModItems.SOUL_UPGRADE_OVERLOAD.get());

        tag(ModTags.Items.CRAFTER_UPGRADES)
                .add(ModItems.SOUL_UPGRADE_SOUL_USAGE_EFFICIENCY_1.get())
                .add(ModItems.SOUL_UPGRADE_SOUL_USAGE_EFFICIENCY_2.get())
                .add(ModItems.SOUL_UPGRADE_SOUL_USAGE_EFFICIENCY_3.get())
                .add(ModItems.SOUL_UPGRADE_SOUL_USAGE_EFFICIENCY_4.get())

                .add(ModItems.SOUL_UPGRADE_TRANSFER_RATE_1.get())
                .add(ModItems.SOUL_UPGRADE_TRANSFER_RATE_2.get())
                .add(ModItems.SOUL_UPGRADE_TRANSFER_RATE_3.get())
                .add(ModItems.SOUL_UPGRADE_TRANSFER_RATE_4.get())

                .add(ModItems.SOUL_UPGRADE_REDSTONE_CONTROL.get())
                .add(ModItems.SOUL_UPGRADE_OVERLOAD.get());

        tag(ModTags.Items.FURNACE_UPGRADES)
                .add(ModItems.SOUL_UPGRADE_SOUL_USAGE_EFFICIENCY_1.get())
                .add(ModItems.SOUL_UPGRADE_SOUL_USAGE_EFFICIENCY_2.get())
                .add(ModItems.SOUL_UPGRADE_SOUL_USAGE_EFFICIENCY_3.get())
                .add(ModItems.SOUL_UPGRADE_SOUL_USAGE_EFFICIENCY_4.get())
                .add(ModItems.SOUL_UPGRADE_SMELT_AMOUNT_1.get())
                .add(ModItems.SOUL_UPGRADE_SMELT_AMOUNT_2.get())
                .add(ModItems.SOUL_UPGRADE_SMELT_AMOUNT_3.get())
                .add(ModItems.SOUL_UPGRADE_SMELT_AMOUNT_4.get())
                .add(ModItems.SOUL_UPGRADE_SMELT_SPEED_1.get())
                .add(ModItems.SOUL_UPGRADE_SMELT_SPEED_2.get())
                .add(ModItems.SOUL_UPGRADE_SMELT_SPEED_3.get())
                .add(ModItems.SOUL_UPGRADE_SMELT_SPEED_4.get())

                .add(ModItems.SOUL_UPGRADE_REDSTONE_CONTROL.get())
                .add(ModItems.SOUL_UPGRADE_OVERLOAD.get());


        tag(ItemTags.HEAD_ARMOR)
            .add(ModItems.SHADOW_HELMET.get());
        tag(ItemTags.CHEST_ARMOR)
                .add(ModItems.SHADOW_CHESTPLATE.get());
        tag(ItemTags.LEG_ARMOR)
                .add(ModItems.SHADOW_LEGGINGS.get());
        tag(ItemTags.FOOT_ARMOR)
                .add(ModItems.SHADOW_BOOTS.get());

        this.tag(ItemTags.LOGS_THAT_BURN)
                .add(ModBlocks.SHADOWWOOD_LOG.get().asItem())
                .add(ModBlocks.STRIPPED_SHADOWWOOD_LOG.get().asItem())
                .add(ModBlocks.SHADOWWOOD_WOOD.get().asItem())
                .add(ModBlocks.STRIPPED_SHADOWWOOD_WOOD.get().asItem());
        this.tag(ItemTags.PLANKS)
                .add(ModBlocks.SHADOWWOOD_PLANKS.get().asItem());

    }
}