package com.shadow.shadowthings.block.custom;

import com.mojang.serialization.MapCodec;
import com.shadow.shadowthings.block.entity.SoulCrafterEntity;
import com.shadow.shadowthings.block.entity.SoulPedestalEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.world.Containers.dropItemStack;

public class SoulPedestalBlock extends BaseEntityBlock {
    public static final VoxelShape SHAPE = Block.box(3,0,3,13,15,13);
    public static final MapCodec<SoulPedestalBlock> CODEC = simpleCodec(SoulPedestalBlock::new);

    public SoulPedestalBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SoulPedestalEntity(pos, state);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL; // Keeps the placeholder block visible for now!
    }

    // --- ITEM INSERTION & EXTRACTION ---
    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide()) return InteractionResult.SUCCESS;


        if (level.getBlockEntity(pos) instanceof SoulPedestalEntity pedestal) {

            if (pedestal.isLocked()) {
                return InteractionResult.sidedSuccess(level.isClientSide()); // Does nothing, or blocks the action
            }

            ItemStack itemInSlot = pedestal.inventory.getStackInSlot(0);
            ItemStack itemInHand = player.getMainHandItem();

            // 1. If pedestal is EMPTY and player has an ITEM -> Insert 1 item
            if (itemInSlot.isEmpty() && !itemInHand.isEmpty()) {
                ItemStack toInsert = itemInHand.copy();
                toInsert.setCount(1); // Only put 1 item on the pedestal
                pedestal.inventory.setStackInSlot(0, toInsert);

                if (!player.isCreative()) {
                    itemInHand.shrink(1);
                }
                level.playSound(null,pos,SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS,0.50f,1f);
                return InteractionResult.SUCCESS;
            }

            // 2. If pedestal has an ITEM -> Give it back to the player
            else if (!itemInSlot.isEmpty()) {
                if(!player.isCreative()){
                    player.getInventory().placeItemBackInInventory(itemInSlot);
                }
                pedestal.inventory.setStackInSlot(0, ItemStack.EMPTY);
                level.playSound(null,pos,SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS,0.50f,0.5f);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    // Make sure the item drops on the ground if the player breaks the pedestal!
    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            if (level.getBlockEntity(pos) instanceof SoulPedestalEntity pedestal) {
                dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), pedestal.inventory.getStackInSlot(0));
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }
}