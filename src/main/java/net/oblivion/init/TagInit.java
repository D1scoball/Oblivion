package net.oblivion.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.oblivion.OblivionMain;

public class TagInit {

    // Block
    public static final TagKey<Block> UNBREAKABLE_BLOCKS = TagKey.of(RegistryKeys.BLOCK, Identifier.ofVanilla("iron_wood_logs"));
    public static final TagKey<Block> GUIDELIGHT_BASE_BLOCKS = TagKey.of(RegistryKeys.BLOCK, OblivionMain.identifierOf("guidelight_base"));

    public static final TagKey<Block> INCORRECT_FOR_FIERY_NETHERITE_TOOL = TagKey.of(RegistryKeys.BLOCK, OblivionMain.identifierOf("incorrect_for_fiery_netherite_tool"));
    public static final TagKey<Block> NEEDS_FIERY_NETHERITE_TOOL = TagKey.of(RegistryKeys.BLOCK, OblivionMain.identifierOf("needs_fiery_netherite_tool"));

    public static final TagKey<Block> IRON_WOOD = TagKey.of(RegistryKeys.BLOCK, OblivionMain.identifierOf("iron_wood"));
    public static final TagKey<Item> CORRECT_FOR_IRON_WOOD = TagKey.of(RegistryKeys.ITEM, OblivionMain.identifierOf("correct_for_iron_wood"));

    public static void init(){
    }
}
