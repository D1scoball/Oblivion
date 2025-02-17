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
            FabricTagBuilder runeWood = getOrCreateTagBuilder(TagInit.RUNE_WOOD);
            for (Map.Entry<Block, Integer> entry : BlockInit.WOOD_BLOCKS.entrySet()) {
                if (entry.getValue() == 2) {
                    runeWood.add(entry.getKey());
                }
            }
            FabricTagBuilder pickaxeMineable = getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE);
            pickaxeMineable.add(BlockInit.FIERY_NETHERITE_BLOCK);
            pickaxeMineable.add(BlockInit.DRILL);
            pickaxeMineable.add(BlockInit.FIERY_ANCIENT_DEBRIS);
            pickaxeMineable.add(BlockInit.SCARLET_BLOCK);
            pickaxeMineable.add(BlockInit.RAW_SCARLET_BLOCK);
            pickaxeMineable.add(BlockInit.SOLARITE_BLOCK);
            pickaxeMineable.add(BlockInit.RAW_SOLARITE_BLOCK);

            FabricTagBuilder shovelMineable = getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE);
            shovelMineable.add(BlockInit.QUICKSAND);
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
            pickaxes.add(ItemInit.SCARLET_PICKAXE);
            pickaxes.add(ItemInit.SOLARITE_PICKAXE);

            FabricTagBuilder axes = getOrCreateTagBuilder(ItemTags.AXES);
            axes.add(ItemInit.FIERY_NETHERITE_AXE);
            axes.add(ItemInit.SCARLET_AXE);
            axes.add(ItemInit.SOLARITE_AXE);

            FabricTagBuilder shovels = getOrCreateTagBuilder(ItemTags.SHOVELS);
            shovels.add(ItemInit.FIERY_NETHERITE_SHOVEL);
            shovels.add(ItemInit.SCARLET_SHOVEL);
            shovels.add(ItemInit.SOLARITE_SHOVEL);

            FabricTagBuilder hoes = getOrCreateTagBuilder(ItemTags.HOES);
            hoes.add(ItemInit.FIERY_NETHERITE_HOE);
            hoes.add(ItemInit.SCARLET_HOE);
            hoes.add(ItemInit.SOLARITE_HOE);

            FabricTagBuilder swords = getOrCreateTagBuilder(ItemTags.SWORDS);
            swords.add(ItemInit.FIERY_NETHERITE_SWORD);
            swords.add(ItemInit.SCARLET_SWORD);
            swords.add(ItemInit.SOLARITE_SWORD);

            FabricTagBuilder head = getOrCreateTagBuilder(ItemTags.HEAD_ARMOR);
            head.add(ItemInit.FIERY_NETHERITE_HELMET);
            head.add(ItemInit.SCARLET_HELMET);
            head.add(ItemInit.SOLARITE_HELMET);

            FabricTagBuilder chestplate = getOrCreateTagBuilder(ItemTags.CHEST_ARMOR);
            chestplate.add(ItemInit.FIERY_NETHERITE_CHESTPLATE);
            chestplate.add(ItemInit.SCARLET_CHESTPLATE);
            chestplate.add(ItemInit.SOLARITE_CHESTPLATE);

            FabricTagBuilder leggings = getOrCreateTagBuilder(ItemTags.LEG_ARMOR);
            leggings.add(ItemInit.FIERY_NETHERITE_LEGGINGS);
            leggings.add(ItemInit.SCARLET_LEGGINGS);
            leggings.add(ItemInit.SOLARITE_LEGGINGS);

            FabricTagBuilder boots = getOrCreateTagBuilder(ItemTags.FOOT_ARMOR);
            boots.add(ItemInit.FIERY_NETHERITE_BOOTS);
            boots.add(ItemInit.SCARLET_BOOTS);
            boots.add(ItemInit.SOLARITE_BOOTS);
        }
    }
}

