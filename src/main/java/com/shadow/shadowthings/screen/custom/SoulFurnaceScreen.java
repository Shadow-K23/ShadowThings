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
    private static final ResourceLocation MOMENTUM_BAR_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(ShadowThings.MODID, "textures/gui/soul_furnace_momentum_bar.png");
    private static final ResourceLocation PROGRESS_BAR_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(ShadowThings.MODID, "textures/gui/soul_furnace_progress_bar.png");

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

            int currentWidth = Math.clamp(this.menu.getScaledProgress(),0,90);

            int screenX = x + 43;
            int screenY = y + 29;

            int textureX = 0;
            int textureY = 0;

            guiGraphics.blit(PROGRESS_BAR_TEXTURE, screenX, screenY, textureX, textureY, currentWidth, 18, 90, 18);
        }
    }

    private void renderMomentumBar(GuiGraphics guiGraphics, int x, int y) {
        if (menu.getMomentum() > 0) {
            int scaledMomentum = this.menu.getScaledMomentum();
            int maxHeight = 71;

            int screenX = x + 4;
            int screenY = y + 5 + (maxHeight - scaledMomentum);

            int textureX = 0;
            int textureY = (maxHeight - scaledMomentum);

            guiGraphics.blit(MOMENTUM_BAR_TEXTURE, screenX, screenY, textureX, textureY, 17, scaledMomentum, 17, 71);
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
    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {

        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 0xFFFFFF, false);
        guiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 0xF2D5FF, false);
    }
}