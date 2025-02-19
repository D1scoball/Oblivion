package net.oblivion.entity.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.LargeTropicalFishEntityModel;
import net.minecraft.client.render.entity.model.TintableCompositeModel;
import net.minecraft.util.Identifier;
import net.oblivion.OblivionMain;
import net.oblivion.entity.Piranha;

@Environment(EnvType.CLIENT)
public class PiranhaRenderer extends MobEntityRenderer<Piranha, TintableCompositeModel<Piranha>> {

    private static final Identifier TEXTURE = OblivionMain.identifierOf("textures/entity/piranha.png");

    public PiranhaRenderer(EntityRendererFactory.Context context) {
        super(context, new LargeTropicalFishEntityModel<>(context.getPart(EntityModelLayers.TROPICAL_FISH_LARGE)), 0.15F);
    }

    @Override
    public Identifier getTexture(Piranha entity) {
        return TEXTURE;
    }
}
