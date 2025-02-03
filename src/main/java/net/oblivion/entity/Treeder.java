package net.oblivion.entity;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

public class Treeder extends PassiveEntity {

    public static final TrackedData<Boolean> SAPLING = DataTracker.registerData(Treeder.class, TrackedDataHandlerRegistry.BOOLEAN);

    private int growSapling = 48000;
    private int saplingTimer = 4800;

    public Treeder(EntityType<? extends PassiveEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createTreederAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.215D);
    }

    public static boolean isValidNaturalSpawn(EntityType<? extends AnimalEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        boolean bl = SpawnReason.isTrialSpawner(spawnReason) || isLightLevelValidForNaturalSpawn(world, pos);
        return world.getBlockState(pos.down()).isIn(BlockTags.ANIMALS_SPAWNABLE_ON) && bl;
    }

    public static boolean isLightLevelValidForNaturalSpawn(BlockRenderView world, BlockPos pos) {
        return world.getBaseLightLevel(pos, 0) > 8;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 2.0D));
        this.goalSelector.add(3, new TemptGoal(this, 0.8D, Ingredient.fromTag(ItemTags.SAPLINGS), true));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(SAPLING, false);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Sapling", this.dataTracker.get(SAPLING));
        nbt.putInt("SaplingTimer", this.saplingTimer);
        nbt.putInt("GrowSapling", this.growSapling);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(SAPLING, nbt.getBoolean("Sapling"));
        this.saplingTimer = nbt.getInt("SaplingTimer");
        this.growSapling = nbt.getInt("GrowSapling");
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (!this.getWorld().isClient()) {
            if (this.dataTracker.get(SAPLING)) {
                if (this.saplingTimer <= 0) {
                    if (this.saplingTimer == 0 && this.getWorld().getBlockState(this.getBlockPos().down()).isOf(Blocks.GRASS_BLOCK)
                            && this.getWorld().getBlockState(this.getBlockPos()).isAir()) {
                        Iterator<RegistryEntry<Block>> iterator = Registries.BLOCK.iterateEntries(BlockTags.SAPLINGS).iterator();

                        while (iterator.hasNext()) {
                            if (this.getRandom().nextFloat() < 0.1f) {
                                if (iterator.next().value().getDefaultState().canPlaceAt(this.getWorld(), this.getBlockPos())) {
                                    this.getWorld().setBlockState(this.getBlockPos(), iterator.next().value().getDefaultState());
                                    break;
                                }
                            }
                        }
                        if (!this.isSilent()) {
                            this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.BLOCK_CHERRY_SAPLING_PLACE, SoundCategory.NEUTRAL, 1.0F, 1.0F, this.getRandom().nextLong());
                        }
                    }
                    this.saplingTimer = 3600 + this.getWorld().getRandom().nextInt(4800);
                }
                this.saplingTimer--;
            } else {
                this.growSapling--;
                if (this.growSapling <= 0) {
                    this.dataTracker.set(SAPLING, true);
                }
            }
        }
    }

//    @Override
//    protected SoundEvent getAmbientSound() {
//        return SoundInit.FUNGUS_IDLE_EVENT;
//    }
//
//    @Override
//    protected SoundEvent getHurtSound(DamageSource source) {
//        return SoundInit.FUNGUS_HURT_EVENT;
//    }
//
//    @Override
//    protected SoundEvent getDeathSound() {
//        return SoundInit.FUNGUS_DEATH_EVENT;
//    }
//
//    @Override
//    protected void playStepSound(BlockPos pos, BlockState state) {
//        this.playSound(SoundEvents.ENTITY_WOLF_STEP, 0.15F, 1.0F);
//    }


    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        if (world.getRandom().nextFloat() < 0.01f) {
            this.dataTracker.set(SAPLING, true);
        }
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    @Override
    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        return world.getBlockState(pos.down()).isIn(BlockTags.DIRT) ? 10.0F : world.getPhototaxisFavor(pos);
    }

    @Override
    public int getMinAmbientSoundDelay() {
        return 120;
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return false;
    }

    @Override
    protected int getXpToDrop() {
        return 1 + this.getWorld().getRandom().nextInt(3);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }
}
