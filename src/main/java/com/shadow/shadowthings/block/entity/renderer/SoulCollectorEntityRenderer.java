package com.shadow.shadowthings.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.shadow.shadowthings.block.entity.SoulCollectorEntity;
import com.shadow.shadowthings.block.entity.base.AbstractSoulEntityRenderer;
import com.shadow.shadowthings.client.model.SoulOrbModel;
import com.shadow.shadowthings.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.phys.Vec3;

public class SoulCollectorEntityRenderer extends AbstractSoulEntityRenderer<SoulCollectorEntity> {

    private final SoulOrbModel orbModel;

    public SoulCollectorEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
        this.orbModel = new SoulOrbModel(context.bakeLayer(SoulOrbModel.LAYER_LOCATION));
    }

    @Override
    public void renderMachine(SoulCollectorEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {

        long time = entity.getLevel().getGameTime();

        float fillRatio = 0f;
        if (entity.getMaxSouls() > 0) {
            fillRatio = (float) entity.getSouls() / (float) entity.getMaxSouls();
        }

        float currentSpeed = 2.0F + (fillRatio * 6.0F);
        float spinAngle = entity.clientSpinAngle + (currentSpeed * partialTick);

        float bob = (float) Math.sin((time + partialTick) / 10.0F) * 0.1F;

        poseStack.pushPose();

        poseStack.translate(0.5D, 1.75D + bob, 0.5D);

        poseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(spinAngle));

        float scale = 0.6F + (fillRatio * 0.6F);
        poseStack.scale(scale, scale, scale);

        poseStack.translate(0.0D, -1.0D, 0.0D);

        // --- DYNAMIC COLOR ---
        int r = (int) (220 - (180 * fillRatio));
        int g = (int) (240 - (60 * fillRatio));
        int b = 255;
        int color = net.minecraft.util.FastColor.ARGB32.color(255, r, g, b);

        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityTranslucent(
                net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("shadowthings", "textures/entity/soul_orb.png")));

        this.orbModel.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, color);

        poseStack.popPose();


    }
}