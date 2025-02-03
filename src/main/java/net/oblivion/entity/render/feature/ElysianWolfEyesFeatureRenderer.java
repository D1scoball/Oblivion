package net.oblivion.entity.render.feature;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.oblivion.OblivionMain;
import net.oblivion.entity.ElysianWolf;
import net.oblivion.entity.model.ElysianWolfModel;

@Environment(EnvType.CLIENT)
public class ElysianWolfEyesFeatureRenderer extends FeatureRenderer<ElysianWolf, ElysianWolfModel<ElysianWolf>> {
    private static final RenderLayer EYE_LAYER = RenderLayer.getEntityAlpha(OblivionMain.identifierOf("textures/entity/feature/elysian_wolf_eyes_feature.png"));

    public ElysianWolfEyesFeatureRenderer(FeatureRendererContext<ElysianWolf, ElysianWolfModel<ElysianWolf>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, ElysianWolf elysianWolf, float f, float g, float h, float j, float k, float l) {
        if (elysianWolf.getHealth() < (elysianWolf.getMaxHealth() / 2)) {
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(EYE_LAYER);
            this.getContextModel().render(matrixStack, vertexConsumer, 15728640, OverlayTexture.DEFAULT_UV);
        }
    }

}