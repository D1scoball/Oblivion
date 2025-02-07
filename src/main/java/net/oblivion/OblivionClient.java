package net.oblivion;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.oblivion.init.RenderInit;
import net.oblivion.network.OblivionClientPacket;

@Environment(EnvType.CLIENT)
public class OblivionClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        RenderInit.init();
        OblivionClientPacket.init();
    }
}
