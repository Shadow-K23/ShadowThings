package com.shadow.shadowthings.util;

import com.shadow.shadowthings.ShadowThings;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks{

        public static final TagKey<Block> NEEDS_SHADOW_TOOL = createTag("needs_shadow_tool");
        public static final TagKey<Block> INCORRECT_FOR_SHADOW_TOOL = createTag("incorrect_for_shadow_tool");

        private static TagKey<Block> createTag(String name){
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(ShadowThings.MODID, name));
        }
    }

    public static class Items{
        public static final TagKey<Item> TRANSFORMABLE_ITEMS = createTag("transformable_items");

        public static final TagKey<Item>  CRAFTER_UPGRADES = createTag("upgrades/crafter");
        public static final TagKey<Item>  CORE_UPGRADES = createTag("upgrades/core");
        public static final TagKey<Item>  FURNACE_UPGRADES = createTag("upgrades/furnace");
        public static final TagKey<Item>  CRUCIBLE_UPGRADES = createTag("upgrades/crucible");
        public static final TagKey<Item>  COLLECTOR_UPGRADES = createTag("upgrades/collector");

        private static TagKey<Item> createTag(String name){
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(ShadowThings.MODID, name));
        }

    }
}
