package com.shadow.shadowthings.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.shadow.shadowthings.block.entity.SoulPedestalEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class SoulPedestalEntityRenderer implements BlockEntityRenderer<SoulPedestalEntity> {

    private final ItemRenderer itemRenderer;

    public SoulPedestalEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(SoulPedestalEntity pedestal, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {

        ItemStack stack = pedestal.inventory.getStackInSlot(0);
        if (stack.isEmpty() || pedestal.getLevel() == null) return;

        // Smooth, non-jittery time value for animation (survives tick boundaries)
        float time = pedestal.getLevel().getGameTime() + partialTick;

        poseStack.pushPose();

        // Pedestal collision box top is at y = 14/16 = 0.875, float just above it
        poseStack.translate(0.5, 1.1, 0.5);

        // Gentle up/down bob
        float bob = Mth.sin(time / 10f) * 0.05f;
        poseStack.translate(0, bob, 0);

        // Slow, constant spin
        poseStack.mulPose(Axis.YP.rotationDegrees((time * 2f) % 360f));

        poseStack.scale(0.5f, 0.5f, 0.5f);

        itemRenderer.renderStatic(
                stack,
                ItemDisplayContext.GROUND,
                packedLight,
                packedOverlay,
                poseStack,
                bufferSource,
                pedestal.getLevel(),
                0
        );

        poseStack.popPose();
    }
}