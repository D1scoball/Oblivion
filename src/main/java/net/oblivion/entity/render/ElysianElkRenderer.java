package net.oblivion.entity.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.oblivion.OblivionMain;
import net.oblivion.entity.ElysianElk;
import net.oblivion.entity.model.ElysianElkModel;
import net.oblivion.init.RenderInit;

@Environment(EnvType.CLIENT)
public class ElysianElkRenderer extends MobEntityRenderer<ElysianElk, ElysianElkModel<ElysianElk>> {
    private static final Identifier TEXTURE = OblivionMain.identifierOf("textures/entity/elysian_elk.png");

    public ElysianElkRenderer(EntityRendererFactory.Context context) {
        super(context, new ElysianElkModel<>(context.getPart(RenderInit.ELYSIAN_ELK_LAYER)), 1.2F);
    }

    @Override
    public void scale(ElysianElk entity, MatrixStack matrixStack, float f) {
        matrixStack.scale(2.0F, 2.0F, 2.0F);
    }

    @Override
    public Identifier getTexture(ElysianElk elk) {
        return TEXTURE;
    }
}
