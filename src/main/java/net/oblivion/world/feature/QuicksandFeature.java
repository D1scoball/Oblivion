package net.oblivion.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.oblivion.init.BlockInit;

public class QuicksandFeature extends Feature<QuicksandFeatureConfig> {

    public QuicksandFeature(Codec<QuicksandFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<QuicksandFeatureConfig> context) {
        QuicksandFeatureConfig quicksandFeatureConfig = context.getConfig();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();

        if (!structureWorldAccess.getBlockState(blockPos).isIn(quicksandFeatureConfig.validBlocks)) {
            return false;
        } else if (quicksandFeatureConfig.requiresBlockBelow && !structureWorldAccess.getBlockState(blockPos.down()).isIn(quicksandFeatureConfig.validBlocks)) {
            return false;
        } else {
            if (structureWorldAccess.getBlockState(blockPos.up()).isAir()) {
                for (BlockPos pos : BlockPos.iterateOutwards(blockPos, quicksandFeatureConfig.size, quicksandFeatureConfig.depth, quicksandFeatureConfig.size)) {
                    if (!structureWorldAccess.getBlockState(pos).isIn(quicksandFeatureConfig.validBlocks) || structureWorldAccess.getBlockState(pos.up()).isIn(BlockTags.LOGS)) {
                        continue;
                    }
                    if (pos.getY() == blockPos.getY() && blockPos.toCenterPos().distanceTo(pos.toCenterPos()) > quicksandFeatureConfig.size && structureWorldAccess.getRandom().nextFloat() < 0.8f) {
                        continue;
                    }
                    BlockState state = quicksandFeatureConfig.stateProvider.get(structureWorldAccess.getRandom(), pos);
                    if (state.isOf(BlockInit.QUICKSAND) && structureWorldAccess.getBlockState(pos.up()).isOf(BlockInit.QUICKSAND)) {
                        state = state.with(Properties.BOTTOM, true);
                    }
                    structureWorldAccess.setBlockState(pos, state, Block.NOTIFY_LISTENERS);
                }

                return true;
            }
            return false;
        }
    }
}

