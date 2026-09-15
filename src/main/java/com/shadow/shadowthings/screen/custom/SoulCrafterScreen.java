package com.shadow.shadowthings.screen.custom;

import com.shadow.shadowthings.network.ModStartCraftingPayload;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.network.PacketDistributor;

public class SoulCrafterScreen extends AbstractContainerScreen<SoulCrafterMenu> {
    // Change "textures/gui/crafter.png" to match your actual file path!
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("shadowthings", "textures/gui/soul_crafter_gui.png");
    private static final ResourceLocation UPGRADE_PANEL =
            ResourceLocation.fromNamespaceAndPath("shadowthings", "textures/gui/upgrade_gui.png");

    public SoulCrafterScreen(SoulCrafterMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void init() {
        super.init();

        // Add the Craft Button
        this.addRenderableWidget(Button.builder(Component.literal("Craft"), button -> {
            // Send the network packet to the server!
            var pos = this.menu.getBlockEntity().getBlockPos();
            PacketDistributor.sendToServer(new ModStartCraftingPayload(pos));
        }).bounds(this.leftPos + 50, this.topPos + 60, 76, 20).build());
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        // Draw the background texture
        guiGraphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);


        int panelX = leftPos + this.imageWidth;
        int panelY = topPos + 5; // Push it down 5 pixels from the top

        // 3. Draw the upgrade panel
        // Parameters: texture, x, y, uOffset, vOffset, width, height
        guiGraphics.blit(UPGRADE_PANEL, panelX, panelY, 0, 0, 27, 83);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY); // Renders item names when hovering
    }
}
