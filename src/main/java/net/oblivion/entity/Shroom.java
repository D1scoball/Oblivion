package net.oblivion.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MushroomPlantBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.oblivion.init.SoundInit;
import org.jetbrains.annotations.Nullable;

public class Shroom extends HostileEntity {

    public static final TrackedData<Integer> SIZE = DataTracker.registerData(Shroom.class, TrackedDataHandlerRegistry.INTEGER);
    public static final TrackedData<Integer> TILT = DataTracker.registerData(Shroom.class, TrackedDataHandlerRegistry.INTEGER);
    public static final TrackedData<Boolean> MUSHROOMS = DataTracker.registerData(Shroom.class, TrackedDataHandlerRegistry.BOOLEAN);

    public Shroom(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createShroomAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 60.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.24F)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0)
                .add(EntityAttributes.GENERIC_ARMOR, 1.0);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(4, new LookAroundGoal(this));

        this.targetSelector.add(1, new RevengeGoal(this).setGroupRevenge(Shroom.class));
        this.targetSelector.add(2, new ActiveTargetGoal(this, PlayerEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal(this, MerchantEntity.class, false));
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(SIZE, 1);
        builder.add(TILT, 0);
        builder.add(MUSHROOMS, true);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setSize(nbt.getInt("Size"), false);
        this.dataTracker.set(TILT, nbt.getInt("Tilt"));
        this.dataTracker.set(MUSHROOMS, nbt.getBoolean("Mushrooms"));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Size", this.getSize());
        nbt.putInt("Tilt", this.dataTracker.get(TILT));
        nbt.putBoolean("Mushrooms", this.dataTracker.get(MUSHROOMS));
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient() && this.getWorld().getTime() % 20 == 0 && this.getRandom().nextFloat() < 0.01f) {
            for (int i = 0; i < 4; i++) {
                BlockPos blockPos = this.getBlockPos().offset(Direction.fromHorizontal(i));
                BlockState state = this.getWorld().getBlockState(blockPos);
                if (state.getBlock() instanceof MushroomPlantBlock mushroomPlantBlock) {
                    mushroomPlantBlock.grow((ServerWorld) this.getWorld(), this.getRandom(), blockPos, state);
                    break;
                }
            }
        }
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        if (SIZE.equals(data)) {
            this.calculateDimensions();
            this.setYaw(this.headYaw);
            this.bodyYaw = this.headYaw;
        }
        super.onTrackedDataSet(data);
    }

    @Override
    protected float getSoundVolume() {
        return 1.2F - 0.1F * (float) this.getSize();
    }

    @Override
    public float getSoundPitch() {
        return 1.2F - 0.1F * (float) this.getSize();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundInit.SHROOM_IDLE_EVENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundInit.SHROOM_HURT_EVENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundInit.SHROOM_DEATH_EVENT;
    }

//    @Override
//    protected void playStepSound(BlockPos pos, BlockState state) {
//        this.playSound(SoundEvents.ENTITY_STEP, 1.0F, 1.0F);
//    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        this.setSize(this.getRandom().nextInt(3) + 1, true);
        this.dataTracker.set(TILT, world.getRandom().nextInt(5));
        this.dataTracker.set(MUSHROOMS, world.getRandom().nextFloat() < 0.5f);
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    @Override
    protected EntityDimensions getBaseDimensions(EntityPose pose) {
        return super.getBaseDimensions(pose).scaled(0.7F + 0.13F * (float) this.getSize());
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (!this.getWorld().isClient() && source.getAttacker() != null && source.isDirect() && this.getRandom().nextFloat() < 0.2f) {
            AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(this.getWorld(), this.getX(), this.getY(), this.getZ());
            areaEffectCloudEntity.setRadius(2.5F + this.getRandom().nextInt(4));
            areaEffectCloudEntity.setRadiusOnUse(-0.5F);
            areaEffectCloudEntity.setWaitTime(10);
            areaEffectCloudEntity.setDuration(areaEffectCloudEntity.getDuration() / 3);
            areaEffectCloudEntity.setRadiusGrowth(-areaEffectCloudEntity.getRadius() / (float) areaEffectCloudEntity.getDuration());
            areaEffectCloudEntity.addEffect(new StatusEffectInstance(StatusEffects.POISON, 100 + this.getRandom().nextInt(60), this.getRandom().nextInt(2) + (this.dataTracker.get(MUSHROOMS) ? 1 : 0)));
            this.getWorld().spawnEntity(areaEffectCloudEntity);
        }
        return super.damage(source, amount);
    }

    @Override
    public boolean addStatusEffect(StatusEffectInstance effect, @Nullable Entity source) {
        if (effect.getEffectType().equals(StatusEffects.POISON)) {
            return false;
        }
        return super.addStatusEffect(effect, source);
    }

    @Override
    protected int getXpToDrop() {
        return 6 + this.getWorld().getRandom().nextInt(3);
    }

    public int getSize() {
        return this.dataTracker.get(SIZE);
    }

    public void setSize(int size, boolean heal) {
        this.dataTracker.set(SIZE, size);
        this.refreshPosition();
        this.calculateDimensions();
        this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(60D + (float) size * 5D);
        this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0.26F - 0.012F * (float) size);
        this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue((double) size * 3D + 5D);
        if (heal) {
            this.setHealth(this.getMaxHealth());
        }
    }
}
