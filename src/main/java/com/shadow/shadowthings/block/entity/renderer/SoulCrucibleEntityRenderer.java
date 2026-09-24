package com.shadow.shadowthings.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.shadow.shadowthings.block.entity.SoulCondenserEntity;
import com.shadow.shadowthings.block.entity.SoulCrucibleEntity;
import com.shadow.shadowthings.block.entity.base.AbstractSoulEntityRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class SoulCrucibleEntityRenderer extends AbstractSoulEntityRenderer<SoulCrucibleEntity> {

    public SoulCrucibleEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }


    @Override
    public void renderMachine(SoulCrucibleEntity soulCrucibleEntity, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1) {

    }
}
