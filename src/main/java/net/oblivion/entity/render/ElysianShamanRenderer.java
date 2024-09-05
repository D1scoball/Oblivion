package net.oblivion.entity.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.oblivion.OblivionMain;
import net.oblivion.entity.ElysianShaman;
import net.oblivion.entity.model.ElysianShamanModel;
import net.oblivion.init.RenderInit;

@Environment(EnvType.CLIENT)
public class ElysianShamanRenderer extends MobEntityRenderer<ElysianShaman, ElysianShamanModel<ElysianShaman>> {
    private static final Identifier TEXTURE = OblivionMain.identifierOf("textures/entity/elysian_shaman.png");

    public ElysianShamanRenderer(EntityRendererFactory.Context context) {
        super(context, new ElysianShamanModel<>(context.getPart(RenderInit.ELYSIAN_SHAMAN_LAYER)), 0.7F);
    }

    @Override
    public Identifier getTexture(ElysianShaman shaman) {
        return TEXTURE;
    }
}
