package net.oblivion.init;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.oblivion.OblivionMain;
import net.oblivion.block.render.DrillBlockEntityRenderer;
import net.oblivion.block.render.GuidelightBlockEntityRenderer;
import net.oblivion.entity.model.*;
import net.oblivion.entity.render.*;
import net.oblivion.entity.render.feature.HornedSheepHornsModel;

@Environment(EnvType.CLIENT)
public class RenderInit {

    public static final EntityModelLayer SHLAMA_LAYER = new EntityModelLayer(OblivionMain.identifierOf("shlama_render_layer"), "shlama_render_layer");
    public static final EntityModelLayer ELYSIAN_ELK_LAYER = new EntityModelLayer(OblivionMain.identifierOf("elysian_elk_render_layer"), "elysian_elk_render_layer");
    public static final EntityModelLayer ELYSIAN_SHAMAN_LAYER = new EntityModelLayer(OblivionMain.identifierOf("elysian_shaman_render_layer"), "elysian_shaman_render_layer");
    public static final EntityModelLayer ELYSIAN_WOLF_LAYER = new EntityModelLayer(OblivionMain.identifierOf("elysian_wolf_render_layer"), "elysian_wolf_render_layer");
    public static final EntityModelLayer GOBLIN_LAYER = new EntityModelLayer(OblivionMain.identifierOf("goblin_render_layer"), "goblin_render_layer");
    public static final EntityModelLayer TREEDER_LAYER = new EntityModelLayer(OblivionMain.identifierOf("treeder_render_layer"), "treeder_render_layer");
    public static final EntityModelLayer SHROOM_LAYER = new EntityModelLayer(OblivionMain.identifierOf("shroom_render_layer"), "shroom_render_layer");
    public static final EntityModelLayer ELK_LAYER = new EntityModelLayer(OblivionMain.identifierOf("elk_render_layer"), "elk_render_layer");
    public static final EntityModelLayer BOAR_LAYER = new EntityModelLayer(OblivionMain.identifierOf("boar_render_layer"), "boar_render_layer");
    public static final EntityModelLayer TURKEY_LAYER = new EntityModelLayer(OblivionMain.identifierOf("turkey_render_layer"), "turkey_render_layer");
    public static final EntityModelLayer WOOLY_COW_LAYER = new EntityModelLayer(OblivionMain.identifierOf("wooly_cow_render_layer"), "wooly_cow_render_layer");
    public static final EntityModelLayer HORNED_SHEEP_LAYER = new EntityModelLayer(OblivionMain.identifierOf("horned_sheep_render_layer"), "horned_sheep_render_layer");

    public static final EntityModelLayer DRILL_LAYER = new EntityModelLayer(OblivionMain.identifierOf("drill_render_layer"), "drill_render_layer");

    public static void init() {
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.IRON_WOOD_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.IRON_WOOD_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.IRON_WOOD_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.POTTED_IRON_WOOD_SAPLING, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.IRON_WOOD_LEAVES, RenderLayer.getCutoutMipped());

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.SILVER_BIRCH_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.SILVER_BIRCH_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.SILVER_BIRCH_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.POTTED_SILVER_BIRCH_SAPLING, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.SILVER_BIRCH_LEAVES, RenderLayer.getCutoutMipped());

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.RUNE_WOOD_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.RUNE_WOOD_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.RUNE_WOOD_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.POTTED_RUNE_WOOD_SAPLING, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.RUNE_WOOD_LEAVES, RenderLayer.getCutoutMipped());

        EntityModelLayerRegistry.registerModelLayer(DRILL_LAYER, DrillBlockEntityRenderer::getTexturedModelData);

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.DRILL, RenderLayer.getCutout());
        BlockEntityRendererFactories.register(BlockInit.DRILL_BLOCK_ENTITY, DrillBlockEntityRenderer::new);

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.GUIDELIGHT, RenderLayer.getCutout());
        BlockEntityRendererFactories.register(BlockInit.GUIDELIGHT_BLOCK_ENTITY, GuidelightBlockEntityRenderer::new);

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.SHIMMERING_GRASS, RenderLayer.getCutout());

        // Entity Renderer
        EntityRendererRegistry.register(EntityInit.SHLAMA, ShlamaRenderer::new);
        EntityRendererRegistry.register(EntityInit.ELYSIAN_ELK, ElysianElkRenderer::new);
        EntityRendererRegistry.register(EntityInit.ELYSIAN_SHAMAN, ElysianShamanRenderer::new);
        EntityRendererRegistry.register(EntityInit.ELYSIAN_WOLF, ElysianWolfRenderer::new);
        EntityRendererRegistry.register(EntityInit.GOBLIN, GoblinRenderer::new);
        EntityRendererRegistry.register(EntityInit.TREEDER, TreederRenderer::new);
        EntityRendererRegistry.register(EntityInit.SHROOM, ShroomRenderer::new);
        EntityRendererRegistry.register(EntityInit.ELK, ElkRenderer::new);
        EntityRendererRegistry.register(EntityInit.BOAR, BoarRenderer::new);
        EntityRendererRegistry.register(EntityInit.TURKEY, TurkeyRenderer::new);
        EntityRendererRegistry.register(EntityInit.WOOLY_COW, WoolyCowRenderer::new);
        EntityRendererRegistry.register(EntityInit.PIRANHA, PiranhaRenderer::new);
        EntityRendererRegistry.register(EntityInit.HORNED_SHEEP, HornedSheepRenderer::new);
        // Entity Model
        EntityModelLayerRegistry.registerModelLayer(SHLAMA_LAYER, ShlamaModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ELYSIAN_ELK_LAYER, ElysianElkModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ELYSIAN_SHAMAN_LAYER, ElysianShamanModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ELYSIAN_WOLF_LAYER, ElysianWolfModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(GOBLIN_LAYER, GoblinModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(TREEDER_LAYER, TreederModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(SHROOM_LAYER, ShroomModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ELK_LAYER, ElkModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(BOAR_LAYER, BoarModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(TURKEY_LAYER, TurkeyModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(WOOLY_COW_LAYER, WoolyCowModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(HORNED_SHEEP_LAYER, HornedSheepHornsModel::getTexturedModelData);
    }
}
