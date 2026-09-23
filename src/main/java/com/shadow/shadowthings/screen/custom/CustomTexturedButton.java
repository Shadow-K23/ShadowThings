package com.shadow.shadowthings.screen.custom;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class CustomTexturedButton extends Button {
    private final ResourceLocation baseTexture;
    private final int baseU;
    private final int baseV;

    private final ResourceLocation hoverTexture;

    public CustomTexturedButton(int x, int y, int width, int height,
                                ResourceLocation baseTexture, int baseU, int baseV,
                                ResourceLocation hoverTexture,
                                OnPress onPress) {
        super(x, y, width, height, Component.empty(), onPress, DEFAULT_NARRATION);
        this.baseTexture = baseTexture;
        this.baseU = baseU;
        this.baseV = baseV;
        this.hoverTexture = hoverTexture;
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

        if (this.isHovered()) {
            guiGraphics.blit(this.hoverTexture, this.getX(), this.getY(), 0, 0, this.width, this.height, this.width, this.height);
        } else {
            guiGraphics.blit(this.baseTexture, this.getX(), this.getY(), this.baseU, this.baseV, this.width, this.height);
        }
    }
}