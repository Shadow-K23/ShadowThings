package com.shadow.shadowthings.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.shadow.shadowthings.block.custom.SoulStructureBlock;
import com.shadow.shadowthings.client.model.SoulCoreModel; // Import your model!
import com.shadow.shadowthings.block.entity.SoulCoreEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class SoulCoreEntityRenderer implements BlockEntityRenderer<SoulCoreEntity> {

    // 1. Point this to your white texture file!
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("shadowthings", "textures/entity/soul_core.png");

    private final SoulCoreModel model;

    public SoulCoreEntityRenderer(BlockEntityRendererProvider.Context context) {
        // 2. Load the 3D model into memory
        this.model = new SoulCoreModel(context.bakeLayer(SoulCoreModel.LAYER_LOCATION));
    }

    @Override
    public AABB getRenderBoundingBox(SoulCoreEntity blockEntity) {
        BlockPos pos = blockEntity.getBlockPos();

        // AABB using 6 doubles: (minX, minY, minZ, maxX, maxY, maxZ)
        return new AABB(
                pos.getX() - 1.0, pos.getY(),       pos.getZ() - 1.0,
                pos.getX() + 2.0, pos.getY() + 4.0, pos.getZ() + 2.0
        );
    }


    @Override
    public void render(SoulCoreEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (blockEntity.getLevel() == null) return;

        // Check if the block is actually formed. If not, stop rendering the 3D model!
        if (!blockEntity.isFormed) {
            return;
        }

        float damageFactor = 1.0f - ((float) Math.max(1, blockEntity.coreHealth) / 1000f);

        // 2. Set a maximum jitter distance (0.1 blocks is a very violent shake)
        float maxJitter = 0.1f * damageFactor;
        float fullness = (float)blockEntity.getSouls() / blockEntity.getMaxSouls(); // Testing value

        // --- ANIMATION MATH ---
        long time = blockEntity.getLevel().getGameTime();
        float bobbingOffset = (float) Math.sin((time + partialTick) / 10.0f) * 0.1f;
        float rotationAngle = (time + partialTick) * 1.5f;


        // --- DYNAMIC COLOR (Cyan to Red) ---
        int r = (int) (40 + 215 * fullness);
        int g = (int) (0 + 60 * fullness);
        int b = (int) (35 + 220 * fullness);
        int color = FastColor.ARGB32.color(255, r, g, b);

        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityTranslucent(TEXTURE));
        poseStack.pushPose();

        // ==========================================
        // 1. GLOBAL SETUP (Applies to everything)
        // ==========================================


        poseStack.translate(0.5, 1.5, 0.5);
        poseStack.mulPose(Axis.XP.rotationDegrees(180f)); // Fix the upside-down Blockbench export

        // Draw the static Base (Normal Light, Normal Size, Normal White Color)
        this.model.base.render(poseStack, vertexConsumer, packedLight, packedOverlay, 0xFFFFFFFF);
        poseStack.pushPose();
        // ==========================================
        // 2. CRYSTAL SETUP (Applies ONLY to the crystal)
        // ==========================================
        //CRYSTAL JITTER
        float randomX = (float) ((Math.random() - 0.5) * 2.0 * maxJitter);
        float randomY = (float) ((Math.random() - 0.5) * 2.0 * maxJitter);
        float randomZ = (float) ((Math.random() - 0.5) * 2.0 * maxJitter);

        // 4. Apply the translation (I added the random offsets directly)
        poseStack.translate(0.0 + randomX, -bobbingOffset + randomY, 0.0 + randomZ);
        poseStack.mulPose(Axis.YP.rotationDegrees(rotationAngle));

        float scale = 0.20f + (1.5f * fullness);

        if (blockEntity.isMeltingDown) {
            int timer = blockEntity.meltdownTimer;

            if (timer >= 1000 && timer < 1700) {
                float phaseProgress = (timer - 1000 + partialTick) / 700.0f;
                float amplitude = phaseProgress * 3.0f;
                float pulse = (float) Math.abs(Math.sin((timer + partialTick) / 15.0f)) * amplitude;
                scale += pulse;
            }
            else if (timer >= 1700) {
                float maxAmplitude = 3.0f;
                float maxScaleBeforeCollapse = (0.20f + (1.5f * fullness)) + (float) Math.abs(Math.sin(1699.0f / 15.0f)) * maxAmplitude;

                float collapseProgress = (timer - 1700 + partialTick) / 100.0f;
                scale = maxScaleBeforeCollapse * (float) Math.pow(1.0 - collapseProgress, 3);
            }
        }

        // Apply the final calculated scale
        poseStack.scale(scale, scale, scale);

        // Draw the Crystal (Glowing Light, Scaled, Spinning, Custom Color)
        this.model.crystal.render(poseStack, vertexConsumer, 15728880, packedOverlay, color);

        poseStack.popPose(); // Erase the crystal math
        poseStack.popPose(); // Erase the global math
    }
}
