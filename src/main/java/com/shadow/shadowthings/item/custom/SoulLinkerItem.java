package com.shadow.shadowthings.item.custom;


import com.shadow.shadowthings.block.entity.SoulCoreEntity;
import com.shadow.shadowthings.block.entity.base.AbstractSoulEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;

public class SoulLinkerItem extends Item {
    public SoulLinkerItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        var level = context.getLevel();
        if (level.isClientSide()) return InteractionResult.SUCCESS;

        BlockPos pos = context.getClickedPos();
        ItemStack stack = context.getItemInHand();
        var player = context.getPlayer();

        if (player == null) return InteractionResult.PASS;

        // 1. CLICKED A CORE: Save its position to the stick's NBT
        if (level.getBlockEntity(pos) instanceof SoulCoreEntity core) {
            // Optional: Check if core has an owner, if not, claim it
            if (core.getOwnerUUID() == null) {
                core.setOwnerUUID(player.getUUID());
            }

            // Verify ownership
            if (!player.getUUID().equals(core.getOwnerUUID())) {
                player.sendSystemMessage(Component.literal("This Core does not belong to you!"));
                return InteractionResult.FAIL;
            }

            CompoundTag tag = new CompoundTag();
            tag.putLong("CoreX", pos.getX());
            tag.putLong("CoreY", pos.getY());
            tag.putLong("CoreZ", pos.getZ());

            // Set it on the item stack
            stack.set(net.minecraft.core.component.DataComponents.CUSTOM_DATA,
                    net.minecraft.world.item.component.CustomData.of(tag)
            );

            player.sendSystemMessage(Component.literal("Soul Core linked to tool!"));
            return InteractionResult.SUCCESS;
        }

        // 2. CLICKED A MACHINE (like the Crafter): Bind it to the saved Core
        if (level.getBlockEntity(pos) instanceof AbstractSoulEntity machine) {
            var customData = stack.get(net.minecraft.core.component.DataComponents.CUSTOM_DATA);
            if (customData == null || !customData.copyTag().contains("CoreX")) {
                player.sendSystemMessage(Component.literal("Bind this tool to a Soul Core first!"));
                return InteractionResult.FAIL;
            }

            CompoundTag tag = customData.copyTag();
            BlockPos corePos = new BlockPos((int)tag.getLong("CoreX"), (int)tag.getLong("CoreY"), (int)tag.getLong("CoreZ"));

            if (level.getBlockEntity(corePos) instanceof SoulCoreEntity core) {
                // Check distance against the Core's radius
                if (pos.distSqr(corePos) > (core.getCoreRadius() * core.getCoreRadius())) {
                    player.sendSystemMessage(Component.literal("Target is out of range of the Soul Core!"));
                    return InteractionResult.FAIL;
                }

                // Link them!
                machine.setLinkedCorePos(corePos);
                player.sendSystemMessage(Component.literal("Machine successfully linked to Soul Core!"));
                return InteractionResult.SUCCESS;
            } else {
                player.sendSystemMessage(Component.literal("Linked Core no longer exists!"));
            }
        }

        return InteractionResult.PASS;
    }
}