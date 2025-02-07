package net.oblivion.access;

import net.minecraft.util.math.BlockPos;

public interface ServerPlayerEntityAccess {

    public void setGuidelightBlockPos(BlockPos guidelightBlockPos);

    public BlockPos getGuidelightBlockPos();

}
