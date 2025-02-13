package net.oblivion.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import net.oblivion.init.EntityInit;
import net.oblivion.init.SoundInit;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class ElysianElk extends AnimalEntity implements Angerable {

    private static final TrackedData<Integer> ANGER_TIME = DataTracker.registerData(ElysianElk.class, TrackedDataHandlerRegistry.INTEGER);

    @Nullable
    private UUID angryAt;

    public ElysianElk(EntityType<? extends ElysianElk> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createElysianElkAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 80.0D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.24F)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 7.0)
                .add(EntityAttributes.GENERIC_ARMOR, 3.0);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.6, true));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.8, elk -> elk.isBaby() ? DamageTypeTags.PANIC_CAUSES : DamageTypeTags.PANIC_ENVIRONMENTAL_CAUSES));
        this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));

        this.targetSelector.add(1, new ActiveTargetGoal(this, PlayerEntity.class, 10, true, false, livingEntity -> this.shouldAngerAt((LivingEntity) livingEntity)));
    }

    @Override
    public void tickMovement() {
        super.tickMovement();

        if (!this.getWorld().isClient()) {
            this.tickAngerLogic((ServerWorld) this.getWorld(), true);

            // super rarely breed
            if (this.getRandom().nextInt(1800000) == 7) {
                for (ElysianElk elysianElk : this.getWorld()
                        .getNonSpectatingEntities(ElysianElk.class, this.getBoundingBox().expand(12.0, 4.0, 12.0))) {
                    if (!elysianElk.isBaby()) {
                        this.setLoveTicks(600);
                        elysianElk.setLoveTicks(600);
                        break;
                    }
                }
            }
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundInit.ELYSIAN_ELK_IDLE_EVENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundInit.ELYSIAN_ELK_HURT_EVENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundInit.ELYSIAN_ELK_DEATH_EVENT;
    }

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
    public ElysianElk createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        return EntityInit.ELYSIAN_ELK.create(serverWorld);
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
    public boolean isUniversallyAngry(World world) {
        return !this.isBaby();
    }

    @Override
    protected int getXpToDrop() {
        return 5 + this.getWorld().getRandom().nextInt(3);
    }

}
