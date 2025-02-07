package net.oblivion.mixin;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.oblivion.access.ServerPlayerEntityAccess;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin implements ServerPlayerEntityAccess {

    @Unique
    private BlockPos guidelightBlockPos = BlockPos.ORIGIN;

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void readCustomDataFromNbtMixin(NbtCompound nbt, CallbackInfo info) {
        int[] guidelightPos = nbt.getIntArray("GuidelightPos");
        this.guidelightBlockPos = new BlockPos(guidelightPos[0], guidelightPos[1], guidelightPos[2]);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void writeCustomDataToNbtMixin(NbtCompound nbt, CallbackInfo info) {
        nbt.putIntArray("GuidelightPos", List.of(this.guidelightBlockPos.getX(), this.guidelightBlockPos.getY(), this.guidelightBlockPos.getZ()));
    }

    @Override
    public void setGuidelightBlockPos(@Nullable BlockPos guidelightBlockPos) {
        this.guidelightBlockPos = guidelightBlockPos;
    }

    @Override
    public BlockPos getGuidelightBlockPos() {
        return this.guidelightBlockPos;
    }
}
