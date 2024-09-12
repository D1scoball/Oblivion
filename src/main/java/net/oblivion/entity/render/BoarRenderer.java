package net.oblivion.entity.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.oblivion.OblivionMain;
import net.oblivion.entity.Boar;
import net.oblivion.entity.model.BoarModel;
import net.oblivion.init.RenderInit;

@Environment(EnvType.CLIENT)
public class BoarRenderer extends MobEntityRenderer<Boar, BoarModel<Boar>> {
    private static final Identifier TEXTURE = OblivionMain.identifierOf("textures/entity/boar.png");

    public BoarRenderer(EntityRendererFactory.Context context) {
        super(context, new BoarModel<>(context.getPart(RenderInit.BOAR_LAYER)), 0.7F);
    }

    @Override
    public void scale(Boar entity, MatrixStack matrixStack, float f) {
        matrixStack.scale(1.2F, 1.2F, 1.2F);
    }

    @Override
    public Identifier getTexture(Boar boar) {
        return TEXTURE;
    }
}
