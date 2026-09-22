package com.shadow.shadowthings.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.shadow.shadowthings.ShadowThings;
import com.shadow.shadowthings.block.entity.SoulCoreEntity;
import com.shadow.shadowthings.client.model.SoulCoreModel;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SoulCoreScreen extends AbstractContainerScreen<SoulCoreMenu> {
    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(ShadowThings.MODID,"textures/gui/soul_core_gui.png");
    private static final ResourceLocation UPGRADE_PANEL =
            ResourceLocation.fromNamespaceAndPath(ShadowThings.MODID,"textures/gui/upgrade_gui.png");

    private SoulCoreModel model;

    public SoulCoreScreen(SoulCoreMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void init() {
        super.init();
        this.imageWidth = 256;
        this.imageHeight = 256;
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        // Add a standard button.
        // You can adjust the X (x + 10), Y (y + 10), Width (60), and Height (20) to fit your GUI texture!
        this.addRenderableWidget(Button.builder(Component.literal("Soul Siphon"), button -> {

            // This is the magic line! It sends the click directly to our Menu's 'clickMenuButton' method
            this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, 0);

        }).bounds(x + 10, y + 10, 60, 20).build());

        var modelPart = this.minecraft.getEntityModels().bakeLayer(SoulCoreModel.LAYER_LOCATION);
        this.model = new SoulCoreModel(modelPart);
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

        guiGraphics.drawString(
                minecraft.font,
                (menu.getSouls()/1000f) + "k / " + menu.getMaxSouls()/1000f + "k",
                x + 150, y + 75,
                0x41beff
        );
        guiGraphics.drawString(
                minecraft.font,
                ("CORE HEALTH: " + menu.getCoreHealth()) + " / 1000" ,
                x + 125, y + 50,
                0xff3333
        );

        int panelX = leftPos + this.imageWidth;
        int panelY = topPos + 5; // Push it down 5 pixels from the top

        // 3. Draw the upgrade panel
        // Parameters: texture, x, y, uOffset, vOffset, width, height
        guiGraphics.blit(UPGRADE_PANEL, panelX, panelY, 0, 0, 27, 83);

        //GUI CRYSTAL
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();

        poseStack.translate(x + (imageWidth / 2.0f), y + (imageHeight / 2.0f), 150.0f);
        poseStack.scale(50.0f, -50.0f, 50.0f);

        float time = this.minecraft.level.getGameTime() + pPartialTick;
        poseStack.mulPose(Axis.XP.rotationDegrees(180.0f));

        poseStack.translate(0, 1.5f , 0);
        poseStack.mulPose(Axis.YP.rotationDegrees(time * 1.75f));
        poseStack.translate(-0, -1.5f , -0);

        SoulCoreEntity core = this.menu.blockEntity;
        if (core != null) {
            float fillRatio = Math.min(1.0f, (float) core.getSouls() / Math.max(1, core.getMaxSouls()));

            float dynamicScale = 0.20f + (1.5f * fillRatio);
            poseStack.scale(dynamicScale, dynamicScale, dynamicScale);

            ResourceLocation CRYSTAL_TEX = ResourceLocation.fromNamespaceAndPath("shadowthings", "textures/entity/soul_core.png");

            var buffer = guiGraphics.bufferSource().getBuffer(RenderType.entityTranslucent(CRYSTAL_TEX));

            int alpha = 255;
            int r = (int) (40 + 215 * fillRatio);
            int g = (int) (0 + 60 * fillRatio);
            int b = (int) (35 + 220 * fillRatio);
            int finalColor = (alpha << 24) | (r << 16) | (g << 8) | b;

            this.model.crystal.render(
                    poseStack,
                    buffer,
                    0xF000F0, // Packed Light
                    OverlayTexture.NO_OVERLAY,
                    finalColor
            );
        }
        guiGraphics.flush();
        poseStack.popPose();
    }
}
