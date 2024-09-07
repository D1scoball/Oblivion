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
public class ShroomModel<T extends LivingEntity> extends EntityModel<T> {
    private final ModelPart base;
    private final ModelPart body;
    private final ModelPart mushrooms;
    private final ModelPart head;
    private final ModelPart hat;
    private final ModelPart leg_left;
    private final ModelPart leg_right;
    private final ModelPart arm_left;
    private final ModelPart arm_right;

    public ShroomModel(ModelPart root) {
        this.base = root.getChild("base");
        this.body = base.getChild("body");
        this.mushrooms = body.getChild("mushrooms");
        this.head = base.getChild("head");
        this.hat = head.getChild("hat");
        this.leg_left = base.getChild("leg_left");
        this.leg_right = base.getChild("leg_right");
        this.arm_left = base.getChild("arm_left");
        this.arm_right = base.getChild("arm_right");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData base = modelPartData.addChild("base", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData body = base.addChild("body", ModelPartBuilder.create().uv(0, 36).cuboid(-4.5F, -5.5F, -3.0F, 9.0F, 12.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -11.5F, 0.0F));

        ModelPartData mushrooms = body.addChild("mushrooms", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -2.0F, -2.0F));

        ModelPartData cube_r1 = mushrooms.addChild("cube_r1", ModelPartBuilder.create().uv(0, 57).cuboid(-1.0F, -1.0F, 0.0F, 3.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, 4.25F, 5.25F, -1.9356F, -1.0624F, 0.322F));

        ModelPartData cube_r2 = mushrooms.addChild("cube_r2", ModelPartBuilder.create().uv(0, 57).cuboid(-1.0F, -1.0F, 0.0F, 3.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, -0.75F, 6.25F, -1.763F, -0.4293F, 0.0808F));

        ModelPartData cube_r3 = mushrooms.addChild("cube_r3", ModelPartBuilder.create().uv(0, 60).cuboid(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-0.634F, 0.25F, 5.884F, -1.8385F, 0.8547F, -0.2041F));

        ModelPartData cube_r4 = mushrooms.addChild("cube_r4", ModelPartBuilder.create().uv(0, 60).cuboid(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(3.366F, 3.25F, -1.116F, -1.4355F, 0.8681F, 0.1035F));

        ModelPartData cube_r5 = mushrooms.addChild("cube_r5", ModelPartBuilder.create().uv(0, 57).cuboid(-1.0F, -1.0F, 0.0F, 3.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, 4.25F, -1.75F, -1.4746F, -0.4346F, -0.0406F));

        ModelPartData cube_r6 = mushrooms.addChild("cube_r6", ModelPartBuilder.create().uv(0, 57).cuboid(-1.0F, -1.0F, 0.0F, 3.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-4.0F, -2.75F, 1.25F, 0.0322F, -0.3377F, -2.0726F));

        ModelPartData cube_r7 = mushrooms.addChild("cube_r7", ModelPartBuilder.create().uv(0, 60).cuboid(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-4.0F, -4.0F, 3.0F, 0.1687F, -0.5642F, -2.4848F));

        ModelPartData head = base.addChild("head", ModelPartBuilder.create().uv(28, 52).cuboid(-3.0F, -6.0F, -3.0F, 6.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -17.0F, 0.0F));

        ModelPartData hat = head.addChild("hat", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -5.0F, -8.0F, 16.0F, 5.0F, 16.0F, new Dilation(0.0F))
                .uv(0, 21).cuboid(-6.0F, -8.0F, -6.0F, 12.0F, 3.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -5.0F, 0.0F));

        ModelPartData leg_left = base.addChild("leg_left", ModelPartBuilder.create().uv(52, 39).mirrored().cuboid(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-2.5F, -6.0F, 0.0F));

        ModelPartData leg_right = base.addChild("leg_right", ModelPartBuilder.create().uv(52, 39).cuboid(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(2.5F, -6.0F, 0.0F));

        ModelPartData arm_left = base.addChild("arm_left", ModelPartBuilder.create().uv(52, 48).cuboid(0.0F, -2.0F, -1.0F, 3.0F, 13.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(4.5F, -14.0F, -0.5F));

        ModelPartData arm_right = base.addChild("arm_right", ModelPartBuilder.create().uv(52, 48).mirrored().cuboid(-3.0F, -2.0F, -1.0F, 3.0F, 13.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-4.5F, -14.0F, -0.5F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.head.pitch = headPitch * 0.010453292F;
        this.head.yaw = headYaw * 0.010453292F;

        this.leg_left.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.arm_right.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.leg_right.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.arm_left.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        this.base.render(matrices, vertices, light, overlay, color);
    }

}

