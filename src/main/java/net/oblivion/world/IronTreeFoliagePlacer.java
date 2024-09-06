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

public class IronTreeFoliagePlacer extends FoliagePlacer {
    public static final MapCodec<IronTreeFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(instance -> fillFoliagePlacerFields(instance).apply(instance, IronTreeFoliagePlacer::new));

    public IronTreeFoliagePlacer(IntProvider intProvider, IntProvider intProvider2) {
        super(intProvider, intProvider2);
    }

    @Override
    protected FoliagePlacerType<?> getType() {
        return WorldInit.IRON_TREE_FOLIAGE_PLACER;
    }

    @Override
    protected void generate(TestableWorld world, FoliagePlacer.BlockPlacer placer, Random random, TreeFeatureConfig config, int trunkHeight, FoliagePlacer.TreeNode treeNode, int foliageHeight, int radius, int offset) {
        BlockPos blockPos = treeNode.getCenter().up(offset);
        this.generateSquare(world, placer, random, config, blockPos, radius + treeNode.getFoliageRadius(), -1 - foliageHeight, false);
        this.generateSquare(world, placer, random, config, blockPos, radius - 1, -foliageHeight, false);
        this.generateSquare(world, placer, random, config, blockPos, radius + treeNode.getFoliageRadius() - 1, 0, false);
        this.generateSquare(world, placer, random, config, blockPos, radius + treeNode.getFoliageRadius() - 2, -2, false);

        int lowerBranch = 3 + random.nextInt(2);
        Direction lowerDirection = Direction.fromHorizontal(random.nextInt(4));
        int lowerBranchDistance = 1+ random.nextInt(2);
        this.generateSquare(world, placer, random, config, blockPos.down(lowerBranch).offset(lowerDirection,lowerBranchDistance), radius + treeNode.getFoliageRadius() - 1, -1 - foliageHeight, false);
        this.generateSquare(world, placer, random, config, blockPos.down(lowerBranch).offset(lowerDirection,lowerBranchDistance), radius + treeNode.getFoliageRadius() - 2, 0, false);

        int lowestBranch = lowerBranch + 1 + random.nextInt(2);
        Direction lowestDirection = lowerDirection.getOpposite();
        int lowestBranchDistance = 1+ random.nextInt(2);
        this.generateSquare(world, placer, random, config, blockPos.down(lowestBranch).offset(lowestDirection, lowestBranchDistance), radius + treeNode.getFoliageRadius() - 1, -1 - foliageHeight, false);
        this.generateSquare(world, placer, random, config, blockPos.down(lowestBranch).offset(lowestDirection, lowestBranchDistance), radius + treeNode.getFoliageRadius() - 2, 0, false);
    }

    @Override
    public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
        return 0;
    }

    @Override
    protected boolean isInvalidForLeaves(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
        return y == 0 ? (dx > 1 || dz > 1) && dx != 0 && dz != 0 : dx == radius && dz == radius && radius > 0;
    }
}

