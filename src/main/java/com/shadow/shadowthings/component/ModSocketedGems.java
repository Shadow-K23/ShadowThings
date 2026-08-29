package com.shadow.shadowthings.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import java.util.List;

public record ModSocketedGems(List<ItemStack> gems) {

    public static final ModSocketedGems EMPTY = new ModSocketedGems(List.of());

    public static final Codec<ModSocketedGems> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    ItemStack.CODEC.listOf().fieldOf("gems").forGetter(ModSocketedGems::gems)
            ).apply(instance, ModSocketedGems::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, ModSocketedGems> STREAM_CODEC =
            ItemStack.STREAM_CODEC.apply(ByteBufCodecs.list())
                    .map(ModSocketedGems::new, ModSocketedGems::gems);
}
