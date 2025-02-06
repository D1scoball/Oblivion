package net.oblivion.entity.model;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.util.math.MathHelper;
import net.oblivion.entity.WoolyCow;

@Environment(EnvType.CLIENT)
public class WoolyCowModel<T extends WoolyCow> extends AnimalModel<T> {
    private final ModelPart body;
    private final ModelPart fur;
    private final ModelPart head;
    private final ModelPart fur2;
    private final ModelPart horns;
    private final ModelPart leg0;
    private final ModelPart fur3;
    private final ModelPart leg1;
    private final ModelPart fur4;
    private final ModelPart leg2;
    private final ModelPart fur5;
    private final ModelPart leg3;
    private final ModelPart fur6;

    public WoolyCowModel(ModelPart root) {
        super(false, 10.0F, 4.0F, 2.0F, 2.0F, 24);
        this.body = root.getChild("body");
        this.fur = body.getChild("fur");
        this.head = root.getChild("head");
        this.fur2 = head.getChild("fur2");
        this.horns = head.getChild("horns");
        this.leg0 = root.getChild("leg0");
        this.fur3 = leg0.getChild("fur3");
        this.leg1 = root.getChild("leg1");
        this.fur4 = leg1.getChild("fur4");
        this.leg2 = root.getChild("leg2");
        this.fur5 = leg2.getChild("fur5");
        this.leg3 = root.getChild("leg3");
        this.fur6 = leg3.getChild("fur6");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 28).cuboid(-6.0F, -2.95F, -10.0F, 12.0F, 10.0F, 18.0F, new Dilation(-0.01F)), ModelTransform.pivot(0.0F, 5.0F, 2.0F));

        ModelPartData fur = body.addChild("fur", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, -2.95F, -10.0F, 12.0F, 10.0F, 18.0F, new Dilation(0.0F))
                .uv(48, 77).cuboid(-6.0F, 7.0F, 8.0F, 12.0F, 3.0F, 0.0F, new Dilation(0.0F))
                .uv(48, 80).cuboid(-6.0F, 7.0F, -10.0F, 12.0F, 3.0F, 0.0F, new Dilation(0.0F))
                .uv(0, 56).cuboid(6.0F, 7.0F, -10.0F, 0.0F, 3.0F, 18.0F, new Dilation(0.0F))
                .uv(36, 56).cuboid(-6.0F, 7.0F, -10.0F, 0.0F, 3.0F, 18.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(60, 14).cuboid(-4.0F, -4.0F, -6.0F, 8.0F, 8.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 4.0F, -8.0F));

        ModelPartData fur2 = head.addChild("fur2", ModelPartBuilder.create().uv(60, 0).cuboid(-4.0F, -4.0F, -6.0F, 8.0F, 8.0F, 6.0F, new Dilation(0.01F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData horns = head.addChild("horns", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 20.0F, 8.0F));

        ModelPartData head_r1 = horns.addChild("head_r1", ModelPartBuilder.create().uv(48, 83).cuboid(-2.0F, -4.0F, -1.0F, 2.0F, 5.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-4.0F, -23.0F, -12.0F, 0.5236F, 0.0F, 0.0F));

        ModelPartData head_r2 = horns.addChild("head_r2", ModelPartBuilder.create().uv(60, 44).cuboid(0.0F, -4.0F, -1.0F, 2.0F, 5.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(4.0F, -23.0F, -11.0F, 0.5236F, 0.0F, 0.0F));

        ModelPartData leg0 = modelPartData.addChild("leg0", ModelPartBuilder.create().uv(60, 28).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(-0.01F)), ModelTransform.pivot(4.0F, 12.0F, 7.0F));

        ModelPartData fur3 = leg0.addChild("fur3", ModelPartBuilder.create().uv(72, 76).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(-0.005F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData leg1 = modelPartData.addChild("leg1", ModelPartBuilder.create().uv(72, 44).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(-0.01F)), ModelTransform.pivot(-4.0F, 12.0F, 7.0F));

        ModelPartData fur4 = leg1.addChild("fur4", ModelPartBuilder.create().uv(0, 77).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(-0.005F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData leg2 = modelPartData.addChild("leg2", ModelPartBuilder.create().uv(72, 60).cuboid(-2.0F, 0.0F, -1.0F, 4.0F, 12.0F, 4.0F, new Dilation(-0.01F)), ModelTransform.pivot(4.0F, 12.0F, -6.0F));

        ModelPartData fur5 = leg2.addChild("fur5", ModelPartBuilder.create().uv(16, 77).cuboid(-2.0F, 0.0F, -1.0F, 4.0F, 12.0F, 4.0F, new Dilation(-0.005F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData leg3 = modelPartData.addChild("leg3", ModelPartBuilder.create().uv(76, 28).cuboid(-2.0F, 0.0F, -1.0F, 4.0F, 12.0F, 4.0F, new Dilation(-0.01F)), ModelTransform.pivot(-4.0F, 12.0F, -6.0F));

        ModelPartData fur6 = leg3.addChild("fur6", ModelPartBuilder.create().uv(32, 77).cuboid(-2.0F, 0.0F, -1.0F, 4.0F, 12.0F, 4.0F, new Dilation(-0.005F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(this.body, this.leg0, this.leg1, this.leg2, this.leg3);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.head.pitch = headPitch * 0.010453292F;
        this.head.yaw = headYaw * 0.010453292F;

        this.leg0.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.leg1.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.leg2.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.leg3.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.horns.visible = !entity.isBaby();
        boolean isSheared = entity.isSheared();
        this.fur.visible = !isSheared;
        this.fur2.visible = !isSheared;
        this.fur3.visible = !isSheared;
        this.fur4.visible = !isSheared;
        this.fur5.visible = !isSheared;
        this.fur6.visible = !isSheared;
    }

}
