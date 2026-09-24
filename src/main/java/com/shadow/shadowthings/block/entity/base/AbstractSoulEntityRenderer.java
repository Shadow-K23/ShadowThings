package com.shadow.shadowthings.block.entity.base;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.shadow.shadowthings.block.entity.SoulFurnaceEntity;
import com.shadow.shadowthings.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;


public abstract class AbstractSoulEntityRenderer<T extends AbstractSoulEntity> implements BlockEntityRenderer<T> {

    public AbstractSoulEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(T blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {

        renderMachine(blockEntity, partialTick, poseStack, bufferSource, packedLight, packedOverlay);

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        boolean holdingLinker = mc.player.getMainHandItem().is(ModItems.SOUL_LINKER.get()) ||
                mc.player.getOffhandItem().is(ModItems.SOUL_LINKER.get());

        if (holdingLinker) {
            VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.lines());
            Vec3 start = new Vec3(0.5, 0.5, 0.5);
            Vec3 originOffset = Vec3.atLowerCornerOf(blockEntity.getBlockPos());

            if (blockEntity.getLinkedCorePos() != null) {
                Vec3 end = Vec3.atCenterOf(blockEntity.getLinkedCorePos()).subtract(originOffset);
                drawThickLine(poseStack, vertexConsumer, start, end, 0.8f, 0.1f, 1.0f, 1.0f, 0.02);

                drawBoxOutline(poseStack, vertexConsumer, start, 0.8f, 0.1f, 1.0f, 1.0f);
            }

            if (blockEntity instanceof SoulFurnaceEntity furnace) {
                if (furnace.inputPos != null) {
                    Vec3 end = Vec3.atCenterOf(furnace.inputPos).subtract(originOffset);
                    drawThickLine(poseStack, vertexConsumer, start, end, 0.2f, 0.6f, 1.0f, 1.0f, 0.02);

                    // Tu zostaje 'end', żeby zaznaczyć wejściową skrzynkę
                    drawBoxOutline(poseStack, vertexConsumer, end, 0.2f, 0.6f, 1.0f, 1.0f);
                }
                if (furnace.outputPos != null) {
                    Vec3 end = Vec3.atCenterOf(furnace.outputPos).subtract(originOffset);
                    drawThickLine(poseStack, vertexConsumer, start, end, 1.0f, 0.6f, 0.1f, 1.0f, 0.02);

                    // Tu zostaje 'end', żeby zaznaczyć wyjściową skrzynkę
                    drawBoxOutline(poseStack, vertexConsumer, end, 1.0f, 0.6f, 0.1f, 1.0f);
                }
            }
        }
    }
    protected abstract void renderMachine(T blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay);

    private void drawLine(PoseStack poseStack, VertexConsumer consumer, Vec3 start, Vec3 end, float r, float g, float b, float a) {
        Matrix4f matrix = poseStack.last().pose();
        Vec3 normal = end.subtract(start).normalize();

        consumer.addVertex(matrix, (float)start.x, (float)start.y, (float)start.z)
                .setColor(r, g, b, a)
                .setNormal(poseStack.last(), (float)normal.x, (float)normal.y, (float)normal.z);

        consumer.addVertex(matrix, (float)end.x, (float)end.y, (float)end.z)
                .setColor(r, g, b, a)
                .setNormal(poseStack.last(), (float)normal.x, (float)normal.y, (float)normal.z);
    }

    private void drawThickLine(PoseStack poseStack, VertexConsumer consumer, Vec3 start, Vec3 end, float r, float g, float b, float a, double thickness) {
        drawLine(poseStack, consumer, start, end, r, g, b, a);

        drawLine(poseStack, consumer, start.add(thickness, 0, 0), end.add(thickness, 0, 0), r, g, b, a);
        drawLine(poseStack, consumer, start.add(-thickness, 0, 0), end.add(-thickness, 0, 0), r, g, b, a);
        drawLine(poseStack, consumer, start.add(0, thickness, 0), end.add(0, thickness, 0), r, g, b, a);
        drawLine(poseStack, consumer, start.add(0, -thickness, 0), end.add(0, -thickness, 0), r, g, b, a);
        drawLine(poseStack, consumer, start.add(0, 0, thickness), end.add(0, 0, thickness), r, g, b, a);
        drawLine(poseStack, consumer, start.add(0, 0, -thickness), end.add(0, 0, -thickness), r, g, b, a);
    }

    private void drawBoxOutline(PoseStack poseStack, VertexConsumer consumer, Vec3 center, float r, float g, float b, float a) {
        Vec3[] c = new Vec3[]{
                new Vec3(center.x - 0.5, center.y - 0.5, center.z - 0.5),
                new Vec3(center.x + 0.5, center.y - 0.5, center.z - 0.5),
                new Vec3(center.x + 0.5, center.y - 0.5, center.z + 0.5),
                new Vec3(center.x - 0.5, center.y - 0.5, center.z + 0.5),
                new Vec3(center.x - 0.5, center.y + 0.5, center.z - 0.5),
                new Vec3(center.x + 0.5, center.y + 0.5, center.z - 0.5),
                new Vec3(center.x + 0.5, center.y + 0.5, center.z + 0.5),
                new Vec3(center.x - 0.5, center.y + 0.5, center.z + 0.5)
        };

        drawLine(poseStack, consumer, c[0], c[1], r, g, b, a);
        drawLine(poseStack, consumer, c[1], c[2], r, g, b, a);
        drawLine(poseStack, consumer, c[2], c[3], r, g, b, a);
        drawLine(poseStack, consumer, c[3], c[0], r, g, b, a);

        drawLine(poseStack, consumer, c[4], c[5], r, g, b, a);
        drawLine(poseStack, consumer, c[5], c[6], r, g, b, a);
        drawLine(poseStack, consumer, c[6], c[7], r, g, b, a);
        drawLine(poseStack, consumer, c[7], c[4], r, g, b, a);

        drawLine(poseStack, consumer, c[0], c[4], r, g, b, a);
        drawLine(poseStack, consumer, c[1], c[5], r, g, b, a);
        drawLine(poseStack, consumer, c[2], c[6], r, g, b, a);
        drawLine(poseStack, consumer, c[3], c[7], r, g, b, a);
    }
}