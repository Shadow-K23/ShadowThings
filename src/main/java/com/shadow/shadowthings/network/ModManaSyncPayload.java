package com.shadow.shadowthings.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record ModManaSyncPayload(int mana, int maxMana) implements CustomPacketPayload {

    // 1. Give your packet a unique ID
    public static final CustomPacketPayload.Type<ModManaSyncPayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("shadowthings", "mana_sync"));

    // 2. Tell the game how to encode/decode the 'mana' integer
    public static final StreamCodec<ByteBuf, ModManaSyncPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, ModManaSyncPayload::mana,
            ByteBufCodecs.INT, ModManaSyncPayload::maxMana,
            ModManaSyncPayload::new

    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
