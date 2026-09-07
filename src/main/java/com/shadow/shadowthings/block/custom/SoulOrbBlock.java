package com.shadow.shadowthings.block.custom;

import com.mojang.serialization.MapCodec;
import com.shadow.shadowthings.block.entity.SoulOrbEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class SoulOrbBlock extends BaseEntityBlock {
    public static final VoxelShape SHAPE = Block.box(2,1,2,14,13,14);
    public static final MapCodec<SoulOrbBlock> CODEC = simpleCodec(SoulOrbBlock::new);

    public SoulOrbBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    //SOUL ORB

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new SoulOrbEntity(blockPos,blockState);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.getBlock() != newState.getBlock()){
            if(level.getBlockEntity(pos) instanceof SoulOrbEntity soulOrbEntity){
                soulOrbEntity.drops();
                level.updateNeighbourForOutputSignal(pos, this);
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                              Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(level.getBlockEntity(pos) instanceof SoulOrbEntity soulOrbEntity){
            if(player.isCrouching() && !level.isClientSide()){
                ((ServerPlayer) player).openMenu(new SimpleMenuProvider(soulOrbEntity, Component.literal("Soul Orb")),pos);
                return ItemInteractionResult.SUCCESS;
            }

            if(soulOrbEntity.inventory.getStackInSlot(0).isEmpty() && !stack.isEmpty()) {
                soulOrbEntity.inventory.insertItem(0, stack.copy(), false);
                stack.shrink(1);
                level.playSound(player,pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f ,2f);
            } else if(stack.isEmpty()){
                ItemStack stackOnOrb = soulOrbEntity.inventory.extractItem(0, 1,false);
                player.setItemInHand(InteractionHand.MAIN_HAND, stackOnOrb);
                soulOrbEntity.clearContents();
                level.playSound(player,pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f ,1f);
            }
        }
        return ItemInteractionResult.SUCCESS;
    }
}

