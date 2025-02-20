package net.oblivion.entity.render.feature;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.SheepEntityModel;
import net.minecraft.client.render.entity.model.SheepWoolEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.Identifier;
import net.oblivion.OblivionMain;
import net.oblivion.entity.HornedSheep;
import net.oblivion.init.RenderInit;
import net.oblivion.mixin.client.QuadrupedEntityModelAccess;

@Environment(EnvType.CLIENT)
public class HornedSheepWoolFeatureRenderer extends FeatureRenderer<HornedSheep, SheepEntityModel<HornedSheep>> {
    private static final Identifier SKIN = Identifier.ofVanilla("textures/entity/sheep/sheep_fur.png");
    private static final Identifier HORNS = OblivionMain.identifierOf("textures/entity/feature/horned_sheep_horns.png");
    private final SheepWoolEntityModel<HornedSheep> model;
    private final HornedSheepHornsModel<HornedSheep> horns;

    public HornedSheepWoolFeatureRenderer(FeatureRendererContext<HornedSheep, SheepEntityModel<HornedSheep>> context, EntityModelLoader loader) {
        super(context);
        this.model = new SheepWoolEntityModel<>(loader.getModelPart(EntityModelLayers.SHEEP_FUR));
        this.horns = new HornedSheepHornsModel<>(loader.getModelPart(RenderInit.HORNED_SHEEP_LAYER));
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, HornedSheep sheepEntity, float f, float g, float h, float j, float k, float l) {
        if (!sheepEntity.isSheared()) {
            if (sheepEntity.isInvisible()) {
                MinecraftClient minecraftClient = MinecraftClient.getInstance();
                boolean bl = minecraftClient.hasOutline(sheepEntity);
                if (bl) {
                    this.getContextModel().copyStateTo(this.model);
                    this.model.animateModel(sheepEntity, f, g, h);
                    this.model.setAngles(sheepEntity, f, g, j, k, l);
                    VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getOutline(SKIN));
                    this.model.render(matrixStack, vertexConsumer, i, LivingEntityRenderer.getOverlay(sheepEntity, 0.0F), -16777216);
                }
            } else {
                render(this.getContextModel(), this.model, SKIN, matrixStack, vertexConsumerProvider, i, sheepEntity, f, g, j, k, l, h, SheepEntity.getRgbColor(sheepEntity.getColor()));
            }
        }
        if (!sheepEntity.isInvisible()) {
            matrixStack.push();
            ModelPart modelPart =  ((QuadrupedEntityModelAccess) this.getContextModel()).getHead();
            modelPart.rotate(matrixStack);
            render(this.getContextModel(), this.horns, HORNS, matrixStack, vertexConsumerProvider, i, sheepEntity, f, g, j, k, l, h, SheepEntity.getRgbColor(sheepEntity.getColor()));
            matrixStack.pop();
        }

    }
}

