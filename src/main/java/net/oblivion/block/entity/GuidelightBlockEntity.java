package net.oblivion.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.oblivion.access.ClientPlayerEntityAccess;
import net.oblivion.init.BlockInit;
import net.oblivion.init.TagInit;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GuidelightBlockEntity extends BlockEntity {

    private final Box box;

    private boolean isActive = false;
    private boolean isEntityNearby = false;
    //    private int initialActivationTick = 0;
    private int teleportTick = -200;
//    private int cooldownTick = 0;

    public static final int TELEPORT_TICKS = 200;
    public static final int TELEPORT_COOLDOWN_TICKS = 200;

    private final List<Integer> lastTeleportedIds = new ArrayList<>();
//    public static final int ACTIVATION_TICKS = 600;

//    private static final int COOLDOWN_TICKS = 600;

    public GuidelightBlockEntity(BlockPos pos, BlockState state) {
        super(BlockInit.GUIDELIGHT_BLOCK_ENTITY, pos, state);
        this.box = new Box(pos).expand(2.5D, 3D, 2.5D);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);

        this.isActive = nbt.getBoolean("IsActive");
//        this.initialActivationTick = nbt.getInt("InitialActivationTick");
        this.teleportTick = nbt.getInt("TeleportTick");
//        this.cooldownTick = nbt.getInt("CooldownTick");
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);

        nbt.putBoolean("IsActive", this.isActive);
//        nbt.putInt("InitialActivationTick", this.initialActivationTick);
        nbt.putInt("TeleportTick", this.teleportTick);
//        nbt.putInt("CooldownTick", this.cooldownTick);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public boolean isActive() {
        return this.isActive;
    }

//    public void setInitialActivationTick(int initialActivationTick) {
//        this.initialActivationTick = initialActivationTick;
//    }
//
//    public int getInitialActivationTick() {
//        return this.initialActivationTick;
//    }

    public void setTeleportTick(int teleportTick) {
        this.teleportTick = teleportTick;
    }

    public int getTeleportTick() {
        return this.teleportTick;
    }

    public void setEntityNearby(boolean entityNearby) {
        this.isEntityNearby = entityNearby;
    }

    public boolean isEntityNearby() {
        return this.isEntityNearby;
    }

//    public void setCooldownTick(int cooldownTick) {
//        this.cooldownTick = cooldownTick;
//    }
//
//    public int getCooldownTick() {
//        return this.cooldownTick;
//    }

    public static void serverTick(World world, BlockPos pos, BlockState state, GuidelightBlockEntity blockEntity) {
        if (world.getTime() % 20 == 0) {
            for (int i = -3; i <= 3; i++) {
                for (int u = -3; u <= 3; u++) {
                    BlockPos checkPos = pos.down().north(i).east(u);
                    if (!world.getBlockState(checkPos).isIn(TagInit.GUIDELIGHT_BASE_BLOCKS)) {
                        if (blockEntity.isActive()) {
                            blockEntity.setActive(false);
                            blockEntity.setTeleportTick(-TELEPORT_COOLDOWN_TICKS);
                            blockEntity.markDirty();
                            world.updateListeners(pos, state, state, 0);

                            world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_BEACON_DEACTIVATE, SoundCategory.BLOCKS, 1, 0.5f, world.getTime());
                        }
                        return;
                    }
                }
            }
            if (!blockEntity.isActive()) {
                blockEntity.setActive(true);
                blockEntity.markDirty();
                world.updateListeners(pos, state, state, 0);
            }
            if (blockEntity.isActive() && blockEntity.getTeleportTick() >= 0) {
                List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class, blockEntity.box, EntityPredicates.EXCEPT_SPECTATOR);
                if (!entities.isEmpty() && entities.stream().anyMatch(entity -> !blockEntity.lastTeleportedIds.contains(entity.getId()))) {
                    blockEntity.setEntityNearby(true);
                } else {
                    blockEntity.setEntityNearby(false);
                    blockEntity.lastTeleportedIds.clear();
                }
            }
        }
        if (!blockEntity.isActive()) {
//            if (blockEntity.getInitialActivationTick() > 0) {
//                blockEntity.setInitialActivationTick(blockEntity.getInitialActivationTick() - 1);
//                blockEntity.markDirty();
//                world.updateListeners(pos, state, state, 0);
//            }
        } else {

//            if (blockEntity.getInitialActivationTick() <= ACTIVATION_TICKS) {
//                blockEntity.setInitialActivationTick(blockEntity.getInitialActivationTick() + 1);
//                blockEntity.markDirty();
//                world.updateListeners(pos, state, state, 0);
//            } else

//            if (blockEntity.getCooldownTick() > 0) {
//                blockEntity.setCooldownTick(blockEntity.getCooldownTick() - 1);
//            } else
            if (blockEntity.getTeleportTick() < 0) {
                blockEntity.setTeleportTick(blockEntity.getTeleportTick() + 1);
                blockEntity.markDirty();
                world.updateListeners(pos, state, state, 0);

                // Todo: Play loading sound of guidelight here

                if (blockEntity.getTeleportTick() == 0) {
                    ((ServerWorld) world).playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.BLOCKS, 1, 0.5f, world.getTime());
                }
            } else if (blockEntity.isEntityNearby()) {
                blockEntity.setTeleportTick(blockEntity.getTeleportTick() + 1);

                if (blockEntity.getTeleportTick() >= TELEPORT_TICKS) {

                    List<Integer> currentEntityIds = new ArrayList();
                    for (LivingEntity livingEntity : world.getEntitiesByClass(LivingEntity.class, blockEntity.box, EntityPredicates.EXCEPT_SPECTATOR)) {
                        if (!blockEntity.lastTeleportedIds.contains(livingEntity.getId())) {
                            blockEntity.lastTeleportedIds.add(livingEntity.getId());


                            // Todo: HERE Teleport and play teleport sound

                        }
                        currentEntityIds.add(livingEntity.getId());
                    }
                    blockEntity.lastTeleportedIds.removeIf(id -> !currentEntityIds.contains(id));

                    world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 1f, 1f, world.getTime());
                    blockEntity.setTeleportTick(-TELEPORT_COOLDOWN_TICKS);
                    blockEntity.setEntityNearby(false);
                    blockEntity.markDirty();
                }
                world.updateListeners(pos, state, state, 0);
            } else if (blockEntity.getTeleportTick() > 0) {
                blockEntity.setTeleportTick(blockEntity.getTeleportTick() - 2);
                blockEntity.markDirty();
                world.updateListeners(pos, state, state, 0);
            }
        }
    }

    public static void clientTick(World world, BlockPos pos, BlockState state, GuidelightBlockEntity blockEntity) {
        if (blockEntity.isActive()) {
//            System.out.println(blockEntity.getTeleportTick());
//            if (blockEntity.getTeleportTick() == -1) {
//                world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.BLOCKS, 1, 0.5f);
//            } else
            if (blockEntity.getTeleportTick() > 0) {
                for (ClientPlayerEntity clientPlayerEntity : world.getEntitiesByClass(ClientPlayerEntity.class, blockEntity.box, EntityPredicates.EXCEPT_SPECTATOR)) {
                    if (!blockEntity.lastTeleportedIds.contains(clientPlayerEntity.getId())) {
                        ((ClientPlayerEntityAccess) clientPlayerEntity).setGuidelightBlockPos(blockEntity.getPos());
                        ((ClientPlayerEntityAccess) clientPlayerEntity).setTeleportTicks(blockEntity.getTeleportTick());
                    }
                }
            } else if (blockEntity.getTeleportTick() <= 0) {

            }
        }
    }

}
