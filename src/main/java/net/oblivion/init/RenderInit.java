package net.oblivion.init;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.oblivion.OblivionMain;
import net.oblivion.entity.model.*;
import net.oblivion.entity.render.*;

@Environment(EnvType.CLIENT)
public class RenderInit {

    public static final EntityModelLayer SHLAMA_LAYER = new EntityModelLayer(OblivionMain.id("shlama_render_layer"), "shlama_render_layer");
    public static final EntityModelLayer ELYSIAN_ELK_LAYER = new EntityModelLayer(OblivionMain.id("elysian_elk_render_layer"), "elysian_elk_render_layer");

    public static void init(){
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.IRON_WOOD_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.IRON_WOOD_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.IRON_WOOD_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.POTTED_IRON_WOOD_SAPLING, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.IRON_WOOD_LEAVES, RenderLayer.getCutoutMipped());

        // Entity Renderer
        EntityRendererRegistry.register(EntityInit.SHLAMA, ShlamaRenderer::new);
        EntityRendererRegistry.register(EntityInit.ELYSIAN_ELK, ElysianElkRenderer::new);
        // Entity Model
        EntityModelLayerRegistry.registerModelLayer(SHLAMA_LAYER, ShlamaModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ELYSIAN_ELK_LAYER, ElysianElkModel::getTexturedModelData);
    }
}
