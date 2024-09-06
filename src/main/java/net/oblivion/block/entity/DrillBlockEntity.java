package net.oblivion.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.oblivion.block.DrillBlock;
import net.oblivion.block.MultiOreBlock;
import net.oblivion.init.BlockInit;

public class DrillBlockEntity extends BlockEntity {

    private int ticksActive = 0;

    public DrillBlockEntity(BlockPos pos, BlockState state) {
        super(BlockInit.DRILL_BLOCK_ENTITY, pos, state);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);

        this.ticksActive = nbt.getInt("Ticks");
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);

        nbt.putInt("Ticks", this.ticksActive);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public static void serverTick(World world, BlockPos pos, BlockState state, DrillBlockEntity blockEntity) {
        if (state.get(DrillBlock.POWERED)) {
            BlockPos facingBlockPos = pos.offset(state.get(DrillBlock.FACING));
            BlockState facingBlockState = world.getBlockState(facingBlockPos);
            if (!facingBlockState.isAir()) {
                blockEntity.ticksActive++;
//                if (blockEntity.ticksActive % 200 == 0) {
                if ((int) (facingBlockState.getHardness(world, facingBlockPos) * 15) <= blockEntity.ticksActive ) {
                    if (facingBlockState.getBlock() instanceof MultiOreBlock && world.getBlockEntity(facingBlockPos) instanceof MultiOreBlockEntity multiOreBlockEntity) {
                        multiOreBlockEntity.decrementDrillCount(pos.offset(state.get(DrillBlock.FACING).getOpposite()));
                    } else if (facingBlockState.isFullCube(world, facingBlockPos) && facingBlockState.isSolidBlock(world, facingBlockPos)) {
                        world.breakBlock(facingBlockPos, true);
                    }
                    blockEntity.ticksActive = 0;
                }
            } else {
                blockEntity.ticksActive = 0;
            }
        } else if (blockEntity.ticksActive > 0) {
            blockEntity.ticksActive = 0;
        }
    }

    public static void clientTick(World world, BlockPos pos, BlockState state, DrillBlockEntity blockEntity) {
        if (state.get(DrillBlock.POWERED)) {
            blockEntity.ticksActive++;
        }
    }


    public float getRotation(float tickDelta) {
        return this.ticksActive + tickDelta;
    }

}
