package com.shadow.testmod.item;

import com.shadow.testmod.TestMod;
import com.shadow.testmod.block.ModBlocks;
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
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TestMod.MODID);


    public static final Supplier<CreativeModeTab> SHADOW_ITEMS_TAB = CREATIVE_MODE_TAB.register("shadow_items_tab",
            () -> CreativeModeTab.builder()
                    .icon(  () -> new ItemStack(ModItems.SHADOWINGOT.get()))
                    .title(Component.translatable("creativetab.testmod.shadow_items"))
                    .displayItems(((itemDisplayParameters, output) -> {
                        output.accept(ModItems.SHADOWINGOT);
                        output.accept(ModItems.RAWSHADOWINGOT);
                        output.accept(ModItems.SHADOWAXE);
                        output.accept(ModItems.SHADOWHOE);
                        output.accept(ModItems.SHADOWPICKAXE);
                        output.accept(ModItems.SHADOWSHOVEL);
                        output.accept(ModItems.SHADOWSPEAR);
                        output.accept(ModItems.SHADOWSWORD);

                        output.accept((ModItems.CHISEL));

                    }))

                    .build());
    public static final Supplier<CreativeModeTab> SHADOW_BLOCKS_TAB = CREATIVE_MODE_TAB.register("shadow_block_tab",
            () -> CreativeModeTab.builder()
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(TestMod.MODID,"shadow_items_tab"))
                    .icon(  () -> new ItemStack(ModBlocks.SHADOW_BLOCK.get()))
                    .title(Component.translatable("creativetab.testmod.shadow_blocks"))
                    .displayItems(((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.SHADOW_BLOCK);
                        output.accept(ModBlocks.SHADOW_ORE);
                        output.accept(ModBlocks.SHADOW_DEEPSLATE_ORE);
                    }))
                    .build());


    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TAB.register(eventBus);
    }

}
