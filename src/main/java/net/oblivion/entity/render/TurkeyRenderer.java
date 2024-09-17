package net.oblivion.entity.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.oblivion.OblivionMain;
import net.oblivion.entity.Turkey;
import net.oblivion.entity.model.TurkeyModel;
import net.oblivion.init.RenderInit;

@Environment(EnvType.CLIENT)
public class TurkeyRenderer extends MobEntityRenderer<Turkey, TurkeyModel<Turkey>> {
    private static final Identifier TEXTURE = OblivionMain.identifierOf("textures/entity/turkey.png");

    public TurkeyRenderer(EntityRendererFactory.Context context) {
        super(context, new TurkeyModel<>(context.getPart(RenderInit.TURKEY_LAYER)), 0.6F);
    }

    @Override
    public void scale(Turkey entity, MatrixStack matrixStack, float f) {
        matrixStack.scale(1.2F, 1.2F, 1.2F);
    }

    @Override
    public Identifier getTexture(Turkey entity) {
        return TEXTURE;
    }
}
