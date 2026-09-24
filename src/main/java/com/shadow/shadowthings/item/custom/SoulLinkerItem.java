package com.shadow.shadowthings.item.custom;


import com.shadow.shadowthings.block.entity.SoulCoreEntity;
import com.shadow.shadowthings.block.entity.SoulFurnaceEntity;
import com.shadow.shadowthings.block.entity.base.AbstractSoulEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;

public class SoulLinkerItem extends Item {
    public SoulLinkerItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (player.isShiftKeyDown()) {
            if (!level.isClientSide()) {
                stack.remove(net.minecraft.core.component.DataComponents.CUSTOM_DATA);
                player.sendSystemMessage(net.minecraft.network.chat.Component.translatable("message.shadowthings.linking_tool_reset"));
            }
            return net.minecraft.world.InteractionResultHolder.success(stack);
        }

        return net.minecraft.world.InteractionResultHolder.pass(stack);
    }
    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        var level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        var player = context.getPlayer();

        if (player == null) return InteractionResult.PASS;

        if (player.isShiftKeyDown()) {
            if (!level.isClientSide()) {
                stack.remove(DataComponents.CUSTOM_DATA);
                player.sendSystemMessage(Component.translatable("message.shadowthings.linking_tool_reset"));
            }
            return InteractionResult.SUCCESS;
        }

        var customDataObj = stack.get(DataComponents.CUSTOM_DATA);
        CompoundTag tag = customDataObj != null ? customDataObj.copyTag() : new CompoundTag();

        if (tag.contains("FurnaceX")) {
            if (!level.isClientSide()) {
                BlockPos furnacePos = new BlockPos((int)tag.getLong("FurnaceX"), (int)tag.getLong("FurnaceY"), (int)tag.getLong("FurnaceZ"));
                String ioState = tag.getString("IOState");

                if (level.getBlockEntity(furnacePos) instanceof SoulFurnaceEntity furnace) {
                    if (pos.distSqr(furnacePos) > 27) {
                        player.sendSystemMessage(Component.translatable("message.shadowthings.furnace_distance_warning"));
                    } else if (level.getCapability(Capabilities.ItemHandler.BLOCK, pos, null) == null) {
                        player.sendSystemMessage(Component.translatable("message.shadowthings.furnace_inventory_warning"));
                    } else if (ioState.equals("INPUT")) {
                        furnace.inputPos = pos;
                        furnace.setChanged();
                        tag.putString("IOState", "OUTPUT");
                        stack.set(net.minecraft.core.component.DataComponents.CUSTOM_DATA, net.minecraft.world.item.component.CustomData.of(tag));
                        player.sendSystemMessage(Component.translatable("message.shadowthings.furnace_input.info"));
                    } else if (ioState.equals("OUTPUT")) {
                        furnace.outputPos = pos;
                        furnace.setChanged();
                        tag.remove("FurnaceX");
                        tag.remove("FurnaceY");
                        tag.remove("FurnaceZ");
                        tag.remove("IOState");
                        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
                        player.sendSystemMessage(Component.translatable("message.shadowthings.furnace_output.info"));
                    }
                } else {
                    player.sendSystemMessage(Component.translatable("message.shadowthings.furnace_destroyed"));
                    stack.remove(DataComponents.CUSTOM_DATA);
                }
            }
            return InteractionResult.SUCCESS;
        }

        if (level.getBlockEntity(pos) instanceof SoulCoreEntity core) {
            if (!level.isClientSide()) {
                tag.putLong("CoreX", pos.getX());
                tag.putLong("CoreY", pos.getY());
                tag.putLong("CoreZ", pos.getZ());
                stack.set(net.minecraft.core.component.DataComponents.CUSTOM_DATA, CustomData.of(tag));
                player.sendSystemMessage(Component.translatable("message.shadowthings.core_saved"));
            }
            return InteractionResult.SUCCESS;
        }

        if (level.getBlockEntity(pos) instanceof AbstractSoulEntity machine) {
            if (!level.isClientSide()) {
                if (machine instanceof SoulFurnaceEntity && !tag.contains("CoreX")) {
                    tag.putLong("FurnaceX", pos.getX());
                    tag.putLong("FurnaceY", pos.getY());
                    tag.putLong("FurnaceZ", pos.getZ());
                    tag.putString("IOState", "INPUT");
                    stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
                    player.sendSystemMessage(Component.translatable("message.shadowthings.furnace_config_start"));
                } else if (tag.contains("CoreX")) {
                    BlockPos corePos = new BlockPos((int)tag.getLong("CoreX"), (int)tag.getLong("CoreY"), (int)tag.getLong("CoreZ"));
                    if (level.getBlockEntity(corePos) instanceof SoulCoreEntity targetCore) {
                        if (pos.distSqr(corePos) > (targetCore.getCoreRadius() * targetCore.getCoreRadius())) {
                            player.sendSystemMessage(Component.translatable("message.shadowthings.core_range_warning"));
                        } else {
                            machine.setLinkedCorePos(corePos);
                            player.sendSystemMessage(Component.translatable("message.shadowthings.core_linking_finished"));
                        }
                    }
                }
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}