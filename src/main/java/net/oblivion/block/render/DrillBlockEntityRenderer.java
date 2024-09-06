package net.oblivion.block.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.oblivion.OblivionMain;
import net.oblivion.block.DrillBlock;
import net.oblivion.block.entity.DrillBlockEntity;
import net.oblivion.init.RenderInit;

@Environment(EnvType.CLIENT)
public class DrillBlockEntityRenderer implements BlockEntityRenderer<DrillBlockEntity> {

    private static final Identifier TEXTURE = OblivionMain.identifierOf("textures/block/entity/drill.png");

    private final ModelPart head;
    private final ModelPart base;

    public DrillBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        ModelPart modelPart = ctx.getLayerModelPart(RenderInit.DRILL_LAYER);
        this.head = modelPart.getChild("head");
        this.base = modelPart.getChild("base");
    }

    public DrillBlockEntityRenderer(ModelPart root) {
        this.head = root.getChild("head");
        this.base = root.getChild("base");
    }


    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(12, 39).cuboid(3.0F, -2.0F, -4.0F, 2.0F, 4.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-5.0F, -2.0F, -4.0F, 2.0F, 4.0F, 3.0F, new Dilation(0.0F))
                .uv(26, 26).cuboid(-3.0F, -3.0F, -5.0F, 6.0F, 6.0F, 4.0F, new Dilation(0.0F))
                .uv(0, 39).cuboid(-2.0F, -2.0F, -7.0F, 4.0F, 4.0F, 2.0F, new Dilation(0.0F))
                .uv(22, 41).cuboid(-1.0F, -1.0F, -9.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(37, 36).cuboid(-2.0F, -5.0F, -4.0F, 4.0F, 2.0F, 3.0F, new Dilation(0.0F))
                .uv(23, 36).cuboid(-2.0F, 3.0F, -4.0F, 4.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 16.0F, 0.0F));

        ModelPartData base = modelPartData.addChild("base", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -16.0F, -2.0F, 16.0F, 16.0F, 10.0F, new Dilation(0.0F))
                .uv(0, 26).cuboid(-6.0F, -14.0F, -2.0F, 12.0F, 12.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void render(DrillBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int light, int overlay) {
        matrices.push();
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutoutNoCull(TEXTURE));

        matrices.translate(0.5D, -0.5D, 0.5D);

        BlockState state = entity.getCachedState();
        Direction blockDirection = state.get(Properties.FACING);
        if (blockDirection.equals(Direction.EAST)) {
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-90F));
        } else if (blockDirection.equals(Direction.SOUTH)) {
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-180F));
        } else if (blockDirection.equals(Direction.WEST)) {
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90F));
        } else if (blockDirection.equals(Direction.UP)) {
            matrices.translate(0D, 1D, -1D);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90F));
        } else if (blockDirection.equals(Direction.DOWN)) {
            matrices.translate(0D, 1D, 1D);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90F));
        }
        base.render(matrices, vertexConsumer, light, overlay);

        if (entity.getCachedState().get(DrillBlock.POWERED)) {
            head.roll = entity.getRotation(tickDelta);
            if (entity.getWorld() != null) {
                BlockState lookingState = entity.getWorld().getBlockState(entity.getPos().offset(blockDirection));
                if (entity.getWorld().getRandom().nextFloat() <= 0.03f && !lookingState.isAir() && lookingState.isFullCube(entity.getWorld(), entity.getPos().offset(blockDirection))) {
                    entity.getWorld().addBlockBreakParticles(entity.getPos().offset(blockDirection), lookingState);
                }
            }
        } else {
            head.roll = 0.0f;
        }
        head.render(matrices, vertexConsumer, light, overlay);
        matrices.pop();
    }
}
