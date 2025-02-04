package net.oblivion.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.util.Identifier;
import net.oblivion.OblivionMain;
import net.oblivion.init.WorldInit;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(WorldRenderer.class)
public class WorldRenderMixin {

    @Shadow
    @Mutable
    @Final
    private MinecraftClient client;

    @Unique
    private static final Identifier OBLIVION_MOON_PHASES = OblivionMain.identifierOf(("textures/environment/oblivion_moon_phases.png"));

    @WrapOperation(method = "renderSky", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/util/Identifier;)V", ordinal = 1))
    private void renderSkyMixin(int texture, Identifier id, Operation<Void> original) {
        if (this.client.world.getRegistryKey() == WorldInit.OBLIVION_WORLD) {
            original.call(texture, OBLIVION_MOON_PHASES);
        } else {
            original.call(texture, id);
        }
    }
}
