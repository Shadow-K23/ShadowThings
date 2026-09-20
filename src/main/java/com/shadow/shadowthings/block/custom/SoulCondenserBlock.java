package com.shadow.shadowthings.block.custom;

import com.mojang.serialization.MapCodec;
import com.shadow.shadowthings.block.entity.ModBlockEntities;
import com.shadow.shadowthings.block.entity.SoulCondenserEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SoulCondenserBlock extends BaseEntityBlock {

    public SoulCondenserBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        // Capture the player's UUID when they place it so it can beam souls to their Core!
        if (placer instanceof Player player && !level.isClientSide()) {
            if (level.getBlockEntity(pos) instanceof SoulCondenserEntity condenser) {
                condenser.ownerUUID = player.getUUID();
                condenser.setChanged();
            }
        }
        super.setPlacedBy(level, pos, state, placer, stack);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SoulCondenserEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.SOUL_CONDENSER_BE.get(),
                (lvl, pos, blockState, blockEntity) -> blockEntity.tick(lvl, pos, blockState));
    }
}