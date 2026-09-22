package com.shadow.shadowthings;

import com.shadow.shadowthings.block.ModBlocks;
import com.shadow.shadowthings.item.ModItems;
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
                    .icon(  () -> new ItemStack(ModItems.SHADOW_INGOT.get()))
                    .title(Component.translatable("creativetab.shadowthings.shadow_items"))
                    .displayItems(((itemDisplayParameters, output) -> {

                        //BASIC ITEMS
                        output.accept(ModItems.SHADOW_INGOT);
                        output.accept(ModItems.RAW_SHADOW_INGOT);
                        output.accept(ModItems.SHADOW_SWORD);
                        output.accept(ModItems.SHADOW_PICKAXE);
                        output.accept(ModItems.SHADOW_AXE);
                        output.accept(ModItems.SHADOW_SHOVEL);
                        output.accept(ModItems.SHADOW_HOE);

                        //WEAPONS
                        output.accept(ModItems.SHADOW_BOW);
                        output.accept(ModItems.SHADOW_SCYTHE);

                        //ADVANCED ITEMS
                        output.accept(ModItems.SOUL_LINKER);
                        output.accept(ModItems.SHADOW_HAMMER);

                        //ARMOR
                        output.accept(ModItems.SHADOW_HELMET);
                        output.accept(ModItems.SHADOW_CHESTPLATE);
                        output.accept(ModItems.SHADOW_LEGGINGS);
                        output.accept(ModItems.SHADOW_BOOTS);
                        //output.accept(ModItems.SHADOW_HORSE_ARMOR);

                        //COMPONENTS

                        output.accept(ModItems.SOUL_MATRIX);

                        output.accept(ModItems.SOUL_UPGRADE_BASE_1);
                        output.accept(ModItems.SOUL_UPGRADE_BASE_2);
                        output.accept(ModItems.SOUL_UPGRADE_BASE_3);
                        output.accept(ModItems.SOUL_UPGRADE_BASE_4);
                        output.accept(ModItems.SOUL_UPGRADE_BASE_5);

                        //SOUL UPGRADES

                        output.accept(ModItems.SOUL_UPGRADE_CAPACITY_1);
                        output.accept(ModItems.SOUL_UPGRADE_CAPACITY_2);
                        output.accept(ModItems.SOUL_UPGRADE_CAPACITY_3);
                        output.accept(ModItems.SOUL_UPGRADE_CAPACITY_4);
                        output.accept(ModItems.SOUL_UPGRADE_TRANSFER_AMOUNT_1);
                        output.accept(ModItems.SOUL_UPGRADE_TRANSFER_AMOUNT_2);
                        output.accept(ModItems.SOUL_UPGRADE_TRANSFER_AMOUNT_3);
                        output.accept(ModItems.SOUL_UPGRADE_TRANSFER_AMOUNT_4);
                        output.accept(ModItems.SOUL_UPGRADE_TRANSFER_RATE_1);
                        output.accept(ModItems.SOUL_UPGRADE_TRANSFER_RATE_2);
                        output.accept(ModItems.SOUL_UPGRADE_TRANSFER_RATE_3);
                        output.accept(ModItems.SOUL_UPGRADE_TRANSFER_RATE_4);
                        output.accept(ModItems.SOUL_UPGRADE_SOUL_USAGE_EFFICIENCY_1);
                        output.accept(ModItems.SOUL_UPGRADE_SOUL_USAGE_EFFICIENCY_2);
                        output.accept(ModItems.SOUL_UPGRADE_SOUL_USAGE_EFFICIENCY_3);
                        output.accept(ModItems.SOUL_UPGRADE_SOUL_USAGE_EFFICIENCY_4);
                        output.accept(ModItems.SOUL_UPGRADE_SMELT_AMOUNT_1);
                        output.accept(ModItems.SOUL_UPGRADE_SMELT_AMOUNT_2);
                        output.accept(ModItems.SOUL_UPGRADE_SMELT_AMOUNT_3);
                        output.accept(ModItems.SOUL_UPGRADE_SMELT_AMOUNT_4);
                        output.accept(ModItems.SOUL_UPGRADE_SMELT_SPEED_1);
                        output.accept(ModItems.SOUL_UPGRADE_SMELT_SPEED_2);
                        output.accept(ModItems.SOUL_UPGRADE_SMELT_SPEED_3);
                        output.accept(ModItems.SOUL_UPGRADE_SMELT_SPEED_4);


                        output.accept(ModItems.SOUL_UPGRADE_REDSTONE_CONTROL);
                        output.accept(ModItems.SOUL_UPGRADE_OVERLOAD);

                        //FOODS
                        output.accept(ModItems.SOUL_FRUIT_1);
                        output.accept(ModItems.SOUL_FRUIT_2);
                        output.accept(ModItems.SOUL_FRUIT_3);
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
                    .icon(  () -> new ItemStack(ModBlocks.SHADOWWOOD_PLANKS.get()))
                    .title(Component.translatable("creativetab.shadowthings.shadow_blocks"))
                    .displayItems(((itemDisplayParameters, output) -> {

                        //BASIC BLOCKS
                        output.accept(ModBlocks.SHADOW_BLOCK);
                        output.accept(ModBlocks.SHADOW_ORE);
                        output.accept(ModBlocks.SHADOW_DEEPSLATE_ORE);
                        output.accept(ModBlocks.SHADOW_NETHER_ORE);
                        output.accept(ModBlocks.SHADOW_END_ORE);


                        //ADVANCED BLOCKS

                        output.accept(ModBlocks.SHADOW_MACHINE_BLOCK);
                        output.accept(ModBlocks.SOUL_CONDENSER);
                        output.accept(ModBlocks.SOUL_CRUCIBLE);
                        output.accept(ModBlocks.SOUL_COLLECTOR);

                        //SOUL RELATED

                        output.accept(ModBlocks.SOUL_STRUCTURE_BLOCK);
                        output.accept(ModBlocks.SOUL_CORE);
                        output.accept(ModBlocks.SOUL_FURNACE_CONTROLLER);
                        output.accept(ModBlocks.SOUL_CRAFTER);
                        output.accept(ModBlocks.SOUL_PEDESTAL);

                        //TREES
                        output.accept((ModBlocks.SHADOWWOOD_LOG));
                        output.accept((ModBlocks.SHADOWWOOD_WOOD));
                        output.accept((ModBlocks.STRIPPED_SHADOWWOOD_LOG));
                        output.accept((ModBlocks.STRIPPED_SHADOWWOOD_WOOD));

                        output.accept((ModBlocks.SHADOWWOOD_PLANKS));
                        output.accept((ModBlocks.SHADOWWOOD_SAPLING));
                        
                        output.accept((ModBlocks.SHADOWWOOD_LEAVES));

                        output.accept(ModBlocks.SHADOW_STAIRS);
                        output.accept(ModBlocks.SHADOW_SLAB);

                        output.accept(ModBlocks.SHADOW_PRESSURE_PLATE);
                        output.accept(ModBlocks.SHADOW_BUTTON);

                        output.accept(ModBlocks.SHADOW_FENCE);
                        output.accept(ModBlocks.SHADOW_FENCE_GATE);

                        output.accept(ModBlocks.SHADOW_DOOR);
                        output.accept(ModBlocks.SHADOW_TRAPDOOR);

                    }))
                    .build());


    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TAB.register(eventBus);
    }

}
