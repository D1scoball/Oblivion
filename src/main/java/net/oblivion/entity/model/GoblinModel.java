package net.oblivion.entity.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;


public class GoblinModel<T extends LivingEntity> extends EntityModel<T> {
    private final ModelPart base;
    private final ModelPart body;
    private final ModelPart body_armor;
    private final ModelPart right_arm;
    private final ModelPart right_armor;
    private final ModelPart left_arm;
    private final ModelPart left_armor;
    private final ModelPart right_leg;
    private final ModelPart right_leg_armor;
    private final ModelPart left_leg;
    private final ModelPart left_leg_armor;
    private final ModelPart head;
    private final ModelPart head_armor;
    private final ModelPart ears;

    public GoblinModel(ModelPart root) {
        this.base = root.getChild("base");
        this.body = base.getChild("body");
        this.body_armor = body.getChild("body_armor");
        this.right_arm = base.getChild("right_arm");
        this.right_armor = right_arm.getChild("right_armor");
        this.left_arm = base.getChild("left_arm");
        this.left_armor = left_arm.getChild("left_armor");
        this.right_leg = base.getChild("right_leg");
        this.right_leg_armor = right_leg.getChild("right_leg_armor");
        this.left_leg = base.getChild("left_leg");
        this.left_leg_armor = left_leg.getChild("left_leg_armor");
        this.head = base.getChild("head");
        this.head_armor = head.getChild("head_armor");
        this.ears = head.getChild("ears");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData base = modelPartData.addChild("base", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 13.0F, -1.0F));

        ModelPartData body = base.addChild("body", ModelPartBuilder.create().uv(0, 34).cuboid(-4.0F, -4.0F, -2.0F, 8.0F, 9.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData body_armor = body.addChild("body_armor", ModelPartBuilder.create().uv(30, 30).cuboid(-4.0F, -4.0F, -2.0F, 8.0F, 9.0F, 4.0F, new Dilation(0.05F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData right_arm = base.addChild("right_arm", ModelPartBuilder.create().uv(48, 0).cuboid(0.0F, -1.0F, -2.0F, 3.0F, 9.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, -3.0F, 0.0F));

        ModelPartData right_armor = right_arm.addChild("right_armor", ModelPartBuilder.create().uv(37, 43).cuboid(-2.0F, -4.0F, -4.0F, 4.0F, 4.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(1.5F, 2.5F, 1.5F));

        ModelPartData left_arm = base.addChild("left_arm", ModelPartBuilder.create().uv(0, 47).cuboid(-3.0F, -1.0F, -2.0F, 3.0F, 9.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, -3.0F, 0.0F));

        ModelPartData left_armor = left_arm.addChild("left_armor", ModelPartBuilder.create().uv(19, 43).cuboid(-2.0F, -4.0F, -4.0F, 4.0F, 4.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.5F, 2.5F, 1.5F));

        ModelPartData right_leg = base.addChild("right_leg", ModelPartBuilder.create().uv(51, 48).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, 5.0F, 0.0F));

        ModelPartData right_leg_armor = right_leg.addChild("right_leg_armor", ModelPartBuilder.create().uv(33, 13).cuboid(-4.0F, -6.0F, 0.0F, 5.0F, 5.0F, 5.0F, new Dilation(0.03F)), ModelTransform.pivot(1.5F, 7.0F, -2.5F));

        ModelPartData left_leg = base.addChild("left_leg", ModelPartBuilder.create().uv(49, 19).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 5.0F, 0.0F));

        ModelPartData left_leg_armor = left_leg.addChild("left_leg_armor", ModelPartBuilder.create().uv(28, 0).cuboid(-4.0F, -6.0F, 0.0F, 5.0F, 5.0F, 5.0F, new Dilation(0.02F)), ModelTransform.pivot(1.5F, 7.0F, -2.5F));

        ModelPartData head = base.addChild("head", ModelPartBuilder.create().uv(0, 18).cuboid(-4.0F, -7.0F, -4.5F, 8.0F, 7.0F, 9.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-1.5F, -2.5F, -5.5F, 3.0F, 4.0F, 2.0F, new Dilation(0.0F))
                .uv(48, 13).cuboid(-4.0F, -5.7F, -4.75F, 8.0F, 4.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -4.0F, 0.0F));

        ModelPartData head_armor = head.addChild("head_armor", ModelPartBuilder.create().uv(0, 0).cuboid(-4.5F, -7.0F, -4.75F, 9.0F, 8.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -0.5F, -0.25F));

        ModelPartData ears = head.addChild("ears", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -3.0F, -2.0F));

        ModelPartData cube_r1 = ears.addChild("cube_r1", ModelPartBuilder.create().uv(25, 17).cuboid(-0.5F, -2.5F, 0.0F, 0.0F, 4.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(4.5F, 0.0F, -1.0F, 0.0F, 0.7854F, 0.0F));

        ModelPartData cube_r2 = ears.addChild("cube_r2", ModelPartBuilder.create().uv(37, 23).cuboid(-4.5F, -2.5F, 0.0F, 6.0F, 4.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.head.pitch = headPitch * 0.010453292F;
        this.head.yaw = headYaw * 0.010453292F;

        this.left_arm.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.right_leg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.right_arm.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.left_leg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        this.base.render(matrices, vertices, light, overlay, color);
    }

}

