package net.oblivion.block.render;

import java.util.List;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BeaconBlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.EnderDragonEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.oblivion.OblivionMain;
import net.oblivion.block.entity.GuidelightBlockEntity;

@Environment(EnvType.CLIENT)
public class GuidelightBlockEntityRenderer implements BlockEntityRenderer<GuidelightBlockEntity> {

    private static final Identifier TEXTURE = OblivionMain.identifierOf("textures/entity/guidelight_beam.png");

    public GuidelightBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
    }

    @Override
    public void render(GuidelightBlockEntity guidelightBlockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int light, int overlay) {
        if (guidelightBlockEntity.isActive()) {//|| guidelightBlockEntity.getInitialActivationTick() > 0
            long worldTime = guidelightBlockEntity.getWorld().getTime();
            float innerRadius = Math.max(0.3f, 0.3f + (float) guidelightBlockEntity.getTeleportTick() / (float) GuidelightBlockEntity.TELEPORT_TICKS * 2.7f);
//            int extraMaxY = (int) ((float) guidelightBlockEntity.getInitialActivationTick() / (float) GuidelightBlockEntity.ACTIVATION_TICKS * 1024f);
            float speed = Math.max(1f,1f + (float) guidelightBlockEntity.getTeleportTick() / (float) GuidelightBlockEntity.TELEPORT_TICKS * 8f);

            float heightScale = 1.0f;
            float outerRadius = 3f;
            int color = 16698342;

            int height = 1024;
            matrices.push();
            matrices.translate(0.5, 0.0, 0.5);
            float f = (float) Math.floorMod(worldTime, 40) + tickDelta;
            float g = -f;
            float h = MathHelper.fractionalPart(g * 0.2F - (float) MathHelper.floor(g * 0.1F));

            matrices.push();
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(f * 2.25F - 45.0F));

            float n = -innerRadius;
            float q = -innerRadius;
            float t = -1.0F + h;
            float u = (float) height * heightScale * (0.5F / innerRadius) + t;

            // small beam
            renderGuidelightLayer(matrices, vertexConsumerProvider.getBuffer(RenderLayer.getBeaconBeam(TEXTURE, false)), color, 0, height, speed, 0.0F, innerRadius, innerRadius, 0.0F, n, 0.0F, 0.0F, q, 0.0F, 1.0F, u, t);
            matrices.pop();

            if (guidelightBlockEntity.getTeleportTick() >= 0) {
                float j = -outerRadius;
                float k = -outerRadius;
                float m = -outerRadius;
                n = -outerRadius;
                t = -1.0F + h;
                u = (float) height * heightScale + t;
                // radius beam
                for (int i = 0; i < 16; i++) {
                    renderGuidelightLayer(matrices, vertexConsumerProvider.getBuffer(RenderLayer.getBeaconBeam(TEXTURE, true)), ColorHelper.Argb.withAlpha(16 - i, color), i, i + 1, speed, j, k, outerRadius, m, n, outerRadius, outerRadius, outerRadius, 0.0F, 1.0F, u, t);
                }
            }

            matrices.pop();
        }
    }

    private static void renderGuidelightLayer(MatrixStack matrices, VertexConsumer vertices, int color, int yOffset, int height, float speed, float x1, float z1, float x2, float z2, float x3, float z3, float x4, float z4, float u1, float u2, float v1, float v2) {
        MatrixStack.Entry entry = matrices.peek();
        renderGuidelightFace(entry, vertices, color, yOffset, height, speed, x1, z1, x2, z2, u1, u2, v1, v2);
        renderGuidelightFace(entry, vertices, color, yOffset, height, speed, x4, z4, x3, z3, u1, u2, v1, v2);
        renderGuidelightFace(entry, vertices, color, yOffset, height, speed, x2, z2, x4, z4, u1, u2, v1, v2);
        renderGuidelightFace(entry, vertices, color, yOffset, height, speed, x3, z3, x1, z1, u1, u2, v1, v2);
    }

    private static void renderGuidelightFace(MatrixStack.Entry matrix, VertexConsumer vertices, int color, int yOffset, int height, float speed, float x1, float z1, float x2, float z2, float u1, float u2, float v1, float v2) {
        renderGuidelightVertex(matrix, vertices, color, (int) (height * speed), x1, z1, u2, v1);
        renderGuidelightVertex(matrix, vertices, color, (int) (yOffset * speed), x1, z1, u2, v2);
        renderGuidelightVertex(matrix, vertices, color, (int) (yOffset * speed), x2, z2, u1, v2);
        renderGuidelightVertex(matrix, vertices, color, (int) (height * speed), x2, z2, u1, v1);
    }

    private static void renderGuidelightVertex(MatrixStack.Entry matrix, VertexConsumer vertices, int color, int y, float x, float z, float u, float v) {
        vertices.vertex(matrix, x, (float) y, z).color(color).texture(u, v).overlay(OverlayTexture.DEFAULT_UV).light(LightmapTextureManager.MAX_LIGHT_COORDINATE).normal(matrix, 0.0F, 1.0F, 0.0F);
    }

    @Override
    public boolean rendersOutsideBoundingBox(GuidelightBlockEntity guidelightBlockEntity) {
        return true;
    }

    @Override
    public int getRenderDistance() {
        return 256;
    }

    @Override
    public boolean isInRenderDistance(GuidelightBlockEntity guidelightBlockEntity, Vec3d vec3d) {
        return Vec3d.ofCenter(guidelightBlockEntity.getPos()).multiply(1.0, 0.0, 1.0).isInRange(vec3d.multiply(1.0, 0.0, 1.0), this.getRenderDistance());
    }
}

