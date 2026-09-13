package com.shadow.shadowthings.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record ModStartCraftingPayload(BlockPos pos) implements CustomPacketPayload {
    public static final Type<ModStartCraftingPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("shadowthings", "start_crafting"));

    public static final StreamCodec<FriendlyByteBuf, ModStartCraftingPayload> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, ModStartCraftingPayload::pos,
            ModStartCraftingPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}