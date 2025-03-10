package net.oblivion;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.oblivion.data.ModelLoader;
import net.oblivion.data.TagLoader;

public class OblivionDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(ModelLoader::new);
        pack.addProvider(TagLoader.BlockTagLoader::new);
        pack.addProvider(TagLoader.ItemTagLoader::new);
    }
}

