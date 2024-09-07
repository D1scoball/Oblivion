package net.oblivion.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class ElysianWolfModel<T extends LivingEntity> extends EntityModel<T> {
    private final ModelPart main;
    private final ModelPart head;
    private final ModelPart ear_right;
    private final ModelPart ear_left;
    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart leg_left_front;
    private final ModelPart leg_right_front;
    private final ModelPart leg_right_back;
    private final ModelPart leg_left_back;

    public ElysianWolfModel(ModelPart root) {
        this.main = root.getChild("main");
        this.head = main.getChild("head");
        this.ear_right = head.getChild("ear_right");
        this.ear_left = head.getChild("ear_left");
        this.body = main.getChild("body");
        this.tail = body.getChild("tail");
        this.leg_left_front = main.getChild("leg_left_front");
        this.leg_right_front = main.getChild("leg_right_front");
        this.leg_right_back = main.getChild("leg_right_back");
        this.leg_left_back = main.getChild("leg_left_back");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData head = main.addChild("head", ModelPartBuilder.create().uv(76, 0).cuboid(-5.5F, -7.5F, -12.0F, 11.0F, 12.0F, 12.0F, new Dilation(0.0F))
                .uv(96, 24).mirrored().cuboid(-7.0F, -4.5F, -9.0F, 2.0F, 8.0F, 8.0F, new Dilation(0.0F)).mirrored(false)
                .uv(96, 24).cuboid(5.0F, -4.5F, -9.0F, 2.0F, 8.0F, 8.0F, new Dilation(0.0F))
                .uv(46, 21).cuboid(-4.5F, -2.0F, -22.0F, 9.0F, 6.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -21.5F, -14.0F));

        ModelPartData ear_right = head.addChild("ear_right", ModelPartBuilder.create().uv(84, 24).cuboid(-1.5F, -8.0F, -2.0F, 3.0F, 10.0F, 3.0F, new Dilation(0.0F))
                .uv(84, 24).cuboid(-4.3121F, -10.8711F, 2.8835F, 3.0F, 10.0F, 3.0F, new Dilation(0.0F))
                .uv(84, 24).cuboid(-7.2759F, -14.3226F, 8.5672F, 3.0F, 10.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, -6.5F, -4.0F, -0.6276F, 0.1522F, -1.2956F));

        ModelPartData ear_left = head.addChild("ear_left", ModelPartBuilder.create().uv(84, 24).mirrored().cuboid(-1.5F, -8.0F, -2.0F, 3.0F, 10.0F, 3.0F, new Dilation(0.0F)).mirrored(false)
                .uv(84, 24).mirrored().cuboid(1.3121F, -10.8711F, 2.8835F, 3.0F, 10.0F, 3.0F, new Dilation(0.0F)).mirrored(false)
                .uv(84, 24).mirrored().cuboid(4.2759F, -14.3226F, 8.5672F, 3.0F, 10.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(5.0F, -6.5F, -4.0F, -0.6276F, -0.1522F, 1.2956F));

        ModelPartData body = main.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-7.0F, -7.75F, -15.25F, 14.0F, 15.0F, 14.0F, new Dilation(0.0F))
                .uv(0, 29).cuboid(-6.0F, -5.75F, -1.25F, 12.0F, 12.0F, 19.0F, new Dilation(0.1F)), ModelTransform.pivot(0.0F, -20.25F, 1.25F));

        ModelPartData tail = body.addChild("tail", ModelPartBuilder.create(), ModelTransform.of(0.0F, 0.25F, 17.75F, -0.7854F, 0.0F, 0.0F));

        ModelPartData cube_r1 = tail.addChild("cube_r1", ModelPartBuilder.create().uv(0, 60).cuboid(-1.5F, -8.5F, -1.5F, 4.0F, 20.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, 0.0F, 5.0F, 1.5708F, 0.0F, -1.5708F));

        ModelPartData leg_left_front = main.addChild("leg_left_front", ModelPartBuilder.create().uv(56, 0).cuboid(-2.5F, -1.0F, -2.5F, 5.0F, 16.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(3.5F, -15.0F, -9.5F));

        ModelPartData leg_right_front = main.addChild("leg_right_front", ModelPartBuilder.create().uv(56, 0).mirrored().cuboid(-2.5F, -1.0F, -2.5F, 5.0F, 16.0F, 5.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-3.5F, -15.0F, -9.5F));

        ModelPartData leg_right_back = main.addChild("leg_right_back", ModelPartBuilder.create().uv(62, 37).mirrored().cuboid(-2.5F, -1.0F, -2.5F, 5.0F, 16.0F, 5.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-3.5F, -15.0F, 15.5F));

        ModelPartData leg_left_back = main.addChild("leg_left_back", ModelPartBuilder.create().uv(62, 37).cuboid(-2.5F, -1.0F, -2.5F, 5.0F, 16.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(3.5F, -15.0F, 15.5F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.head.pitch = headPitch * 0.010453292F;
        this.head.yaw = headYaw * 0.010453292F;

        this.leg_left_front.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.leg_right_back.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.leg_right_front.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.leg_left_back.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        this.main.render(matrices, vertices, light, overlay, color);
    }

}

