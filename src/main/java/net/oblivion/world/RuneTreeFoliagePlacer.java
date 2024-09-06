package net.oblivion.world;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import net.oblivion.init.WorldInit;

public class RuneTreeFoliagePlacer extends FoliagePlacer {
    public static final MapCodec<RuneTreeFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(instance -> fillFoliagePlacerFields(instance).apply(instance, RuneTreeFoliagePlacer::new));

    public RuneTreeFoliagePlacer(IntProvider intProvider, IntProvider intProvider2) {
        super(intProvider, intProvider2);
    }

    @Override
    protected FoliagePlacerType<?> getType() {
        return WorldInit.RUNE_TREE_FOLIAGE_PLACER;
    }

    @Override
    protected void generate(TestableWorld world, FoliagePlacer.BlockPlacer placer, Random random, TreeFeatureConfig config, int trunkHeight, FoliagePlacer.TreeNode treeNode, int foliageHeight, int radius, int offset) {
        BlockPos blockPos = treeNode.getCenter().up(offset);

        for (int i = offset; i >= offset - foliageHeight; i--) {
            int heightFromBottom = offset - i;
            int halfHeight = foliageHeight / 2;

            int j;
            if (heightFromBottom <= halfHeight) {
                j = Math.min(radius + treeNode.getFoliageRadius() - 1 + heightFromBottom / 2, radius + treeNode.getFoliageRadius());
            } else {
                j = Math.max(radius + treeNode.getFoliageRadius() - 1 - (heightFromBottom - halfHeight) / 2, 0);
            }

            this.generateSquare(world, placer, random, config, treeNode.getCenter(), j, i, false);
        }
        int lowerBranch = 5 + random.nextInt(2);
        Direction lowerDirection = Direction.fromHorizontal(random.nextInt(4));

        this.generateSquare(world, placer, random, config, blockPos.down(lowerBranch).offset(lowerDirection, 1), radius + treeNode.getFoliageRadius() - 2, 0, false);
        this.generateSquare(world, placer, random, config, blockPos.down(lowerBranch + 1).offset(lowerDirection, 1), radius + treeNode.getFoliageRadius() - 1, 0, false);
    }

    @Override
    public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
        return trunkHeight / 2 + 1;
    }

    @Override
    protected boolean isInvalidForLeaves(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
        return y == 0 ? (dx > 1 || dz > 1) && dx != 0 && dz != 0 : dx == radius && dz == radius && radius > 0;
    }
}

