package net.oblivion.entity;

import java.util.EnumSet;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.oblivion.init.EntityInit;
import org.jetbrains.annotations.Nullable;

public class Shlama extends AnimalEntity implements Shearable {

    public static final TrackedData<Boolean> EARS = DataTracker.registerData(Shlama.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> SHEARED = DataTracker.registerData(Shlama.class, TrackedDataHandlerRegistry.BOOLEAN);

    private int woolGrouth = 3000;

    public Shlama(EntityType<? extends Shlama> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createShlamaAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 60.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.5D));
        this.goalSelector.add(2, new AnimalMateGoal(this, 0.8D));
        this.goalSelector.add(3, new TemptGoal(this, 0.8D, Ingredient.ofItems(Items.SHORT_GRASS, Items.TALL_GRASS, Items.FERN), true));
        this.goalSelector.add(4, new FollowParentGoal(this, 1.0D));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 0.8D));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(SHEARED, false);
        builder.add(EARS, false);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Sheared", this.isSheared());
        nbt.putBoolean("Ears", this.dataTracker.get(EARS));
        nbt.putInt("WoolGrowth", this.woolGrouth);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setSheared(nbt.getBoolean("Sheared"));
        this.dataTracker.set(EARS, nbt.getBoolean("Ears"));
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

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        this.dataTracker.set(EARS, world.getRandom().nextFloat() < 0.3f);
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    @Override
    public Shlama createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        return EntityInit.SHLAMA.create(serverWorld);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.SHORT_GRASS) || stack.isOf(Items.FERN) || stack.isOf(Items.TALL_GRASS);
    }

    @Override
    public void sheared(SoundCategory shearedSoundCategory) {
        this.getWorld().playSoundFromEntity(null, this, SoundEvents.ENTITY_SHEEP_SHEAR, shearedSoundCategory, 1.0F, 1.0F);
        this.setSheared(true);
        ItemScatterer.spawn(this.getWorld(), this.getX(), this.getY() + this.getHeight() / 1.5f, this.getZ(), new ItemStack(Blocks.WHITE_WOOL, 1 + this.random.nextInt(4)));
    }

    @Override
    public boolean isShearable() {
        return this.isAlive() && !this.isSheared() && !this.isBaby();
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
}
