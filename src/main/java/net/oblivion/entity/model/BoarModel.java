package net.oblivion.entity.model;

import com.google.common.collect.ImmutableList;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class BoarModel<T extends AnimalEntity> extends AnimalModel<T> {
    private final ModelPart main;
    private final ModelPart body;
    private final ModelPart leg_back_left;
    private final ModelPart leg_front_left;
    private final ModelPart leg_back_right;
    private final ModelPart leg_front_right;
    private final ModelPart head;
    private final ModelPart horns;

    public BoarModel(ModelPart root) {
        super(true, 11.5F, 2.0F);
        this.main = root.getChild("main");
        this.body = main.getChild("body");
        this.leg_back_left = main.getChild("leg_back_left");
        this.leg_front_left = main.getChild("leg_front_left");
        this.leg_back_right = main.getChild("leg_back_right");
        this.leg_front_right = main.getChild("leg_front_right");
        this.head = main.getChild("head");
        this.horns = head.getChild("horns");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData body = main.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-7.0F, -12.0F, -7.0F, 14.0F, 12.0F, 10.0F, new Dilation(0.0F))
                .uv(0, 22).cuboid(-5.0F, -10.0F, 3.0F, 10.0F, 9.0F, 5.0F, new Dilation(0.0F))
                .uv(0, 26).cuboid(0.0F, -14.0F, -7.0F, 0.0F, 2.0F, 10.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(0.0F, -12.0F, 3.0F, 0.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 21.0F, 2.0F));

        ModelPartData cube_r1 = body.addChild("cube_r1", ModelPartBuilder.create().uv(36, 41).cuboid(-3.0F, 0.0F, 0.0F, 6.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -8.0F, 8.0F, 0.3054F, 0.0F, 0.0F));

        ModelPartData head = main.addChild("head", ModelPartBuilder.create().uv(30, 22).cuboid(-4.5F, -4.0F, -5.0F, 9.0F, 8.0F, 5.0F, new Dilation(0.0F))
                .uv(27, 35).cuboid(-3.0F, 1.0F, -8.0F, 6.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 15.0F, -5.0F));

        ModelPartData cube_r2 = head.addChild("cube_r2", ModelPartBuilder.create().uv(0, 7).cuboid(0.5F, -2.0F, -1.0F, 3.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, -4.0F, -1.0F, 0.0F, -0.2618F, 0.0F));

        ModelPartData cube_r3 = head.addChild("cube_r3", ModelPartBuilder.create().uv(42, 35).cuboid(-3.5F, -2.0F, -1.0F, 3.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, -4.0F, -1.0F, 0.0F, 0.2618F, 0.0F));

        ModelPartData horns = head.addChild("horns", ModelPartBuilder.create(), ModelTransform.pivot(-2.0F, 2.0F, -7.0F));

        ModelPartData cube_r4 = horns.addChild("cube_r4", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -2.0F, 0.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.2182F, 0.0F));

        ModelPartData cube_r5 = horns.addChild("cube_r5", ModelPartBuilder.create().uv(25, 22).cuboid(0.0F, -2.0F, 0.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(4.0F, 0.0F, 0.0F, 0.0F, -0.2182F, 0.0F));

        ModelPartData leg_back_left = main.addChild("leg_back_left", ModelPartBuilder.create().uv(38, 0).cuboid(-1.5F, -1.0F, -1.5F, 3.0F, 5.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(3.0F, 20.0F, 7.5F));

        ModelPartData leg_front_left = main.addChild("leg_front_left", ModelPartBuilder.create().uv(12, 38).cuboid(-1.5F, -1.0F, -1.5F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, 21.0F, -2.5F));

        ModelPartData leg_back_right = main.addChild("leg_back_right", ModelPartBuilder.create().uv(0, 38).cuboid(-1.5F, -1.0F, -1.5F, 3.0F, 5.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, 20.0F, 7.5F));

        ModelPartData leg_front_right = main.addChild("leg_front_right", ModelPartBuilder.create().uv(24, 41).cuboid(-1.5F, -1.0F, -1.5F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, 21.0F, -2.5F));
        return TexturedModelData.of(modelData, 64, 64);
    }
    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(this.body,this.leg_front_left,this.leg_front_right,this.leg_back_right,this.leg_back_left);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.head.pitch = headPitch * 0.010453292F;
        this.head.yaw = headYaw * 0.010453292F;

        this.leg_front_left.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.leg_back_right.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.leg_front_right.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.leg_back_left.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.horns.visible = !entity.isBaby();
    }

}
