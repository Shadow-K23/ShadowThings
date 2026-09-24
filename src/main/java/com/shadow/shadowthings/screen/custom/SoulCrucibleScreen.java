package com.shadow.shadowthings.screen.custom;

import com.shadow.shadowthings.network.ModStartCraftingPayload;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.network.PacketDistributor;

public class SoulCrucibleScreen extends AbstractContainerScreen<SoulCrucibleMenu> {
    // Change "textures/gui/crafter.png" to match your actual file path!
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("shadowthings", "textures/gui/soul_crucible_gui.png");
    private static final ResourceLocation FIRE_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("shadowthings", "textures/gui/soul_crucible_fire.png");
    private static final ResourceLocation SOUL_BAR_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("shadowthings", "textures/gui/soul_crucible_souls_bar.png");

    public SoulCrucibleScreen(SoulCrucibleMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelY = 5;
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        //SOUL BAR
        int souls = this.menu.getData().get(0);
        int maxSouls = this.menu.getData().get(1);

        int maxSoulBarHeight = 71;
        int soulBarWidth = 16;

        if (maxSouls > 0 && souls > 0) {
            float soulRatio = (float) souls / maxSouls;
            int currentSoulHeight = (int) (maxSoulBarHeight * soulRatio);
            int emptySoulPixels = maxSoulBarHeight - currentSoulHeight;

            int soulBarX = this.leftPos + 5;
            int soulBarY = this.topPos + 5;

            guiGraphics.blit(SOUL_BAR_TEXTURE,
                    soulBarX,
                    soulBarY + emptySoulPixels,
                    0,
                    emptySoulPixels,
                    soulBarWidth,
                    currentSoulHeight,
                    soulBarWidth,
                    maxSoulBarHeight);
        }

        int burnTime = this.menu.getData().get(4);
        int totalBurnTime = this.menu.getData().get(5);

        //CENTER FIRE
        int maxFireHeight = 51;
        int fireWidth = 39;

        if (burnTime > 0 && totalBurnTime > 0) {

            int currentFireHeight = (int) (((float) burnTime / totalBurnTime) * maxFireHeight);
            int emptyFirePixels = maxFireHeight - currentFireHeight;

            int fireX = this.leftPos + 69;
            int fireY = this.topPos + 12;

            guiGraphics.blit(FIRE_TEXTURE,
                    fireX,
                    fireY + emptyFirePixels,
                    0,
                    emptyFirePixels,
                    fireWidth,
                    currentFireHeight,
                    fireWidth,
                    maxFireHeight);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY); // Renders item names when hovering
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title,  this.titleLabelX, this.titleLabelY, 0xFFFFFF, false);
    }
}
