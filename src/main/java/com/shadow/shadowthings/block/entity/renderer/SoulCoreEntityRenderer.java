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

        float damageFactor = 1.0f - ((float) Math.max(1, blockEntity.coreHealth) / 10000f);

        // 2. Set a maximum jitter distance (0.1 blocks is a very violent shake)
        float maxJitter = 0.1f * damageFactor;
        float fullness = Math.min(1.0f,(float)blockEntity.getSouls() / blockEntity.getMaxSouls()); // Testing value

        // --- ANIMATION MATH ---
        long time = blockEntity.getLevel().getGameTime();
        float bobbingOffset = (float) Math.sin((time + partialTick) / 10.0f) * 0.1f;
        float rotationAngle = (time + partialTick) * 1.5f;


        // --- DYNAMIC COLOR (Cyan to Red) ---
        int r = (int) (40 + 215 * fullness);
        int g = (int) (0 + 60 * fullness);
        int b = (int) (35 + 220 * fullness);
        float scale = 0.20f + (1.5f * fullness);

        // --- 2. MELTDOWN OVERRIDES ---
        if (blockEntity.isMeltingDown) {
            int timer = blockEntity.meltdownTimer;
            float exactTimer = timer + partialTick;

            // A. Stop the gentle floating bob instantly
            bobbingOffset = 0.0f;

            // B. Accelerate Rotation (Exponentially faster as timer increases)
            rotationAngle += exactTimer * (exactTimer / 30.0f);

            // C. Flashing Colors (Strobes faster as detonation approaches)
            float flashSpeed = 0.2f + (exactTimer / 800.0f);
            float flash = (float) Math.abs(Math.sin(exactTimer * flashSpeed));
            r = 255; // Lock red to max for extreme danger
            g = (int) (255 * flash); // Flash white/yellow
            b = (int) (255 * flash);

            // D. Scaling Overrides (Replacing the old slow pulse)
            if (timer < 1680) {
                // The crystal slowly swells and bulges outward constantly until collapse
                scale += (exactTimer / 1700.0f) * 1.5f;
            }
            else if (timer >= 1680) {
                // Phase 3B: Dead Silence - Snap to pitch black!
                r = 0; g = 0; b = 0;

                // Freeze rotation exactly where it was at tick 1699!
                float frozenTimer = 1679.0f;
                rotationAngle = (time - (timer - 1680) + partialTick) * 1.5f + (frozenTimer * (frozenTimer / 30.0f));

                // The violent cubic collapse shrink
                float maxScaleBeforeCollapse = (0.20f + (1.5f * fullness)) + 1.5f;
                float collapseProgress = (exactTimer - 1680) / 100.0f;
                scale = maxScaleBeforeCollapse * (float) Math.pow(1.0 - collapseProgress, 3);
            }
        }
        // Apply final color
        int color = FastColor.ARGB32.color(255, r, g, b);

        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityTranslucent(TEXTURE));
        poseStack.pushPose();

        // ==========================================
        // 1. GLOBAL SETUP (Applies to everything)
        // ==========================================
        poseStack.translate(0.5, 1.5, 0.5);
        poseStack.mulPose(Axis.XP.rotationDegrees(180f));

        this.model.base.render(poseStack, vertexConsumer, packedLight, packedOverlay, 0xFFFFFFFF);
        poseStack.pushPose();

        // ==========================================
        // 2. CRYSTAL SETUP (Applies ONLY to the crystal)
        // ==========================================
        float randomX = (float) ((Math.random() - 0.5) * 2.0 * maxJitter);
        float randomY = (float) ((Math.random() - 0.5) * 2.0 * maxJitter);
        float randomZ = (float) ((Math.random() - 0.5) * 2.0 * maxJitter);

        // Apply the modified translations and angles!
        poseStack.translate(0.0 + randomX, -bobbingOffset + randomY, 0.0 + randomZ);
        poseStack.mulPose(Axis.YP.rotationDegrees(rotationAngle));
        poseStack.scale(scale, scale, scale);

        // Draw the Crystal (Glowing Light, Scaled, Spinning, Custom Color)
        this.model.crystal.render(poseStack, vertexConsumer, 15728880, packedOverlay, color);

        poseStack.popPose(); // Erase the crystal math
        poseStack.popPose(); // Erase the global math
    }
}
