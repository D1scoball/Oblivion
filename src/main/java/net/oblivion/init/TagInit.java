package net.oblivion.init;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.oblivion.OblivionMain;

public class TagInit {

    // Block
    public static final TagKey<Block> UNBREAKABLE_BLOCKS = TagKey.of(RegistryKeys.BLOCK, Identifier.ofVanilla("iron_wood_logs"));
    public static final TagKey<Block> GUIDELIGHT_BASE_BLOCKS = TagKey.of(RegistryKeys.BLOCK, OblivionMain.identifierOf("guidelight_base"));

    public static void init(){

    }
}
