package com.shadow.shadowthings.soul;

import com.shadow.shadowthings.server.ModDataAttachments;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;


public class ModManaHudOverlay {

    private static final ResourceLocation MANA_BOX = ResourceLocation.fromNamespaceAndPath("shadowthings", "textures/gui/mana_bar_box.png");
    private static final ResourceLocation MANA_BAR = ResourceLocation.fromNamespaceAndPath("shadowthings", "textures/gui/mana_bar.png");

    private static float displayedMana = 100f;

    public static void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null || minecraft.level == null ) return;

        var manaData = minecraft.player.getData(ModDataAttachments.PLAYER_SOUL_MANA);

        if (!manaData.hasSynced) return;

        int targetMana = manaData.getMana();
        int maxMana = manaData.getMaxMana();

        // 1. SMOOTH RESIZING MATH (Lerping with DeltaTracker)
        // deltaTracker.getGameTimeDeltaPartialTick(true) keeps the speed identical no matter your FPS!
        float delta = deltaTracker.getGameTimeDeltaPartialTick(true);
        displayedMana += (targetMana - displayedMana) * (0.1f * delta);

        if (Math.abs(displayedMana - targetMana) < 0.5f) {
            displayedMana = targetMana;
        }

        // --- UPDATE THESE NUMBERS TO MATCH YOUR PNG DIMENSIONS ---
        int boxWidth = 102;
        int boxHeight = 22;

        int maxBarWidth = 101;
        int frameHeight = 20;  // Height of just ONE frame of your animation
        int totalFrames = 4;   // How many frames are in your tall image
        int totalImageHeight = frameHeight * totalFrames;
        // ---------------------------------------------------------

        float manaPercentage = displayedMana / (float) maxMana;
        int currentAnimatedWidth = (int) (maxBarWidth * manaPercentage);

        // 2. ANIMATION MATH
        // We use Util.getMillis() to get real-world milliseconds.
        // 150 is the speed (milliseconds per frame). Lower = faster, Higher = slower.
        int currentFrame = (int) ((net.minecraft.Util.getMillis() / 300) % totalFrames);
        int textureYOffset = currentFrame * frameHeight;

        // 3. SCREEN POSITIONING
        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        int screenHeight = minecraft.getWindow().getGuiScaledHeight();

        // 10 pixels of padding from the right edge, and 10 pixels from the bottom
        int paddingRight = 10;
        int paddingBottom = 10;

        int x = screenWidth - boxWidth - paddingRight;
        int y = screenHeight - boxHeight - paddingBottom;

        // 4. DRAW BACKGROUND BOX
        guiGraphics.blit(MANA_BOX, x, y, 0, 0, boxWidth, boxHeight, boxWidth, boxHeight);

        // 5. DRAW ANIMATED BAR (With Scissoring)
        // We add +2 to x and y so the bar sits inside the box's borders
        int barX = x + 1;
        int barY = y + 1;

        double scale = minecraft.getWindow().getGuiScale();

        // Turn on clipping box based on current mana width
        guiGraphics.enableScissor(
                barX,
                barY,
                barX + currentAnimatedWidth,
                barY + frameHeight
        );

        // Draw the full bar, but shifted down to the correct animation frame!
        guiGraphics.blit(MANA_BAR, barX, barY, 0, textureYOffset, maxBarWidth, frameHeight, maxBarWidth, totalImageHeight);

        // Turn off clipping box
        guiGraphics.disableScissor();

        Component text = Component.translatable("gui.shadowthings.player_souls")
                .append(Component.literal(" " + targetMana + "/" + maxMana));
        // 6. DRAW TEXT
        guiGraphics.drawString(
                minecraft.font,
                text,
                x + 5, y + 7,
                0x41beff
        );
    }
}
