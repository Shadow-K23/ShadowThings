package com.shadow.shadowthings.jei;

import com.shadow.shadowthings.block.ModBlocks;
import com.shadow.shadowthings.recipe.SoulInfusionRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class SoulInfusionCategory implements IRecipeCategory<SoulInfusionRecipe> {

    public static final RecipeType<SoulInfusionRecipe> TYPE = RecipeType.create("shadowthings", "soul_infusion", SoulInfusionRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public SoulInfusionCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(150, 80);
        this.icon = helper.createDrawableItemStack(new ItemStack(ModBlocks.SOUL_CRAFTER.get()));
    }

    @Override
    public RecipeType<SoulInfusionRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Soul Infusion");
    }

    @Override
    public int getWidth() {
        return 150;
    }

    @Override
    public int getHeight() {
        return 80;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SoulInfusionRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 15, 32).addIngredients(recipe.catalyst());

        int startX = 47;
        int startY = 22;
        for (int i = 0; i < recipe.pedestalItems().size(); i++) {
            int xOffset = (i % 3) * 18;
            int yOffset = (i / 3) * 18;
            builder.addSlot(RecipeIngredientRole.INPUT, startX + xOffset, startY + yOffset)
                    .addIngredients(recipe.pedestalItems().get(i));
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, 117, 32).addItemStack(recipe.result());
    }

    @Override
    public void draw(SoulInfusionRecipe recipe, mezz.jei.api.gui.ingredient.IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        Minecraft minecraft = Minecraft.getInstance();
        guiGraphics.drawString(minecraft.font, recipe.soulCost() + " Souls", 47, 5, 0x8A2BE2, false);
    }
}
