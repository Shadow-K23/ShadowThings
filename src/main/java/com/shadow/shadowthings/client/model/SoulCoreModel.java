package com.shadow.shadowthings.client.model;// Made with Blockbench 5.1.6
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.shadow.shadowthings.ShadowThings;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class SoulCoreModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			ResourceLocation.fromNamespaceAndPath("shadowthings", "soul_core"), "main");
	public final ModelPart base;
	private final ModelPart center;
	private final ModelPart sides;
	private final ModelPart corners;
	private final ModelPart pillars;
	private final ModelPart pillar;
	private final ModelPart pillar2;
	private final ModelPart pillar3;
	private final ModelPart pillar4;
	public final ModelPart crystal;

	public SoulCoreModel(ModelPart root) {
		this.base = root.getChild("base");
		this.center = this.base.getChild("center");
		this.sides = this.base.getChild("sides");
		this.corners = this.base.getChild("corners");
		this.pillars = this.corners.getChild("pillars");
		this.pillar = this.pillars.getChild("pillar");
		this.pillar2 = this.pillars.getChild("pillar2");
		this.pillar3 = this.pillars.getChild("pillar3");
		this.pillar4 = this.pillars.getChild("pillar4");
		this.crystal = root.getChild("crystal");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition base = partdefinition.addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition center = base.addOrReplaceChild("center", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -6.0F, -8.0F, 16.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(128, 58).addBox(-3.0F, -8.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(128, 0).addBox(4.0F, -8.0F, -5.0F, 1.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(172, 40).addBox(3.0F, -8.0F, -5.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(142, 136).addBox(3.0F, -7.0F, -4.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(190, 23).addBox(3.0F, -8.0F, 4.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(90, 160).addBox(-4.0F, -8.0F, -5.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(144, 24).addBox(-4.0F, -7.0F, -4.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(190, 120).addBox(-4.0F, -8.0F, 4.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(128, 12).addBox(-5.0F, -8.0F, -5.0F, 1.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(56, 110).addBox(-6.0F, -9.0F, -6.0F, 1.0F, 3.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(114, 88).addBox(5.0F, -9.0F, -6.0F, 1.0F, 3.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(138, 117).addBox(-1.0F, -9.0F, -3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(156, 74).addBox(-2.0F, -9.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(142, 80).addBox(-1.0F, -9.0F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(78, 125).addBox(7.0F, -13.0F, 6.0F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(168, 61).addBox(6.0F, -11.0F, 6.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(110, 189).addBox(7.0F, -10.0F, 7.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(188, 56).addBox(6.0F, -12.0F, 7.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(82, 110).addBox(-7.0F, -11.0F, 6.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(106, 189).addBox(-8.0F, -10.0F, 6.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(50, 188).addBox(-8.0F, -12.0F, 7.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(142, 187).addBox(-7.0F, -13.0F, 7.0F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(88, 189).addBox(7.0F, -10.0F, -8.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(124, 168).addBox(7.0F, -11.0F, -7.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(188, 63).addBox(6.0F, -12.0F, -8.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(178, 187).addBox(6.0F, -13.0F, -7.0F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(76, 187).addBox(-7.0F, -13.0F, -8.0F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(188, 49).addBox(-8.0F, -12.0F, -8.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(92, 189).addBox(-7.0F, -10.0F, -7.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(136, 82).addBox(-8.0F, -11.0F, -7.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(86, 88).addBox(6.0F, -8.0F, -6.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(86, 102).addBox(-8.0F, -8.0F, -6.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(144, 55).addBox(2.0F, -9.0F, 6.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(188, 77).addBox(-4.0F, -9.0F, -8.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(188, 37).addBox(2.0F, -9.0F, -8.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(188, 34).addBox(-4.0F, -9.0F, 6.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r1 = center.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(188, 83).addBox(2.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(122, 188).addBox(-4.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, -8.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r2 = center.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(80, 188).addBox(2.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(188, 80).addBox(-4.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, -8.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r3 = center.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(28, 110).addBox(-1.0F, -2.0F, -6.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, 7.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r4 = center.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 110).addBox(-1.0F, -2.0F, -6.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, -7.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r5 = center.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(46, 125).addBox(0.0F, -3.0F, -4.0F, 1.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -6.0F, 6.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r6 = center.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(24, 124).addBox(0.0F, -3.0F, -4.0F, 1.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -6.0F, -5.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r7 = center.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(164, 162).addBox(-1.0F, -1.0F, -2.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(160, 135).addBox(0.0F, -2.0F, -2.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -6.0F, -4.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r8 = center.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(162, 23).addBox(-1.0F, -2.0F, -2.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(164, 102).addBox(0.0F, -1.0F, -2.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -6.0F, 4.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition sides = base.addOrReplaceChild("sides", CubeListBuilder.create().texOffs(0, 22).addBox(-24.0F, -3.0F, -8.0F, 16.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 44).addBox(-8.0F, -3.0F, -24.0F, 16.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(64, 0).addBox(8.0F, -3.0F, -8.0F, 16.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(64, 22).addBox(-8.0F, -3.0F, 8.0F, 16.0F, 6.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.0F));

		PartDefinition corners = base.addOrReplaceChild("corners", CubeListBuilder.create().texOffs(64, 44).addBox(-24.0F, -3.0F, 8.0F, 16.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 66).addBox(-24.0F, -3.0F, -24.0F, 16.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(64, 66).addBox(8.0F, -3.0F, -24.0F, 16.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 88).addBox(8.0F, -3.0F, 8.0F, 16.0F, 6.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.0F));

		PartDefinition pillars = corners.addOrReplaceChild("pillars", CubeListBuilder.create(), PartPose.offset(-11.0F, -25.0F, 11.0F));

		PartDefinition pillar = pillars.addOrReplaceChild("pillar", CubeListBuilder.create().texOffs(156, 67).addBox(-7.0F, -5.0F, 3.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(72, 184).addBox(-7.0F, 12.0F, 6.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(138, 103).addBox(-7.0F, 14.0F, 7.0F, 1.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(172, 61).addBox(-7.0F, 4.0F, 5.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(144, 33).addBox(-10.0F, 6.0F, 7.0F, 5.0F, 8.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(14, 141).addBox(-10.0F, 6.0F, 5.0F, 0.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(130, 136).addBox(-11.0F, 2.0F, 5.0F, 1.0F, 12.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(56, 138).addBox(-12.0F, 4.0F, 5.0F, 1.0F, 10.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(152, 55).addBox(-11.0F, 5.0F, 10.0F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(30, 185).addBox(-12.0F, 6.0F, 10.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 177).addBox(-12.0F, 6.0F, 11.0F, 2.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(128, 82).addBox(-5.0F, -2.0F, 3.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(178, 112).addBox(-7.0F, -2.0F, 3.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(166, 79).addBox(-7.0F, -2.0F, 5.0F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(168, 119).addBox(-7.0F, -2.0F, 7.0F, 4.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(90, 163).addBox(-7.0F, -2.0F, 8.0F, 4.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(144, 132).addBox(-7.0F, -4.0F, 7.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(96, 149).addBox(-8.0F, -4.0F, 3.0F, 1.0F, 10.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(154, 80).addBox(-10.0F, -2.0F, 3.0F, 2.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(166, 11).addBox(-10.0F, 6.0F, 5.0F, 3.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(114, 103).addBox(-12.0F, 12.0F, 6.0F, 5.0F, 10.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(128, 66).addBox(-13.0F, 12.0F, 6.0F, 1.0F, 10.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(156, 184).addBox(-13.0F, 14.0F, 12.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(174, 128).addBox(-9.0F, -1.0F, 7.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(4, 186).addBox(-9.0F, 0.0F, 9.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(174, 137).addBox(-10.0F, 0.0F, 7.0F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(152, 96).addBox(-10.0F, 2.0F, 10.0F, 5.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(156, 117).addBox(-10.0F, 4.0F, 11.0F, 5.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(166, 87).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(182, 128).addBox(-1.0F, -7.0F, 1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(122, 182).addBox(-3.0F, -7.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(180, 56).addBox(-3.0F, -7.0F, 1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(140, 96).addBox(-3.0F, -6.0F, 3.0F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(178, 96).addBox(-3.0F, -6.0F, 5.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(172, 71).addBox(-5.0F, -6.0F, 0.0F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(178, 104).addBox(-7.0F, -6.0F, 1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition pillar2 = pillars.addOrReplaceChild("pillar2", CubeListBuilder.create().texOffs(156, 128).addBox(-7.0F, -5.0F, 3.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(186, 18).addBox(-7.0F, 12.0F, 6.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(140, 82).addBox(-7.0F, 14.0F, 7.0F, 1.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(172, 169).addBox(-7.0F, 4.0F, 5.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(144, 44).addBox(-10.0F, 6.0F, 7.0F, 5.0F, 8.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(142, 156).addBox(-10.0F, 6.0F, 5.0F, 0.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(24, 137).addBox(-11.0F, 2.0F, 5.0F, 1.0F, 12.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(144, 117).addBox(-12.0F, 4.0F, 5.0F, 1.0F, 10.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(146, 179).addBox(-11.0F, 5.0F, 10.0F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(186, 94).addBox(-12.0F, 6.0F, 10.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(50, 179).addBox(-12.0F, 6.0F, 11.0F, 2.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(182, 134).addBox(-5.0F, -2.0F, 3.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(114, 178).addBox(-7.0F, -2.0F, 3.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(128, 166).addBox(-7.0F, -2.0F, 5.0F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(34, 170).addBox(-7.0F, -2.0F, 7.0F, 4.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(68, 164).addBox(-7.0F, -2.0F, 8.0F, 4.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(154, 92).addBox(-7.0F, -4.0F, 7.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(26, 154).addBox(-8.0F, -4.0F, 3.0F, 1.0F, 10.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(106, 154).addBox(-10.0F, -2.0F, 3.0F, 2.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 167).addBox(-10.0F, 6.0F, 5.0F, 3.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(82, 116).addBox(-12.0F, 12.0F, 6.0F, 5.0F, 10.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(130, 120).addBox(-13.0F, 12.0F, 6.0F, 1.0F, 10.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(96, 186).addBox(-13.0F, 14.0F, 12.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(10, 175).addBox(-9.0F, -1.0F, 7.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(186, 103).addBox(-9.0F, 0.0F, 9.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(176, 8).addBox(-10.0F, 0.0F, 7.0F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(56, 153).addBox(-10.0F, 2.0F, 10.0F, 5.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(152, 156).addBox(-10.0F, 4.0F, 11.0F, 5.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(186, 110).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(182, 140).addBox(-1.0F, -7.0F, 1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(56, 183).addBox(-3.0F, -7.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(180, 63).addBox(-3.0F, -7.0F, 1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(176, 17).addBox(-3.0F, -6.0F, 3.0F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(178, 120).addBox(-3.0F, -6.0F, 5.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(80, 173).addBox(-5.0F, -6.0F, 0.0F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(178, 160).addBox(-7.0F, -6.0F, 1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(22.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition pillar3 = pillars.addOrReplaceChild("pillar3", CubeListBuilder.create().texOffs(158, 145).addBox(-7.0F, -5.0F, 3.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(114, 186).addBox(-7.0F, 12.0F, 6.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 141).addBox(-7.0F, 14.0F, 7.0F, 1.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(90, 173).addBox(-7.0F, 4.0F, 5.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(142, 145).addBox(-10.0F, 6.0F, 7.0F, 5.0F, 8.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(46, 157).addBox(-10.0F, 6.0F, 5.0F, 0.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(106, 137).addBox(-11.0F, 2.0F, 5.0F, 1.0F, 12.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(68, 149).addBox(-12.0F, 4.0F, 5.0F, 1.0F, 10.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(102, 180).addBox(-11.0F, 5.0F, 10.0F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(186, 115).addBox(-12.0F, 6.0F, 10.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(180, 168).addBox(-12.0F, 6.0F, 11.0F, 2.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(88, 183).addBox(-5.0F, -2.0F, 3.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(34, 179).addBox(-7.0F, -2.0F, 3.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(10, 167).addBox(-7.0F, -2.0F, 5.0F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(44, 170).addBox(-7.0F, -2.0F, 7.0F, 4.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(164, 92).addBox(-7.0F, -2.0F, 8.0F, 4.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(184, 46).addBox(-7.0F, -4.0F, 7.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(118, 154).addBox(-8.0F, -4.0F, 3.0F, 1.0F, 10.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 155).addBox(-10.0F, -2.0F, 3.0F, 2.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(152, 167).addBox(-10.0F, 6.0F, 5.0F, 3.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(106, 120).addBox(-12.0F, 12.0F, 6.0F, 5.0F, 10.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(78, 133).addBox(-13.0F, 12.0F, 6.0F, 1.0F, 10.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(118, 186).addBox(-13.0F, 14.0F, 12.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(18, 176).addBox(-9.0F, -1.0F, 7.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(186, 146).addBox(-9.0F, 0.0F, 9.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(26, 176).addBox(-10.0F, 0.0F, 7.0F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(130, 153).addBox(-10.0F, 2.0F, 10.0F, 5.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(160, 33).addBox(-10.0F, 4.0F, 11.0F, 5.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(186, 153).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(106, 183).addBox(-1.0F, -7.0F, 1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(184, 0).addBox(-3.0F, -7.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(80, 181).addBox(-3.0F, -7.0F, 1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(176, 24).addBox(-3.0F, -6.0F, 3.0F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(42, 179).addBox(-3.0F, -6.0F, 5.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(174, 0).addBox(-5.0F, -6.0F, 0.0F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(138, 179).addBox(-7.0F, -6.0F, 1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(22.0F, 0.0F, -22.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition pillar4 = pillars.addOrReplaceChild("pillar4", CubeListBuilder.create().texOffs(160, 44).addBox(-7.0F, -5.0F, 3.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(186, 158).addBox(-7.0F, 12.0F, 6.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(142, 66).addBox(-7.0F, 14.0F, 7.0F, 1.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(68, 174).addBox(-7.0F, 4.0F, 5.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(80, 149).addBox(-10.0F, 6.0F, 7.0F, 5.0F, 8.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(80, 160).addBox(-10.0F, 6.0F, 5.0F, 0.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(118, 137).addBox(-11.0F, 2.0F, 5.0F, 1.0F, 12.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(150, 0).addBox(-12.0F, 4.0F, 5.0F, 1.0F, 10.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(130, 182).addBox(-11.0F, 5.0F, 10.0F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(186, 167).addBox(-12.0F, 6.0F, 10.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(182, 31).addBox(-12.0F, 6.0F, 11.0F, 2.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(184, 6).addBox(-5.0F, -2.0F, 3.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(160, 179).addBox(-7.0F, -2.0F, 3.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(22, 168).addBox(-7.0F, -2.0F, 5.0F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(172, 31).addBox(-7.0F, -2.0F, 7.0F, 4.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(164, 152).addBox(-7.0F, -2.0F, 8.0F, 4.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(186, 27).addBox(-7.0F, -4.0F, 7.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(36, 156).addBox(-8.0F, -4.0F, 3.0F, 1.0F, 10.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(156, 55).addBox(-10.0F, -2.0F, 3.0F, 2.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(168, 51).addBox(-10.0F, 6.0F, 5.0F, 3.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 124).addBox(-12.0F, 12.0F, 6.0F, 5.0F, 10.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(92, 133).addBox(-13.0F, 12.0F, 6.0F, 1.0F, 10.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(34, 187).addBox(-13.0F, 14.0F, 12.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(176, 40).addBox(-9.0F, -1.0F, 7.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(38, 187).addBox(-9.0F, 0.0F, 9.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(176, 87).addBox(-10.0F, 0.0F, 7.0F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(14, 154).addBox(-10.0F, 2.0F, 10.0F, 5.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(162, 0).addBox(-10.0F, 4.0F, 11.0F, 5.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(134, 187).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(10, 184).addBox(-1.0F, -7.0F, 1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(184, 40).addBox(-3.0F, -7.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(182, 70).addBox(-3.0F, -7.0F, 1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(176, 146).addBox(-3.0F, -6.0F, 3.0F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(168, 179).addBox(-3.0F, -6.0F, 5.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(124, 174).addBox(-5.0F, -6.0F, 0.0F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(176, 179).addBox(-7.0F, -6.0F, 1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -22.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition crystal = partdefinition.addOrReplaceChild("crystal", CubeListBuilder.create().texOffs(98, 173).addBox(3.0F, -7.0F, -4.0F, 1.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(56, 166).addBox(2.0F, -9.0F, -4.0F, 1.0F, 16.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(46, 138).addBox(-2.0F, -10.0F, -4.0F, 4.0F, 18.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(110, 166).addBox(-3.0F, -9.0F, -4.0F, 1.0F, 16.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(128, 24).addBox(-3.0F, -9.0F, -3.0F, 7.0F, 16.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(64, 88).addBox(-3.0F, -10.0F, -2.0F, 7.0F, 18.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(128, 41).addBox(-3.0F, -9.0F, 2.0F, 7.0F, 16.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(134, 174).addBox(3.0F, -7.0F, 3.0F, 1.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(102, 163).addBox(2.0F, -9.0F, 3.0F, 1.0F, 16.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(36, 137).addBox(-2.0F, -10.0F, 3.0F, 4.0F, 18.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(64, 166).addBox(-3.0F, -9.0F, 3.0F, 1.0F, 16.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(76, 174).addBox(-4.0F, -7.0F, -4.0F, 1.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(60, 166).addBox(-4.0F, -9.0F, -3.0F, 1.0F, 16.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(68, 125).addBox(-4.0F, -10.0F, -2.0F, 1.0F, 18.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(106, 166).addBox(-4.0F, -9.0F, 2.0F, 1.0F, 16.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 177).addBox(-4.0F, -7.0F, 3.0F, 1.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(182, 189).addBox(2.0F, -11.0F, -3.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(176, 153).addBox(-2.0F, -13.0F, -3.0F, 4.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(160, 51).addBox(-2.0F, -11.0F, -2.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(0, 190).addBox(-3.0F, -11.0F, -3.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(168, 109).addBox(2.0F, -13.0F, -2.0F, 1.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(150, 15).addBox(-2.0F, -11.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(114, 168).addBox(-3.0F, -13.0F, -2.0F, 1.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(186, 189).addBox(2.0F, -11.0F, 2.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(106, 116).addBox(-2.0F, -11.0F, 2.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(150, 177).addBox(-2.0F, -13.0F, 2.0F, 4.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(10, 190).addBox(-3.0F, -11.0F, 2.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(146, 189).addBox(1.0F, -15.0F, -2.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(44, 187).addBox(-1.0F, -17.0F, -2.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(54, 189).addBox(-1.0F, -17.0F, -1.0F, 2.0F, 6.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(190, 18).addBox(-2.0F, -15.0F, -2.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(184, 86).addBox(1.0F, -17.0F, -1.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(184, 177).addBox(-1.0F, -15.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(150, 184).addBox(-2.0F, -17.0F, -1.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(14, 190).addBox(1.0F, -15.0F, 1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(158, 152).addBox(-1.0F, -15.0F, 1.0F, 2.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(160, 187).addBox(-1.0F, -17.0F, 1.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(62, 190).addBox(-2.0F, -15.0F, 1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(64, 184).addBox(-1.0F, -19.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, 0.0F));

		PartDefinition cube_r9 = crystal.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(64, 184).addBox(-1.0F, -16.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(146, 189).addBox(1.0F, -12.0F, -2.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(14, 190).addBox(1.0F, -12.0F, 1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(184, 86).addBox(1.0F, -14.0F, -1.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(190, 18).addBox(-2.0F, -12.0F, -2.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(44, 187).addBox(-1.0F, -14.0F, -2.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(158, 152).addBox(-1.0F, -12.0F, 1.0F, 2.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(62, 190).addBox(-2.0F, -12.0F, 1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(160, 187).addBox(-1.0F, -14.0F, 1.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(150, 184).addBox(-2.0F, -14.0F, -1.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(184, 177).addBox(-1.0F, -12.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(54, 189).addBox(-1.0F, -14.0F, -1.0F, 2.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.0F, -1.5708F, 3.1416F));

		PartDefinition cube_r10 = crystal.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(182, 189).addBox(2.0F, -9.0F, -3.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(168, 109).addBox(2.0F, -11.0F, -2.0F, 1.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(186, 189).addBox(2.0F, -9.0F, 2.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 190).addBox(-3.0F, -9.0F, -3.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(176, 153).addBox(-2.0F, -11.0F, -3.0F, 4.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(106, 116).addBox(-2.0F, -9.0F, 2.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(10, 190).addBox(-3.0F, -9.0F, 2.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(150, 177).addBox(-2.0F, -11.0F, 2.0F, 4.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(150, 15).addBox(-2.0F, -9.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(160, 51).addBox(-2.0F, -9.0F, -2.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(114, 168).addBox(-3.0F, -11.0F, -2.0F, 1.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 3.1416F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		base.render(poseStack, vertexConsumer, packedLight, packedOverlay,color);
		crystal.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}
}