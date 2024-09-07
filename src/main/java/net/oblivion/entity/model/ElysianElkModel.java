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
public class ElysianElkModel<T extends AnimalEntity> extends AnimalModel<T> {
    private final ModelPart elk;
    private final ModelPart body;
    private final ModelPart leg_front_left;
    private final ModelPart leg_front_right;
    private final ModelPart leg_back_right;
    private final ModelPart leg_back_left;
    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart horns;
    private final ModelPart tail;

    public ElysianElkModel(ModelPart root) {
        super(true, 16.0F, 4.0F);
        this.elk = root.getChild("elk");
        this.body = elk.getChild("body");
        this.leg_front_left = elk.getChild("leg_front_left");
        this.leg_front_right = elk.getChild("leg_front_right");
        this.leg_back_right = elk.getChild("leg_back_right");
        this.leg_back_left = elk.getChild("leg_back_left");
        this.neck = elk.getChild("neck");
        this.head = elk.getChild("head");
        this.horns = head.getChild("horns");
        this.tail = elk.getChild("tail");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData elk = modelPartData.addChild("elk", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, -1.0F));

        ModelPartData body = elk.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(-0.5F, 9.5F, 7.5F));

        ModelPartData cube_r1 = body.addChild("cube_r1", ModelPartBuilder.create().uv(0, 17).cuboid(-4.0F, 2.0F, 2.0F, 8.0F, 8.0F, 11.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -6.5F, -9.5F, -0.0436F, 0.0F, 0.0F));

        ModelPartData cube_r2 = body.addChild("cube_r2", ModelPartBuilder.create().uv(0, 36).cuboid(-5.0F, -4.0F, -4.5F, 10.0F, 9.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.5F, -12.0F, -0.0873F, 0.0F, 0.0F));

        ModelPartData leg_front_left = elk.addChild("leg_front_left", ModelPartBuilder.create().uv(32, 59).cuboid(-2.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, 13.0F, -5.0F));

        ModelPartData leg_front_right = elk.addChild("leg_front_right", ModelPartBuilder.create().uv(16, 54).mirrored().cuboid(-2.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-3.0F, 13.0F, -5.0F));

        ModelPartData leg_back_right = elk.addChild("leg_back_right", ModelPartBuilder.create().uv(0, 54).mirrored().cuboid(-2.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-3.0F, 13.0F, 8.0F));

        ModelPartData leg_back_left = elk.addChild("leg_back_left", ModelPartBuilder.create().uv(46, 13).cuboid(-2.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, 13.0F, 8.0F));

        ModelPartData neck = elk.addChild("neck", ModelPartBuilder.create().uv(38, 42).cuboid(-3.0F, -4.0F, -3.0F, 6.0F, 12.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, 4.0F, -8.75F, 0.6545F, 0.0F, 0.0F));

        ModelPartData head = elk.addChild("head", ModelPartBuilder.create().uv(31, 29).cuboid(-4.0F, -12.0F, -8.0F, 7.0F, 6.0F, 7.0F, new Dilation(0.0F))
                .uv(48, 59).cuboid(-3.5F, -9.5F, -13.0F, 6.0F, 3.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 9.0F, -7.0F));

        ModelPartData horns = head.addChild("horns", ModelPartBuilder.create().uv(0, 0).cuboid(-13.0F, -16.0F, 1.0F, 25.0F, 17.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -12.5F, -3.0F, -0.0873F, 0.0F, 0.0F));

        ModelPartData tail = elk.addChild("tail", ModelPartBuilder.create().uv(27, 17).cuboid(-3.0F, 0.0F, 0.0F, 6.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, 9.0F, 11.0F, 0.0873F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(this.body,this.neck,this.leg_front_left,this.leg_front_right,this.leg_back_right,this.leg_back_left,this.tail);
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
