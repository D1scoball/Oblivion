package net.oblivion.entity.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.oblivion.OblivionMain;
import net.oblivion.entity.Treeder;
import net.oblivion.entity.model.TreederModel;
import net.oblivion.init.RenderInit;

@Environment(EnvType.CLIENT)
public class TreederRenderer extends MobEntityRenderer<Treeder, TreederModel<Treeder>> {
    private static final Identifier TEXTURE = OblivionMain.identifierOf("textures/entity/treeder.png");

    public TreederRenderer(EntityRendererFactory.Context context) {
        super(context, new TreederModel<>(context.getPart(RenderInit.TREEDER_LAYER)), 0.2F);
    }

    @Override
    public Identifier getTexture(Treeder treeder) {
        return TEXTURE;
    }
}
