package com.shadow.shadowthings.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.shadow.shadowthings.ShadowThings;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SoulFurnaceScreen extends AbstractContainerScreen<SoulFurnaceMenu> {
    // Make sure you place your texture file here!
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(ShadowThings.MODID, "textures/gui/soul_furnace_gui.png");

    public SoulFurnaceScreen(SoulFurnaceMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        // If your GUI image is a non-standard size, change these (default is 176x166)
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void init() {
        super.init();
        // Disables the default "Inventory" text rendering if it overlaps your slots
        this.inventoryLabelY = 10000;
        this.titleLabelY = 5;
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        // 1. Draw the main GUI background
        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(guiGraphics, x, y);
        renderMomentumBar(guiGraphics, x, y);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if (menu.getProgress() > 0) {
            int progress = this.menu.getScaledProgress();

            // X and Y on the screen (e.g., exactly between the input and output slots)
            int screenX = x + 79;
            int screenY = y + 34;

            // X and Y of the filled arrow on your TEXTURE file (e.g., off to the right side at 176, 14)
            int textureX = 176;
            int textureY = 14;

            // Width is dynamic (progress), Height is static (e.g., 17)
            guiGraphics.blit(TEXTURE, screenX, screenY, textureX, textureY, progress, 17);
        }
    }

    private void renderMomentumBar(GuiGraphics guiGraphics, int x, int y) {
        if (menu.getMomentum() > 0) {
            int scaledMomentum = this.menu.getScaledMomentum(); // Max height is 50 pixels

            // To make a bar fill from BOTTOM to TOP, we draw it upside down.
            // Screen Y = starting Y + (maxHeight - currentHeight)
            int screenX = x + 10;
            int screenY = y + 20 + (50 - scaledMomentum);

            // Texture Y also shifts down by (maxHeight - currentHeight)
            int textureX = 176;
            int textureY = 32 + (50 - scaledMomentum);

            guiGraphics.blit(TEXTURE, screenX, screenY, textureX, textureY, 10, scaledMomentum);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics, mouseX, mouseY, delta);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);

        // Optional: Render a tooltip when hovering over the momentum bar
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        if (mouseX >= x + 10 && mouseX <= x + 20 && mouseY >= y + 20 && mouseY <= y + 70) {
            guiGraphics.renderTooltip(this.font, Component.literal("Momentum: " + menu.getMomentum() + " / " + menu.getMaxMomentum()), mouseX, mouseY);
        }
    }
}