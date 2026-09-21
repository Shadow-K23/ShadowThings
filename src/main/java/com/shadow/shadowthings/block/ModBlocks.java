package com.shadow.shadowthings.block;

import com.shadow.shadowthings.ShadowThings;
import com.shadow.shadowthings.block.custom.*;
import com.shadow.shadowthings.item.ModItems;
import com.shadow.shadowthings.sound.ModSounds;
import com.shadow.shadowthings.worldgen.Tree.ModTreeGrowers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
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

    //ORES

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
    public static final DeferredBlock<Block> SHADOW_NETHER_ORE = registerBlock("shadow_nether_ore",
            () -> new DropExperienceBlock(UniformInt.of(2,6),BlockBehaviour.Properties.of()
                    .strength(4f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.ANCIENT_DEBRIS)));
    public static final DeferredBlock<Block> SHADOW_END_ORE = registerBlock("shadow_end_ore",
            () -> new DropExperienceBlock(UniformInt.of(2,6),BlockBehaviour.Properties.of()
                    .strength(4f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.ANCIENT_DEBRIS)));


    //PLANTS

    public static final DeferredBlock<Block> RADISH_CROP = BLOCKS.register("radish_crop",
            () -> new RadishCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEETROOTS)));

    public static final DeferredBlock<Block> SHADOW_BERRY_BUSH = BLOCKS.register("shadow_berry_bush",
            () -> new ShadowBerryBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH)));


    //ADVANCED BLOCKS

    public static final DeferredBlock<Block> SHADOW_MACHINE_BLOCK = registerBlock("shadow_machine_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(2f)
                    .sound(SoundType.METAL)));

    //SHADOW SOULS RELATED BLOCKS

    public static final DeferredBlock<Block> SOUL_CORE = registerBlock("soul_core",
            () -> new SoulCoreBlock(BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .strength(2f)
                    .sound(SoundType.SCULK_CATALYST)));
    public static final DeferredBlock<Block> SOUL_STRUCTURE_BLOCK = registerBlock("soul_structure_block",
            () -> new SoulStructureBlock(BlockBehaviour.Properties.of()
                    .strength(2f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL)));

    public static final DeferredBlock<Block> SOUL_PEDESTAL = registerBlock("soul_pedestal",
            () -> new SoulPedestalBlock(BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .strength(2f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL)));

    public static final DeferredBlock<Block> SOUL_CRAFTER = registerBlock("soul_crafter",
            () -> new SoulCrafterBlock(BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .strength(2f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.SCULK_CATALYST)));

    public static final DeferredBlock<Block> SOUL_FURNACE_CONTROLLER = registerBlock("soul_furnace_controller",
            () -> new SoulFurnaceBlock(BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .strength(2f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.SCULK_CATALYST)));

    public static final DeferredBlock<Block> SOUL_CONDENSER = registerBlock("soul_condenser",
            () -> new SoulCondenserBlock(BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .strength(2f)
                    .sound(SoundType.SCULK)));

    public static final DeferredBlock<Block> SOUL_CRUCIBLE = registerBlock("soul_crucible",
            () -> new SoulCrucibleBlock(BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .strength(2f)
                    .sound(SoundType.SCULK_SENSOR)));

    public static final DeferredBlock<Block> SOUL_COLLECTOR = registerBlock("soul_collector",
            () -> new SoulCollectorBlock(BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .strength(2f)
                    .sound(SoundType.SCULK_CATALYST)));

    //SHADOW TREE

    public static final DeferredBlock<Block> SHADOWWOOD_LOG = registerBlock("shadowwood_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)));
    public static final DeferredBlock<Block> SHADOWWOOD_WOOD = registerBlock("shadowwood_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));
    public static final DeferredBlock<Block> STRIPPED_SHADOWWOOD_LOG = registerBlock("stripped_shadowwood_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG)));
    public static final DeferredBlock<Block> STRIPPED_SHADOWWOOD_WOOD = registerBlock("stripped_shadowwood_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)));

    public static final DeferredBlock<Block> SHADOWWOOD_PLANKS = registerBlock("shadowwood_planks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)){
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }
            });
    public static final DeferredBlock<Block> SHADOWWOOD_LEAVES = registerBlock("shadowwood_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)){
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 60;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 30;
                }
            });
    public static final DeferredBlock<Block> SHADOWWOOD_SAPLING = registerBlock("shadowwood_sapling",
            () -> new SaplingBlock(ModTreeGrowers.SHADOWWOOD,BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)));


    //NON-BLOCK BLOCKS


    public static final DeferredBlock<StairBlock> SHADOW_STAIRS = registerBlock("shadow_stairs",
            () -> new StairBlock(ModBlocks.SHADOWWOOD_PLANKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.of().strength(2f).sound(SoundType.WOOD).requiresCorrectToolForDrops()));
    public static final DeferredBlock<SlabBlock> SHADOW_SLAB = registerBlock("shadow_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().strength(2f).sound(SoundType.WOOD).requiresCorrectToolForDrops()));

    public static final DeferredBlock<PressurePlateBlock> SHADOW_PRESSURE_PLATE = registerBlock("shadow_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.OAK, BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<ButtonBlock> SHADOW_BUTTON = registerBlock("shadow_button",
            () -> new ButtonBlock(BlockSetType.OAK,20, BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops().noCollission()));

    public static final DeferredBlock<FenceBlock> SHADOW_FENCE = registerBlock("shadow_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.of().strength(2f).sound(SoundType.WOOD).requiresCorrectToolForDrops()));
    public static final DeferredBlock<FenceGateBlock> SHADOW_FENCE_GATE = registerBlock("shadow_fence_gate",
            () -> new FenceGateBlock(WoodType.ACACIA,BlockBehaviour.Properties.of().strength(2f).sound(SoundType.WOOD).requiresCorrectToolForDrops()));

    public static final DeferredBlock<DoorBlock> SHADOW_DOOR = registerBlock("shadow_door",
            () -> new DoorBlock(BlockSetType.OAK,BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops().noOcclusion()));
    public static final DeferredBlock<TrapDoorBlock> SHADOW_TRAPDOOR = registerBlock("shadow_trapdoor",
            () -> new TrapDoorBlock(BlockSetType.OAK,BlockBehaviour.Properties.of().strength(2f).sound(SoundType.WOOD).requiresCorrectToolForDrops().noOcclusion()));


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
