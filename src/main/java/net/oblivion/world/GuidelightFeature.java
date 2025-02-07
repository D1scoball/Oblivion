package net.oblivion.world;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.oblivion.init.BlockInit;

public class GuidelightFeature extends Feature<DefaultFeatureConfig> {

    public GuidelightFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        generate(context.getWorld(), context.getOrigin(), false);
        return true;
    }

    public static void generate(ServerWorldAccess world, BlockPos pos, boolean breakBlocks) {
        for (int i = -3; i <= 3; i++) {
            for (int j = -3; j <= 3; j++) {
                BlockPos blockPos = pos.add(j, 0, i);
                if (!world.getBlockState(blockPos).isOf(Blocks.STONE_BRICKS)) {
                    if (breakBlocks) {
                        world.breakBlock(blockPos, true, null);
                    }

                    world.setBlockState(blockPos, Blocks.STONE_BRICKS.getDefaultState(), Block.NOTIFY_ALL);
                }
            }
        }
        world.setBlockState(pos.up(), BlockInit.GUIDELIGHT.getDefaultState(), Block.NOTIFY_ALL);
    }
}

