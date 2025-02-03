package net.oblivion.entity.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.oblivion.OblivionMain;
import net.oblivion.entity.ElysianWolf;
import net.oblivion.entity.model.ElysianWolfModel;
import net.oblivion.entity.render.feature.ElysianWolfEyesFeatureRenderer;
import net.oblivion.init.RenderInit;

@Environment(EnvType.CLIENT)
public class ElysianWolfRenderer extends MobEntityRenderer<ElysianWolf, ElysianWolfModel<ElysianWolf>> {
    private static final Identifier TEXTURE = OblivionMain.identifierOf("textures/entity/elysian_wolf.png");

    public ElysianWolfRenderer(EntityRendererFactory.Context context) {
        super(context, new ElysianWolfModel<>(context.getPart(RenderInit.ELYSIAN_WOLF_LAYER)), 1.3F);
        this.addFeature(new ElysianWolfEyesFeatureRenderer(this));
    }

    @Override
    public Identifier getTexture(ElysianWolf wolf) {
        return TEXTURE;
    }
}
