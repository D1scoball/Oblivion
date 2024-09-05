package net.oblivion.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.ModelIds;
import net.oblivion.init.EntityInit;

public class ModelLoader extends FabricModelProvider {

    public ModelLoader(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        for (int i = 0; i < EntityInit.SPAWN_EGGS.size(); i++) {
            blockStateModelGenerator.registerParentedItemModel(EntityInit.SPAWN_EGGS.get(i),ModelIds.getMinecraftNamespacedItem("template_spawn_egg"));
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
//        for (int i = 0; i < EntityInit.SPAWN_EGGS.size(); i++) {
////SpawnEggItem.getAll().forEach(item -> this.registerParentedItemModel(item, ModelIds.getMinecraftNamespacedItem("template_spawn_egg")));
//                itemModelGenerator.register(EntityInit.SPAWN_EGGS.get(i), Models.TEMPL);
//            itemModelGenerator.regist
//            }//"parent": "item/template_spawn_egg"

    }
}

