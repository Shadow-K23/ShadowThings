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
                        output.accept(ModItems.SHADOWAXE);
                        output.accept(ModItems.SHADOWHOE);
                        output.accept(ModItems.SHADOWPICKAXE);
                        output.accept(ModItems.SHADOWSHOVEL);
                        output.accept(ModItems.SHADOWSPEAR);
                        output.accept(ModItems.SHADOWSWORD);
                        //ADVANCED ITEMS
                        output.accept(ModItems.CHISEL);
                        //FOODS
                        output.accept(ModItems.DRAGON_FRUIT);
                        //FUELS
                        output.accept(ModItems.SUPER_FUEL);
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
                        //ADVANCED BLOCKS
                        output.accept(ModBlocks.MAGIC_BLOCK);
                    }))
                    .build());


    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TAB.register(eventBus);
    }

}
