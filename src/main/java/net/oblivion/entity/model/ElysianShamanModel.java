package net.oblivion.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.oblivion.entity.ElysianShaman;

@Environment(EnvType.CLIENT)
public class ElysianShamanModel<T extends LivingEntity> extends EntityModel<T> {
    private final ModelPart main;
    private final ModelPart body;
    private final ModelPart body_down;
    private final ModelPart arm_right;
    private final ModelPart arm_right_down;
    private final ModelPart spike1;
    private final ModelPart spike2;
    private final ModelPart spike3;
    private final ModelPart arm_left;
    private final ModelPart arm_left_down;
    private final ModelPart spike4;
    private final ModelPart spike5;
    private final ModelPart spike6;
    private final ModelPart head;
    private final ModelPart horns;
    private final ModelPart h1P1;
    private final ModelPart h1P2;
    private final ModelPart h1P3;
    private final ModelPart h1P4;
    private final ModelPart h1P5;
    private final ModelPart h1P6;
    private final ModelPart h1P7;
    private final ModelPart h1P8;
    private final ModelPart leg_right;
    private final ModelPart leg_left;

    public ElysianShamanModel(ModelPart root) {
        this.main = root.getChild("main");
        this.body = main.getChild("body");
        this.body_down = main.getChild("body_down");
        this.arm_right = main.getChild("arm_right");
        this.arm_right_down = arm_right.getChild("arm_right_down");
        this.spike1 = arm_right.getChild("spike1");
        this.spike2 = arm_right.getChild("spike2");
        this.spike3 = arm_right.getChild("spike3");
        this.arm_left = main.getChild("arm_left");
        this.arm_left_down = arm_left.getChild("arm_left_down");
        this.spike4 = arm_left.getChild("spike4");
        this.spike5 = arm_left.getChild("spike5");
        this.spike6 = arm_left.getChild("spike6");
        this.head = main.getChild("head");
        this.horns = head.getChild("horns");
        this.h1P1 = horns.getChild("h1P1");
        this.h1P2 = h1P1.getChild("h1P2");
        this.h1P3 = h1P2.getChild("h1P3");
        this.h1P4 = h1P3.getChild("h1P4");
        this.h1P5 = horns.getChild("h1P5");
        this.h1P6 = h1P5.getChild("h1P6");
        this.h1P7 = h1P6.getChild("h1P7");
        this.h1P8 = h1P7.getChild("h1P8");
        this.leg_right = main.getChild("leg_right");
        this.leg_left = main.getChild("leg_left");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData body = main.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -16.0F, -3.0F, 10.0F, 16.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -20.0F, 1.0F, 0.2182F, 0.0F, 0.0F));

        ModelPartData cube_r1 = body.addChild("cube_r1", ModelPartBuilder.create().uv(0, 22).cuboid(-5.0F, -3.0F, -3.0F, 10.0F, 12.0F, 6.0F, new Dilation(-0.01F)), ModelTransform.of(0.0F, 2.2796F, -0.5782F, -0.2182F, 0.0F, 0.0F));

        ModelPartData body_down = main.addChild("body_down", ModelPartBuilder.create().uv(32, 32).cuboid(-4.0F, -2.0F, -2.0F, 8.0F, 8.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -19.0F, 0.5F));

        ModelPartData arm_right = main.addChild("arm_right", ModelPartBuilder.create().uv(0, 40).mirrored().cuboid(-4.0F, -2.0F, -2.0F, 4.0F, 13.0F, 4.0F, new Dilation(0.01F)).mirrored(false), ModelTransform.of(-5.0F, -33.0F, -4.0F, 0.0F, 0.0F, 0.0F));

        ModelPartData arm_right_down = arm_right.addChild("arm_right_down", ModelPartBuilder.create().uv(0, 57).mirrored().cuboid(-2.0F, 0.0F, -4.0F, 4.0F, 11.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-2.0F, 11.0F, 2.0F, -0.1745F, 0.0F, 0.0F));

        ModelPartData spike1 = arm_right.addChild("spike1", ModelPartBuilder.create().uv(76, 4).cuboid(-2.0F, -1.0F, -1.5F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, -1.0F, 0.0F, 0.0F, -0.2618F, 0.48F));

        ModelPartData cube_r2 = spike1.addChild("cube_r2", ModelPartBuilder.create().uv(84, 4).cuboid(-1.0F, -1.5F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-3.5F, 0.5F, -0.5F, 0.0F, 0.0F, 1.5708F));

        ModelPartData spike2 = arm_right.addChild("spike2", ModelPartBuilder.create().uv(76, 0).cuboid(-2.0F, -1.0F, -1.5F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, 3.0F, 1.0F, -0.0786F, 0.3405F, 0.2483F));

        ModelPartData cube_r3 = spike2.addChild("cube_r3", ModelPartBuilder.create().uv(84, 0).cuboid(-1.0F, -1.5F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-3.5F, 0.5F, -0.5F, 0.0F, 0.0F, 1.5708F));

        ModelPartData spike3 = arm_right.addChild("spike3", ModelPartBuilder.create().uv(76, 4).cuboid(-2.0F, -1.0F, -1.5F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, -1.5F, 1.25F, 0.2957F, 0.3571F, 1.377F));

        ModelPartData cube_r4 = spike3.addChild("cube_r4", ModelPartBuilder.create().uv(84, 4).cuboid(-1.0F, -1.5F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-3.5F, 0.5F, -0.5F, 0.0F, 0.0F, 1.5708F));

        ModelPartData arm_left = main.addChild("arm_left", ModelPartBuilder.create().uv(0, 40).cuboid(0.0F, -2.0F, -2.0F, 4.0F, 13.0F, 4.0F, new Dilation(0.01F)), ModelTransform.of(5.0F, -33.0F, -4.0F, 0.0F, 0.0F, 0.0F));

        ModelPartData arm_left_down = arm_left.addChild("arm_left_down", ModelPartBuilder.create().uv(0, 57).cuboid(-2.0F, 0.0F, -4.0F, 4.0F, 11.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, 11.0F, 2.0F, -0.1745F, 0.0F, 0.0F));

        ModelPartData spike4 = arm_left.addChild("spike4", ModelPartBuilder.create().uv(76, 0).cuboid(-2.0F, -1.0F, -1.5F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, 8.5F, 1.25F, 0.2705F, -0.1188F, 2.4744F));

        ModelPartData cube_r5 = spike4.addChild("cube_r5", ModelPartBuilder.create().uv(84, 0).cuboid(-1.0F, -1.5F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-3.5F, 0.5F, -0.5F, 0.0F, 0.0F, 1.5708F));

        ModelPartData spike5 = arm_left.addChild("spike5", ModelPartBuilder.create().uv(76, 4).cuboid(-2.0F, -1.0F, -1.5F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, 1.5F, 2.0F, -0.1126F, 0.5254F, 2.7835F));

        ModelPartData cube_r6 = spike5.addChild("cube_r6", ModelPartBuilder.create().uv(84, 4).cuboid(-1.0F, -1.5F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-3.5F, 0.5F, -0.5F, 0.0F, 0.0F, 1.5708F));

        ModelPartData spike6 = arm_left.addChild("spike6", ModelPartBuilder.create().uv(76, 4).cuboid(-2.0F, -1.0F, -1.5F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(1.5F, -1.5F, 0.0F, -1.0797F, 0.2705F, 1.9198F));

        ModelPartData cube_r7 = spike6.addChild("cube_r7", ModelPartBuilder.create().uv(84, 4).cuboid(-1.0F, -1.5F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-3.5F, 0.5F, -0.5F, 0.0F, 0.0F, 1.5708F));

        ModelPartData head = main.addChild("head", ModelPartBuilder.create().uv(32, 16).cuboid(-4.0F, -6.0F, -7.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F))
                .uv(32, 0).cuboid(-4.0F, -6.0F, -7.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.25F)), ModelTransform.pivot(0.0F, -35.0F, -3.0F));

        ModelPartData horns = head.addChild("horns", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -2.0F, 0.0F));

        ModelPartData h1P1 = horns.addChild("h1P1", ModelPartBuilder.create().uv(58, 32).cuboid(-3.0F, -1.5F, -1.5F, 6.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(4.0F, -3.5F, -3.5F, 0.0F, 0.0F, -0.2618F));

        ModelPartData h1P2 = h1P1.addChild("h1P2", ModelPartBuilder.create().uv(58, 38).cuboid(-1.5F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(3.25F, 0.3F, 0.0F, 0.0F, 0.0F, 0.7854F));

        ModelPartData h1P3 = h1P2.addChild("h1P3", ModelPartBuilder.create().uv(58, 42).cuboid(-1.0607F, -1.6464F, -1.0F, 5.0F, 2.0F, 2.0F, new Dilation(0.01F)), ModelTransform.of(5.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

        ModelPartData h1P4 = h1P3.addChild("h1P4", ModelPartBuilder.create().uv(58, 46).cuboid(-0.574F, -0.7727F, -1.0F, 3.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(4.0F, -1.0F, 0.0F, 0.0F, 0.0F, -0.3927F));

        ModelPartData h1P5 = horns.addChild("h1P5", ModelPartBuilder.create(), ModelTransform.of(-4.0F, -3.5F, -3.5F, 3.1416F, 0.0F, -2.8798F));

        ModelPartData cube_r8 = h1P5.addChild("cube_r8", ModelPartBuilder.create().uv(58, 32).mirrored().cuboid(-3.0F, -1.5F, -1.5F, 6.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        ModelPartData h1P6 = h1P5.addChild("h1P6", ModelPartBuilder.create(), ModelTransform.of(3.25F, 0.3F, 0.0F, 0.0F, 0.0F, 0.7854F));

        ModelPartData cube_r9 = h1P6.addChild("cube_r9", ModelPartBuilder.create().uv(58, 38).mirrored().cuboid(-3.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(1.5F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        ModelPartData h1P7 = h1P6.addChild("h1P7", ModelPartBuilder.create(), ModelTransform.of(5.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

        ModelPartData cube_r10 = h1P7.addChild("cube_r10", ModelPartBuilder.create().uv(58, 42).mirrored().cuboid(-2.5F, -1.0F, -1.0F, 5.0F, 2.0F, 2.0F, new Dilation(0.01F)).mirrored(false), ModelTransform.of(1.4393F, -0.6464F, 0.0F, 0.0F, 3.1416F, 0.0F));

        ModelPartData h1P8 = h1P7.addChild("h1P8", ModelPartBuilder.create(), ModelTransform.of(4.0F, -1.0F, 0.0F, 0.0F, 0.0F, -0.3927F));

        ModelPartData cube_r11 = h1P8.addChild("cube_r11", ModelPartBuilder.create().uv(58, 46).mirrored().cuboid(-1.5F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.926F, 0.2273F, 0.0F, 0.0F, 3.1416F, 0.0F));

        ModelPartData leg_right = main.addChild("leg_right", ModelPartBuilder.create().uv(32, 45).mirrored().cuboid(-2.0F, -2.0F, -1.5F, 4.0F, 12.0F, 4.0F, new Dilation(0.01F)).mirrored(false)
                .uv(58, 2).mirrored().cuboid(-2.0F, 10.0F, -1.5F, 4.0F, 2.0F, 4.0F, new Dilation(0.01F)).mirrored(false), ModelTransform.pivot(-2.5F, -12.0F, 0.5F));

        ModelPartData leg_left = main.addChild("leg_left", ModelPartBuilder.create().uv(32, 45).cuboid(-2.0F, -2.0F, -1.5F, 4.0F, 12.0F, 4.0F, new Dilation(0.01F))
                .uv(58, 2).cuboid(-2.0F, 10.0F, -1.5F, 4.0F, 2.0F, 4.0F, new Dilation(0.01F)), ModelTransform.pivot(2.5F, -12.0F, 0.5F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.head.pitch = headPitch * 0.010453292F;
        this.head.yaw = headYaw * 0.010453292F;

        this.leg_right.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.leg_left.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;

        this.main.yaw = 0.0f;
        this.arm_left.yaw = 0.0f;
        this.arm_right.yaw = 0.0f;

        if (entity.getDataTracker().get(ElysianShaman.SPIN_ATTACK)) {
            this.main.yaw = animationProgress;
            this.arm_left.pitch = 1.613f;
            this.arm_left.yaw = 1.413f;
            this.arm_right.pitch = 1.613f;
            this.arm_right.yaw = -1.413f;

        } else if (entity.handSwingProgress > 0.0f) {
            float f = MathHelper.sin(entity.handSwingProgress * (float) Math.PI);
            int handUsage = entity.getDataTracker().get(ElysianShaman.HAND_USAGE);
            if (handUsage == 0) {
                this.arm_right.pitch -= f / 6.28f / 1.5f;
            } else if (handUsage == 1) {
                this.arm_left.pitch -= f / 6.28f / 1.5f;
            } else if (handUsage == 2) {
                this.arm_left.pitch -= f / 6.28f / 1.5f;
                this.arm_right.pitch -= f / 6.28f / 1.5f;
            }
        } else {
            this.arm_right.pitch = (-0.2F + 1.5F * MathHelper.wrap(limbAngle, 13.0F)) * limbDistance;
            this.arm_left.pitch = (-0.2F - 1.5F * MathHelper.wrap(limbAngle, 13.0F)) * limbDistance;
        }

    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        this.main.render(matrices, vertices, light, overlay, color);
    }

}

