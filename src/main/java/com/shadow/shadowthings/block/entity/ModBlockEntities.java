package com.shadow.shadowthings.block.entity;

import com.shadow.shadowthings.ShadowThings;
import com.shadow.shadowthings.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, ShadowThings.MODID);

    public static final Supplier<BlockEntityType<SoulCoreEntity>> SOUL_CORE_BE =
            BLOCK_ENTITIES.register("soul_core_be", () -> BlockEntityType.Builder.of(
                    SoulCoreEntity::new, ModBlocks.SOUL_CORE.get()).build(null));

    public static final Supplier<BlockEntityType<SoulPedestalEntity>> SOUL_PEDESTAL_BE =
            BLOCK_ENTITIES.register("soul_pedestal_be", () -> BlockEntityType.Builder.of(
                    SoulPedestalEntity::new, ModBlocks.SOUL_PEDESTAL.get()).build(null));

    public static final Supplier<BlockEntityType<SoulCrafterEntity>> SOUL_CRAFTER_BE =
                BLOCK_ENTITIES.register("soul_crafter_be", () -> BlockEntityType.Builder.of(
                        SoulCrafterEntity::new, ModBlocks.SOUL_CRAFTER.get()).build(null));

    public static final Supplier<BlockEntityType<SoulFurnaceEntity>> SOUL_FURNACE_BE =
                BLOCK_ENTITIES.register("soul_furnace_be", () -> BlockEntityType.Builder.of(
                        SoulFurnaceEntity::new, ModBlocks.SOUL_FURNACE_CONTROLLER.get()).build(null));

    public static final Supplier<BlockEntityType<SoulCondenserEntity>> SOUL_CONDENSER_BE =
                BLOCK_ENTITIES.register("soul_condenser_be", () -> BlockEntityType.Builder.of(
                        SoulCondenserEntity::new, ModBlocks.SOUL_CONDENSER.get()).build(null));

    public static final Supplier<BlockEntityType<SoulCrucibleEntity>> SOUL_CRUCIBLE_BE =
                BLOCK_ENTITIES.register("soul_crucible_be", () -> BlockEntityType.Builder.of(
                        SoulCrucibleEntity::new, ModBlocks.SOUL_CRUCIBLE.get()).build(null));

    public static final Supplier<BlockEntityType<SoulCollectorEntity>> SOUL_COLLECTOR_BE =
                BLOCK_ENTITIES.register("soul_collector_be", () -> BlockEntityType.Builder.of(
                        SoulCollectorEntity::new, ModBlocks.SOUL_COLLECTOR.get()).build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
