package net.oblivion.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.oblivion.OblivionMain;
import net.oblivion.init.SoundInit;

import java.util.EnumSet;

public class ElysianShaman extends HostileEntity {

    public static final TrackedData<Integer> HAND_USAGE = DataTracker.registerData(ElysianShaman.class, TrackedDataHandlerRegistry.INTEGER);
    public static final TrackedData<Boolean> SPIN_ATTACK = DataTracker.registerData(ElysianShaman.class, TrackedDataHandlerRegistry.BOOLEAN);

    private static final EntityAttributeModifier SPIN_MODIFIER = new EntityAttributeModifier(OblivionMain.identifierOf("spin_modifier"), 3.5D, EntityAttributeModifier.Operation.ADD_VALUE);

    public ElysianShaman(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createElysianShamanAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0F)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.18F)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10F)
                .add(EntityAttributes.GENERIC_ARMOR, 2.0F)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0F);
    }

    // Spin attack
    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new SpinAttack(this, 1.6, false));
        this.goalSelector.add(3, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));

        this.targetSelector.add(1, new RevengeGoal(this).setGroupRevenge(ElysianWolf.class, ElysianElk.class, ElysianShaman.class));
        this.targetSelector.add(2, new ActiveTargetGoal(this, PlayerEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal(this, MerchantEntity.class, false));
        this.targetSelector.add(3, new ActiveTargetGoal(this, IronGolemEntity.class, true));
    }

    public static boolean canSpawn(EntityType<ElysianShaman> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return canSpawnInDark(type, world, spawnReason, pos, random) || spawnReason == SpawnReason.SPAWNER;
    }

    @Override
    protected void initDataTracker(net.minecraft.entity.data.DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(HAND_USAGE, 0);
        builder.add(SPIN_ATTACK, false);
    }

    @Override
    public void swingHand(Hand hand) {
        this.dataTracker.set(HAND_USAGE, this.getRandom().nextInt(3));
        super.swingHand(hand);
    }

    @Override
    protected float getSoundVolume() {
        return 1.0f;
    }

    @Override
    public float getSoundPitch() {
        return 1.0f;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundInit.ELYSIAN_SHAMAN_IDLE_EVENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundInit.ELYSIAN_SHAMAN_HURT_EVENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundInit.ELYSIAN_SHAMAN_DEATH_EVENT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        super.playStepSound(pos, state);
//        this.playSound(SoundInit.ORC_STEP_EVENT, 1.0F, 1.0F);
    }

    @Override
    public int getLimitPerChunk() {
        return 2;
    }

    @Override
    protected int getXpToDrop() {
        return 7 + this.getWorld().getRandom().nextInt(3);
    }

    private static class SpinAttack extends Goal {
        protected final PathAwareEntity mob;
        private final double speed;
        private final boolean pauseWhenMobIdle;
        private Path path;
        private double targetX;
        private double targetY;
        private double targetZ;
        private int updateCountdownTicks;
        private int cooldown;
        private int spinTime;
        private long lastUpdateTime;

        public SpinAttack(ElysianShaman mob, double speed, boolean pauseWhenMobIdle) {
            this.mob = mob;
            this.speed = speed;
            this.pauseWhenMobIdle = pauseWhenMobIdle;
            this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        }

        @Override
        public boolean canStart() {
            long l = this.mob.getWorld().getTime();
            if (l - this.lastUpdateTime < 20L) {
                return false;
            } else {
                this.lastUpdateTime = l;
                LivingEntity livingEntity = this.mob.getTarget();
                if (livingEntity == null) {
                    return false;
                } else if (!livingEntity.isAlive()) {
                    return false;
                } else {
                    this.path = this.mob.getNavigation().findPathTo(livingEntity, 0);
                    return this.path != null;
                }
            }
        }

        @Override
        public boolean shouldContinue() {
            if (this.spinTime > 400) {
                return false;
            }
            LivingEntity livingEntity = this.mob.getTarget();
            if (livingEntity == null) {
                return false;
            } else if (!livingEntity.isAlive()) {
                return false;
            } else if (!this.pauseWhenMobIdle) {
                return !this.mob.getNavigation().isIdle();
            } else {
                return this.mob.isInWalkTargetRange(livingEntity.getBlockPos()) && (!(livingEntity instanceof PlayerEntity) || !livingEntity.isSpectator() && !((PlayerEntity) livingEntity).isCreative());
            }
        }

        @Override
        public void start() {
            this.mob.getNavigation().startMovingAlong(this.path, this.speed);
            this.mob.setAttacking(true);
            this.updateCountdownTicks = 0;
            this.cooldown = 0;
            this.spinTime = 0;
            this.mob.getDataTracker().set(SPIN_ATTACK, true);
        }

        @Override
        public void stop() {
            LivingEntity livingEntity = this.mob.getTarget();
            if (!EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(livingEntity)) {
                this.mob.setTarget(null);
            }

            this.mob.setAttacking(false);
            this.mob.getNavigation().stop();
            this.resetCooldown();
            this.mob.getDataTracker().set(SPIN_ATTACK, false);
        }

        @Override
        public boolean shouldRunEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            LivingEntity livingEntity = this.mob.getTarget();
            if (livingEntity != null) {
                this.mob.getLookControl().lookAt(livingEntity, 30.0F, 30.0F);
                this.updateCountdownTicks = Math.max(this.updateCountdownTicks - 1, 0);
                this.spinTime++;
                if ((this.pauseWhenMobIdle || this.mob.getVisibilityCache().canSee(livingEntity))
                        && this.updateCountdownTicks <= 0
                        && (
                        this.targetX == 0.0 && this.targetY == 0.0 && this.targetZ == 0.0
                                || livingEntity.squaredDistanceTo(this.targetX, this.targetY, this.targetZ) >= 1.0
                                || this.mob.getRandom().nextFloat() < 0.05F
                )) {
                    this.targetX = livingEntity.getX();
                    this.targetY = livingEntity.getY();
                    this.targetZ = livingEntity.getZ();
                    this.updateCountdownTicks = 4 + this.mob.getRandom().nextInt(7);
                    double d = this.mob.squaredDistanceTo(livingEntity);
                    if (d > 1024.0) {
                        this.updateCountdownTicks += 10;
                    } else if (d > 256.0) {
                        this.updateCountdownTicks += 5;
                    }

                    if (!this.mob.getNavigation().startMovingTo(livingEntity, this.speed)) {
                        this.updateCountdownTicks += 15;
                    }

                    this.updateCountdownTicks = this.getTickCount(this.updateCountdownTicks);
                }

                this.cooldown = Math.max(this.cooldown - 1, 0);
                this.attack(livingEntity);
            }
        }

        private void attack(LivingEntity target) {
            if (this.canAttack(target)) {
                this.mob.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_KNOCKBACK).addTemporaryModifier(SPIN_MODIFIER);
                this.mob.tryAttack(target);
                this.mob.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_KNOCKBACK).removeModifier(OblivionMain.identifierOf("spin_modifier"));
                if (this.mob.getRandom().nextFloat() < 0.3f) {
                    stop();
                }
            }
        }

        private void resetCooldown() {
            this.cooldown = this.mob.getRandom().nextInt(200) + 200;
        }

        private boolean isCooledDown() {
            return this.cooldown <= 0;
        }

        private boolean canAttack(LivingEntity target) {
            return this.isCooledDown() && this.mob.isInAttackRange(target) && this.mob.getVisibilityCache().canSee(target);
        }

    }

}
