package net.oblivion.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.oblivion.init.BlockInit;
import net.oblivion.init.ItemInit;
import net.oblivion.init.TagInit;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class TagLoader {

    public static class BlockTagLoader extends FabricTagProvider.BlockTagProvider {

        public BlockTagLoader(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            FabricTagBuilder axeMineable = getOrCreateTagBuilder(BlockTags.AXE_MINEABLE);
            for (Block block : BlockInit.WOOD_BLOCKS.keySet()) {
                axeMineable.add(block);
            }
            FabricTagBuilder ironWood = getOrCreateTagBuilder(TagInit.IRON_WOOD);
            for (Map.Entry<Block, Integer> entry : BlockInit.WOOD_BLOCKS.entrySet()) {
                if (entry.getValue() == 0) {
                    ironWood.add(entry.getKey());
                }
            }
            FabricTagBuilder pickaxeMineable = getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE);
            pickaxeMineable.add(BlockInit.FIERY_NETHERITE_BLOCK);
            pickaxeMineable.add(BlockInit.DRILL);
            pickaxeMineable.add(BlockInit.FIERY_NETHERITE_ORE);
        }
    }

    public static class ItemTagLoader extends FabricTagProvider.ItemTagProvider {

        public ItemTagLoader(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            FabricTagBuilder pickaxes = getOrCreateTagBuilder(ItemTags.PICKAXES);
            pickaxes.add(ItemInit.FIERY_NETHERITE_PICKAXE);

            FabricTagBuilder axes = getOrCreateTagBuilder(ItemTags.AXES);
            axes.add(ItemInit.FIERY_NETHERITE_AXE);

            FabricTagBuilder shovels = getOrCreateTagBuilder(ItemTags.SHOVELS);
            shovels.add(ItemInit.FIERY_NETHERITE_SHOVEL);

            FabricTagBuilder hoes = getOrCreateTagBuilder(ItemTags.HOES);
            hoes.add(ItemInit.FIERY_NETHERITE_HOE);

            FabricTagBuilder swords = getOrCreateTagBuilder(ItemTags.SWORDS);
            swords.add(ItemInit.FIERY_NETHERITE_SWORD);

            FabricTagBuilder head = getOrCreateTagBuilder(ItemTags.HEAD_ARMOR);
            head.add(ItemInit.FIERY_NETHERITE_HELMET);

            FabricTagBuilder chestplate = getOrCreateTagBuilder(ItemTags.CHEST_ARMOR);
            chestplate.add(ItemInit.FIERY_NETHERITE_CHESTPLATE);

            FabricTagBuilder leggings = getOrCreateTagBuilder(ItemTags.LEG_ARMOR);
            leggings.add(ItemInit.FIERY_NETHERITE_LEGGINGS);

            FabricTagBuilder boots = getOrCreateTagBuilder(ItemTags.FOOT_ARMOR);
            boots.add(ItemInit.FIERY_NETHERITE_BOOTS);
        }
    }
}

