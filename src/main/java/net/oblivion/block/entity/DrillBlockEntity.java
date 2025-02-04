package net.oblivion.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.oblivion.block.DrillBlock;
import net.oblivion.block.MultiOreBlock;
import net.oblivion.init.BlockInit;
import net.oblivion.init.SoundInit;

public class DrillBlockEntity extends BlockEntity {

    private int ticksActive = 0;
    private boolean prevPowered;

    private final SoundInstance drillOn;
    private final SoundInstance drillIdle;
    private final SoundInstance drillOff;

    public DrillBlockEntity(BlockPos pos, BlockState state) {
        super(BlockInit.DRILL_BLOCK_ENTITY, pos, state);

        Random random = Random.create();
        this.drillOn = new PositionedSoundInstance(SoundInit.DRILL_ON_EVENT, SoundCategory.BLOCKS, 1.0f, 1.0f, random, pos.getX(), pos.getY(), pos.getZ());
        this.drillIdle = new PositionedSoundInstance(SoundInit.DRILL_IDLE_EVENT.getId(), SoundCategory.BLOCKS, 1.0f, 1.0f, random, true, 0, SoundInstance.AttenuationType.LINEAR, pos.getX(), pos.getY(), pos.getZ(), false);
        this.drillOff = new PositionedSoundInstance(SoundInit.DRILL_OFF_EVENT, SoundCategory.BLOCKS, 1.0f, 1.0f, random, pos.getX(), pos.getY(), pos.getZ());
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
                if (blockEntity.ticksActive % 1200 == 0) {
                    if ((int) (facingBlockState.getHardness(world, facingBlockPos) * 15) <= blockEntity.ticksActive) {
                        if (facingBlockState.getBlock() instanceof MultiOreBlock && world.getBlockEntity(facingBlockPos) instanceof MultiOreBlockEntity multiOreBlockEntity) {
                            multiOreBlockEntity.decrementDrillCount(pos.offset(state.get(DrillBlock.FACING).getOpposite()));
                        } else if (facingBlockState.isFullCube(world, facingBlockPos) && facingBlockState.isSolidBlock(world, facingBlockPos)) {
                            world.breakBlock(facingBlockPos, true);
                        }
                        blockEntity.ticksActive = 0;
                    }
                }
            } else {
                blockEntity.ticksActive = 0;
            }
        } else if (blockEntity.ticksActive > 0) {
            blockEntity.ticksActive = 0;
        }
    }

    public static void clientTick(World world, BlockPos pos, BlockState state, DrillBlockEntity blockEntity) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (state.get(DrillBlock.POWERED)) {
            blockEntity.ticksActive++;
            if (!blockEntity.prevPowered) {
                client.getSoundManager().play(blockEntity.drillOn);
                client.getSoundManager().play(blockEntity.drillIdle, 14);
            } else {
                if (!client.getSoundManager().isPlaying(blockEntity.drillOn) && !client.getSoundManager().isPlaying(blockEntity.drillIdle)) {
                    client.getSoundManager().play(blockEntity.drillIdle);
                }
            }
        } else if (blockEntity.prevPowered) {
            client.getSoundManager().stop(blockEntity.drillIdle);
            client.getSoundManager().play(blockEntity.drillOff);
        } else {
            client.getSoundManager().stop(blockEntity.drillIdle);
        }
        blockEntity.prevPowered = state.get(DrillBlock.POWERED);
    }

    public float getRotation(float tickDelta) {
        return this.ticksActive + tickDelta;
    }

}
