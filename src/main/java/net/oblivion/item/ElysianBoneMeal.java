package net.oblivion.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.event.GameEvent;
import net.oblivion.init.BlockInit;

public class ElysianBoneMeal extends Item {
    public ElysianBoneMeal(Item.Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockPos blockPos2 = blockPos.offset(context.getSide());
        if (useOnFertilizable(context.getStack(), world, blockPos)) {
            if (!world.isClient()) {
                context.getPlayer().emitGameEvent(GameEvent.ITEM_INTERACT_FINISH);
                world.syncWorldEvent(WorldEvents.BONE_MEAL_USED, blockPos, 15);
            }

            return ActionResult.success(world.isClient());
        } else {
            BlockState blockState = world.getBlockState(blockPos);
            boolean bl = blockState.isSideSolidFullSquare(world, blockPos, context.getSide());
            if (bl && BoneMealItem.useOnGround(context.getStack(), world, blockPos2, context.getSide())) {
                if (!world.isClient()) {
                    context.getPlayer().emitGameEvent(GameEvent.ITEM_INTERACT_FINISH);
                    world.syncWorldEvent(WorldEvents.BONE_MEAL_USED, blockPos2, 15);
                }

                return ActionResult.success(world.isClient());
            } else {
                return ActionResult.PASS;
            }
        }
    }

    public static boolean useOnFertilizable(ItemStack stack, World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof Fertilizable fertilizable && fertilizable.isFertilizable(world, pos, state)) {
            if (world instanceof ServerWorld ) {
                if (state.isOf(Blocks.GRASS_BLOCK)) {
                    BlockState blockState = BlockInit.SHIMMERING_GRASS.getDefaultState();
                    for (BlockPos blockPos : BlockPos.iterateOutwards(pos, 5, 3, 5)) {
                        if (world.getBlockState(blockPos).isOf(Blocks.GRASS_BLOCK) && world.getBlockState(blockPos.up()).isAir()) {
                            if (world.getRandom().nextFloat() < 0.3f) {
                                world.setBlockState(blockPos.up(), blockState);
                            }
                        }
                    }
                } else if (fertilizable.canGrow(world, world.random, pos, state)) {
                    fertilizable.grow((ServerWorld) world, world.random, pos, state);
                }

                stack.decrement(1);
            }

            return true;
        }

        return false;
    }

}
