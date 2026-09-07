package com.shadow.shadowthings.client.model; // Make sure this matches your folder!

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

// 1. Extend Model instead of EntityModel!
public class SoulOrbModel extends Model {

	// Make sure your modid is correct here!
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			ResourceLocation.fromNamespaceAndPath("shadowthings", "soulorb_converted"), "main");

	private final ModelPart ball;

	public SoulOrbModel(ModelPart root) {
		// 2. 1.21 Models require a RenderType passed to the super class!
		super(RenderType::entityTranslucent);
		this.ball = root.getChild("ball");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		// --- YOUR BLOCKBENCH MATH (UNCHANGED) ---
		PartDefinition ball = partdefinition.addOrReplaceChild("ball", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -2.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(6, 28).addBox(-2.0F, -2.0F, 5.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(24, 6).addBox(-6.0F, -2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(12, 28).addBox(-2.0F, -2.0F, -4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 28).addBox(3.0F, -2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 12).addBox(2.0F, -3.0F, -1.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 20).addBox(-5.0F, -3.0F, -1.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(24, 0).addBox(-2.0F, -6.0F, 0.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(24, 10).addBox(-2.0F, -5.0F, -2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(18, 28).addBox(-2.0F, -4.0F, -3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(24, 28).addBox(-2.0F, -5.0F, 3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(30, 6).addBox(-2.0F, -4.0F, 4.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(30, 8).addBox(-2.0F, 2.0F, -2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(30, 10).addBox(-2.0F, 1.0F, -3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(30, 12).addBox(-2.0F, 2.0F, 3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(30, 14).addBox(-2.0F, 1.0F, 4.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(24, 3).addBox(-2.0F, 3.0F, 0.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 17.0F, -1.0F));

		PartDefinition cube_r1 = ball.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(32, 4).addBox(-2.0F, -2.0F, 1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 1.0F, 2.0F, 0.0F, -1.5708F, -1.5708F));
		PartDefinition cube_r2 = ball.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(32, 2).addBox(-2.0F, -2.0F, 1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 0.0F, 2.0F, 0.0F, -1.5708F, -1.5708F));
		PartDefinition cube_r3 = ball.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(32, 0).addBox(-2.0F, -2.0F, 1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -6.0F, 2.0F, 0.0F, -1.5708F, -1.5708F));
		PartDefinition cube_r4 = ball.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, -2.0F, 1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -5.0F, 2.0F, 0.0F, -1.5708F, -1.5708F));
		PartDefinition cube_r5 = ball.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(12, 31).addBox(-2.0F, -2.0F, 1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 1.0F, 2.0F, 0.0F, -1.5708F, -1.5708F));
		PartDefinition cube_r6 = ball.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(6, 31).addBox(-2.0F, -2.0F, 1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 0.0F, 2.0F, 0.0F, -1.5708F, -1.5708F));
		PartDefinition cube_r7 = ball.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(30, 30).addBox(-2.0F, -2.0F, 1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -6.0F, 2.0F, 0.0F, -1.5708F, -1.5708F));
		PartDefinition cube_r8 = ball.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(30, 28).addBox(-2.0F, -2.0F, 1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -5.0F, 2.0F, 0.0F, -1.5708F, -1.5708F));
		PartDefinition cube_r9 = ball.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(30, 26).addBox(-2.0F, -2.0F, 1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(30, 20).addBox(-2.0F, -2.0F, -6.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -2.0F, 3.0F, 0.0F, 0.0F, -1.5708F));
		PartDefinition cube_r10 = ball.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(30, 24).addBox(-2.0F, -2.0F, 1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(30, 18).addBox(-2.0F, -2.0F, -4.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -2.0F, 2.0F, 0.0F, 0.0F, -1.5708F));
		PartDefinition cube_r11 = ball.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(24, 30).addBox(-2.0F, -2.0F, 1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(18, 30).addBox(-2.0F, -2.0F, -6.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -2.0F, 3.0F, 0.0F, 0.0F, -1.5708F));
		PartDefinition cube_r12 = ball.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(30, 22).addBox(-2.0F, -2.0F, 1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(30, 16).addBox(-2.0F, -2.0F, -4.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -2.0F, 2.0F, 0.0F, 0.0F, -1.5708F));
		PartDefinition cube_r13 = ball.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(20, 20).addBox(-2.0F, -3.0F, -1.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 4.0F, 2.0F, 1.5708F, 0.0F, 1.5708F));
		PartDefinition cube_r14 = ball.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(20, 12).addBox(-2.0F, -3.0F, -1.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -3.0F, 2.0F, 1.5708F, 0.0F, 1.5708F));
		PartDefinition cube_r15 = ball.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(10, 20).addBox(-2.0F, -3.0F, -1.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.0F, 3.0F, 0.0F, 1.5708F, 0.0F));
		PartDefinition cube_r16 = ball.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(10, 12).addBox(-2.0F, -3.0F, -1.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.0F, -4.0F, 0.0F, 1.5708F, 0.0F));
		// ------------------------------------------

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	// 3. The 1.21 render method with the unified ARGB "color" integer!
	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		ball.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}
}