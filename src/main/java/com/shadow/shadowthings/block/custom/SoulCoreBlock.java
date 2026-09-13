package com.shadow.shadowthings.block.custom;

import com.mojang.serialization.MapCodec;
import com.shadow.shadowthings.block.ModBlocks;
import com.shadow.shadowthings.block.entity.ModBlockEntities;
import com.shadow.shadowthings.block.entity.SoulCoreEntity;
import com.shadow.shadowthings.world.SoulCoreData;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class SoulCoreBlock extends BaseEntityBlock {
    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 16, 16);
    public static final MapCodec<SoulCoreBlock> CODEC = simpleCodec(SoulCoreBlock::new);
    private static BlockPattern soulCorePattern;

    public SoulCoreBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(SoulStructureBlock.FORMED, false));
    }



    public static BlockPattern getOrCreateSoulCorePattern() {
        if (soulCorePattern == null) {
            soulCorePattern = BlockPatternBuilder.start()
                    // Y=2 (Top layer of the pillars)
                    .aisle(
                            "S~S",
                            "~~~",
                            "S~S"
                    )
                    // Y=1 (Middle layer of the pillars)
                    .aisle(
                            "S~S",
                            "~~~",
                            "S~S"
                    )
                    // Y=0 (The 3x3 base)
                    .aisle(
                            "SSS",
                            "SCS",
                            "SSS"
                    )
                    .where('C', BlockInWorld.hasState(BlockStatePredicate.forBlock(ModBlocks.SOUL_CORE.get())))
                    .where('S', BlockInWorld.hasState(state -> state.is(ModBlocks.SOUL_STRUCTURE_BLOCK.get()) && !state.getValue(SoulStructureBlock.FORMED)))
                    .where('~', BlockInWorld.hasState(BlockStatePredicate.ANY)) // ~ means we don't care what block is in the air space!
                    .build();
        }
        return soulCorePattern;
    }

    public static void trySpawnMultiblock(Level level, BlockPos pos) {
        if (level.isClientSide) return;
        BlockPattern.BlockPatternMatch match = getOrCreateSoulCorePattern().find(level, pos);
        if (match != null) {
            for (int width = 0; width < getOrCreateSoulCorePattern().getWidth(); width++) {
                for (int height = 0; height < getOrCreateSoulCorePattern().getHeight(); height++) {
                    for (int depth = 0; depth < getOrCreateSoulCorePattern().getDepth(); depth++) {
                        BlockInWorld blockInWorld = match.getBlock(width, height, depth);
                        BlockPos currentPos = blockInWorld.getPos();
                        BlockState currentState = level.getBlockState(currentPos);

                        if (currentState.is(ModBlocks.SOUL_STRUCTURE_BLOCK.get())) {
                            level.setBlock(currentPos, currentState.setValue(SoulStructureBlock.FORMED, true), 3);
                        } else if (currentState.is(ModBlocks.SOUL_CORE.get())) {
                            BlockState newCoreState = currentState.setValue(SoulStructureBlock.FORMED, true);
                            level.setBlock(currentPos, newCoreState, 3);
                            if (level.getBlockEntity(currentPos) instanceof SoulCoreEntity core) {
                                core.isFormed = true;
                                core.setChanged();
                                level.sendBlockUpdated(currentPos, newCoreState, newCoreState, 3);
                            }
                        }
                    }
                }
            }
        }
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
        if (state.getValue(SoulStructureBlock.FORMED)) {
            return RenderShape.ENTITYBLOCK_ANIMATED;
        }
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new SoulCoreEntity(blockPos, blockState);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {

            // Unregister the core from the global tracker so the player can build a new one
            if (!level.isClientSide() && level.getBlockEntity(pos) instanceof SoulCoreEntity coreEntity) {
                if (coreEntity.ownerUUID != null) {
                    SoulCoreData data = SoulCoreData.get((ServerLevel) level);
                    data.removeCore(coreEntity.ownerUUID);
                }
            }

            unformMultiblock(level, pos);
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            // 1. If sneaking (Shift + Right-Click) -> Try to form the multiblock
                if (player.isShiftKeyDown()) {
                        trySpawnMultiblock(level,pos);
                        return InteractionResult.SUCCESS;
                    }

            // 2. If NOT sneaking (Normal Right-Click) -> Open the UI
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof SoulCoreEntity coreEntity && coreEntity.isFormed) {
                player.openMenu(coreEntity, pos);
            }else{
                player.displayClientMessage(Component.literal("§cIncomplete Structure!"), true);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.SUCCESS;
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

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable net.minecraft.world.entity.LivingEntity placer, net.minecraft.world.item.ItemStack stack) {
        if (placer instanceof Player player && !level.isClientSide()) {
            SoulCoreData data = SoulCoreData.get((ServerLevel) level);

            // 1. REJECTION: If they already have a core, bounce it back to them!
            if (data.hasCore(player.getUUID())) {
                player.displayClientMessage(Component.literal("§cYou can only have one Soul Core in the world!"), true);

                // Instantly remove the block that was just placed
                level.setBlock(pos, net.minecraft.world.level.block.Blocks.AIR.defaultBlockState(), 3);

                // Refund the item (if not in Creative mode)
                if (!player.isCreative()) {
                    net.minecraft.world.item.ItemStack refundedItem = new net.minecraft.world.item.ItemStack(this);
                    // Try to put it in their inventory, otherwise drop it at their feet
                    player.drop(refundedItem, false);

                }
                return; // Stop the rest of the registration code from running
            }

            // 2. SUCCESS: Register the new Core normally
            data.setCore(player.getUUID(), pos);
            if (level.getBlockEntity(pos) instanceof SoulCoreEntity coreEntity) {
                coreEntity.ownerUUID = player.getUUID();
                coreEntity.setChanged();
            }
        }
        super.setPlacedBy(level, pos, state, placer, stack);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        // We only want the server to do the math and siphoning!
        if (level.isClientSide()) {
            return null;
        }

        return createTickerHelper(type, ModBlockEntities.SOUL_CORE_BE.get(),
                (lvl, pos, blockState, blockEntity) -> blockEntity.tick(lvl, pos, blockState));
    }
}