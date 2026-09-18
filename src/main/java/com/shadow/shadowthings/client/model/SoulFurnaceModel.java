package com.shadow.shadowthings.client.model;// Made with Blockbench 5.1.6
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.shadow.shadowthings.block.entity.SoulFurnaceEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class SoulFurnaceModel<T extends Entity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			ResourceLocation.fromNamespaceAndPath("shadowthings", "soul_furnace"), "main");
	public final ModelPart base;
	private final ModelPart plate;
	private final ModelPart pillars;
	private final ModelPart pillars2;
	public final ModelPart ball;

	public SoulFurnaceModel(ModelPart root) {
		this.base = root.getChild("base");
		this.plate = this.base.getChild("plate");
		this.pillars = this.base.getChild("pillars");
		this.pillars2 = this.pillars.getChild("pillars2");
		this.ball = root.getChild("ball");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition base = partdefinition.addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition plate = base.addOrReplaceChild("plate", CubeListBuilder.create().texOffs(0, 0).addBox(-24.0F, -8.0F, -24.0F, 48.0F, 8.0F, 48.0F, new CubeDeformation(0.0F))
		.texOffs(0, 77).addBox(-6.0F, -10.0F, -9.0F, 12.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(76, 56).addBox(6.0F, -10.0F, -6.0F, 3.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(0, 56).addBox(-5.0F, -9.0F, -5.0F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(76, 70).addBox(-9.0F, -10.0F, -6.0F, 3.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(0, 83).addBox(-6.0F, -10.0F, 5.0F, 12.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(54, 138).addBox(-8.0F, -12.0F, 6.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(106, 80).addBox(-6.0F, -11.0F, 6.0F, 12.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(108, 100).addBox(-6.0F, -11.0F, -8.0F, 12.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(138, 131).addBox(6.0F, -12.0F, 6.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(138, 137).addBox(-8.0F, -12.0F, -8.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(82, 140).addBox(6.0F, -12.0F, -8.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(106, 56).addBox(-6.0F, -10.0F, -5.0F, 1.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(106, 68).addBox(5.0F, -10.0F, -5.0F, 1.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r1 = plate.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(20, 110).mirror().addBox(-7.0F, -1.0F, -1.0F, 12.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-7.0F, -10.0F, -1.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r2 = plate.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(108, 103).mirror().addBox(-7.0F, -1.0F, -1.0F, 12.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(7.0F, -10.0F, -1.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r3 = plate.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(20, 110).addBox(-5.0F, -1.0F, -1.0F, 12.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, -10.0F, -1.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r4 = plate.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(108, 103).addBox(-5.0F, -1.0F, -1.0F, 12.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, -10.0F, -1.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition pillars = base.addOrReplaceChild("pillars", CubeListBuilder.create().texOffs(40, 56).addBox(-22.0F, -9.0F, 13.0F, 9.0F, 1.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(72, 84).addBox(-22.0F, -11.0F, 15.0F, 7.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(100, 84).addBox(-22.0F, -13.0F, 16.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(88, 93).addBox(-22.0F, -23.0F, 17.0F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(68, 102).addBox(-22.0F, -24.0F, 17.0F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(84, 116).addBox(-21.0F, -26.0F, 16.0F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(40, 66).addBox(13.0F, -9.0F, 13.0F, 9.0F, 1.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(0, 113).addBox(-20.0F, -34.0F, 16.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(124, 83).addBox(-19.0F, -36.0F, 15.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(16, 113).addBox(16.0F, -34.0F, 16.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(132, 95).addBox(-18.0F, -38.0F, 15.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(100, 129).addBox(-16.0F, -43.0F, 14.0F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(136, 100).addBox(-17.0F, -43.0F, 14.0F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(134, 78).addBox(15.0F, -38.0F, 15.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(136, 61).addBox(-16.0F, -44.0F, 13.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(60, 86).addBox(-15.0F, -47.0F, 12.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(136, 65).addBox(13.0F, -44.0F, 13.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(68, 108).addBox(16.0F, -13.0F, 16.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(28, 95).addBox(17.0F, -23.0F, 17.0F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(68, 128).addBox(15.0F, -36.0F, 15.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(32, 86).addBox(15.0F, -11.0F, 15.0F, 7.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(104, 116).addBox(16.0F, -26.0F, 16.0F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(84, 123).addBox(17.0F, -24.0F, 17.0F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(110, 129).addBox(14.0F, -43.0F, 14.0F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(48, 95).addBox(12.0F, -47.0F, 12.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(94, 137).addBox(16.0F, -43.0F, 14.0F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(20, 98).addBox(-20.0F, -24.0F, 16.0F, 2.0F, 11.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 125).addBox(-21.0F, -23.0F, 22.0F, 3.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 125).addBox(18.0F, -23.0F, 22.0F, 3.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 141).addBox(-21.0F, -31.0F, 17.0F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(124, 141).addBox(-16.0F, -34.0F, 16.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(42, 141).addBox(15.0F, -34.0F, 16.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(6, 141).addBox(20.0F, -31.0F, 17.0F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(48, 130).addBox(18.0F, -24.0F, 16.0F, 2.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r5 = pillars.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(106, 145).addBox(-1.0F, -4.0F, 0.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(15.0F, -39.0F, 14.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r6 = pillars.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(60, 144).addBox(-1.0F, -4.0F, 0.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.0F, -38.0F, 18.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r7 = pillars.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(126, 146).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.0F, -34.0F, 20.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r8 = pillars.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(118, 146).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(126, 129).addBox(-1.0F, 12.0F, 0.0F, 2.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(17.0F, -36.0F, 19.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r9 = pillars.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(64, 130).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.0F, -36.0F, 17.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r10 = pillars.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(28, 89).addBox(-1.0F, -4.0F, 0.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(142, 112).addBox(-1.0F, 4.0F, 0.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.0F, -38.0F, 16.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r11 = pillars.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(32, 77).addBox(-1.0F, -4.0F, 0.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.0F, -39.0F, 15.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r12 = pillars.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(12, 141).addBox(-1.0F, -5.0F, -1.0F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.0F, -26.0F, 21.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r13 = pillars.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(138, 143).addBox(-1.0F, -5.0F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.0F, -33.0F, 15.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r14 = pillars.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(36, 72).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.0F, -34.0F, 18.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r15 = pillars.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(142, 117).addBox(-1.0F, -5.0F, 0.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(15.0F, -33.0F, 16.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r16 = pillars.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(144, 74).addBox(-1.0F, -5.0F, 0.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-15.0F, -33.0F, 16.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r17 = pillars.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(48, 142).addBox(0.0F, -5.0F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-16.0F, -33.0F, 15.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r18 = pillars.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(118, 141).addBox(0.0F, -5.0F, 0.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(72, 76).addBox(0.0F, -13.0F, 0.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-18.0F, -29.0F, 16.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r19 = pillars.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(122, 146).addBox(0.0F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-20.0F, -34.0F, 18.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r20 = pillars.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(86, 146).addBox(0.0F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-18.0F, -34.0F, 20.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r21 = pillars.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(32, 82).addBox(0.0F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(120, 129).addBox(-1.0F, 12.0F, 0.0F, 2.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-17.0F, -36.0F, 19.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r22 = pillars.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(72, 81).addBox(0.0F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-19.0F, -36.0F, 17.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r23 = pillars.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(102, 145).addBox(0.0F, -4.0F, 0.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.0F, -39.0F, 15.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r24 = pillars.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(90, 140).addBox(0.0F, -4.0F, 0.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-15.0F, -39.0F, 14.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r25 = pillars.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(36, 67).addBox(0.0F, -4.0F, 0.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-16.0F, -38.0F, 18.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r26 = pillars.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(132, 139).addBox(0.0F, -5.0F, -1.0F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-18.0F, -26.0F, 21.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r27 = pillars.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(124, 113).addBox(-1.0F, -6.0F, 0.0F, 3.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(23.0F, -17.0F, 19.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r28 = pillars.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(8, 125).addBox(-2.0F, -6.0F, 0.0F, 3.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-23.0F, -17.0F, 19.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition pillars2 = pillars.addOrReplaceChild("pillars2", CubeListBuilder.create().texOffs(0, 67).addBox(-22.0F, -9.0F, 13.0F, 9.0F, 1.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(0, 89).addBox(-22.0F, -11.0F, 15.0F, 7.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(92, 108).addBox(-22.0F, -13.0F, 16.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 98).addBox(-22.0F, -23.0F, 17.0F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(104, 123).addBox(-22.0F, -24.0F, 17.0F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(116, 106).addBox(-21.0F, -26.0F, 16.0F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(36, 76).addBox(13.0F, -9.0F, 13.0F, 9.0F, 1.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(32, 113).addBox(-20.0F, -34.0F, 16.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(128, 72).addBox(-19.0F, -36.0F, 15.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(68, 116).addBox(16.0F, -34.0F, 16.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(82, 135).addBox(-18.0F, -38.0F, 15.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(54, 130).addBox(-16.0F, -43.0F, 14.0F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(102, 137).addBox(-17.0F, -43.0F, 14.0F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(136, 56).addBox(15.0F, -38.0F, 15.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(136, 108).addBox(-16.0F, -44.0F, 13.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(132, 89).addBox(-15.0F, -47.0F, 12.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(138, 127).addBox(13.0F, -44.0F, 13.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(108, 92).addBox(16.0F, -13.0F, 16.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(48, 102).addBox(17.0F, -23.0F, 17.0F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(84, 129).addBox(15.0F, -36.0F, 15.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(60, 93).addBox(15.0F, -11.0F, 15.0F, 7.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(48, 117).addBox(16.0F, -26.0F, 16.0F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(48, 124).addBox(17.0F, -24.0F, 17.0F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(132, 113).addBox(14.0F, -43.0F, 14.0F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(132, 121).addBox(12.0F, -47.0F, 12.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(110, 137).addBox(16.0F, -43.0F, 14.0F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(132, 127).addBox(-20.0F, -24.0F, 16.0F, 2.0F, 11.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(24, 125).addBox(-21.0F, -23.0F, 22.0F, 3.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(128, 56).addBox(18.0F, -23.0F, 22.0F, 3.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(24, 141).addBox(-21.0F, -31.0F, 17.0F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(144, 87).addBox(-16.0F, -34.0F, 16.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(144, 92).addBox(15.0F, -34.0F, 16.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(30, 141).addBox(20.0F, -31.0F, 17.0F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(76, 134).addBox(18.0F, -24.0F, 16.0F, 2.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r29 = pillars2.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(146, 78).addBox(-1.0F, -4.0F, 0.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(15.0F, -39.0F, 14.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r30 = pillars2.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(76, 146).addBox(-1.0F, -4.0F, 0.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.0F, -38.0F, 18.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r31 = pillars2.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(50, 147).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.0F, -34.0F, 20.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r32 = pillars2.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(46, 147).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(70, 134).addBox(-1.0F, 12.0F, 0.0F, 2.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(17.0F, -36.0F, 19.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r33 = pillars2.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(146, 140).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.0F, -36.0F, 17.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r34 = pillars2.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(72, 146).addBox(-1.0F, -4.0F, 0.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(144, 97).addBox(-1.0F, 4.0F, 0.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.0F, -38.0F, 16.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r35 = pillars2.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(68, 146).addBox(-1.0F, -4.0F, 0.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.0F, -39.0F, 15.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r36 = pillars2.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(36, 141).addBox(-1.0F, -5.0F, -1.0F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.0F, -26.0F, 21.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r37 = pillars2.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(144, 102).addBox(-1.0F, -5.0F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.0F, -33.0F, 15.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r38 = pillars2.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(146, 137).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.0F, -34.0F, 18.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r39 = pillars2.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(144, 143).addBox(-1.0F, -5.0F, 0.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(15.0F, -33.0F, 16.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r40 = pillars2.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(144, 121).addBox(-1.0F, -5.0F, 0.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-15.0F, -33.0F, 16.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r41 = pillars2.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(144, 69).addBox(0.0F, -5.0F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-16.0F, -33.0F, 15.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r42 = pillars2.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(54, 144).addBox(0.0F, -5.0F, 0.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(64, 146).addBox(0.0F, -13.0F, 0.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-18.0F, -29.0F, 16.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r43 = pillars2.addOrReplaceChild("cube_r43", CubeListBuilder.create().texOffs(146, 134).addBox(0.0F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-20.0F, -34.0F, 18.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r44 = pillars2.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(134, 146).addBox(0.0F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-18.0F, -34.0F, 20.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r45 = pillars2.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(146, 131).addBox(0.0F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(64, 134).addBox(-1.0F, 12.0F, 0.0F, 2.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-17.0F, -36.0F, 19.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r46 = pillars2.addOrReplaceChild("cube_r46", CubeListBuilder.create().texOffs(130, 146).addBox(0.0F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-19.0F, -36.0F, 17.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r47 = pillars2.addOrReplaceChild("cube_r47", CubeListBuilder.create().texOffs(42, 146).addBox(0.0F, -4.0F, 0.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.0F, -39.0F, 15.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r48 = pillars2.addOrReplaceChild("cube_r48", CubeListBuilder.create().texOffs(114, 145).addBox(0.0F, -4.0F, 0.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-15.0F, -39.0F, 14.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r49 = pillars2.addOrReplaceChild("cube_r49", CubeListBuilder.create().texOffs(110, 145).addBox(0.0F, -4.0F, 0.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-16.0F, -38.0F, 18.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r50 = pillars2.addOrReplaceChild("cube_r50", CubeListBuilder.create().texOffs(18, 141).addBox(0.0F, -5.0F, -1.0F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-18.0F, -26.0F, 21.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r51 = pillars2.addOrReplaceChild("cube_r51", CubeListBuilder.create().texOffs(40, 125).addBox(-1.0F, -6.0F, 0.0F, 3.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(23.0F, -17.0F, 19.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r52 = pillars2.addOrReplaceChild("cube_r52", CubeListBuilder.create().texOffs(32, 125).addBox(-2.0F, -6.0F, 0.0F, 3.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-23.0F, -17.0F, 19.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition ball = partdefinition.addOrReplaceChild("ball", CubeListBuilder.create().texOffs(140, 83).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(116, 113).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(124, 89).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(90, 145).addBox(-2.0F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(96, 145).addBox(1.0F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(136, 69).addBox(-1.0F, -2.0F, 1.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(80, 146).addBox(-1.0F, -2.0F, -2.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(108, 106).addBox(1.0F, -3.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(144, 147).addBox(1.0F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(94, 135).addBox(-2.0F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(140, 87).addBox(-2.0F, -3.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(112, 106).addBox(-0.5F, -3.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(144, 125).addBox(-0.5F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(128, 78).addBox(-0.5F, -3.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 148).addBox(-0.5F, 0.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 148).addBox(1.0F, -1.5F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(8, 148).addBox(-2.0F, -1.5F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 148).addBox(1.0F, -1.5F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 148).addBox(-2.0F, -1.5F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	public void setupAnim(SoulFurnaceEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		base.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		ball.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}

	@Override
	public void setupAnim(T t, float v, float v1, float v2, float v3, float v4) {

	}
}