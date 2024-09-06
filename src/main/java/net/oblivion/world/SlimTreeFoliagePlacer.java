package net.oblivion.world;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import net.oblivion.init.WorldInit;

public class SlimTreeFoliagePlacer extends FoliagePlacer {

    public static final MapCodec<SlimTreeFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(instance -> fillFoliagePlacerFields(instance).apply(instance, SlimTreeFoliagePlacer::new));

    public SlimTreeFoliagePlacer(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

    @Override
    protected FoliagePlacerType<?> getType() {
        return WorldInit.SLIM_TREE_FOLIAGE_PLACER;
    }

    /*
    * "height": 6,
      "offset": 3,
      "radius": 4
    * */

    @Override
    protected void generate(TestableWorld world, FoliagePlacer.BlockPlacer placer, Random random, TreeFeatureConfig config, int trunkHeight, FoliagePlacer.TreeNode treeNode, int foliageHeight, int radius, int offset) {
        BlockPos blockPos = treeNode.getCenter().down(offset);
        int baseRadius = radius + treeNode.getFoliageRadius()- 1;

        for (int y = 0; y <= foliageHeight; y++) {
            int currentRadius = baseRadius - y / 2;
            generateNaturalLayer(world, placer, random, config, blockPos.up(y), currentRadius);
        }

        generateConicalBottom(world, placer, random, config, blockPos.down(1), baseRadius - 1);
    }

    private void generateNaturalLayer(TestableWorld world, FoliagePlacer.BlockPlacer placer, Random random, TreeFeatureConfig config, BlockPos position, int radius) {
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                if (x * x + z * z <= radius * radius + random.nextInt(3) - 1) {
                    placeFoliageBlock(world, placer, random, config, position.add(x, 0, z));
                }
            }
        }
    }

    // New method to generate a more conical bottom for the foliage
    private void generateConicalBottom(TestableWorld world, FoliagePlacer.BlockPlacer placer, Random random, TreeFeatureConfig config, BlockPos basePosition, int radius) {
        int taperHeight = 2; // The height to taper the foliage
        for (int y = 0; y >= -taperHeight; y--) {
            int currentRadius = Math.max(1, radius + y); // Gradually reduce radius, ensuring it's at least 1
            for (int x = -currentRadius; x <= currentRadius; x++) {
                for (int z = -currentRadius; z <= currentRadius; z++) {
                    BlockPos leafPos = basePosition.add(x, y, z);
                    if (x * x + z * z <= currentRadius * currentRadius + random.nextInt(2) - 1) {
                        placeFoliageBlock(world, placer, random, config, leafPos);
                    }
                }
            }
        }
    }

    @Override
    public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
        return 6 + random.nextInt(2);
    }

    @Override
    protected boolean isInvalidForLeaves(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
        return dx == radius && dz == radius && (random.nextInt(2) == 0 || y == 0);
    }
}

