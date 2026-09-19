package com.shadow.shadowthings.block.custom;

import com.shadow.shadowthings.block.ModBlocks;
import com.shadow.shadowthings.block.entity.base.AbstractSoulEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class SoulStructureBlock extends Block {
    public static final BooleanProperty FORMED = BooleanProperty.create("formed");

    public SoulStructureBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FORMED, false));
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FORMED);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock()) && state.getValue(FORMED)) {
            // Search down and around for the Core
            for (int x = -1; x <= 1; x++) {
                for (int y = -2; y <= 0; y++) {
                    for (int z = -1; z <= 1; z++) {
                        BlockPos checkPos = pos.offset(x, y, z);
                        if (level.getBlockState(checkPos).is(ModBlocks.SOUL_CORE.get())) {
                            ((SoulCoreBlock) level.getBlockState(checkPos).getBlock()).unformMultiblock(level, checkPos);
                            break;
                        }
                        if (level.getBlockState(checkPos).is(ModBlocks.SOUL_FURNACE_CONTROLLER.get())) {
                            ((SoulFurnaceBlock) level.getBlockState(checkPos).getBlock()).unformMultiblock(level, checkPos);
                            break;
                        }
                    }
                }
            }
        }

        super.onRemove(state, level, pos, newState, isMoving);
    }
    @Override
    protected net.minecraft.world.InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {

        // Only forward the click if the structure is actually fully formed
        if (state.getValue(FORMED)) {

            // Search a small area downwards to find the center Core
            for (int x = -1; x <= 1; x++) {
                for (int y = -2; y <= 0; y++) {
                    for (int z = -1; z <= 1; z++) {
                        BlockPos checkPos = pos.offset(x, y, z);
                        BlockState checkState = level.getBlockState(checkPos);

                        if (checkState.is(ModBlocks.SOUL_CORE.get()) || checkState.is(ModBlocks.SOUL_FURNACE_CONTROLLER.get())) {
                            // 1. Create a fake click target pointing at the Core's position
                            BlockHitResult coreHitResult = new BlockHitResult(
                                    hitResult.getLocation(), hitResult.getDirection(), checkPos, hitResult.isInside()
                            );

                            // 2. Fire the Core's exact interaction method using our fake click!
                            return checkState.useWithoutItem(level, player, coreHitResult);
                        }
                    }
                }
            }
        }

        // If it isn't formed, just do nothing (act like a normal block)
        return net.minecraft.world.InteractionResult.PASS;
    }
}

