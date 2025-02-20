package net.oblivion.entity.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.SheepEntityModel;
import net.minecraft.util.Identifier;
import net.oblivion.entity.HornedSheep;
import net.oblivion.entity.render.feature.HornedSheepWoolFeatureRenderer;

@Environment(EnvType.CLIENT)
public class HornedSheepRenderer extends MobEntityRenderer<HornedSheep, SheepEntityModel<HornedSheep>> {

    private static final Identifier TEXTURE = Identifier.ofVanilla("textures/entity/sheep/sheep.png");

    public HornedSheepRenderer(EntityRendererFactory.Context context) {
        super(context, new SheepEntityModel<>(context.getPart(EntityModelLayers.SHEEP)), 0.7F);
        this.addFeature(new HornedSheepWoolFeatureRenderer(this, context.getModelLoader()));
    }

    public Identifier getTexture(HornedSheep sheepEntity) {
        return TEXTURE;
    }
}



