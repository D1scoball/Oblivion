package net.oblivion.mixin.client;

import com.mojang.authlib.GameProfile;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.oblivion.access.ClientPlayerEntityAccess;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity implements ClientPlayerEntityAccess {

    @Nullable
    @Unique
    private BlockPos guidelightBlockPos = null;
    @Unique
    private int teleportTicks = 0;

    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Override
    public void setGuidelightBlockPos(@Nullable BlockPos guidelightBlockPos) {
        this.guidelightBlockPos = guidelightBlockPos;
    }

    @Override
    public @Nullable BlockPos getGuidelightBlockPos() {
        return this.guidelightBlockPos;
    }

    // Set teleport ticks to -200 to still render overlay after teleport
    @Inject(method = "tick", at = @At("TAIL"))
    private void tickMixin(CallbackInfo info) {
        if (this.guidelightBlockPos != null && (this.teleportTicks < 0 || !this.guidelightBlockPos.isWithinDistance(this.getPos(), 2.5D))) {
            this.guidelightBlockPos = null;
            this.teleportTicks = 0;
        }
    }

    @Override
    public void setTeleportTicks(int teleportTicks) {
        this.teleportTicks = teleportTicks;
    }

    @Override
    public int getTeleportTicks() {
        return this.teleportTicks;
    }
}
