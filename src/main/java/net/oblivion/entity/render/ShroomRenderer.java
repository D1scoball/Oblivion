package net.oblivion.entity.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.oblivion.OblivionMain;
import net.oblivion.entity.Shroom;
import net.oblivion.entity.model.ShroomModel;
import net.oblivion.init.RenderInit;

@Environment(EnvType.CLIENT)
public class ShroomRenderer extends MobEntityRenderer<Shroom, ShroomModel<Shroom>> {
    private static final Identifier TEXTURE = OblivionMain.identifierOf("textures/entity/shroom.png");

    public ShroomRenderer(EntityRendererFactory.Context context) {
        super(context, new ShroomModel<>(context.getPart(RenderInit.SHROOM_LAYER)), 0.6F);
    }

    @Override
    protected void scale(Shroom entity, MatrixStack matrices, float amount) {
        matrices.scale(0.7F + 0.13F * entity.getSize(), 0.7F + 0.13F * entity.getSize(), 0.7F + 0.13F * entity.getSize());
    }

    @Override
    public Identifier getTexture(Shroom shroom) {
        return TEXTURE;
    }
}
