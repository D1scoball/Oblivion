package net.oblivion.entity;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SchoolingFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.oblivion.init.ItemInit;

import java.util.EnumSet;

public class Piranha extends SchoolingFishEntity {

    public Piranha(EntityType<? extends SchoolingFishEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createPiranhaAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 14.0D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 20.0);
    }

    public static boolean canPiranhaSpawn(EntityType<Piranha> type, WorldAccess world, SpawnReason reason, BlockPos pos, Random random) {
        return world.getFluidState(pos.down()).isIn(FluidTags.WATER) && world.getBlockState(pos.up()).isOf(Blocks.WATER) || WaterCreatureEntity.canSpawn(type, world, reason, pos, random);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new AttackGoal(this, 1.7f, false));
        this.goalSelector.add(1, new FollowGroupLeaderGoal(this));
        this.goalSelector.add(2, new SwimToRandomPlaceGoal(this));

        this.targetSelector.add(1, new RevengeGoal(this).setGroupRevenge(Piranha.class));
        this.targetSelector.add(2, new ActiveTargetGoal(this, PlayerEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal(this, AnimalEntity.class, true));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_TROPICAL_FISH_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_TROPICAL_FISH_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_TROPICAL_FISH_HURT;
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_TROPICAL_FISH_FLOP;
    }

    @Override
    public ItemStack getBucketItem() {
        return new ItemStack(ItemInit.PIRANHA_BUCKET);
    }

    private static class SwimToRandomPlaceGoal extends SwimAroundGoal {

        private final Piranha piranha;

        public SwimToRandomPlaceGoal(Piranha piranha) {
            super(piranha, 1.0, 40);
            this.piranha = piranha;
        }

        @Override
        public boolean canStart() {
            return !this.piranha.hasLeader() && super.canStart();
        }
    }

    private static class AttackGoal extends Goal {
        protected final PathAwareEntity mob;
        private final double speed;
        private final boolean pauseWhenMobIdle;
        private Path path;
        private double targetX;
        private double targetY;
        private double targetZ;
        private int updateCountdownTicks;
        private int cooldown;
        private long lastUpdateTime;

        public AttackGoal(PathAwareEntity mob, double speed, boolean pauseWhenMobIdle) {
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
                } else if (!livingEntity.isTouchingWater()) {
                    return false;
                } else {
                    this.path = this.mob.getNavigation().findPathTo(livingEntity, 0);
                    return this.path != null || this.mob.isInAttackRange(livingEntity);
                }
            }
        }

        @Override
        public boolean shouldContinue() {
            LivingEntity livingEntity = this.mob.getTarget();
            if (livingEntity == null) {
                return false;
            } else if (!livingEntity.isAlive()) {
                return false;
            } else if (!livingEntity.isTouchingWater()) {
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

            System.out.println("START TEST");
        }

        @Override
        public void stop() {
            LivingEntity livingEntity = this.mob.getTarget();
            if (!EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(livingEntity)) {
                this.mob.setTarget(null);
            }

            this.mob.setAttacking(false);
            this.mob.getNavigation().stop();
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
            if (this.cooldown <= 0 && this.mob.isInAttackRange(target) && this.mob.getVisibilityCache().canSee(target)) {
                this.cooldown = this.getTickCount(20);
                this.mob.swingHand(Hand.MAIN_HAND);
                this.mob.tryAttack(target);

                System.out.println("ATTACK " + target);
            }
        }
    }
}
