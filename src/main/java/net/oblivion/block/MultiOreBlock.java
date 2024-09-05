package net.oblivion.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.*;

public class MultiOreBlock extends Block {

    public MultiOreBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient()) {
            for (BlockPos blockPos : getMultiOreBlockPoses(world, pos)) {
                world.breakBlock(blockPos, true, player);
            }
        }
        return super.onBreak(world, pos, state, player);
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        super.onBroken(world, pos, state);
    }

    public List<BlockPos> getMultiOreBlockPoses(World world, BlockPos startPos) {
        Set<BlockPos> visited = new HashSet<>();  // Use a set for fast lookup
        List<BlockPos> poses = new ArrayList<>();
        Stack<BlockPos> stack = new Stack<>();
        stack.push(startPos);

        // Start DFS
        while (!stack.isEmpty()) {
            BlockPos currentPos = stack.pop();

            // Skip if already visited
            if (visited.contains(currentPos)) {
                continue;
            }

            // Mark the current position as visited
            visited.add(currentPos);

            // Check if the current block matches the required type
            if (!world.getBlockState(currentPos).isOf(this.asBlock())) {
                continue;
            }

            // Add to the list of poses
            poses.add(currentPos);

            // Add neighboring positions to the stack for further exploration
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dz = -1; dz <= 1; dz++) {
                        // Skip the current block itself
                        if (dx == 0 && dy == 0 && dz == 0) {
                            continue;
                        }

                        // Calculate neighboring position
                        BlockPos neighborPos = currentPos.add(dx, dy, dz);

                        // Skip if already visited
                        if (!visited.contains(neighborPos)) {
                            stack.push(neighborPos);
                        }
                    }
                }
            }
        }

        return poses;
    }

}
