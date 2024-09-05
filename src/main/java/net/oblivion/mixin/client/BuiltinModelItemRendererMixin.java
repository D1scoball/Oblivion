package net.oblivion.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.oblivion.block.entity.DrillBlockEntity;
import net.oblivion.init.BlockInit;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Environment(EnvType.CLIENT)
@Mixin(BuiltinModelItemRenderer.class)
public class BuiltinModelItemRendererMixin {

    @Shadow
    @Mutable
    @Final
    private BlockEntityRenderDispatcher blockEntityRenderDispatcher;

    @Unique
    private final DrillBlockEntity renderDrill = new DrillBlockEntity(BlockPos.ORIGIN, BlockInit.DRILL.getDefaultState());

    @Inject(method = "render", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/block/Block;getDefaultState()Lnet/minecraft/block/BlockState;"), cancellable = true, locals = LocalCapture.CAPTURE_FAILSOFT)
    private void renderMixin(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, CallbackInfo info, Item item, Block block) {
        if (block.getDefaultState().isOf(BlockInit.DRILL)) {
            this.blockEntityRenderDispatcher.renderEntity(renderDrill, matrices, vertexConsumers, light, overlay);
            info.cancel();
        }
    }
}
