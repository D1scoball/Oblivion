package net.oblivion.network.packet;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;
import net.oblivion.OblivionMain;

/**
 * Index: 0: start, 1: stop
 */
public record GuidelightSoundPacket(BlockPos guidelightPos, int soundIndex) implements CustomPayload {

    public static final CustomPayload.Id<GuidelightSoundPacket> PACKET_ID = new CustomPayload.Id<>(OblivionMain.identifierOf("guidelight_sound_packet"));

    public static final PacketCodec<RegistryByteBuf, GuidelightSoundPacket> PACKET_CODEC = PacketCodec.of((value, buf) -> {
        buf.writeBlockPos(value.guidelightPos);
        buf.writeInt(value.soundIndex);
    }, buf -> new GuidelightSoundPacket(buf.readBlockPos(), buf.readInt()));

    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }

}

