package com.shadow.shadowthings.block.custom;

import com.mojang.serialization.MapCodec;
import com.shadow.shadowthings.block.entity.ModBlockEntities;
import com.shadow.shadowthings.block.entity.SoulCollectorEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class SoulCollectorBlock extends BaseEntityBlock {

    public SoulCollectorBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {

        // If the player is sneaking and has an empty hand, cycle the radius
        if (player.isShiftKeyDown() && player.getMainHandItem().isEmpty()) {
            if (!level.isClientSide()) {
                BlockEntity blockEntity = level.getBlockEntity(pos);
                if (blockEntity instanceof SoulCollectorEntity collector) {
                    collector.cycleRadius(player);
                }
            }
            return InteractionResult.SUCCESS;
        }

        // Otherwise, do nothing (or open a GUI if you add one later)
        return InteractionResult.PASS;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);

            // Easily drop all upgrades using the method we just added to the base class!
            if (blockEntity instanceof com.shadow.shadowthings.block.entity.base.AbstractSoulEntity soulEntity) {
                soulEntity.dropAllUpgrades();
            }

            super.onRemove(state, level, pos, newState, isMoving);
        }
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL; // Tells the game to render the block using a JSON model
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SoulCollectorEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.SOUL_COLLECTOR_BE.get(),
                (lvl, pos, blockState, blockEntity) -> blockEntity.tick(lvl, pos, blockState));
    }
}