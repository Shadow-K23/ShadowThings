package com.shadow.testmod.block;

import com.shadow.testmod.TestMod;
import com.shadow.testmod.block.custom.MagicBlock;
import com.shadow.testmod.item.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(TestMod.MODID);


    public static final DeferredBlock<Block> SHADOW_BLOCK = registerBlock("shadow_block", () -> new Block(BlockBehaviour.Properties.of()
            .strength(4f)
            .requiresCorrectToolForDrops()
            .sound(SoundType.NETHERITE_BLOCK)));

    public static final DeferredBlock<Block> SHADOW_ORE = registerBlock("shadow_ore", () -> new DropExperienceBlock(UniformInt.of(2,6),BlockBehaviour.Properties.of()
            .strength(4f)
            .requiresCorrectToolForDrops()
            .sound(SoundType.ANCIENT_DEBRIS)));

    public static final DeferredBlock<Block> SHADOW_DEEPSLATE_ORE = registerBlock("shadow_deepslate_ore", () -> new DropExperienceBlock(UniformInt.of(2,6),BlockBehaviour.Properties.of()
            .strength(4f)
            .requiresCorrectToolForDrops()
            .sound(SoundType.ANCIENT_DEBRIS)));

    public static final DeferredBlock<Block> MAGIC_BLOCK = registerBlock( "magic_block", () -> new MagicBlock(BlockBehaviour.Properties.of()
            .strength(2f)
            .requiresCorrectToolForDrops()));


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
