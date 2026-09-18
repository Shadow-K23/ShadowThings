package com.shadow.shadowthings.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.shadow.shadowthings.ShadowThings;
import com.shadow.shadowthings.block.custom.SoulFurnaceBlock;
import com.shadow.shadowthings.block.entity.SoulFurnaceEntity;
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

public class SoulFurnaceEntityRenderer implements BlockEntityRenderer<SoulFurnaceEntity> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ShadowThings.MODID, "textures/entity/soul_furnace.png");

    private final SoulFurnaceModel furnaceModel;
    private final ItemRenderer itemRenderer;

    public SoulFurnaceEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
        this.furnaceModel = new SoulFurnaceModel(context.bakeLayer(SoulFurnaceModel.LAYER_LOCATION));
    }

    @Override
    public void render(SoulFurnaceEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        if (!entity.isFormed) return;

        float centerX = 0.5f;
        float centerY = 1.5f; // Adjust this if your base/item is too high or low
        float centerZ = 0.5f;

        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityTranslucentCull(TEXTURE));


        // --- 2. RENDER THE STATIC BASE BONE ---
        poseStack.pushPose();
        // If your base is meant to sit on the controller block itself rather than the center of the cradle,
        // change these coordinates back to (0.5f, 1.5f, 0.5f).
        poseStack.translate(0.5f, 1.5f, 0.5f);

        // Standard Blockbench Java export correction (flips the model right-side up)
        poseStack.mulPose(Axis.XP.rotationDegrees(180));

        // RENDER ONLY THE BASE BONE!
        this.furnaceModel.base.render(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();


        // --- 3. RENDER THE HOVERING ITEM ---
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

        // --- 4. PARAMETRIC MATH: THE ORBITING BALL BONE ---
        float orbitTime = entity.prevOrbitAngle + (entity.orbitAngle - entity.prevOrbitAngle) * partialTick;

        poseStack.pushPose();

        // Start at the center of your Controller block
        poseStack.translate(0.5f, 1.825f, 0.5f);

        // --- ORBITAL PRECESSION (The Wobble) ---
        float precessionTime = (entity.getLevel().getGameTime() + partialTick) * 0.15f;
        // Max tilt lowered to 15 degrees so the circle doesn't stretch too heavily at high speeds
        float currentTilt = 15.0f + ((entity.momentum / Math.max(1, entity.maxMomentum)));

        poseStack.mulPose(Axis.ZP.rotationDegrees((float) Math.sin(precessionTime) * currentTilt));
        poseStack.mulPose(Axis.XP.rotationDegrees((float) Math.cos(precessionTime) * currentTilt));


        // --- SPEED MIRAGES (The Perfect Ring) ---
        // A full circle is 2*PI radians. We slice the circle into 16 perfect increments.
        float trailGap = (float) (Math.PI * 2) / 16.0f;

        int ballCount = 1; // Default to just the main crystal

        // Only start spawning trailing mirages once momentum hits 150
        if (entity.momentum >= 150.0f) {
            // Normalize momentum between 150 and 500 into a 0.0 to 1.0 scale
            float progress = (entity.momentum - 150.0f) / (500.0f - 150.0f);

            // Square the progress (progress * progress) for an exponential curve.
            // It starts growing slowly right after 150, then accelerates aggressively toward 500!
            float exponentialFactor = progress * progress;

            // Map the exponential curve to our 16 extra trail slots
            int extraBalls = (int) (exponentialFactor * 16.0f);

            ballCount = 1 + extraBalls;
        }

        for (int i = 0; i < ballCount; i++) {
            poseStack.pushPose();

            // Space each ghost ball strictly by the perfect mathematical gap
            float timeOffset = orbitTime - (i * trailGap);

            // The flat plane orbit
            double orbitX = Math.sin(timeOffset) * 0.85;
            double orbitZ = Math.cos(timeOffset) * 0.85;

            poseStack.translate(orbitX, 0.0, orbitZ);
            poseStack.mulPose(Axis.XP.rotationDegrees(180));

            //poseStack.mulPose(Axis.YP.rotationDegrees(timeOffset * 1.5f));
            //poseStack.mulPose(Axis.XP.rotationDegrees(timeOffset * 1.0f));

            this.furnaceModel.ball.render(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
        }

        poseStack.popPose();
    }

    @Override
    public net.minecraft.world.phys.AABB getRenderBoundingBox(SoulFurnaceEntity blockEntity) {
        return new net.minecraft.world.phys.AABB(blockEntity.getBlockPos()).inflate(3.0);
    }
}