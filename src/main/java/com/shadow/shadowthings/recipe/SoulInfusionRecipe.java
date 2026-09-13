package com.shadow.shadowthings.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.List;

public record SoulInfusionRecipe(Ingredient catalyst, List<Ingredient> pedestalItems, int soulCost, ItemStack result) implements Recipe<RecipeInput> {

    // 1. THE JSON CODEC (Reads from your datapack files)
    public static final MapCodec<SoulInfusionRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC_NONEMPTY.fieldOf("catalyst").forGetter(SoulInfusionRecipe::catalyst),
            Ingredient.CODEC_NONEMPTY.listOf().fieldOf("pedestal_items").forGetter(SoulInfusionRecipe::pedestalItems),
            Codec.INT.fieldOf("soul_cost").forGetter(SoulInfusionRecipe::soulCost),
            ItemStack.CODEC.fieldOf("result").forGetter(SoulInfusionRecipe::result)
    ).apply(instance, SoulInfusionRecipe::new));

    // 2. THE NETWORK CODEC (Sends the recipe from the Server to the Client for JEI)
    public static final StreamCodec<RegistryFriendlyByteBuf, SoulInfusionRecipe> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC, SoulInfusionRecipe::catalyst,
            Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), SoulInfusionRecipe::pedestalItems,
            ByteBufCodecs.INT, SoulInfusionRecipe::soulCost,
            ItemStack.STREAM_CODEC, SoulInfusionRecipe::result,
            SoulInfusionRecipe::new
    );

    @Override
    public boolean matches(RecipeInput input, Level level) {
        // We will write the complex matching logic inside the BlockEntity later!
        return false;
    }

    @Override
    public ItemStack assemble(RecipeInput input, HolderLookup.Provider registries) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return this.result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.SOUL_INFUSION_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.SOUL_INFUSION_TYPE.get();
    }
}