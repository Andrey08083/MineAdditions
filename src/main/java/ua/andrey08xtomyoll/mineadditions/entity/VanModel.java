// Made with Blockbench 3.7.5
// Exported for Minecraft version 1.12
// Paste this class into your mod and generate all required imports
package ua.andrey08xtomyoll.mineadditions.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class VanModel extends ModelBase {
	 final ModelRenderer Body;
	 final ModelRenderer cube_r1;
	 final ModelRenderer cube_r2;
	 final ModelRenderer cube_r3;
	final ModelRenderer cube_r4;
	 final ModelRenderer cube_r5;
	 final ModelRenderer cube_r6;
	 final ModelRenderer cube_r7;
	 final ModelRenderer cube_r8;
	 final ModelRenderer cube_r9;
	 final ModelRenderer cube_r10;
	 final ModelRenderer cube_r11;
	 final ModelRenderer cube_r12;
	 final ModelRenderer cube_r13;
	 final ModelRenderer cube_r14;
	 ModelRenderer cube_r15;
	 final ModelRenderer cube_r16;
	 final ModelRenderer Fronttintakle;
	 final ModelRenderer cube_r17;
	 final ModelRenderer cube_r18;
	 final ModelRenderer cube_r19;
	 final ModelRenderer cube_r20;
	 final ModelRenderer cube_r21;
	 final ModelRenderer cube_r22;
	 final ModelRenderer cube_r23;
	 final ModelRenderer cube_r24;
	 final ModelRenderer cube_r25;
	 final ModelRenderer cube_r26;
	 final ModelRenderer cube_r27;
	 final ModelRenderer cube_r28;
	 final ModelRenderer Boddy;
	 final ModelRenderer bb_main;
	 final ModelRenderer cube_r29;
	 final ModelRenderer cube_r30;
	 final ModelRenderer cube_r31;
	 final ModelRenderer cube_r32;
	 final ModelRenderer cube_r33;
	 final ModelRenderer cube_r34;
	 final ModelRenderer cube_r35;
	 final ModelRenderer cube_r36;
	 final ModelRenderer cube_r37;
	 final ModelRenderer cube_r38;
	 final ModelRenderer cube_r39;
	 final ModelRenderer cube_r40;

	public VanModel() {
		textureWidth = 64;
		textureHeight = 64;

		Body = new ModelRenderer(this);
		Body.setRotationPoint(1.0F, 9.0F, 0.0F);


		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(-9.8F, 8.9F, 0.6F);
		Body.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.0F, 1.5708F, 0.3665F);
		cube_r1.cubeList.add(new ModelBox(cube_r1, 22, 33, -3.0F, -1.0F, 0.0F, 6, 4, 1, 0.0F, false));

		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(7.8F, 8.9F, 0.6F);
		Body.addChild(cube_r2);
		setRotationAngle(cube_r2, -0.1745F, -1.5708F, -0.192F);
		cube_r2.cubeList.add(new ModelBox(cube_r2, 36, 36, -3.0F, -1.0F, 0.0F, 6, 4, 1, 0.0F, false));

		cube_r3 = new ModelRenderer(this);
		cube_r3.setRotationPoint(-8.8F, 5.3F, 0.6F);
		Body.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.0F, 1.5708F, 0.2356F);
		cube_r3.cubeList.add(new ModelBox(cube_r3, 31, 18, -3.0F, -1.0F, 0.0F, 6, 4, 1, 0.0F, false));

		cube_r4 = new ModelRenderer(this);
		cube_r4.setRotationPoint(6.8F, 5.3F, 0.6F);
		Body.addChild(cube_r4);
		setRotationAngle(cube_r4, -0.0436F, -1.5708F, -0.192F);
		cube_r4.cubeList.add(new ModelBox(cube_r4, 36, 23, -3.0F, -1.0F, 0.0F, 6, 4, 1, 0.0F, false));

		cube_r5 = new ModelRenderer(this);
		cube_r5.setRotationPoint(-8.0F, 1.3F, 0.6F);
		Body.addChild(cube_r5);
		setRotationAngle(cube_r5, 0.0F, 1.5708F, 0.192F);
		cube_r5.cubeList.add(new ModelBox(cube_r5, 30, 4, -3.0F, -1.0F, 0.0F, 6, 5, 1, 0.0F, false));

		cube_r6 = new ModelRenderer(this);
		cube_r6.setRotationPoint(6.0F, 1.3F, 0.6F);
		Body.addChild(cube_r6);
		setRotationAngle(cube_r6, 0.0F, -1.5708F, -0.192F);
		cube_r6.cubeList.add(new ModelBox(cube_r6, 0, 32, -3.0F, -1.0F, 0.0F, 6, 5, 1, 0.0F, false));

		cube_r7 = new ModelRenderer(this);
		cube_r7.setRotationPoint(-1.0F, 7.0F, 7.1F);
		Body.addChild(cube_r7);
		setRotationAngle(cube_r7, 0.1309F, 0.0F, 0.0F);
		cube_r7.cubeList.add(new ModelBox(cube_r7, 4, 63, -7.0F, -1.0F, -1.0F, 14, -2, 0, 0.0F, false));

		cube_r8 = new ModelRenderer(this);
		cube_r8.setRotationPoint(-1.0F, 7.0F, -6.2F);
		Body.addChild(cube_r8);
		setRotationAngle(cube_r8, -0.1309F, 0.0F, 0.0F);
		cube_r8.cubeList.add(new ModelBox(cube_r8, 35, 60, -7.0F, -1.0F, 1.0F, 14, -2, 0, 0.0F, false));

		cube_r9 = new ModelRenderer(this);
		cube_r9.setRotationPoint(-1.0F, 6.0F, 6.9F);
		Body.addChild(cube_r9);
		setRotationAngle(cube_r9, 0.1309F, 0.0F, 0.0F);
		cube_r9.cubeList.add(new ModelBox(cube_r9, 35, 60, -7.0F, -1.0F, -1.0F, 14, -2, 0, 0.0F, false));

		cube_r10 = new ModelRenderer(this);
		cube_r10.setRotationPoint(-1.0F, 6.0F, -6.0F);
		Body.addChild(cube_r10);
		setRotationAngle(cube_r10, -0.1309F, 0.0F, 0.0F);
		cube_r10.cubeList.add(new ModelBox(cube_r10, 35, 58, -7.0F, -1.0F, 1.0F, 14, -2, 0, 0.0F, false));

		cube_r11 = new ModelRenderer(this);
		cube_r11.setRotationPoint(-1.0F, 4.0F, 6.6F);
		Body.addChild(cube_r11);
		setRotationAngle(cube_r11, 0.1309F, 0.0F, 0.0F);
		cube_r11.cubeList.add(new ModelBox(cube_r11, 4, 63, -7.0F, -1.0F, -1.0F, 14, -2, 0, 0.0F, false));

		cube_r12 = new ModelRenderer(this);
		cube_r12.setRotationPoint(-1.0F, 4.0F, -5.6F);
		Body.addChild(cube_r12);
		setRotationAngle(cube_r12, -0.1309F, 0.0F, 0.0F);
		cube_r12.cubeList.add(new ModelBox(cube_r12, 35, 56, -7.0F, -1.0F, 1.0F, 14, -2, 0, 0.0F, false));

		cube_r13 = new ModelRenderer(this);
		cube_r13.setRotationPoint(-3.0F, 5.0F, 6.8F);
		Body.addChild(cube_r13);
		setRotationAngle(cube_r13, 0.1309F, 0.0F, 0.0F);
		cube_r13.cubeList.add(new ModelBox(cube_r13, 4, 59, -5.0F, -1.0F, -1.0F, 14, -2, 0, 0.0F, false));

		cube_r14 = new ModelRenderer(this);
		cube_r14.setRotationPoint(1.0F, 5.0F, -5.7F);
		Body.addChild(cube_r14);
		setRotationAngle(cube_r14, -0.1309F, 0.0F, 0.0F);
		cube_r14.cubeList.add(new ModelBox(cube_r14, 35, 54, -9.0F, -1.0F, 1.0F, 14, -2, 0, 0.0F, false));

		cube_r15 = new ModelRenderer(this);
		cube_r15.setRotationPoint(-3.0F, 3.0F, 6.4F);
		Body.addChild(cube_r15);
		setRotationAngle(cube_r15, 0.1309F, 0.0F, 0.0F);
		cube_r15.cubeList.add(new ModelBox(cube_r15, 18, 59, -5.0F, -1.0F, -1.0F, 14, -2, 0, 0.0F, false));

		cube_r16 = new ModelRenderer(this);
		cube_r16.setRotationPoint(1.0F, 3.0F, -5.3F);
		Body.addChild(cube_r16);
		setRotationAngle(cube_r16, -0.1309F, 0.0F, 0.0F);
		cube_r16.cubeList.add(new ModelBox(cube_r16, 35, 52, -9.0F, -1.0F, 1.0F, 14, -2, 0, 0.0F, false));

		Fronttintakle = new ModelRenderer(this);
		Fronttintakle.setRotationPoint(-1.0F, 14.2F, 9.6F);
		Body.addChild(Fronttintakle);


		cube_r17 = new ModelRenderer(this);
		cube_r17.setRotationPoint(-2.0F, -12.9F, -14.3F);
		Fronttintakle.addChild(cube_r17);
		setRotationAngle(cube_r17, 0.0F, 0.2182F, 1.5708F);
		cube_r17.cubeList.add(new ModelBox(cube_r17, 10, 38, -1.0F, -4.0F, 0.0F, 1, 4, 1, 0.0F, false));

		cube_r18 = new ModelRenderer(this);
		cube_r18.setRotationPoint(-2.0F, -8.0F, -15.1F);
		Fronttintakle.addChild(cube_r18);
		setRotationAngle(cube_r18, -0.1484F, 0.0F, 0.0F);
		cube_r18.cubeList.add(new ModelBox(cube_r18, 0, 0, -1.0F, -5.0F, 0.0F, 1, 5, 1, 0.0F, false));
		cube_r18.cubeList.add(new ModelBox(cube_r18, 0, 14, 4.0F, -5.0F, 0.0F, 1, 5, 1, 0.0F, false));

		cube_r19 = new ModelRenderer(this);
		cube_r19.setRotationPoint(0.0F, 0.0F, 0.0F);
		Fronttintakle.addChild(cube_r19);
		setRotationAngle(cube_r19, 0.6109F, 0.0F, 0.0F);
		cube_r19.cubeList.add(new ModelBox(cube_r19, 23, 4, -1.0F, -1.0F, -1.0F, 2, 1, 1, 0.0F, false));

		cube_r20 = new ModelRenderer(this);
		cube_r20.setRotationPoint(0.0F, -1.5F, -1.1F);
		Fronttintakle.addChild(cube_r20);
		setRotationAngle(cube_r20, 0.6109F, 0.0F, 0.0F);
		cube_r20.cubeList.add(new ModelBox(cube_r20, 18, 46, -2.0F, -1.0F, -1.0F, 4, 2, 1, 0.0F, false));

		cube_r21 = new ModelRenderer(this);
		cube_r21.setRotationPoint(-3.0F, -7.2F, -2.9F);
		Fronttintakle.addChild(cube_r21);
		setRotationAngle(cube_r21, 0.2618F, 0.0F, 0.0F);
		cube_r21.cubeList.add(new ModelBox(cube_r21, 27, 48, 0.0F, -1.0F, -1.0F, 1, 7, 1, 0.0F, false));
		cube_r21.cubeList.add(new ModelBox(cube_r21, 46, 28, 5.0F, -1.0F, -1.0F, 1, 7, 1, 0.0F, false));

		cube_r22 = new ModelRenderer(this);
		cube_r22.setRotationPoint(0.0F, -7.4F, -3.0F);
		Fronttintakle.addChild(cube_r22);
		setRotationAngle(cube_r22, 0.2618F, 0.0F, 0.0F);
		cube_r22.cubeList.add(new ModelBox(cube_r22, 0, 38, -2.0F, -1.0F, -1.0F, 4, 7, 1, 0.0F, false));

		cube_r23 = new ModelRenderer(this);
		cube_r23.setRotationPoint(0.0F, -12.9F, -3.9F);
		Fronttintakle.addChild(cube_r23);
		setRotationAngle(cube_r23, 0.1484F, 0.0F, 0.0F);
		cube_r23.cubeList.add(new ModelBox(cube_r23, 22, 26, -3.0F, -1.0F, -1.0F, 6, 6, 1, 0.0F, false));

		cube_r24 = new ModelRenderer(this);
		cube_r24.setRotationPoint(-2.0F, -7.2F, -15.3F);
		Fronttintakle.addChild(cube_r24);
		setRotationAngle(cube_r24, -0.2618F, 0.0F, 0.0F);
		cube_r24.cubeList.add(new ModelBox(cube_r24, 18, 49, -1.0F, -1.0F, 0.0F, 1, 7, 1, 0.0F, false));
		cube_r24.cubeList.add(new ModelBox(cube_r24, 22, 49, 4.0F, -1.0F, 0.0F, 1, 7, 1, 0.0F, false));

		cube_r25 = new ModelRenderer(this);
		cube_r25.setRotationPoint(0.0F, 0.0F, -18.2F);
		Fronttintakle.addChild(cube_r25);
		setRotationAngle(cube_r25, -0.6109F, 0.0F, 0.0F);
		cube_r25.cubeList.add(new ModelBox(cube_r25, 23, 18, -1.0F, -1.0F, 0.0F, 2, 1, 1, 0.0F, false));

		cube_r26 = new ModelRenderer(this);
		cube_r26.setRotationPoint(0.0F, -1.5F, -17.1F);
		Fronttintakle.addChild(cube_r26);
		setRotationAngle(cube_r26, -0.6109F, 0.0F, 0.0F);
		cube_r26.cubeList.add(new ModelBox(cube_r26, 46, 46, -2.0F, -1.0F, 0.0F, 4, 2, 1, 0.0F, false));

		cube_r27 = new ModelRenderer(this);
		cube_r27.setRotationPoint(0.0F, -7.4F, -15.2F);
		Fronttintakle.addChild(cube_r27);
		setRotationAngle(cube_r27, -0.2618F, 0.0F, 0.0F);
		cube_r27.cubeList.add(new ModelBox(cube_r27, 36, 28, -2.0F, -1.0F, 0.0F, 4, 7, 1, 0.0F, false));

		cube_r28 = new ModelRenderer(this);
		cube_r28.setRotationPoint(0.0F, -12.9F, -14.3F);
		Fronttintakle.addChild(cube_r28);
		setRotationAngle(cube_r28, -0.1484F, 0.0F, 0.0F);
		cube_r28.cubeList.add(new ModelBox(cube_r28, 0, 46, -2.0F, 0.0F, 0.0F, 4, 5, 1, 0.0F, false));

		Boddy = new ModelRenderer(this);
		Boddy.setRotationPoint(1.0F, 9.0F, 0.0F);
		Boddy.cubeList.add(new ModelBox(Boddy, 0, 22, -4.0F, -12.0F, 0.0F, 6, 9, 1, 4.0F, false));
		Boddy.cubeList.add(new ModelBox(Boddy, 0, 14, -5.0F, -24.0F, -3.0F, 8, 1, 7, 0.0F, false));

		bb_main = new ModelRenderer(this);
		bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -4.0F, -38.0F, -3.0F, 8, 7, 7, 0.0F, false));

		cube_r29 = new ModelRenderer(this);
		cube_r29.setRotationPoint(0.0F, -33.0F, 4.0F);
		bb_main.addChild(cube_r29);
		setRotationAngle(cube_r29, 0.3491F, 0.0F, 0.0F);
		cube_r29.cubeList.add(new ModelBox(cube_r29, 14, 22, -4.0F, -1.0F, 0.0F, 8, 3, 1, 0.0F, false));

		cube_r30 = new ModelRenderer(this);
		cube_r30.setRotationPoint(0.0F, -35.0F, 4.0F);
		bb_main.addChild(cube_r30);
		setRotationAngle(cube_r30, 0.3491F, 0.0F, 0.0F);
		cube_r30.cubeList.add(new ModelBox(cube_r30, 23, 0, -4.0F, -1.0F, 0.0F, 8, 3, 1, 0.0F, false));

		cube_r31 = new ModelRenderer(this);
		cube_r31.setRotationPoint(0.0F, -37.0F, 4.0F);
		bb_main.addChild(cube_r31);
		setRotationAngle(cube_r31, 0.3491F, 0.0F, 0.0F);
		cube_r31.cubeList.add(new ModelBox(cube_r31, 23, 14, -4.0F, -1.0F, 0.0F, 8, 3, 1, 0.0F, false));

		cube_r32 = new ModelRenderer(this);
		cube_r32.setRotationPoint(-2.9F, -34.8F, 3.0F);
		bb_main.addChild(cube_r32);
		setRotationAngle(cube_r32, 1.5708F, 0.0F, -1.2217F);
		cube_r32.cubeList.add(new ModelBox(cube_r32, 22, 38, -4.2216F, -6.0F, 0.4446F, 3, 7, 1, 0.0F, false));

		cube_r33 = new ModelRenderer(this);
		cube_r33.setRotationPoint(-2.9F, -36.8F, 3.0F);
		bb_main.addChild(cube_r33);
		setRotationAngle(cube_r33, 1.5708F, 0.0F, -1.2217F);
		cube_r33.cubeList.add(new ModelBox(cube_r33, 30, 41, -4.2216F, -6.0F, 0.4446F, 3, 7, 1, 0.0F, false));

		cube_r34 = new ModelRenderer(this);
		cube_r34.setRotationPoint(-2.9F, -38.8F, 3.0F);
		bb_main.addChild(cube_r34);
		setRotationAngle(cube_r34, 1.5708F, 0.0F, -1.2217F);
		cube_r34.cubeList.add(new ModelBox(cube_r34, 38, 41, -4.2216F, -6.0F, 0.4446F, 3, 7, 1, 0.0F, false));

		cube_r35 = new ModelRenderer(this);
		cube_r35.setRotationPoint(5.0F, -35.5F, 3.0F);
		bb_main.addChild(cube_r35);
		setRotationAngle(cube_r35, 1.5708F, 0.0F, -1.9199F);
		cube_r35.cubeList.add(new ModelBox(cube_r35, 41, 10, -4.2216F, -6.0F, 0.4446F, 3, 7, 1, 0.0F, false));

		cube_r36 = new ModelRenderer(this);
		cube_r36.setRotationPoint(5.0F, -37.5F, 3.0F);
		bb_main.addChild(cube_r36);
		setRotationAngle(cube_r36, 1.5708F, 0.0F, -1.9199F);
		cube_r36.cubeList.add(new ModelBox(cube_r36, 10, 44, -4.2216F, -6.0F, 0.4446F, 3, 7, 1, 0.0F, false));

		cube_r37 = new ModelRenderer(this);
		cube_r37.setRotationPoint(5.0F, -39.5F, 3.0F);
		bb_main.addChild(cube_r37);
		setRotationAngle(cube_r37, 1.5708F, 0.0F, -1.9199F);
		cube_r37.cubeList.add(new ModelBox(cube_r37, 44, 0, -4.2216F, -6.0F, 0.4446F, 3, 7, 1, 0.0F, false));

		cube_r38 = new ModelRenderer(this);
		cube_r38.setRotationPoint(-10.0F, -29.0F, 0.0F);
		bb_main.addChild(cube_r38);
		setRotationAngle(cube_r38, 0.2182F, 0.0F, 0.0F);
		cube_r38.cubeList.add(new ModelBox(cube_r38, 30, 10, -1.0F, -1.0F, -1.0F, 2, 1, 3, 2.0F, false));

		cube_r39 = new ModelRenderer(this);
		cube_r39.setRotationPoint(-8.5F, -21.1F, -0.1F);
		bb_main.addChild(cube_r39);
		setRotationAngle(cube_r39, -0.7941F, 0.0F, 0.0F);
		cube_r39.cubeList.add(new ModelBox(cube_r39, 14, 26, -2.2F, -6.0F, -5.0F, 1, 17, 1, 2.0F, false));
		cube_r39.cubeList.add(new ModelBox(cube_r39, 18, 26, 18.3F, -6.0F, -5.0F, 1, 17, 1, 2.0F, false));

		cube_r40 = new ModelRenderer(this);
		cube_r40.setRotationPoint(10.0F, -29.0F, 0.0F);
		bb_main.addChild(cube_r40);
		setRotationAngle(cube_r40, 0.3927F, 0.0F, 0.0F);
		cube_r40.cubeList.add(new ModelBox(cube_r40, 45, 18, -1.0F, -1.0F, -1.0F, 2, 1, 3, 2.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		Body.render(f5);
		Boddy.render(f5);
		bb_main.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}