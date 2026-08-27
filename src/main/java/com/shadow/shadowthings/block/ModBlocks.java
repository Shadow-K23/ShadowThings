package com.shadow.shadowthings.block;

import com.shadow.shadowthings.ShadowThings;
import com.shadow.shadowthings.block.custom.MagicBlock;
import com.shadow.shadowthings.block.custom.RadishCropBlock;
import com.shadow.shadowthings.item.ModItems;
import com.shadow.shadowthings.sound.ModSounds;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(ShadowThings.MODID);


    public static final DeferredBlock<Block> SHADOW_BLOCK = registerBlock("shadow_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.NETHERITE_BLOCK)));

    public static final DeferredBlock<Block> SHADOW_ORE = registerBlock("shadow_ore",
            () -> new DropExperienceBlock(UniformInt.of(2,6),BlockBehaviour.Properties.of()
                    .strength(4f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.ANCIENT_DEBRIS)));

    public static final DeferredBlock<Block> SHADOW_DEEPSLATE_ORE = registerBlock("shadow_deepslate_ore",
            () -> new DropExperienceBlock(UniformInt.of(2,6),BlockBehaviour.Properties.of()
                    .strength(4f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.ANCIENT_DEBRIS)));

    public static final DeferredBlock<Block> MAGIC_BLOCK = registerBlock( "magic_block",
            () -> new MagicBlock(BlockBehaviour.Properties.of()
                    .strength(2f)
                    .requiresCorrectToolForDrops()
                    .sound(ModSounds.MAGIC_BLOCK_SOUNDS)));

    //CROPS

    public static final DeferredBlock<Block> RADISH_CROP = BLOCKS.register("",
            () -> new RadishCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEETROOTS)));


    //NON-BLOCK BLOCKS

    public static final DeferredBlock<StairBlock> SHADOW_STAIRS = registerBlock("shadow_stairs",
            () -> new StairBlock(ModBlocks.SHADOW_BLOCK.get().defaultBlockState(),
                    BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<SlabBlock> SHADOW_SLAB = registerBlock("shadow_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops()));

    public static final DeferredBlock<PressurePlateBlock> SHADOW_PRESSURE_PLATE = registerBlock("shadow_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.IRON, BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<ButtonBlock> SHADOW_BUTTON = registerBlock("shadow_button",
            () -> new ButtonBlock(BlockSetType.IRON,20, BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops().noCollission()));

    public static final DeferredBlock<FenceBlock> SHADOW_FENCE = registerBlock("shadow_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<FenceGateBlock> SHADOW_FENCE_GATE = registerBlock("shadow_fence_gate",
            () -> new FenceGateBlock(WoodType.ACACIA,BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<WallBlock> SHADOW_WALL = registerBlock("shadow_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops()));

    public static final DeferredBlock<DoorBlock> SHADOW_DOOR = registerBlock("shadow_door",
            () -> new DoorBlock(BlockSetType.IRON,BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops().noOcclusion()));
    public static final DeferredBlock<TrapDoorBlock> SHADOW_TRAPDOOR = registerBlock("shadow_trapdoor",
            () -> new TrapDoorBlock(BlockSetType.IRON,BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops().noOcclusion()));


    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block){
        DeferredBlock<T> toReturn = BLOCKS.register(name,block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block){
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }


    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
