package net.oblivion.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.*;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.oblivion.init.BlockInit;
import net.oblivion.init.EntityInit;
import net.oblivion.init.ItemInit;

public class ModelLoader extends FabricModelProvider {

    public ModelLoader(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        for (int i = 0; i < EntityInit.SPAWN_EGGS.size(); i++) {
            blockStateModelGenerator.registerParentedItemModel(EntityInit.SPAWN_EGGS.get(i), ModelIds.getMinecraftNamespacedItem("template_spawn_egg"));
        }
        for (int i = 0; i < BlockInit.DATAGEN_BLOCKS.size(); i++) {
            blockStateModelGenerator.registerSimpleCubeAll(BlockInit.DATAGEN_BLOCKS.get(i));
            blockStateModelGenerator.registerParentedItemModel(BlockInit.DATAGEN_BLOCKS.get(i), ModelIds.getBlockModelId(BlockInit.DATAGEN_BLOCKS.get(i)));
        }
        blockStateModelGenerator.registerSingleton(BlockInit.FIERY_ANCIENT_DEBRIS, TexturedModel.CUBE_COLUMN);

        Identifier identifier = blockStateModelGenerator.createSubModel(BlockInit.SHIMMERING_GRASS, "", Models.CROSS, TextureMap::cross);
        Identifier identifier2 = blockStateModelGenerator.createSubModel(BlockInit.SHIMMERING_GRASS, "_lit", Models.CROSS, TextureMap::cross);
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(BlockInit.SHIMMERING_GRASS).coordinate(BlockStateModelGenerator.createBooleanModelMap(Properties.LIT, identifier2, identifier)));
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        for (int i = 0; i < ItemInit.ITEMS.size(); i++) {
            itemModelGenerator.register(ItemInit.ITEMS.get(i), Models.GENERATED);
        }
        for (int i = 0; i < ItemInit.TOOLS.size(); i++) {
            itemModelGenerator.register(ItemInit.TOOLS.get(i), Models.HANDHELD);
        }
    }
}

