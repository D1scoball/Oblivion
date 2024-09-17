package net.oblivion.access;

import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public interface ClientPlayerEntityAccess {

    public void setGuidelightBlockPos(@Nullable BlockPos guidelightBlockPos);

    @Nullable
    public BlockPos getGuidelightBlockPos();

    public void setTeleportTicks(int teleportTicks);

    public int getTeleportTicks();
}
