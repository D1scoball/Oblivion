package net.oblivion.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.oblivion.OblivionMain;

public class TagInit {

    // Block
    public static final TagKey<Block> GUIDELIGHT_BASE_BLOCKS = TagKey.of(RegistryKeys.BLOCK, OblivionMain.identifierOf("guidelight_base"));

    public static final TagKey<Block> INCORRECT_FOR_FIERY_NETHERITE_TOOL = TagKey.of(RegistryKeys.BLOCK, OblivionMain.identifierOf("incorrect_for_fiery_netherite_tool"));
    public static final TagKey<Block> NEEDS_FIERY_NETHERITE_TOOL = TagKey.of(RegistryKeys.BLOCK, OblivionMain.identifierOf("needs_fiery_netherite_tool"));

    public static final TagKey<Block> INCORRECT_FOR_SCARLET_TOOL = TagKey.of(RegistryKeys.BLOCK, OblivionMain.identifierOf("incorrect_for_scarlet_tool"));
    public static final TagKey<Block> NEEDS_SCARLET_TOOL = TagKey.of(RegistryKeys.BLOCK, OblivionMain.identifierOf("needs_scarlet_tool"));

    public static final TagKey<Block> INCORRECT_FOR_SOLARITE_TOOL = TagKey.of(RegistryKeys.BLOCK, OblivionMain.identifierOf("incorrect_for_solarite_tool"));
    public static final TagKey<Block> NEEDS_SOLARITE_TOOL = TagKey.of(RegistryKeys.BLOCK, OblivionMain.identifierOf("needs_solarite_tool"));

    public static final TagKey<Block> IRON_WOOD = TagKey.of(RegistryKeys.BLOCK, OblivionMain.identifierOf("iron_wood"));
    public static final TagKey<Item> CORRECT_FOR_IRON_WOOD = TagKey.of(RegistryKeys.ITEM, OblivionMain.identifierOf("correct_for_iron_wood"));

    public static final TagKey<Block> RUNE_WOOD = TagKey.of(RegistryKeys.BLOCK, OblivionMain.identifierOf("rune_wood"));
    public static final TagKey<Item> CORRECT_FOR_RUNE_WOOD = TagKey.of(RegistryKeys.ITEM, OblivionMain.identifierOf("correct_for_rune_wood"));

    public static void init(){
    }
}
