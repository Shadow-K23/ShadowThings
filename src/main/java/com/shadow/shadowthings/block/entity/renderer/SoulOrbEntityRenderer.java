package com.shadow.shadowthings.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.shadow.shadowthings.client.model.SoulOrbModel; // Import your model!
import com.shadow.shadowthings.block.entity.SoulOrbEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class SoulOrbEntityRenderer implements BlockEntityRenderer<SoulOrbEntity> {

    // 1. Point this to your white texture file!
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("shadowthings", "textures/entity/soul_orb.png");

    private final SoulOrbModel model;

    public SoulOrbEntityRenderer(BlockEntityRendererProvider.Context context) {
        // 2. Load the 3D model into memory
        this.model = new SoulOrbModel(context.bakeLayer(SoulOrbModel.LAYER_LOCATION));
    }

    @Override
    public void render(SoulOrbEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (blockEntity.getLevel() == null) return;

        // --- MOCK DATA FOR TESTING ---
        // For testing, let's pretend it's 50% full (0.5f).
        // Once it works, change this to: float fullness = (float) blockEntity.getSouls() / blockEntity.getMaxSouls();
        float fullness = 1.0f;

        // --- ANIMATION MATH ---
        long time = blockEntity.getLevel().getGameTime();
        float bobbingOffset = (float) Math.sin((time + partialTick) / 10.0f) * 0.1f;
        float rotationAngle = (time + partialTick) * 1.5f;

        // --- DYNAMIC COLOR (Cyan to Red) ---
        int r = (int) (0 + (255 - 0) * fullness);
        int g = (int) (255 + (0 - 255) * fullness);
        int b = (int) (255 + (0 - 255) * fullness);

        // Use Minecraft's built-in ARGB packer to guarantee the correct color format!
        int color = net.minecraft.util.FastColor.ARGB32.color(255, r, g, b);

        poseStack.pushPose();

        // --- POSITION & SCALE ---
        poseStack.translate(0.5, 3.5 + bobbingOffset, 0.5);
        poseStack.mulPose(Axis.YP.rotationDegrees(rotationAngle));

        // IMPORTANT: Blockbench Java models render upside down by default!
        // We flip it 180 degrees to fix it.
        poseStack.mulPose(Axis.XP.rotationDegrees(180f));

        float scale = 0.15f + (1.5f * fullness);
        poseStack.scale(scale, scale, scale);

        // --- DRAW THE MODEL ---
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityTranslucent(TEXTURE));

        // We use 15728880 to make it glow in the dark!
        this.model.renderToBuffer(poseStack, vertexConsumer, 15728880, packedOverlay, color);

        poseStack.popPose();
    }
}