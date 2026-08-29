package com.shadow.shadowthings.component;

import com.shadow.shadowthings.ShadowThings;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import javax.xml.crypto.Data;
import java.util.function.UnaryOperator;

public class ModDataComponents {

    // 1. Initialize the specialized DataComponents registry
    public static final DeferredRegister.DataComponents DATA_COMPONENT_TYPES =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, ShadowThings.MODID);

    // 2. Use the built-in registerComponentType method directly!
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<BlockPos>> COORDINATES =
            DATA_COMPONENT_TYPES.registerComponentType("coordinates", builder -> builder.persistent(BlockPos.CODEC));



    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ModSocketedGems>> SOCKETED_GEMS =
            DATA_COMPONENT_TYPES.registerComponentType("socketed_gems", builder -> builder
                    .persistent(ModSocketedGems.CODEC)
                    .networkSynchronized(ModSocketedGems.STREAM_CODEC)
            );

    public static void register(IEventBus eventBus) {
        DATA_COMPONENT_TYPES.register(eventBus);
    }
}
