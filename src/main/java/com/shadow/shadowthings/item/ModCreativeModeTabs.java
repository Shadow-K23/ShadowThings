package com.shadow.shadowthings.item;

import com.shadow.shadowthings.ShadowThings;
import com.shadow.shadowthings.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ShadowThings.MODID);


    public static final Supplier<CreativeModeTab> SHADOW_ITEMS_TAB = CREATIVE_MODE_TAB.register("shadow_items_tab",
            () -> CreativeModeTab.builder()
                    .icon(  () -> new ItemStack(ModItems.SHADOWINGOT.get()))
                    .title(Component.translatable("creativetab.shadowthings.shadow_items"))
                    .displayItems(((itemDisplayParameters, output) -> {

                        //BASIC ITEMS
                        output.accept(ModItems.SHADOWINGOT);
                        output.accept(ModItems.RAWSHADOWINGOT);
                        output.accept(ModItems.SHADOWSWORD);
                        output.accept(ModItems.SHADOWPICKAXE);
                        output.accept(ModItems.SHADOWAXE);
                        output.accept(ModItems.SHADOWSHOVEL);
                        output.accept(ModItems.SHADOWHOE);
                        output.accept(ModItems.SHADOWSPEAR);
                        //WEAPONS
                        output.accept(ModItems.SHADOW_BOW);
                        //ADVANCED ITEMS
                        output.accept(ModItems.CHISEL);
                        output.accept(ModItems.SHADOWHAMMER);
                        //ARMOR
                        output.accept(ModItems.SHADOW_HELMET);
                        output.accept(ModItems.SHADOW_CHESTPLATE);
                        output.accept(ModItems.SHADOW_LEGGINGS);
                        output.accept(ModItems.SHADOW_BOOTS);
                        output.accept(ModItems.SHADOW_HORSE_ARMOR);
                        //FOODS
                        output.accept(ModItems.DRAGON_FRUIT);
                        output.accept(ModItems.SHADOW_BERRIES);
                        //SEEDS
                        output.accept(ModItems.RADISH_SEEDS);
                        //FUELS
                        output.accept(ModItems.SUPER_FUEL);
                        //MISC
                        output.accept(ModItems.BAR_BRAWL_MUSIC_DISC);
                    }))

                    .build());
    public static final Supplier<CreativeModeTab> SHADOW_BLOCKS_TAB = CREATIVE_MODE_TAB.register("shadow_block_tab",
            () -> CreativeModeTab.builder()
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(ShadowThings.MODID,"shadow_items_tab"))
                    .icon(  () -> new ItemStack(ModBlocks.SHADOW_BLOCK.get()))
                    .title(Component.translatable("creativetab.shadowthings.shadow_blocks"))
                    .displayItems(((itemDisplayParameters, output) -> {
                        //BASIC BLOCKS
                        output.accept(ModBlocks.SHADOW_BLOCK);
                        output.accept(ModBlocks.SHADOW_ORE);
                        output.accept(ModBlocks.SHADOW_DEEPSLATE_ORE);


                        output.accept(ModBlocks.SHADOW_STAIRS);
                        output.accept(ModBlocks.SHADOW_SLAB);

                        output.accept(ModBlocks.SHADOW_PRESSURE_PLATE);
                        output.accept(ModBlocks.SHADOW_BUTTON);

                        output.accept(ModBlocks.SHADOW_FENCE);
                        output.accept(ModBlocks.SHADOW_FENCE_GATE);
                        output.accept(ModBlocks.SHADOW_WALL);

                        output.accept(ModBlocks.SHADOW_DOOR);
                        output.accept(ModBlocks.SHADOW_TRAPDOOR);

                        //ADVANCED BLOCKS
                        output.accept(ModBlocks.MAGIC_BLOCK);
                    }))
                    .build());


    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TAB.register(eventBus);
    }

}
