package net.oblivion.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.oblivion.network.packet.GuidelightSoundPacket;

public class OblivionServerPacket {

    public static void init() {
        PayloadTypeRegistry.playS2C().register(GuidelightSoundPacket.PACKET_ID, GuidelightSoundPacket.PACKET_CODEC);

//        ServerPlayNetworking.registerGlobalReceiver(GuidelightSoundPacket.PACKET_ID, (payload, context) -> {
//            context.server().execute(() -> {
//            });
//        });
    }

}
