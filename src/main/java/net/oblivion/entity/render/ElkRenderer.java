package net.oblivion.entity.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.oblivion.OblivionMain;
import net.oblivion.entity.Elk;
import net.oblivion.entity.model.ElkModel;
import net.oblivion.init.RenderInit;

@Environment(EnvType.CLIENT)
public class ElkRenderer extends MobEntityRenderer<Elk, ElkModel<Elk>> {
    private static final Identifier TEXTURE = OblivionMain.identifierOf("textures/entity/elk.png");

    public ElkRenderer(EntityRendererFactory.Context context) {
        super(context, new ElkModel<>(context.getPart(RenderInit.ELYSIAN_ELK_LAYER)), 0.8F);
    }

    @Override
    public void scale(Elk entity, MatrixStack matrixStack, float f) {
        matrixStack.scale(1.3F, 1.3F, 1.3F);
    }

    @Override
    public Identifier getTexture(Elk elk) {
        return TEXTURE;
    }
}
