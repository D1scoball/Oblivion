package net.oblivion.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.TntBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.oblivion.block.entity.GuidelightBlockEntity;
import net.oblivion.init.BlockInit;
import net.oblivion.init.WorldInit;
import net.oblivion.state.GuidelightPersistantState;
import org.jetbrains.annotations.Nullable;

public class GuidelightBlock extends BlockWithEntity {

    public GuidelightBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return null;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GuidelightBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, BlockInit.GUIDELIGHT_BLOCK_ENTITY, world.isClient() ? GuidelightBlockEntity::clientTick : GuidelightBlockEntity::serverTick);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient() && world.getRegistryKey() == WorldInit.OBLIVION_WORLD) {
            world.createExplosion(null, Explosion.createDamageSource(world, null), null, pos.getX(), pos.getY(), pos.getZ(), 7.0F, false, World.ExplosionSourceType.BLOCK);
            GuidelightPersistantState guidelightPersistantState = GuidelightPersistantState.getGuidelightPersistentState(((ServerWorld) world).getServer());
            if (guidelightPersistantState.getGuidelightPos().equals(pos)) {
                guidelightPersistantState.setGuidelightPos(BlockPos.ORIGIN);
                guidelightPersistantState.markDirty();
            }
        }
        return super.onBreak(world, pos, state, player);
    }
}
