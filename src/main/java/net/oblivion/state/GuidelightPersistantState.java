package net.oblivion.state;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import java.util.List;

public class GuidelightPersistantState extends PersistentState {

    private BlockPos guidelightPos = BlockPos.ORIGIN;

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        nbt.putIntArray("GuidelightPos", List.of(this.guidelightPos.getX(), this.guidelightPos.getY(), this.guidelightPos.getZ()));
        return nbt;
    }

    public static PersistentState.Type<GuidelightPersistantState> getPersistentStateType() {
        return new PersistentState.Type<>(GuidelightPersistantState::new, (nbt, registryLookup) -> fromNbt(nbt), null);
    }

    public static GuidelightPersistantState fromNbt(NbtCompound nbt) {
        GuidelightPersistantState guidelightPersistantState = new GuidelightPersistantState();
        int[] guidelightPos = nbt.getIntArray("GuidelightPos");
        guidelightPersistantState.setGuidelightPos(new BlockPos(guidelightPos[0], guidelightPos[1], guidelightPos[2]));
        return guidelightPersistantState;
    }

    public BlockPos getGuidelightPos() {
        return guidelightPos;
    }

    public void setGuidelightPos(BlockPos guidelightPos) {
        this.guidelightPos = guidelightPos;
    }

    public static GuidelightPersistantState getGuidelightPersistentState(MinecraftServer server) {
        PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();
        GuidelightPersistantState state = persistentStateManager.getOrCreate(getPersistentStateType(), "oblivion");
        state.markDirty();
        return state;
    }
}
