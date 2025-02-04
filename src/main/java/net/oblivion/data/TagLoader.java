package net.oblivion.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.oblivion.init.BlockInit;

import java.util.concurrent.CompletableFuture;

public class TagLoader extends FabricTagProvider.BlockTagProvider {

    public TagLoader(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        for (Block block : BlockInit.WOOD_BLOCKS) {
            getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(block);
        }
        FabricTagBuilder pickaxeMineable = getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE);
        pickaxeMineable.add(BlockInit.FIERY_NETHERITE_BLOCK);
        pickaxeMineable.add(BlockInit.DRILL);
        pickaxeMineable.add(BlockInit.FIERY_NETHERITE_ORE);
    }
}

