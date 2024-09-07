package net.oblivion.entity.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.oblivion.OblivionMain;
import net.oblivion.entity.Goblin;
import net.oblivion.entity.model.GoblinModel;
import net.oblivion.init.RenderInit;

@Environment(EnvType.CLIENT)
public class GoblinRenderer extends MobEntityRenderer<Goblin, GoblinModel<Goblin>> {
    private static final Identifier TEXTURE = OblivionMain.identifierOf("textures/entity/goblin.png");

    public GoblinRenderer(EntityRendererFactory.Context context) {
        super(context, new GoblinModel<>(context.getPart(RenderInit.GOBLIN_LAYER)), 0.5F);
    }

    @Override
    public Identifier getTexture(Goblin goblin) {
        return TEXTURE;
    }
}
