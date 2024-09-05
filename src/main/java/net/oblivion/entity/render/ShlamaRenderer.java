package net.oblivion.entity.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.oblivion.OblivionMain;
import net.oblivion.entity.Shlama;
import net.oblivion.entity.model.ShlamaModel;
import net.oblivion.init.RenderInit;

@Environment(EnvType.CLIENT)
public class ShlamaRenderer extends MobEntityRenderer<Shlama, ShlamaModel<Shlama>> {
    private static final Identifier TEXTURE = OblivionMain.identifierOf("textures/entity/shlama.png");

    public ShlamaRenderer(EntityRendererFactory.Context context) {
        super(context, new ShlamaModel<>(context.getPart(RenderInit.SHLAMA_LAYER)), 1.0F);
    }

    @Override
    public Identifier getTexture(Shlama shlama) {
        return TEXTURE;
    }
}
