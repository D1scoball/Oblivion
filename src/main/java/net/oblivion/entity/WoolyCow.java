package net.oblivion.entity;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Shearable;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.oblivion.init.EntityInit;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class WoolyCow extends CowEntity implements Shearable, Angerable {

    private static final TrackedData<Boolean> SHEARED = DataTracker.registerData(WoolyCow.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> ANGER_TIME = DataTracker.registerData(WoolyCow.class, TrackedDataHandlerRegistry.INTEGER);

    @Nullable
    private UUID angryAt;
    private int woolGrouth = 3000;

    public WoolyCow(EntityType<? extends CowEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createWoolyCowAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 60.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.22D) .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 20.0);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.5, true));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 2.0, woolyCow -> woolyCow.isBaby() ? DamageTypeTags.PANIC_CAUSES : DamageTypeTags.PANIC_ENVIRONMENTAL_CAUSES));
        this.goalSelector.add(2, new WanderNearTargetGoal(this, 0.9, 32.0F));
        this.goalSelector.add(2, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(3, new TemptGoal(this, 1.25, stack -> stack.isIn(ItemTags.COW_FOOD), false));
        this.goalSelector.add(4, new FollowParentGoal(this, 1.25));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(7, new LookAroundGoal(this));
        this.targetSelector.add(1, new WoolyCow.ProtectBabiesGoal());
        this.targetSelector.add(2, new RevengeGoal(this));
        this.targetSelector.add(3, new ActiveTargetGoal(this, PlayerEntity.class, 10, true, false, livingEntity -> this.shouldAngerAt((LivingEntity) livingEntity)));
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(SHEARED, false);
        builder.add(ANGER_TIME, 0);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Sheared", this.isSheared());
        nbt.putInt("WoolGrowth", this.woolGrouth);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setSheared(nbt.getBoolean("Sheared"));
        this.woolGrouth = nbt.getInt("WoolGrowth");
    }

    @Override
    public void tick() {
        super.tick();
        if (this.woolGrouth > 0 && this.isSheared()) {
            this.woolGrouth--;
            if (this.woolGrouth == 0) {
                this.setSheared(false);
                this.woolGrouth = 1000 + this.getRandom().nextInt(4000);
            }
        }
    }

    @Override
    public void tickMovement() {
        super.tickMovement();

        if (!this.getWorld().isClient()) {
            this.tickAngerLogic((ServerWorld) this.getWorld(), true);
        }
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.getItem() instanceof ShearsItem) {
            if (!this.getWorld().isClient() && this.isShearable()) {
                this.sheared(SoundCategory.PLAYERS);
                this.emitGameEvent(GameEvent.SHEAR, player);
                itemStack.damage(1, player, getSlotForHand(hand));
                return ActionResult.SUCCESS;
            } else {
                return ActionResult.CONSUME;
            }
        } else {
            return super.interactMob(player, hand);
        }
    }

    @Nullable
    public CowEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        return EntityInit.WOOLY_COW.create(serverWorld);
    }

    @Override
    public void sheared(SoundCategory shearedSoundCategory) {
        this.getWorld().playSoundFromEntity(null, this, SoundEvents.ENTITY_SHEEP_SHEAR, shearedSoundCategory, 1.0F, 1.0F);
        this.setSheared(true);
        ItemScatterer.spawn(this.getWorld(), this.getX(), this.getY() + this.getHeight() / 1.5f, this.getZ(), new ItemStack(Blocks.ORANGE_WOOL, 1 + this.random.nextInt(4)));
    }

    @Override
    public boolean isShearable() {
        return this.isAlive() && !this.isSheared() && !this.isBaby();
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

    public boolean isSheared() {
        return this.dataTracker.get(SHEARED);
    }

    public void setSheared(boolean sheared) {
        this.dataTracker.set(SHEARED, sheared);
    }

    private class ProtectBabiesGoal extends ActiveTargetGoal<PlayerEntity> {
        public ProtectBabiesGoal() {
            super(WoolyCow.this, PlayerEntity.class, 20, true, true, null);
        }

        @Override
        public boolean canStart() {
            if (!WoolyCow.this.isBaby()) {
                if (super.canStart()) {
                    for (WoolyCow woolyCow : WoolyCow.this.getWorld()
                            .getNonSpectatingEntities(WoolyCow.class, WoolyCow.this.getBoundingBox().expand(8.0, 4.0, 8.0))) {
                        if (woolyCow.isBaby()) {
                            return true;
                        }
                    }
                }

            }
            return false;
        }

        @Override
        protected double getFollowRange() {
            return super.getFollowRange() * 0.8;
        }
    }
}
