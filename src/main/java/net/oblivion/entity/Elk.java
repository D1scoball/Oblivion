package net.oblivion.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.oblivion.init.EntityInit;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class Elk extends AnimalEntity implements Angerable {

    private static final TrackedData<Integer> ANGER_TIME = DataTracker.registerData(Elk.class, TrackedDataHandlerRegistry.INTEGER);

    @Nullable
    private UUID angryAt;

    public Elk(EntityType<? extends Elk> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createElkAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 40.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.235F)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.6, true));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.8, elk -> elk.isBaby() ? DamageTypeTags.PANIC_CAUSES : DamageTypeTags.PANIC_ENVIRONMENTAL_CAUSES));
        this.goalSelector.add(2, new WanderNearTargetGoal(this, 0.9, 32.0F));
        this.goalSelector.add(2, new AnimalMateGoal(this, 0.8D));
        this.goalSelector.add(3, new TemptGoal(this, 1.0D, Ingredient.ofItems(Items.SHORT_GRASS, Items.TALL_GRASS, Items.FERN), false));
        this.goalSelector.add(4, new FollowParentGoal(this, 1.2D));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.targetSelector.add(1, new Elk.ProtectBabiesGoal());
        this.targetSelector.add(2, new RevengeGoal(this));
        this.targetSelector.add(3, new ActiveTargetGoal(this, PlayerEntity.class, 10, true, false, livingEntity -> this.shouldAngerAt((LivingEntity) livingEntity)));
    }

    @Override
    public void tickMovement() {
        super.tickMovement();

        if (!this.getWorld().isClient()) {
            this.tickAngerLogic((ServerWorld) this.getWorld(), true);
        }
    }

//    @Override
//    protected SoundEvent getAmbientSound() {
//        return this.isBaby() ? SoundInit.BABY_DEER_IDLE_EVENT : SoundInit.DEER_IDLE_EVENT;
//    }
//
//    @Override
//    protected SoundEvent getHurtSound(DamageSource source) {
//        return this.isBaby() ? SoundInit.BABY_DEER_HURT_EVENT : SoundInit.DEER_HURT_EVENT;
//    }
//
//    @Override
//    protected SoundEvent getDeathSound() {
//        return this.isBaby() ? SoundInit.BABY_DEER_HURT_EVENT : SoundInit.DEER_DEATH_EVENT;
//    }

//    @Override
//    protected void playStepSound(BlockPos pos, BlockState state) {
//        this.playSound(SoundEvents.ENTITY_WOLF_STEP, 0.15F, 1.0F);
//    }

    @Override
    protected void initDataTracker(net.minecraft.entity.data.DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(ANGER_TIME, 0);
    }

    @Override
    public Elk createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        return EntityInit.ELK.create(serverWorld);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.SHORT_GRASS) || stack.isOf(Items.FERN) || stack.isOf(Items.TALL_GRASS);
    }

    @Override
    public int getAngerTime() {
        return this.dataTracker.get(ANGER_TIME);
    }

    @Override
    public void setAngerTime(int angerTime) {
        this.dataTracker.set(ANGER_TIME, angerTime);
    }

    @Override
    public void chooseRandomAngerTime() {
        this.setAngerTime(this.getRandom().nextInt(300) + 600);
    }

    @Nullable
    @Override
    public UUID getAngryAt() {
        return this.angryAt;
    }

    @Override
    public void setAngryAt(@Nullable UUID angryAt) {
        this.angryAt = angryAt;
    }

    @Override
    protected int getXpToDrop() {
        return 3 + this.getWorld().getRandom().nextInt(3);
    }

    private class ProtectBabiesGoal extends ActiveTargetGoal<PlayerEntity> {
        public ProtectBabiesGoal() {
            super(Elk.this, PlayerEntity.class, 20, true, true, null);
        }

        @Override
        public boolean canStart() {
            if (!Elk.this.isBaby()) {
                if (super.canStart()) {
                    for (Elk elk : Elk.this.getWorld()
                            .getNonSpectatingEntities(Elk.class, Elk.this.getBoundingBox().expand(8.0, 4.0, 8.0))) {
                        if (elk.isBaby()) {
                            return true;
                        }
                    }
                }

            }
            return false;
        }

        @Override
        protected double getFollowRange() {
            return super.getFollowRange() * 0.5;
        }
    }

}
