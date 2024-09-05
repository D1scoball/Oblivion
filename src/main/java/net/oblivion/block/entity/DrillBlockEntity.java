package net.oblivion.block.entity;

import net.minecraft.block.BannerBlock;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ConduitBlockEntity;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.oblivion.block.DrillBlock;
import net.oblivion.init.BlockInit;

import java.util.List;

public class DrillBlockEntity extends BlockEntity {

    private int ticksActive = 0;

    public DrillBlockEntity(BlockPos pos, BlockState state) {
        super(BlockInit.DRILL_BLOCK_ENTITY, pos, state);
    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public static void serverTick(World world, BlockPos pos, BlockState state, DrillBlockEntity blockEntity) {

    }

    public static void clientTick(World world, BlockPos pos, BlockState state, DrillBlockEntity blockEntity) {
//        blockEntity.ticks++;
//        long l = world.getTime();
//        List<BlockPos> list = blockEntity.activatingBlocks;
//        if (l % 40L == 0L) {
//            blockEntity.active = updateActivatingBlocks(world, pos, list);
//            openEye(blockEntity, list);
//        }
//
//        updateTargetEntity(world, pos, blockEntity);
//        spawnNautilusParticles(world, pos, list, blockEntity.targetEntity, blockEntity.ticks);
//        if (state.get(DrillBlock.POWERED)) {
            blockEntity.ticksActive++;
//        }
    }


    public float getRotation(float tickDelta) {
        return this.ticksActive + tickDelta;
    }
//    BannerBlock
}
