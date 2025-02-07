package net.oblivion.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.oblivion.block.entity.GuidelightBlockEntity;
import net.oblivion.init.BlockInit;
import net.oblivion.init.SoundInit;
import net.oblivion.network.packet.GuidelightSoundPacket;

@Environment(EnvType.CLIENT)
public class OblivionClientPacket {

    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(GuidelightSoundPacket.PACKET_ID, (payload, context) -> {
            BlockPos guidelightPos = payload.guidelightPos();
            int soundIndex = payload.soundIndex();
            context.client().execute(() -> {
                if (context.player().getWorld().getBlockState(guidelightPos).isOf(BlockInit.GUIDELIGHT) && context.player().getWorld().getBlockEntity(guidelightPos) instanceof GuidelightBlockEntity guidelightBlockEntity) {
                    if (guidelightBlockEntity.guidelightTeleport == null) {
                        guidelightBlockEntity.guidelightTeleport = new PositionedSoundInstance(SoundInit.GUIDELIGHT_TELEPORT_EVENT, SoundCategory.BLOCKS, 1.0f, 1.0f, context.player().getRandom(), guidelightPos.getX(), guidelightPos.getY(), guidelightPos.getZ());
                        guidelightBlockEntity.guidelightStop = new PositionedSoundInstance(SoundInit.GUIDELIGHT_STOP_EVENT, SoundCategory.BLOCKS, 1.0f, 1.0f, context.player().getRandom(), guidelightPos.getX(), guidelightPos.getY(), guidelightPos.getZ());
                    }
                    if (soundIndex == 0) {
                        if (!context.client().getSoundManager().isPlaying(guidelightBlockEntity.guidelightTeleport)) {
                            context.client().getSoundManager().play(guidelightBlockEntity.guidelightTeleport);
                        }
                    }
                    if (soundIndex == 1) {
                        if (context.client().getSoundManager().isPlaying(guidelightBlockEntity.guidelightTeleport)) {
                            context.client().getSoundManager().stop(guidelightBlockEntity.guidelightTeleport);
                            context.client().getSoundManager().play(guidelightBlockEntity.guidelightStop);
                        }
                    }
                }
            });
        });
    }

}
