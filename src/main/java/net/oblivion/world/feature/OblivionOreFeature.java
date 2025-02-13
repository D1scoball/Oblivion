package net.oblivion.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.ArrayList;
import java.util.List;

public class OblivionOreFeature extends Feature<OblivionOreFeatureConfig> {

    public OblivionOreFeature(Codec<OblivionOreFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<OblivionOreFeatureConfig> context) {
        OblivionOreFeatureConfig oblivionOreFeatureConfig = context.getConfig();

        List<BlockPos> oreBlockPoses = new ArrayList<>();
        ServerWorldAccess world = context.getWorld();
        for (int i = 0; i < oblivionOreFeatureConfig.size(); i++) {
            for (int u = 0; u < oblivionOreFeatureConfig.size(); u++) {
                for (int o = 0; o < oblivionOreFeatureConfig.size(); o++) {
                    BlockPos pos = context.getOrigin().south(i).east(u).down(o);

                    for (Direction direction : Direction.values()) {
                        if (world.isAir(pos.offset(direction))) {
                            return false;
                        }
                    }
                    if (oblivionOreFeatureConfig.target().test(world.getBlockState(pos), context.getRandom())) {
                        oreBlockPoses.add(pos);
                    } else {
                        return false;
                    }
                }
            }
        }
        for (BlockPos orePos : oreBlockPoses) {
            world.setBlockState(orePos, oblivionOreFeatureConfig.blockStateProvider().get(context.getRandom(), orePos), Block.NOTIFY_ALL);
        }
        return true;
    }

}
