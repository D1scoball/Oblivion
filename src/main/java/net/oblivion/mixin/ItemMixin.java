package net.oblivion.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.oblivion.init.TagInit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {

    // New Set: add requiresTool() to block in BlockInit
    @Inject(method = "isCorrectForDrops", at = @At("HEAD"), cancellable = true)
    private void isCorrectForDropsMixin(ItemStack stack, BlockState state, CallbackInfoReturnable<Boolean> info) {
        if (stack.isIn(ItemTags.AXES) && state.isIn(TagInit.IRON_WOOD)) {
            info.setReturnValue(stack.isIn(TagInit.CORRECT_FOR_IRON_WOOD));
        }
    }
}
