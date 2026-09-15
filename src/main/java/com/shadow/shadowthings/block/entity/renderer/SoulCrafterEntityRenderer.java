package com.shadow.shadowthings.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.shadow.shadowthings.block.entity.SoulCrafterEntity;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class SoulCrafterEntityRenderer implements BlockEntityRenderer<SoulCrafterEntity> {

    private final ItemRenderer itemRenderer;

    public SoulCrafterEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(SoulCrafterEntity crafter, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {

        ItemStack stack = crafter.inventory.getStackInSlot(0);
        if (stack.isEmpty() || crafter.getLevel() == null) return;

        poseStack.pushPose();

        // 1. HEIGHT & BOBBING
        float time = crafter.getLevel().getGameTime() + partialTick;
        float height = 0.75f + (crafter.renderYOffset * 0.75f);
        poseStack.translate(0.5, height, 0.5);
        if(!crafter.isCrafting) {
            float bob = Mth.sin(time / 10f) * 0.05f;
            poseStack.translate(0, bob, 0);
        }
        // 2. SMOOTH ROTATION MATH
        float currentSpeed = 2f;
        if (crafter.isCrafting && crafter.requiredSouls > 0) {
            // Again, cast FIRST!
            float progress = (float) crafter.getSouls() / (float) crafter.requiredSouls;
            currentSpeed = 4f + (progress * 128f);
        }

        // 2. Add the partialTick for high-FPS smoothness
        float smoothAngle = crafter.spinAngle + (currentSpeed * partialTick);

        // 3. Apply the rotation!
        poseStack.mulPose(Axis.YP.rotationDegrees(smoothAngle));

        // 3. SCALING
        float scale = crafter.isCrafting ? 0.75f : 0.5f;
        poseStack.scale(scale, scale, scale);

        // 4. RENDER ITEM
        itemRenderer.renderStatic(
                stack,
                ItemDisplayContext.GROUND,
                packedLight,
                packedOverlay,
                poseStack,
                bufferSource,
                crafter.getLevel(),
                0
        );

        poseStack.popPose();
    }
}