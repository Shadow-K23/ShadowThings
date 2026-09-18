package com.shadow.shadowthings.block.custom;

import com.mojang.serialization.MapCodec;
import com.shadow.shadowthings.block.ModBlocks;
import com.shadow.shadowthings.block.entity.ModBlockEntities;
import com.shadow.shadowthings.block.entity.SoulFurnaceEntity; // We will create this next!
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class SoulFurnaceBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 16, 16);
    public static final MapCodec<SoulFurnaceBlock> CODEC = simpleCodec(SoulFurnaceBlock::new);
    private static BlockPattern soulFurnacePattern;

    public SoulFurnaceBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(SoulStructureBlock.FORMED, false)
                .setValue(FACING, net.minecraft.core.Direction.NORTH));
    }

    // --- THE MAGNETIC CRADLE PATTERN ---
    public static BlockPattern getOrCreateFurnacePattern() {
        if (soulFurnacePattern == null) {
            soulFurnacePattern = BlockPatternBuilder.start()
                    // Y=2 (Top layer of the corner pillars)
                    .aisle(
                            "O~O",
                            "~~~",
                            "O~O"
                    )
                    // Y=1 (Middle layer of the corner pillars)
                    .aisle(
                            "O~O",
                            "~~~",
                            "O~O"
                    )
                    // Y=0 (The 3x3 solid base)
                    .aisle(
                            "SSS",
                            "SFS",
                            "SSS" //
                    )
                    .where('F', BlockInWorld.hasState(BlockStatePredicate.forBlock(ModBlocks.SOUL_FURNACE_CONTROLLER.get())))
                    .where('S', BlockInWorld.hasState(state -> state.is(ModBlocks.SOUL_STRUCTURE_BLOCK.get()) && !state.getValue(SoulStructureBlock.FORMED)))
                    .where('O', BlockInWorld.hasState(state -> state.is(ModBlocks.SOUL_STRUCTURE_BLOCK.get()) && !state.getValue(SoulStructureBlock.FORMED)))
                    .where('~', BlockInWorld.hasState(BlockStatePredicate.ANY))
                    .build();
        }
        return soulFurnacePattern;
    }

    public static void trySpawnMultiblock(Level level, BlockPos pos) {
        if (level.isClientSide) return;
        BlockPattern.BlockPatternMatch match = getOrCreateFurnacePattern().find(level, pos);

        if (match != null) {
            for (int width = 0; width < getOrCreateFurnacePattern().getWidth(); width++) {
                for (int height = 0; height < getOrCreateFurnacePattern().getHeight(); height++) {
                    for (int depth = 0; depth < getOrCreateFurnacePattern().getDepth(); depth++) {
                        BlockInWorld blockInWorld = match.getBlock(width, height, depth);
                        BlockPos currentPos = blockInWorld.getPos();
                        BlockState currentState = level.getBlockState(currentPos);

                        if (currentState.is(ModBlocks.SOUL_STRUCTURE_BLOCK.get())) {
                            level.setBlock(currentPos, currentState.setValue(SoulStructureBlock.FORMED, true), 3);
                        } else if (currentState.is(ModBlocks.SOUL_FURNACE_CONTROLLER.get())) {
                            BlockState newFurnaceState = currentState.setValue(SoulStructureBlock.FORMED, true);
                            level.setBlock(currentPos, newFurnaceState, 3);

                            if (level.getBlockEntity(currentPos) instanceof SoulFurnaceEntity furnace) {
                                furnace.isFormed = true;
                                furnace.setChanged();
                                level.sendBlockUpdated(currentPos, newFurnaceState, newFurnaceState, 3);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SoulStructureBlock.FORMED, FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            if (player.isShiftKeyDown()) {
                trySpawnMultiblock(level, pos);
            } else {
                BlockEntity entity = level.getBlockEntity(pos);
                if (entity instanceof SoulFurnaceEntity furnace && furnace.isFormed) {
                    player.openMenu(furnace, pos);
                } else {
                    player.displayClientMessage(Component.literal("§cIncomplete Structure!"), true);
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    public void unformMultiblock(Level level, BlockPos furnacePos) {
        BlockState coreState = level.getBlockState(furnacePos);
        if (coreState.hasProperty(SoulStructureBlock.FORMED)) {
            level.setBlock(furnacePos, coreState.setValue(SoulStructureBlock.FORMED, false), 3);
        }

        if (level.getBlockEntity(furnacePos) instanceof SoulFurnaceEntity furnace) {
            furnace.isFormed = false;
            furnace.setChanged();
            level.sendBlockUpdated(furnacePos, coreState, coreState, 3);
        }

        for (int x = -1; x <= 1; x++) {
            for (int y = 0; y <= 2; y++) {
                for (int z = -1; z <= 1; z++) {
                    BlockPos checkPos = furnacePos.offset(x, y, z);
                    BlockState state = level.getBlockState(checkPos);
                    if (state.is(ModBlocks.SOUL_STRUCTURE_BLOCK.get()) && state.hasProperty(SoulStructureBlock.FORMED)) {
                        level.setBlock(checkPos, state.setValue(SoulStructureBlock.FORMED, false), 3);
                    }
                }
            }
        }
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            unformMultiblock(level, pos);
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) { return SHAPE; }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return state.getValue(SoulStructureBlock.FORMED) ? RenderShape.ENTITYBLOCK_ANIMATED : RenderShape.MODEL;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() { return CODEC; }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SoulFurnaceEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.SOUL_FURNACE_BE.get(),
                (lvl, pos, blockState, blockEntity) -> blockEntity.tick(lvl, pos, blockState));
    }
}