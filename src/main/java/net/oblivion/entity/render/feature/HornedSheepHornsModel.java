package net.oblivion.entity.render.feature;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

@Environment(EnvType.CLIENT)
public class HornedSheepHornsModel<T extends LivingEntity> extends EntityModel<T> {

    private final ModelPart horns;

    public HornedSheepHornsModel(ModelPart root) {
        this.horns = root.getChild("horns");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData horns = modelPartData.addChild("horns", ModelPartBuilder.create().uv(0, 0).cuboid(-7.0F, -7.5F, -1.5F, 4.0F, 7.0F, 6.0F, new Dilation(0.0F))
                .uv(20, 0).cuboid(-7.0F, -3.5F, -4.5F, 4.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 13).cuboid(3.0F, -7.5F, -1.5F, 4.0F, 7.0F, 6.0F, new Dilation(0.0F))
                .uv(20, 6).cuboid(3.0F, -3.5F, -4.5F, 4.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void animateModel(T sheepEntity, float f, float g, float h) {
        super.animateModel(sheepEntity, f, g, h);
    }

    @Override
    public void setAngles(T sheepEntity, float f, float g, float h, float i, float j) {
        this.horns.visible = !sheepEntity.isBaby();
        this.horns.pivotY = 2.8f;
        this.horns.pivotZ = -2.5f;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        this.horns.render(matrices, vertices, light, overlay, color);
    }
}

