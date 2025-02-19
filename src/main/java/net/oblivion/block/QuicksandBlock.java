package net.oblivion.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.oblivion.init.BlockInit;
import org.jetbrains.annotations.Nullable;

public class QuicksandBlock extends Block {

    private static final MapCodec<QuicksandBlock> CODEC = createCodec(QuicksandBlock::new);
    private static final VoxelShape SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 15.0, 16.0);
    private static final BooleanProperty BOTTOM = Properties.BOTTOM;

    @Override
    public MapCodec<QuicksandBlock> getCodec() {
        return CODEC;
    }

    public QuicksandBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(BOTTOM, Boolean.FALSE));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(BOTTOM);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        if(!ctx.getWorld().isClient() && ctx.getWorld().getBlockState(ctx.getBlockPos().up()).isOf(BlockInit.QUICKSAND)){
            return this.getDefaultState().with(BOTTOM,true);
        }
        return super.getPlacementState(ctx);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (!world.isClient()) {
            if (world.getBlockState(pos.down()).isOf(BlockInit.QUICKSAND)) {
                world.setBlockState(pos.down(), world.getBlockState(pos.down()).with(BOTTOM, true));
            }
        }
        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        super.onStateReplaced(state, world, pos, newState, moved);
        if (!world.isClient() && !state.isOf(newState.getBlock()) && world.getBlockState(pos.down()).isOf(BlockInit.QUICKSAND)) {
            world.setBlockState(pos.down(), world.getBlockState(pos.down()).with(BOTTOM, false));
        }
    }

    @Override
    protected boolean hasSidedTransparency(BlockState state) {
        return !state.get(BOTTOM);
    }

    @Override
    protected boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        if (stateFrom.isOf(this) && !stateFrom.get(BOTTOM)) {
            return false;
        }
        return stateFrom.isOf(this) || super.isSideInvisible(state, stateFrom, direction);
    }

    @Override
    protected VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.empty();
    }

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!(entity instanceof LivingEntity) || entity.getBlockStateAtPos().isOf(this)) {
            entity.slowMovement(state, new Vec3d(0.8F, 0.15F, 0.8F));
        }

        if (!world.isClient() && Box.from(Vec3d.of(pos)).contains(entity.getEyePos())) {
            entity.damage(entity.getDamageSources().inWall(), 1.0F);
        }
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (!((double) fallDistance < 4.0) && entity instanceof LivingEntity livingEntity) {
            LivingEntity.FallSounds fallSounds = livingEntity.getFallSounds();
            SoundEvent soundEvent = (double) fallDistance < 7.0 ? fallSounds.small() : fallSounds.big();
            entity.playSound(soundEvent, 1.0F, 1.0F);
        }
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (context instanceof EntityShapeContext entityShapeContext) {
            Entity entity = entityShapeContext.getEntity();

            if (entity != null) {
                if (entity.fallDistance > 2.5F) {
                    return SHAPE;
                }

                boolean bl = entity instanceof FallingBlockEntity;
                if (bl || canWalkOnQuicksand(entity) && context.isAbove(VoxelShapes.fullCube(), pos, false) && !context.isDescending()) {
                    return SHAPE;
                }
            }
        }

        return VoxelShapes.empty();
    }

    @Override
    protected VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(BOTTOM)) {
            return super.getOutlineShape(state, world, pos, context);
        }
        return SHAPE;
    }

    public static boolean canWalkOnQuicksand(Entity entity) {
        if (entity.getType().isIn(EntityTypeTags.POWDER_SNOW_WALKABLE_MOBS)) {
            return true;
        } else {
            return entity instanceof LivingEntity livingEntity && livingEntity.getEquippedStack(EquipmentSlot.FEET).isOf(Items.LEATHER_BOOTS);
        }
    }

    @Override
    protected boolean canPathfindThrough(BlockState state, NavigationType type) {
        return false;
    }
}

