package com.shadow.shadowthings.datagen;

import com.shadow.shadowthings.ShadowThings;
import com.shadow.shadowthings.block.ModBlocks;
import com.shadow.shadowthings.item.ModItems;
import it.unimi.dsi.fastutil.bytes.Byte2IntSortedMap;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        //LISTS OF ITEMS FOR SMELTING
        List<ItemLike> SHADOW_SMELTABLES = List.of(ModItems.RAWSHADOWINGOT,ModBlocks.SHADOW_ORE,ModBlocks.SHADOW_DEEPSLATE_ORE);

        //BLOCK CRAFTING
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SHADOW_BLOCK.get())
                .pattern("BBB")
                .pattern("BBB")
                .pattern("BBB")
                .define('B', ModItems.SHADOWINGOT.get())
                .unlockedBy("has_shadow_ingot",has(ModItems.SHADOWINGOT)).save(recipeOutput, "shadowthings:shadow_block_craft");

        //TOOL RECIPES
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.SHADOWSWORD.get())
                .pattern("B")
                .pattern("B")
                .pattern("C")
                .define('B', ModItems.SHADOWINGOT.get())
                .define('C', Items.BLAZE_ROD)
                .unlockedBy("has_shadow_ingot",has(ModItems.SHADOWINGOT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.SHADOWSPEAR.get())
                .pattern("  B")
                .pattern(" C ")
                .pattern("C  ")
                .define('B', ModItems.SHADOWINGOT.get())
                .define('C', Items.BLAZE_ROD)
                .unlockedBy("has_shadow_ingot",has(ModItems.SHADOWINGOT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.SHADOWSHOVEL.get())
                .pattern("B")
                .pattern("C")
                .pattern("C")
                .define('B', ModItems.SHADOWINGOT.get())
                .define('C', Items.BLAZE_ROD)
                .unlockedBy("has_shadow_ingot",has(ModItems.SHADOWINGOT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.SHADOWAXE.get())
                .pattern("BB")
                .pattern("BC")
                .pattern(" C")
                .define('B', ModItems.SHADOWINGOT.get())
                .define('C', Items.BLAZE_ROD)
                .unlockedBy("has_shadow_ingot",has(ModItems.SHADOWINGOT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.SHADOWHOE.get())
                .pattern("BB")
                .pattern("C ")
                .pattern("C ")
                .define('B', ModItems.SHADOWINGOT.get())
                .define('C', Items.BLAZE_ROD)
                .unlockedBy("has_shadow_ingot",has(ModItems.SHADOWINGOT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.SHADOWPICKAXE.get())
                .pattern("BBB")
                .pattern(" C ")
                .pattern(" C ")
                .define('B', ModItems.SHADOWINGOT.get())
                .define('C', Items.BLAZE_ROD)
                .unlockedBy("has_shadow_ingot",has(ModItems.SHADOWINGOT)).save(recipeOutput);



        //SHAPELESS
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ModItems.SHADOWINGOT,9)
                .requires(ModBlocks.SHADOW_BLOCK)
                .unlockedBy("has_shadow_block",has(ModBlocks.SHADOW_BLOCK)).save(recipeOutput,"shadowthings:shadow_ingot_craft");

        oreSmelting(recipeOutput, SHADOW_SMELTABLES,RecipeCategory.MISC,ModItems.SHADOWINGOT.get(), 0.25f,200, "shadow_steel");
        oreBlasting(recipeOutput, SHADOW_SMELTABLES,RecipeCategory.MISC,ModItems.SHADOWINGOT.get(), 0.25f,100, "shadow_steel");
    }
    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                       List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, ShadowThings.MODID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
