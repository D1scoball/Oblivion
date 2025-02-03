package net.oblivion.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.oblivion.entity.Treeder;

@Environment(EnvType.CLIENT)
public class TreederModel<T extends LivingEntity> extends EntityModel<T> {
    private final ModelPart base;
    private final ModelPart left_leg;
    private final ModelPart right_leg;
    private final ModelPart right_arm;
    private final ModelPart left_arm;
    private final ModelPart body;
    private final ModelPart sapling;

    public TreederModel(ModelPart root) {
        this.base = root.getChild("base");
        this.left_leg = base.getChild("left_leg");
        this.right_leg = base.getChild("right_leg");
        this.right_arm = base.getChild("right_arm");
        this.left_arm = base.getChild("left_arm");
        this.body = base.getChild("body");
        this.sapling = body.getChild("sapling");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData base = modelPartData.addChild("base", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 18.0F, 0.0F));

        ModelPartData left_leg = base.addChild("left_leg", ModelPartBuilder.create().uv(26, 20).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 4.0F, 0.0F));

        ModelPartData right_leg = base.addChild("right_leg", ModelPartBuilder.create().uv(26, 16).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, 4.0F, 0.0F));

        ModelPartData right_arm = base.addChild("right_arm", ModelPartBuilder.create().uv(24, 0).cuboid(0.0F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F))
                .uv(14, 22).cuboid(-2.0F, 2.0F, 0.0F, 6.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, 0.0F, 0.0F));

        ModelPartData left_arm = base.addChild("left_arm", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F))
                .uv(14, 16).cuboid(-4.0F, 2.0F, 0.0F, 6.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, 0.0F, 0.0F));

        ModelPartData body = base.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData sapling = body.addChild("sapling", ModelPartBuilder.create().uv(0, 23).cuboid(-1.0F, -6.0F, -2.0F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 16).cuboid(-3.0F, -12.0F, -0.5F, 7.0F, 7.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.base.roll = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance * 0.1F;
        this.left_arm.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.right_leg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.right_arm.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.left_leg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;

        this.sapling.visible = entity.getDataTracker().get(Treeder.SAPLING);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        this.base.render(matrices, vertices, light, overlay, color);
    }

}

