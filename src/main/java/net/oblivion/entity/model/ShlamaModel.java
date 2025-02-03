package net.oblivion.entity.model;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.util.math.MathHelper;
import net.oblivion.entity.Shlama;

@Environment(EnvType.CLIENT)
public class ShlamaModel<T extends Shlama> extends AnimalModel<T> {
    private final ModelPart head;
    private final ModelPart antler;
    private final ModelPart ears;
    private final ModelPart neck;
    private final ModelPart right_front_leg;
    private final ModelPart right_back_leg;
    private final ModelPart left_back_leg;
    private final ModelPart left_front_leg;
    private final ModelPart body;
    private final ModelPart wool;

    public ShlamaModel(ModelPart root) {
        super(true, 22.0F, 4.0F);
        this.head = root.getChild("head");
        this.antler = head.getChild("antler");
        this.ears = head.getChild("ears");
        this.neck = root.getChild("neck");
        this.right_front_leg = root.getChild("right_front_leg");
        this.right_back_leg = root.getChild("right_back_leg");
        this.left_back_leg = root.getChild("left_back_leg");
        this.left_front_leg = root.getChild("left_front_leg");
        this.body = root.getChild("body");
        this.wool = body.getChild("wool");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(24, 0).cuboid(-4.0F, -5.0F, -12.0F, 7.0F, 5.0F, 6.0F, new Dilation(0.0F))
                .uv(24, 14).cuboid(-6.0F, -9.0F, -6.0F, 11.0F, 9.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.0F, -13.0F, -14.0F));

        ModelPartData antler = head.addChild("antler", ModelPartBuilder.create().uv(40, 44).mirrored().cuboid(4.0F, -2.0F, 2.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
                .uv(56, 39).cuboid(4.0F, -2.0F, 0.0F, 2.0F, 7.0F, 2.0F, new Dilation(0.0F))
                .uv(48, 39).mirrored().cuboid(-2.0F, 1.0F, 0.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-3.0F, -14.0F, -2.0F));

        ModelPartData ears = head.addChild("ears", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, -9.0F, 0.0F));

        ModelPartData ear_right_r1 = ears.addChild("ear_right_r1", ModelPartBuilder.create().uv(20, 43).cuboid(-5.5F, -0.25F, -2.0F, 6.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-4.5F, 0.25F, 0.0F, 0.0F, 0.0F, 0.3927F));

        ModelPartData ear_left_r1 = ears.addChild("ear_left_r1", ModelPartBuilder.create().uv(20, 38).cuboid(-1.0F, 0.0F, -2.0F, 6.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(6.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3927F));

        ModelPartData neck = modelPartData.addChild("neck", ModelPartBuilder.create().uv(0, 0).cuboid(-2.5F, -16.0F, -3.0F, 6.0F, 23.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 3.0F, -14.0F));

        ModelPartData right_front_leg = modelPartData.addChild("right_front_leg", ModelPartBuilder.create().uv(0, 29).cuboid(-2.5F, 0.0F, -2.5F, 5.0F, 14.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.5F, 10.0F, -7.5F));

        ModelPartData right_back_leg = modelPartData.addChild("right_back_leg", ModelPartBuilder.create().uv(0, 29).cuboid(-2.5F, 0.0F, -2.5F, 5.0F, 14.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.5F, 10.0F, 9.5F));

        ModelPartData left_front_leg = modelPartData.addChild("left_front_leg", ModelPartBuilder.create().uv(0, 29).cuboid(-2.5F, 0.0F, -2.5F, 5.0F, 14.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(2.5F, 10.0F, -7.5F));

        ModelPartData left_back_leg = modelPartData.addChild("left_back_leg", ModelPartBuilder.create().uv(0, 29).cuboid(-2.5F, 0.0F, -2.5F, 5.0F, 14.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(2.5F, 10.0F, 9.5F));

        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(1, 85).cuboid(-6.5F, -6.0F, -12.0F, 13.0F, 13.0F, 23.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.5F, 3.0F, 1.0F));

        ModelPartData wool = body.addChild("wool", ModelPartBuilder.create().uv(0, 48).cuboid(-7.5F, -7.0F, -12.0F, 15.0F, 14.0F, 23.0F, new Dilation(0.1F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(this.body, this.neck, this.right_back_leg, this.right_front_leg, this.left_back_leg, this.left_front_leg);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.head.pitch = headPitch * 0.010453292F;
        this.head.yaw = headYaw * 0.010453292F;

        float l = animationProgress * 0.1F + limbAngle * 0.5F;
        float m = 0.08F + limbDistance * 0.4F;
        float k = 0.0f;
        if (entity.getDataTracker().get(Shlama.EARS)) {
            k = 1.2f;
        }
        this.ears.getChild("ear_left_r1").roll = k + (float) (-Math.PI / 6) - MathHelper.cos(l * 1.2F) * m;
        this.ears.getChild("ear_right_r1").roll = -k + (float) (Math.PI / 6) + MathHelper.cos(l) * m;

        this.right_front_leg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.right_back_leg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.left_front_leg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.left_back_leg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.antler.visible = !entity.isBaby();
        this.wool.visible = !entity.isSheared();
    }

}
