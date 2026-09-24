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
    private static final ResourceLocation SIPHON_ACTIVE_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("shadowthings", "textures/gui/soul_siphon_button_active.png");
    private static final ResourceLocation HEALTH_BAR_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("shadowthings", "textures/gui/core_health_bar.png");

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

        this.titleLabelX = -30;
        this.titleLabelY = -35;
        this.inventoryLabelX = 8;
        this.inventoryLabelY = this.imageHeight - 136;

        var modelPart = this.minecraft.getEntityModels().bakeLayer(SoulCoreModel.LAYER_LOCATION);
        this.model = new SoulCoreModel(modelPart);
        // Add a standard button.
        // You can adjust the X (x + 10), Y (y + 10), Width (60), and Height (20) to fit your GUI texture!
        this.addRenderableWidget(new CustomTexturedButton(
                x + 234, y + 80,
                18, 18,
                GUI_TEXTURE,
                234, 80,
                SIPHON_ACTIVE_TEXTURE,
                button -> {
                    this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, 0);
                }
        ));


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
        int transferRate = this.menu.blockEntity.getTransferRate();
        int transferAmount = this.menu.blockEntity.getTransferAmount();

        if (scaledProgress > 0) {

            // 3. Draw the filled bar overlay!
            // Assuming your empty bar is at UI coordinates X=100, Y=20
            // And your filled bar texture is placed at X=176, Y=0 on your .png file
            int barMaxHeight = 69; // Must match the number in your Menu class

        }
        int centerX = x + (this.imageWidth / 2);
        int labelX = centerX - (this.font.width(Component.translatable("gui.shadowthings.soul_amount")) / 2);
        int valueX = centerX - (this.font.width((menu.getSouls()/1000f) + "k / " + menu.getMaxSouls()/1000f + "k") / 2);
        //INFORMATION TEXT
        //TRANSFER
        guiGraphics.drawString(
                minecraft.font,
                Component.translatable("gui.shadowthings.transfer_rate"),
                x + 15, y + 25,
                0xa2a2a2
        );
        guiGraphics.drawString(
                minecraft.font,
                String.valueOf(transferRate),
                x + 15, y + 37,
                0xFFFFFF
        );
        guiGraphics.drawString(
                minecraft.font,
                Component.translatable("gui.shadowthings.transfer_amount"),
                x + 15, y + 57,
                0xa2a2a2
        );
        guiGraphics.drawString(
                minecraft.font,
                String.valueOf(transferAmount),
                x + 15, y + 69,
                0xFFFFFF
        );
        //SOULS NET TRANSFER
        int netChange = menu.getSoulNetChange();
        int perSecond = netChange / 5;

        int color = perSecond > 0 ? 0x55FF55 : (perSecond < 0 ? 0xFF5555 : 0x5b5b5b);
        String sign = perSecond > 0 ? "+" + perSecond : String.valueOf(perSecond);


        guiGraphics.drawString(
                minecraft.font,
                Component.translatable("gui.shadowthings.soul_net_change"),
                x + 15, y + 89,
                0xa2a2a2
        );
        guiGraphics.drawString(
                this.font,
                sign + "/s",
                x + 15, y + 101,
                color,
                false
        );

        //SOULS
        guiGraphics.drawString(
                minecraft.font,
                Component.translatable("gui.shadowthings.soul_amount"),
                labelX, y + 135,
                0xa2a2a2
        );
        guiGraphics.drawString(
                minecraft.font,
                (menu.getSouls()/1000f) + "k / " + menu.getMaxSouls()/1000f + "k",
                valueX, y + 150,
                0x41beff
        );

        //SIPHON

        boolean isSiphonEnabled = this.menu.getSiphonState();
        Component siphonState;
        int colorSip;
        if(isSiphonEnabled){
            siphonState = Component.translatable("gui.shadowthings.active");
            colorSip = 0x55FF55;
        }else{
            siphonState = Component.translatable("gui.shadowthings.inactive");
            colorSip = 0xFF5555;
        }

        guiGraphics.drawString(
                minecraft.font,
                Component.translatable("gui.shadowthings.core_siphon"),
                centerX - 115, y + 135,
                colorSip
        );
        guiGraphics.drawString(
                minecraft.font,
                siphonState,
                centerX - 115, y + 150,
                colorSip
        );
        //CORE HP
        int currentHealth = this.menu.getCoreHealth();
        int maxHealth = 10000;

        float hpRatio = (float) currentHealth / Math.max(1, maxHealth);
        float hpPercent = (float) currentHealth * 100 / maxHealth;
        int maxBarHeight = 71;
        int barWidth = 15;

        int emptyPixels = maxBarHeight - (int) (maxBarHeight * hpRatio);
        int currentBarHeight = maxBarHeight - emptyPixels;

        int barX = x + 216;
        int barY = y + 5;

        int textureU = 0;
        int textureV = 0;

        guiGraphics.blit(HEALTH_BAR_TEXTURE,
                barX,
                barY + emptyPixels,
                textureU,
                textureV + emptyPixels,
                barWidth,
                currentBarHeight,
                71,
                15);

        guiGraphics.drawString(
                minecraft.font,
                Component.translatable("gui.shadowthings.core_health") ,
                centerX + 62, y + 135,
                0xFF597A
        );
        guiGraphics.drawString(
                minecraft.font,
                hpPercent + "%" ,
                centerX + 76, y + 150,
                0xFF597A
        );

        //GUI CRYSTAL
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();

        poseStack.translate(x + (imageWidth / 2.0f), y - 5 + (imageHeight / 2.0f), 150.0f);
        poseStack.scale(35.0f, -35.0f, 35.0f);

        float time = this.minecraft.level.getGameTime() + pPartialTick;
        poseStack.mulPose(Axis.XP.rotationDegrees(180.0f));

        poseStack.translate(0, 2f , 0);
        poseStack.mulPose(Axis.YP.rotationDegrees(time * 1.75f));
        poseStack.translate(-0, -2f , -0);

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
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY); // Renders item names when hovering
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 0xFFFFFF, false);
        guiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 0xF2D5FF, false);

    }
}
