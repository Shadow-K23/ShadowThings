package com.shadow.shadowthings.block.custom;

import com.mojang.serialization.MapCodec;
import com.shadow.shadowthings.block.ModBlocks;
import com.shadow.shadowthings.block.entity.SoulCoreEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class SoulCoreBlock extends BaseEntityBlock {
    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 13, 16);
    public static final MapCodec<SoulCoreBlock> CODEC = simpleCodec(SoulCoreBlock::new);

    public SoulCoreBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(SoulStructureBlock.FORMED, false));
    }

    public boolean checkMultiblock(Level level, BlockPos corePos) {
        // 1. Check the 3x3 Base (Y = 0)
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if (x == 0 && z == 0) continue;

                BlockPos checkPos = corePos.offset(x, 0, z);
                if (!level.getBlockState(checkPos).is(ModBlocks.SOUL_STRUCTURE_BLOCK.get())) {
                    return false;
                }
            }
        }

        // 2. Check the Corner Pillars (Y = 1 and Y = 2)
        int[][] corners = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        for (int[] corner : corners) {
            for (int y = 1; y <= 2; y++) {
                BlockPos checkPos = corePos.offset(corner[0], y, corner[1]);
                if (!level.getBlockState(checkPos).is(ModBlocks.SOUL_STRUCTURE_BLOCK.get())) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SoulStructureBlock.FORMED);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new SoulCoreEntity(blockPos, blockState);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            unformMultiblock(level, pos);
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide()) {

            // 1. If sneaking (Shift + Right-Click) -> Try to form the multiblock
            if (player.isShiftKeyDown()) {
                if (checkMultiblock(level, pos)) {
                    formMultiblock(level, pos);
                    player.displayClientMessage(Component.literal("§bSoul Core Activated!"), true);
                } else {
                    player.displayClientMessage(Component.literal("§cIncomplete Structure!"), true);
                }
                return InteractionResult.SUCCESS;
            }

            // 2. If NOT sneaking (Normal Right-Click) -> Open the UI
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof SoulCoreEntity coreEntity) {
                player.openMenu(coreEntity, pos);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.SUCCESS;
    }

    private void formMultiblock(Level level, BlockPos corePos) {
        // 1. Transform the Core itself and sync its entity tracker
        BlockState coreState = level.getBlockState(corePos);
        if (coreState.hasProperty(SoulStructureBlock.FORMED)) {
            level.setBlock(corePos, coreState.setValue(SoulStructureBlock.FORMED, true), 3);
        }

        if (level.getBlockEntity(corePos) instanceof SoulCoreEntity coreEntity) {
            coreEntity.isFormed = true;
            coreEntity.setChanged();
            level.sendBlockUpdated(corePos, coreState, coreState, 3);
        }

        // 2. Transform the 3x3 Base
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if (x == 0 && z == 0) continue;
                setFormedState(level, corePos.offset(x, 0, z), true);
            }
        }

        // 3. Transform the Pillars
        int[][] corners = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        for (int[] corner : corners) {
            for (int y = 1; y <= 2; y++) {
                setFormedState(level, corePos.offset(corner[0], y, corner[1]), true);
            }
        }
    }

    public void unformMultiblock(Level level, BlockPos corePos) {
        // 1. Unform the Core itself and sync its entity tracker
        BlockState coreState = level.getBlockState(corePos);
        if (coreState.hasProperty(SoulStructureBlock.FORMED)) {
            level.setBlock(corePos, coreState.setValue(SoulStructureBlock.FORMED, false), 3);
        }

        if (level.getBlockEntity(corePos) instanceof SoulCoreEntity coreEntity) {
            coreEntity.isFormed = false;
            coreEntity.setChanged();
            level.sendBlockUpdated(corePos, coreState, coreState, 3);
        }

        // 2. Scan and reset the surrounding 3x3x2 area
        for (int x = -1; x <= 1; x++) {
            for (int y = 0; y <= 2; y++) {
                for (int z = -1; z <= 1; z++) {
                    if (x == 0 && y == 0 && z == 0) continue;
                    BlockPos checkPos = corePos.offset(x, y, z);
                    BlockState state = level.getBlockState(checkPos);

                    if (state.is(ModBlocks.SOUL_STRUCTURE_BLOCK.get()) && state.hasProperty(SoulStructureBlock.FORMED)) {
                        level.setBlock(checkPos, state.setValue(SoulStructureBlock.FORMED, false), 3);
                    }
                }
            }
        }
    }

    private void setFormedState(Level level, BlockPos pos, boolean formed) {
        BlockState state = level.getBlockState(pos);
        if (state.hasProperty(SoulStructureBlock.FORMED)) {
            level.setBlock(pos, state.setValue(SoulStructureBlock.FORMED, formed), 3);
        }
    }
}