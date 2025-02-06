package net.oblivion.entity.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.oblivion.OblivionMain;
import net.oblivion.entity.WoolyCow;
import net.oblivion.entity.model.WoolyCowModel;
import net.oblivion.init.RenderInit;

@Environment(EnvType.CLIENT)
public class WoolyCowRenderer extends MobEntityRenderer<WoolyCow, WoolyCowModel<WoolyCow>> {
    private static final Identifier TEXTURE = OblivionMain.identifierOf("textures/entity/wooly_cow.png");

    public WoolyCowRenderer(EntityRendererFactory.Context context) {
        super(context, new WoolyCowModel<>(context.getPart(RenderInit.WOOLY_COW_LAYER)), 0.7F);
    }

    @Override
    public Identifier getTexture(WoolyCow woolyCow) {
        return TEXTURE;
    }
}
