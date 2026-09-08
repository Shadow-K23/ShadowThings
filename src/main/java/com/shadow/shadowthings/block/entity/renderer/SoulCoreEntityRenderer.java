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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.level.block.state.BlockState;

public class SoulCoreEntityRenderer implements BlockEntityRenderer<SoulCoreEntity> {

    // 1. Point this to your white texture file!
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("shadowthings", "textures/entity/soul_core.png");

    private final SoulCoreModel model;

    public SoulCoreEntityRenderer(BlockEntityRendererProvider.Context context) {
        // 2. Load the 3D model into memory
        this.model = new SoulCoreModel(context.bakeLayer(SoulCoreModel.LAYER_LOCATION));
    }

    @Override
    public net.minecraft.world.phys.AABB getRenderBoundingBox(SoulCoreEntity blockEntity) {
        net.minecraft.core.BlockPos pos = blockEntity.getBlockPos();

        // AABB using 6 doubles: (minX, minY, minZ, maxX, maxY, maxZ)
        return new net.minecraft.world.phys.AABB(
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

            // ==========================================
            // 1. GLOBAL SETUP (Applies to everything)
            // ==========================================
            poseStack.pushPose();

            poseStack.translate(0.5, 1.5, 0.5);
            poseStack.mulPose(Axis.XP.rotationDegrees(180f)); // Fix the upside-down Blockbench export

            // Draw the static Base (Normal Light, Normal Size, Normal White Color)
            this.model.base.render(poseStack, vertexConsumer, packedLight, packedOverlay, 0xFFFFFFFF);


            // ==========================================
            // 2. CRYSTAL SETUP (Applies ONLY to the crystal)
            // ==========================================
            poseStack.pushPose();

            // Because we flipped the world 180 degrees earlier, we have to subtract the bobbing offset!
            poseStack.translate(0.0, -bobbingOffset - 0.15, 0.0);

            poseStack.mulPose(Axis.YP.rotationDegrees(rotationAngle));

            float scale = 0.20f + (1.5f * fullness);
            poseStack.scale(scale, scale, scale);

            // Draw the Crystal (Glowing Light, Scaled, Spinning, Custom Color)
            this.model.crystal.render(poseStack, vertexConsumer, 15728880, packedOverlay, color);

            poseStack.popPose(); // Erase the crystal math
            poseStack.popPose(); // Erase the global math
        }
    }
