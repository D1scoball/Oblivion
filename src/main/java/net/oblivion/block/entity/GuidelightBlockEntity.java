package net.oblivion.block.entity;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.*;
import net.minecraft.world.Heightmap;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.oblivion.access.ClientPlayerEntityAccess;
import net.oblivion.access.ServerPlayerEntityAccess;
import net.oblivion.init.BlockInit;
import net.oblivion.init.TagInit;
import net.oblivion.init.WorldInit;
import net.oblivion.network.packet.GuidelightSoundPacket;
import net.oblivion.state.GuidelightPersistantState;
import net.oblivion.world.feature.GuidelightFeature;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GuidelightBlockEntity extends BlockEntity {

    private final Box box;

    private boolean isActive = false;
    private boolean isEntityNearby = false;
    private int teleportTick = -200;

    public static final int TELEPORT_TICKS = 200;
    public static final int TELEPORT_COOLDOWN_TICKS = 200;

    private final List<Integer> lastTeleportedIds = new ArrayList<>();

    public SoundInstance guidelightTeleport = null;
    public SoundInstance guidelightStop = null;

    private List<UUID> teleportSoundPlayers = new ArrayList<>();

    public GuidelightBlockEntity(BlockPos pos, BlockState state) {
        super(BlockInit.GUIDELIGHT_BLOCK_ENTITY, pos, state);
        this.box = new Box(pos).expand(2.5D, 3D, 2.5D);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);

        this.isActive = nbt.getBoolean("IsActive");
        this.teleportTick = nbt.getInt("TeleportTick");
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);

        nbt.putBoolean("IsActive", this.isActive);
        nbt.putInt("TeleportTick", this.teleportTick);
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
                if (world.getRegistryKey() != WorldInit.OBLIVION_WORLD && world.getRegistryKey() != World.OVERWORLD) {
                    blockEntity.setEntityNearby(false);
                    blockEntity.lastTeleportedIds.clear();
                } else {
                    List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class, blockEntity.box, EntityPredicates.EXCEPT_SPECTATOR);
                    if (!entities.isEmpty() && entities.stream().anyMatch(entity -> !blockEntity.lastTeleportedIds.contains(entity.getId())) && entities.stream().anyMatch(entity -> entity instanceof ServerPlayerEntity)) {
                        blockEntity.setEntityNearby(true);
                    } else {
                        blockEntity.setEntityNearby(false);
                        blockEntity.lastTeleportedIds.clear();
                    }
                }
            }
        }
        if (!blockEntity.isActive()) {
        } else {
            if (blockEntity.getTeleportTick() < 0) {
                blockEntity.setTeleportTick(blockEntity.getTeleportTick() + 1);
                blockEntity.markDirty();
                world.updateListeners(pos, state, state, 0);

                if (blockEntity.getTeleportTick() == 0) {
                    ((ServerWorld) world).playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.BLOCKS, 1, 0.5f, world.getTime());
                }
            } else if (blockEntity.isEntityNearby()) {
                if (blockEntity.getTeleportTick() == 10) {
                    for (ServerPlayerEntity serverPlayerEntity : world.getEntitiesByClass(ServerPlayerEntity.class, blockEntity.box, EntityPredicates.EXCEPT_SPECTATOR)) {
                        blockEntity.teleportSoundPlayers.add(serverPlayerEntity.getUuid());
                        ServerPlayNetworking.send(serverPlayerEntity, new GuidelightSoundPacket(pos, 0));
                    }
                }

                blockEntity.setTeleportTick(blockEntity.getTeleportTick() + 1);

                if (blockEntity.getTeleportTick() == 100 && world.getRegistryKey() != WorldInit.OBLIVION_WORLD) {
                    ServerWorld oblivion = world.getServer().getWorld(WorldInit.OBLIVION_WORLD);
                    ChunkPos chunkPos = new ChunkPos(ChunkSectionPos.getSectionCoord(pos.getX()), ChunkSectionPos.getSectionCoord(pos.getZ()));
                    oblivion.getChunkManager().addTicket(ChunkTicketType.PORTAL, chunkPos, 1, chunkPos.getStartPos());
                }

                if (blockEntity.getTeleportTick() >= TELEPORT_TICKS) {
                    List<Integer> currentEntityIds = new ArrayList<>();

                    boolean isInOblivion = world.getRegistryKey() == WorldInit.OBLIVION_WORLD;
                    BlockPos nonPlayerPos = BlockPos.ORIGIN;
                    if (isInOblivion && world.getEntitiesByClass(LivingEntity.class, blockEntity.box, EntityPredicates.EXCEPT_SPECTATOR).stream().anyMatch(entity -> !(entity instanceof ServerPlayerEntity))) {
                        for (LivingEntity livingEntity : world.getEntitiesByClass(LivingEntity.class, blockEntity.box, EntityPredicates.EXCEPT_SPECTATOR)) {
                            if (livingEntity instanceof ServerPlayerEntity serverPlayerEntity) {
                                BlockPos guidelightPos = ((ServerPlayerEntityAccess) serverPlayerEntity).getGuidelightBlockPos();
                                if (guidelightPos.equals(BlockPos.ORIGIN)) {
                                    continue;
                                } else {
                                    ServerWorld overWorld = ((ServerWorld) world).getServer().getWorld(World.OVERWORLD);
                                    if (overWorld.getBlockState(guidelightPos).isOf(BlockInit.GUIDELIGHT) && overWorld.getBlockEntity(guidelightPos) instanceof GuidelightBlockEntity guidelightBlockEntity && guidelightBlockEntity.isActive()) {
                                        nonPlayerPos = guidelightPos;
                                    } else {
                                        continue;
                                    }
                                }
                                break;
                            }
                        }
                    }
                    for (LivingEntity livingEntity : world.getEntitiesByClass(LivingEntity.class, blockEntity.box, EntityPredicates.EXCEPT_SPECTATOR)) {
                        if (!blockEntity.lastTeleportedIds.contains(livingEntity.getId())) {
                            blockEntity.lastTeleportedIds.add(livingEntity.getId());

                            if (livingEntity instanceof ServerPlayerEntity serverPlayerEntity) {
                                Vec3d teleportPos = null;
                                if (isInOblivion) {
                                    BlockPos guidelightPos = ((ServerPlayerEntityAccess) serverPlayerEntity).getGuidelightBlockPos();
                                    ServerWorld overWorld = ((ServerWorld) world).getServer().getWorld(World.OVERWORLD);
                                    TeleportTarget teleportTarget;
                                    if (guidelightPos.equals(BlockPos.ORIGIN)) {
                                        teleportTarget = serverPlayerEntity.getRespawnTarget(false, TeleportTarget.NO_OP);
                                    } else {
                                        if (overWorld.getBlockState(guidelightPos).isOf(BlockInit.GUIDELIGHT) && overWorld.getBlockEntity(guidelightPos) instanceof GuidelightBlockEntity guidelightBlockEntity && guidelightBlockEntity.isActive()) {
                                            teleportPos = serverPlayerEntity.getPos().subtract(pos.getX(), pos.getY(), pos.getZ()).add(guidelightPos.getX(), guidelightPos.getY(), guidelightPos.getZ());
                                            teleportTarget = new TeleportTarget(overWorld, teleportPos, Vec3d.ZERO, serverPlayerEntity.getYaw(), serverPlayerEntity.getPitch(), TeleportTarget.NO_OP);
                                        } else {
                                            teleportTarget = serverPlayerEntity.getRespawnTarget(false, TeleportTarget.NO_OP);
                                        }
                                    }
                                    serverPlayerEntity.teleportTo(teleportTarget);
                                } else {
                                    BlockPos guidelightPos = GuidelightPersistantState.getGuidelightPersistentState(((ServerWorld) world).getServer()).getGuidelightPos();
                                    if (guidelightPos.equals(BlockPos.ORIGIN)) {
                                        generateGuidelight((ServerWorld) world, pos);
                                        guidelightPos = GuidelightPersistantState.getGuidelightPersistentState(((ServerWorld) world).getServer()).getGuidelightPos();
                                        teleportPos = serverPlayerEntity.getPos().subtract(pos.getX(), pos.getY(), pos.getZ()).add(guidelightPos.getX(), guidelightPos.getY(), guidelightPos.getZ());
                                    } else {
                                        teleportPos = serverPlayerEntity.getPos().subtract(pos.getX(), pos.getY(), pos.getZ()).add(guidelightPos.getX(), guidelightPos.getY(), guidelightPos.getZ());
                                    }
                                    serverPlayerEntity.teleportTo(new TeleportTarget(((ServerWorld) world).getServer().getWorld(WorldInit.OBLIVION_WORLD), teleportPos, Vec3d.ZERO, serverPlayerEntity.getYaw(), serverPlayerEntity.getPitch(), TeleportTarget.NO_OP));

                                }

                            } else {
                                // non players
                                Vec3d teleportPos = null;
                                if (isInOblivion) {
                                    ServerWorld overWorld = ((ServerWorld) world).getServer().getWorld(World.OVERWORLD);
                                    if (nonPlayerPos.equals(BlockPos.ORIGIN)) {
                                        continue;
                                    } else {
                                        teleportPos = livingEntity.getPos().subtract(pos.getX(), pos.getY(), pos.getZ()).add(nonPlayerPos.getX(), nonPlayerPos.getY(), nonPlayerPos.getZ());
                                        livingEntity.teleportTo(new TeleportTarget(overWorld, teleportPos, Vec3d.ZERO, livingEntity.getYaw(), livingEntity.getPitch(), TeleportTarget.NO_OP));
                                    }
                                } else {
                                    BlockPos guidelightPos = GuidelightPersistantState.getGuidelightPersistentState(((ServerWorld) world).getServer()).getGuidelightPos();
                                    if (guidelightPos.equals(BlockPos.ORIGIN)) {
                                        generateGuidelight((ServerWorld) world, pos);
                                        guidelightPos = GuidelightPersistantState.getGuidelightPersistentState(((ServerWorld) world).getServer()).getGuidelightPos();
                                        teleportPos = livingEntity.getPos().subtract(pos.getX(), pos.getY(), pos.getZ()).add(guidelightPos.getX(), guidelightPos.getY(), guidelightPos.getZ());
                                    } else {
                                        teleportPos = livingEntity.getPos().subtract(pos.getX(), pos.getY(), pos.getZ()).add(guidelightPos.getX(), guidelightPos.getY(), guidelightPos.getZ());
                                    }
                                    livingEntity.teleportTo(new TeleportTarget(((ServerWorld) world).getServer().getWorld(WorldInit.OBLIVION_WORLD), teleportPos, Vec3d.ZERO, livingEntity.getYaw(), livingEntity.getPitch(), TeleportTarget.NO_OP));
                                }

                            }
                        }
                        currentEntityIds.add(livingEntity.getId());
                    }
                    blockEntity.lastTeleportedIds.removeIf(id -> !currentEntityIds.contains(id));
                    blockEntity.teleportSoundPlayers.clear();
                    blockEntity.setTeleportTick(-TELEPORT_COOLDOWN_TICKS);
                    blockEntity.setEntityNearby(false);
                    blockEntity.markDirty();
                }
                world.updateListeners(pos, state, state, 0);
            } else if (blockEntity.getTeleportTick() > 0) {
                blockEntity.setTeleportTick(blockEntity.getTeleportTick() - 2);
                blockEntity.markDirty();
                world.updateListeners(pos, state, state, 0);
            } else {
                if (world.getTime() % 80L == 0L) {
                    world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_BEACON_AMBIENT, SoundCategory.BLOCKS, 1, 0.5f, world.getTime());
                }
            }
            List<UUID> removePlayers = new ArrayList<>();
            for (UUID teleportPlayer : blockEntity.teleportSoundPlayers) {
                if (world.getPlayerByUuid(teleportPlayer) instanceof ServerPlayerEntity serverPlayerEntity && !blockEntity.box.contains(serverPlayerEntity.getPos())) {
                    ServerPlayNetworking.send(serverPlayerEntity, new GuidelightSoundPacket(pos, 1));
                    removePlayers.add(serverPlayerEntity.getUuid());
                }
            }
            blockEntity.teleportSoundPlayers.removeAll(removePlayers);
        }
    }

    public static void clientTick(World world, BlockPos pos, BlockState state, GuidelightBlockEntity blockEntity) {
        if (blockEntity.isActive()) {
            double d = (double) pos.getX() + world.getRandom().nextFloat();
            double e = (double) pos.getY() + world.getRandom().nextFloat() * 2f;
            double f = (double) pos.getZ() + world.getRandom().nextFloat();
            double g = (double) (0.4F - (world.getRandom().nextFloat() + world.getRandom().nextFloat()) * 0.4F);

            world.addParticle(ParticleTypes.END_ROD, d, e,
                    f, world.getRandom().nextGaussian() * 0.005, world.getRandom().nextGaussian() * 0.005, world.getRandom().nextGaussian() * 0.005);
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

    private static void generateGuidelight(ServerWorld world, BlockPos pos) {
        GuidelightPersistantState guidelightPersistantState = GuidelightPersistantState.getGuidelightPersistentState(world.getServer());

        ServerWorld oblivion = world.getServer().getWorld(WorldInit.OBLIVION_WORLD);

        ChunkPos chunkPos = new ChunkPos(ChunkSectionPos.getSectionCoord(pos.getX()), ChunkSectionPos.getSectionCoord(pos.getZ()));
        oblivion.getChunkManager().addTicket(ChunkTicketType.PORTAL, chunkPos, 1, chunkPos.getStartPos());

        BlockPos guildelightPos = oblivion.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, pos);

        for (int i = -3; i <= 3; i++) {
            for (int j = -3; j <= 3; j++) {
                for (int k = 0; k <= 20; k++) {
                    BlockPos checkPos = guildelightPos.up(2).add(i, k, j);
                    if (!oblivion.getBlockState(checkPos).isAir()) {
                        oblivion.breakBlock(checkPos, false);
                    }
                }
            }
        }

        GuidelightFeature.generate(oblivion, guildelightPos, false);

        guidelightPersistantState.setGuidelightPos(guildelightPos.up());
        guidelightPersistantState.markDirty();
    }

}
