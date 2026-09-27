package com.shadow.shadowthings.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.shadow.shadowthings.ShadowThings;
import com.shadow.shadowthings.block.custom.SoulFurnaceBlock;
import com.shadow.shadowthings.block.entity.SoulFurnaceEntity;
import com.shadow.shadowthings.block.entity.base.AbstractSoulEntityRenderer;
import com.shadow.shadowthings.client.model.SoulFurnaceModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class SoulFurnaceEntityRenderer extends AbstractSoulEntityRenderer<SoulFurnaceEntity> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ShadowThings.MODID, "textures/entity/soul_furnace.png");

    private final SoulFurnaceModel furnaceModel;
    private final ItemRenderer itemRenderer;
    private float clientSmoothedMomentum = 0.0f;

    public SoulFurnaceEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.furnaceModel = new SoulFurnaceModel(context.bakeLayer(SoulFurnaceModel.LAYER_LOCATION));
    }

    @Override
    public void renderMachine(SoulFurnaceEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        if (!entity.isFormed) return;

        float centerX = 0.5f;
        float centerY = 1.175f;
        float centerZ = 0.5f;


        //RENDER THE STATIC BASE BONE
        poseStack.pushPose();
        poseStack.translate(0.5f, 1.5f, 0.5f);
        poseStack.mulPose(Axis.XP.rotationDegrees(180));

        // Request buffer strictly right before drawing the base
        VertexConsumer baseConsumer = buffer.getBuffer(RenderType.entityTranslucentCull(TEXTURE));
        this.furnaceModel.base.render(poseStack, baseConsumer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();

        float momentumLerpRate = 0.1f;
        this.clientSmoothedMomentum += (entity.momentum - this.clientSmoothedMomentum) * momentumLerpRate;

        float speedPercent = this.clientSmoothedMomentum / Math.max(1.0f, entity.maxMomentum);

        // RENDER THE HOVERING ITEM
        ItemStack inputStack = entity.mainInventory.getStackInSlot(0);
        if (!inputStack.isEmpty()) {
            poseStack.pushPose();
            poseStack.translate(centerX, centerY + 0.5f, centerZ);

            float itemTime = (entity.getLevel().getGameTime() + partialTick) * 3.0f;
            poseStack.mulPose(Axis.YP.rotationDegrees(itemTime));
            poseStack.scale(0.75f, 0.75f, 0.75f);

            this.itemRenderer.renderStatic(inputStack, ItemDisplayContext.GROUND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, entity.getLevel(), 0);
            poseStack.popPose();
        }

        // PARAMETRIC MATH: THE ORBITING BALL BONE
        float orbitTime = entity.prevOrbitAngle + (entity.orbitAngle - entity.prevOrbitAngle) * partialTick;

        poseStack.pushPose();

        // Start at the center of your Controller block
        poseStack.translate(0.5f, 1.825f, 0.5f);


        // SPEED MIRAGES
        float trailGap = (float) (Math.PI * 2) / 20.0f;

        int ballCount = 1;

        // Only start spawning trailing mirages once momentum hits 150
        if (entity.momentum >= 125.0f) {
            // 1. Normalize momentum between 125 and 650
            float progress = (entity.momentum - 125.0f) / (650.0f - 125.0f);
            // Safety clamp
            progress = Math.clamp(progress, 0.0f, 1.0f);

            float linearPart = progress * 0.35f;
            float exponentialPart = (progress * progress) * 0.65f;

            float curveFactor = linearPart + exponentialPart;

            int extraBalls = (int) (curveFactor * 20.0f);

            ballCount = 1 + extraBalls;
        }

        // --- DYNAMIC COLOR CALCULATION ---

        float red = 0.3f + (speedPercent * 0.70f);
        float green = 0.1f + (speedPercent * 0.25f);
        float blue = 0.25f + (speedPercent * 0.75f);

        int rInt = (int) (red * 255.0f);
        int gInt = (int) (green * 255.0f);
        int bInt = (int) (blue * 255.0f);
        int packedColor = (255 << 24) | (rInt << 16) | (gInt << 8) | bInt;


        // --- 5. RENDER CRYSTAL BALLS (Pass 1) ---
        VertexConsumer ballConsumer = buffer.getBuffer(RenderType.entityTranslucentCull(TEXTURE));

        for (int ring = 0; ring < 2; ring++) {
            poseStack.pushPose();

            float radius = (ring == 0) ? 0.7f : 1f;
            float currentOrbitTime = (ring == 0) ? orbitTime : -orbitTime * 0.70f;

            float smoothSpeed = speedPercent * speedPercent * (3.0f - 2.0f * speedPercent);

            if (ring == 0) {
                // Inner Ring: Primary Spin (Y-Axis) - Scaled by smoothSpeed
                float spinY = (float) (Math.sin(orbitTime * 0.015f) * 300.0f + Math.sin(orbitTime * 0.005f) * 600.0f);
                poseStack.mulPose(Axis.YP.rotationDegrees(spinY * smoothSpeed));

                // Inner Ring: Secondary Spin (X-Axis)
                float spinX = (float) (Math.cos(orbitTime * 0.018f) * 400.0f + Math.sin(orbitTime * 0.006f) * 500.0f);
                poseStack.mulPose(Axis.XP.rotationDegrees(spinX * smoothSpeed));
            } else {
                // Outer Ring: Primary Spin (Y-Axis) - Scaled by smoothSpeed
                float spinY = (float) (Math.sin(orbitTime * 0.02f) * 450.0f + Math.sin(orbitTime * 0.007f) * 750.0f);
                poseStack.mulPose(Axis.YP.rotationDegrees(spinY * smoothSpeed));

                // Outer Ring: Secondary Spin (Z-Axis)
                float spinZ = (float) (Math.cos(orbitTime * 0.022f) * 550.0f + Math.sin(orbitTime * 0.008f) * 700.0f);
                poseStack.mulPose(Axis.ZP.rotationDegrees(spinZ * smoothSpeed));
            }

            for (int i = 0; i < ballCount; i++) {
                poseStack.pushPose();

                float timeOffset = currentOrbitTime - (i * trailGap);
                double orbitX = Math.sin(timeOffset) * radius;
                double orbitZ = Math.cos(timeOffset) * radius;

                poseStack.translate(orbitX, -0.3, orbitZ);
                poseStack.mulPose(Axis.XP.rotationDegrees(180));

                this.furnaceModel.ball.render(poseStack, ballConsumer, packedLight, OverlayTexture.NO_OVERLAY, packedColor);

                poseStack.popPose();
            }
            poseStack.popPose();
        }

        // --- 6. RENDER ENERGY BEAMS (Pass 2) ---
        // Only run the entire second pass if we are actively smelting!
        if (entity.isSmelting) {
            VertexConsumer beamConsumer = buffer.getBuffer(RenderType.lightning());

            for (int ring = 0; ring < 2; ring++) {
                poseStack.pushPose();

                float radius = (ring == 0) ? 0.7f : 1f;
                float currentOrbitTime = (ring == 0) ? orbitTime : -orbitTime * 0.70f;

                float smoothSpeed = speedPercent * speedPercent * (3.0f - 2.0f * speedPercent);

                if (ring == 0) {
                    // Inner Ring: Primary Spin (Y-Axis) - Scaled by smoothSpeed so it glides to a stop
                    float spinY = (float) (Math.sin(orbitTime * 0.015f) * 300.0f + Math.sin(orbitTime * 0.005f) * 600.0f);
                    poseStack.mulPose(Axis.YP.rotationDegrees(spinY * smoothSpeed));

                    // Inner Ring: Secondary Spin (X-Axis)
                    float spinX = (float) (Math.cos(orbitTime * 0.018f) * 400.0f + Math.sin(orbitTime * 0.006f) * 500.0f);
                    poseStack.mulPose(Axis.XP.rotationDegrees(spinX * smoothSpeed));
                } else {
                    // Outer Ring: Primary Spin (Y-Axis) - Scaled by smoothSpeed
                    float spinY = (float) (Math.sin(orbitTime * 0.02f) * 450.0f + Math.sin(orbitTime * 0.007f) * 750.0f);
                    poseStack.mulPose(Axis.YP.rotationDegrees(spinY * smoothSpeed));

                    // Outer Ring: Secondary Spin (Z-Axis)
                    float spinZ = (float) (Math.cos(orbitTime * 0.022f) * 550.0f + Math.sin(orbitTime * 0.008f) * 700.0f);
                    poseStack.mulPose(Axis.ZP.rotationDegrees(spinZ * smoothSpeed));
                }

                for (int i = 0; i < ballCount; i++) {
                    float timeOffset = currentOrbitTime - (i * trailGap);
                    double orbitX = Math.sin(timeOffset) * radius;
                    double orbitZ = Math.cos(timeOffset) * radius;

                    float timeTicks = entity.getLevel().getGameTime() + partialTick;
                    float arcNoise = (float) Math.sin(timeTicks * 0.8f + (i * 4.3f) + (ring * 10)) + (float) Math.sin(timeTicks * 1.7f + i - ring);
                    float arcThreshold = 1.5f - (speedPercent * 2.5f);

                    if (arcNoise > arcThreshold) {
                        float yaw = (float) Math.toDegrees(Math.atan2(orbitX, orbitZ));
                        float length = (float) Math.sqrt((radius * radius) + (-0.3 * -0.3));

                        poseStack.pushPose();
                        poseStack.mulPose(Axis.YP.rotationDegrees(yaw));


                        Matrix4f poseMatrix = poseStack.last().pose();
                        float thickness = 0.05f + (float) (Math.sin(orbitTime * 15.0f + i) * 0.02f);

                        // Vertical Beam (Front & Back)
                        beamConsumer.addVertex(poseMatrix, 0, -thickness, 0).setColor(rInt, gInt, bInt, 180);
                        beamConsumer.addVertex(poseMatrix, 0, thickness, 0).setColor(rInt, gInt, bInt, 180);
                        beamConsumer.addVertex(poseMatrix, 0, thickness, length).setColor(rInt, gInt, bInt, 180);
                        beamConsumer.addVertex(poseMatrix, 0, -thickness, length).setColor(rInt, gInt, bInt, 180);

                        beamConsumer.addVertex(poseMatrix, 0, -thickness, length).setColor(rInt, gInt, bInt, 180);
                        beamConsumer.addVertex(poseMatrix, 0, thickness, length).setColor(rInt, gInt, bInt, 180);
                        beamConsumer.addVertex(poseMatrix, 0, thickness, 0).setColor(rInt, gInt, bInt, 180);
                        beamConsumer.addVertex(poseMatrix, 0, -thickness, 0).setColor(rInt, gInt, bInt, 180);

                        // Horizontal Beam (Top & Bottom)
                        beamConsumer.addVertex(poseMatrix, -thickness, 0, 0).setColor(rInt, gInt, bInt, 180);
                        beamConsumer.addVertex(poseMatrix, thickness, 0, 0).setColor(rInt, gInt, bInt, 180);
                        beamConsumer.addVertex(poseMatrix, thickness, 0, length).setColor(rInt, gInt, bInt, 180);
                        beamConsumer.addVertex(poseMatrix, -thickness, 0, length).setColor(rInt, gInt, bInt, 180);

                        beamConsumer.addVertex(poseMatrix, -thickness, 0, length).setColor(rInt, gInt, bInt, 180);
                        beamConsumer.addVertex(poseMatrix, thickness, 0, length).setColor(rInt, gInt, bInt, 180);
                        beamConsumer.addVertex(poseMatrix, thickness, 0, 0).setColor(rInt, gInt, bInt, 180);
                        beamConsumer.addVertex(poseMatrix, -thickness, 0, 0).setColor(rInt, gInt, bInt, 180);

                        poseStack.popPose();
                    }
                }
                poseStack.popPose();
            }
        }

        poseStack.popPose();
    }

    @Override
    public net.minecraft.world.phys.AABB getRenderBoundingBox(SoulFurnaceEntity blockEntity) {
        return new net.minecraft.world.phys.AABB(blockEntity.getBlockPos()).inflate(3.0);
    }
}