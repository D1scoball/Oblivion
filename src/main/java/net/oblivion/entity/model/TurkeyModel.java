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
public class TurkeyModel<T extends AnimalEntity> extends AnimalModel<T> {
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart right_leg;
    private final ModelPart left_leg;
    private final ModelPart right_wing;
    private final ModelPart left_wing;
    private final ModelPart neck;

    public TurkeyModel(ModelPart root) {
        super(true, 18.0F, 4.0F);
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.right_leg = root.getChild("right_leg");
        this.left_leg = root.getChild("left_leg");
        this.right_wing = root.getChild("right_wing");
        this.left_wing = root.getChild("left_wing");
        this.neck = root.getChild("neck");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(45, 17).cuboid(-4.5F, -9.0F, -4.5F, 9.0F, 9.0F, 9.0F, new Dilation(0.0F))
                .uv(38, 0).cuboid(-3.5F, -3.0F, -10.5F, 7.0F, 3.0F, 6.0F, new Dilation(0.0F))
                .uv(0, 10).cuboid(0.0F, -16.0F, -4.5F, 0.0F, 13.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(-0.5F, -2.0F, -10.0F));

        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -5.0F, -9.0F, 11.0F, 10.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(-0.5F, 10.0F, 0.0F));

        ModelPartData right_leg = modelPartData.addChild("right_leg", ModelPartBuilder.create().uv(33, 9).cuboid(-3.0F, 8.75F, -4.0F, 6.0F, 0.0F, 5.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 9.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.5F, 15.0F, -1.0F));

        ModelPartData left_leg = modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(31, 35).cuboid(-3.0F, 8.75F, -4.0F, 6.0F, 0.0F, 5.0F, new Dilation(0.0F))
                .uv(8, 0).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 9.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(4.5F, 15.0F, -1.0F));

        ModelPartData right_wing = modelPartData.addChild("right_wing", ModelPartBuilder.create().uv(18, 26).cuboid(-2.0F, -1.0F, -2.0F, 2.0F, 10.0F, 14.0F, new Dilation(0.0F))
                .uv(24, 53).cuboid(0.0F, -1.0F, 12.0F, 0.0F, 10.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.5F, 6.0F, -6.0F));

        ModelPartData left_wing = modelPartData.addChild("left_wing", ModelPartBuilder.create().uv(36, 36).cuboid(0.0F, -1.0F, -2.0F, 2.0F, 10.0F, 14.0F, new Dilation(0.0F))
                .uv(38, 53).cuboid(2.0F, -1.0F, 12.0F, 0.0F, 10.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(5.5F, 6.0F, -6.0F));

        ModelPartData neck = modelPartData.addChild("neck", ModelPartBuilder.create().uv(0, 44).cuboid(-3.0F, -10.0F, -6.0F, 6.0F, 15.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(-0.5F, 8.0F, -7.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(this.body,this.neck,this.left_leg,this.right_leg,this.left_wing,this.right_wing);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.head.pitch = headPitch * 0.010453292F;
        this.head.yaw = headYaw * 0.010453292F;

        this.left_leg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.right_leg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
//        this.leg_front_right.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
//        this.leg_back_left.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
//        this.horns.visible = !entity.isBaby();
    }

}
