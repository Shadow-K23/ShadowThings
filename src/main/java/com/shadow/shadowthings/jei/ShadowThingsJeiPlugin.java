package com.shadow.shadowthings.jei;

import com.shadow.shadowthings.block.ModBlocks;
import com.shadow.shadowthings.recipe.ModRecipes;
import com.shadow.shadowthings.recipe.SoulInfusionRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class ShadowThingsJeiPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath("shadowthings", "jei_plugin");
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.SOUL_FURNACE_CONTROLLER.get()), RecipeTypes.SMELTING);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.SOUL_CRAFTER.get()), SoulInfusionCategory.TYPE);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new SoulInfusionCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        if (Minecraft.getInstance().level == null) {
            return;
        }
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<SoulInfusionRecipe> infusionRecipes = recipeManager.getAllRecipesFor(ModRecipes.SOUL_INFUSION_TYPE.get())
                .stream()
                .map(RecipeHolder::value)
                .toList();
            System.out.println("[SHADOW THINGS JEI] NUMBER OF RECIPES FOUND: " + infusionRecipes.size());
            registration.addRecipes(SoulInfusionCategory.TYPE, infusionRecipes);
    }
}