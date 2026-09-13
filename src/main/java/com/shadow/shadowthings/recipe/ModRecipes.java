package com.shadow.shadowthings.recipe;

import com.mojang.serialization.MapCodec;
import com.shadow.shadowthings.ShadowThings;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, "shadowthings");

    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, "shadowthings");

    // Register the Serializer
    public static final Supplier<RecipeSerializer<SoulInfusionRecipe>> SOUL_INFUSION_SERIALIZER =
            SERIALIZERS.register("soul_infusion", () -> new RecipeSerializer<>() {
                @Override
                public MapCodec<SoulInfusionRecipe> codec() {
                    return SoulInfusionRecipe.CODEC;
                }
                @Override
                public StreamCodec<RegistryFriendlyByteBuf, SoulInfusionRecipe> streamCodec() {
                    return SoulInfusionRecipe.STREAM_CODEC;
                }
            });

    // Register the Type
    public static final Supplier<RecipeType<SoulInfusionRecipe>> SOUL_INFUSION_TYPE =
            TYPES.register("soul_infusion", () -> new RecipeType<>() {
                @Override
                public String toString() {
                    return "soul_infusion";
                }
            });

    public static void register(net.neoforged.bus.api.IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}