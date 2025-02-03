package net.oblivion.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.oblivion.OblivionMain;

public class ElysianWolf extends HostileEntity {

    private static final EntityAttributeModifier RAGE_MODIFIER = new EntityAttributeModifier(OblivionMain.identifierOf("rage_modifier"), 5D, EntityAttributeModifier.Operation.ADD_VALUE);

    public ElysianWolf(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createElysianWolfAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3F)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10.0)
                .add(EntityAttributes.GENERIC_ARMOR, 2.0);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 0.8));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(4, new LookAroundGoal(this));

        this.targetSelector.add(1, new RevengeGoal(this).setGroupRevenge(ElysianWolf.class, ElysianElk.class, ElysianShaman.class));
        this.targetSelector.add(2, new ActiveTargetGoal(this, PlayerEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal(this, MerchantEntity.class, false));
        this.targetSelector.add(3, new ActiveTargetGoal(this, IronGolemEntity.class, true));
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (!this.getWorld().isClient() && this.getHealth() < (this.getMaxHealth() / 2)) {
            if (!this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).hasModifier(OblivionMain.identifierOf("rage_modifier"))) {
                this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).addTemporaryModifier(RAGE_MODIFIER);
            }
        }
        return super.damage(source, amount);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return super.getAmbientSound();
//        return SoundInit.ORC_IDLE_EVENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return super.getHurtSound(source);
//        return SoundInit.ORC_HURT_EVENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return super.getDeathSound();
//        return SoundInit.ORC_DEATH_EVENT;
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
        return 5 + this.getWorld().getRandom().nextInt(3);
    }

}
