package com.shadow.shadowthings.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.shadow.shadowthings.ShadowThings;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SoulCoreScreen extends AbstractContainerScreen<SoulCoreMenu> {
    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(ShadowThings.MODID,"textures/gui/soul_core/soul_core_gui.png");

    public SoulCoreScreen(SoulCoreMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        // 1. Draw the main UI background first
        guiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        // 2. Do we have souls to draw?
        int scaledProgress = menu.getScaledSoulProgress();
        if (scaledProgress > 0) {

            // 3. Draw the filled bar overlay!
            // Assuming your empty bar is at UI coordinates X=100, Y=20
            // And your filled bar texture is placed at X=176, Y=0 on your .png file
            int barMaxHeight = 69; // Must match the number in your Menu class

            guiGraphics.blit(GUI_TEXTURE,
                    x + 150,
                    y + 8 + (barMaxHeight - scaledProgress), // Screen Y (Pushed down so it grows upward)
                    237, // Texture U (X coordinate of the filled bar on your .png file)
                    barMaxHeight - scaledProgress, // Texture V (Y coordinate of the filled bar on your .png file)
                    19, // Width of the bar
                    scaledProgress // Height of the bar we are actively drawing
            );
        }
    }
}
