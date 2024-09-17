package net.oblivion.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.oblivion.access.ClientPlayerEntityAccess;
import net.oblivion.util.RenderUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Environment(EnvType.CLIENT)
@Mixin(InGameOverlayRenderer.class)
public class InGameOverlayRendererMixin {

    @Inject(method = "renderOverlays", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isOnFire()Z"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void renderOverlaysMixin(MinecraftClient client, MatrixStack matrices, CallbackInfo info, PlayerEntity playerEntity) {
        if (((ClientPlayerEntityAccess) playerEntity).getGuidelightBlockPos() != null) {
            RenderUtil.renderGuidelightOverlay(client, matrices, ((ClientPlayerEntityAccess) playerEntity).getTeleportTicks());
        }
    }


}
