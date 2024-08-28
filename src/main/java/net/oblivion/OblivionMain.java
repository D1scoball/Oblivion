package net.oblivion;

import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import net.oblivion.init.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OblivionMain implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("oblivion");

    @Override
    public void onInitialize() {
        BlockInit.init();
        ConfigInit.init();
        EntityInit.init();
        EventInit.init();
        ItemInit.init();
        SoundInit.init();
        SpawnInit.init();
        TagInit.init();
    }

    public static Identifier id(String name) {
        return Identifier.of("oblivion", name);
    }
}