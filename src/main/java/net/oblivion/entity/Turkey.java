package net.oblivion.entity;

import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.oblivion.init.EntityInit;
import net.oblivion.init.SoundInit;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Turkey extends AnimalEntity implements Saddleable {

    private static final TrackedData<Boolean> SADDLED = DataTracker.registerData(Turkey.class, TrackedDataHandlerRegistry.BOOLEAN);

    public float flapProgress;
    public float maxWingDeviation;
    public float prevMaxWingDeviation;
    public float prevFlapProgress;

    private Map<UUID, Integer> playerFavoriteMap = new HashMap<>();

    public Turkey(EntityType<? extends Turkey> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createTurkeyAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.215D);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 2.0D));
        this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(3, new TemptGoal(this, 1.0D, Ingredient.ofItems(Items.SHORT_GRASS, Items.TALL_GRASS, Items.FERN), true));
        this.goalSelector.add(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(SADDLED, false);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(SADDLED, nbt.getBoolean("Saddle"));

        this.playerFavoriteMap.clear();
        for (int i = 0; i < nbt.getInt("PlayerFavorites"); i++) {
            this.playerFavoriteMap.put(nbt.getUuid("PlayerFavorite" + i), nbt.getInt("PlayerFavoriteValue" + i));
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Saddle", this.isSaddled());

        nbt.putInt("PlayerFavorites", this.playerFavoriteMap.size());
        int count = 0;
        for (Map.Entry<UUID, Integer> entry : this.playerFavoriteMap.entrySet()) {
            nbt.putUuid("PlayerFavorite" + count, entry.getKey());
            nbt.putInt("PlayerFavoriteValue" + count, entry.getValue());
            count++;
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.getWorld().isClient() && this.getWorld().getTime() % 40 == 0) {
            if (!this.playerFavoriteMap.isEmpty()) {
                this.playerFavoriteMap.replaceAll((k, v) -> v - 1);
                if (this.getFirstPassenger() instanceof PlayerEntity playerEntity && !playerEntity.isCreative()) {
                    if (this.playerFavoriteMap.containsKey(this.getFirstPassenger().getUuid())) {
                        if (this.playerFavoriteMap.get(this.getFirstPassenger().getUuid()) <= 0) {
                            this.getFirstPassenger().stopRiding();
                            spawnPlayerReactionParticles(false);
                        } else {
                            this.playerFavoriteMap.put(this.getFirstPassenger().getUuid(), this.playerFavoriteMap.get(this.getFirstPassenger().getUuid()) - 1);
                        }
                    } else {
                        this.getFirstPassenger().stopRiding();
                        spawnPlayerReactionParticles(false);
                    }
                }
            } else if (this.getFirstPassenger() != null) {
                this.getFirstPassenger().stopRiding();
                spawnPlayerReactionParticles(false);
            }
        }
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        Vec3d vec3d = this.getVelocity();

        this.prevFlapProgress = this.flapProgress;
        this.prevMaxWingDeviation = this.maxWingDeviation;
        this.maxWingDeviation = this.maxWingDeviation + (this.isOnGround() ? -1.0F : 4.0F) * 0.3F;
        this.maxWingDeviation = MathHelper.clamp(this.maxWingDeviation, 0.0F, 1.0F);

        if (!this.isOnGround() && vec3d.y < 0.0) {
            this.setVelocity(vec3d.multiply(1.0f, 0.89f, 1.0f));
        }
        this.flapProgress = this.flapProgress + 0.4F;
    }


    @Override
    protected SoundEvent getAmbientSound() {
        return SoundInit.TURKEY_IDLE_EVENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundInit.TURKEY_HURT_EVENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundInit.TURKEY_DEATH_EVENT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15F, 1.0F);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (stack.get(DataComponentTypes.FOOD) != null) {
            if (!this.getWorld().isClient()) {
                if (this.playerFavoriteMap.containsKey(player.getUuid())) {
                    this.playerFavoriteMap.put(player.getUuid(), this.playerFavoriteMap.get(player.getUuid()) + stack.get(DataComponentTypes.FOOD).nutrition() * 10);
                } else {
                    this.playerFavoriteMap.put(player.getUuid(), stack.get(DataComponentTypes.FOOD).nutrition() * 10);
                }
                spawnPlayerReactionParticles(true);
                stack.decrement(1);
            }

            return ActionResult.success(this.getWorld().isClient());
        }
        boolean bl = this.isBreedingItem(stack);
        if (!bl && this.isSaddled() && !this.hasPassengers() && !player.shouldCancelInteraction()) {
            if (!this.getWorld().isClient()) {
                if (player.isSneaking()) {
                    if (player.giveItemStack(new ItemStack(Items.SADDLE))) {
                        player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F);
                    } else {
                        ItemEntity itemEntity = player.dropItem(new ItemStack(Items.SADDLE), false);
                        if (itemEntity != null) {
                            itemEntity.resetPickupDelay();
                            itemEntity.setOwner(player.getUuid());
                        }
                    }
                    this.dataTracker.set(SADDLED, false);
                } else {
                    player.startRiding(this);
                }
            }
            return ActionResult.success(this.getWorld().isClient());
        } else {
            ActionResult actionResult = super.interactMob(player, hand);
            if (!actionResult.isAccepted()) {
                ItemStack itemStack = player.getStackInHand(hand);
                return itemStack.isOf(Items.SADDLE) ? itemStack.useOnEntity(player, this, hand) : ActionResult.PASS;
            } else {
                return actionResult;
            }
        }
    }

    @Override
    public Turkey createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        return EntityInit.TURKEY.create(serverWorld);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.SHORT_GRASS) || stack.isOf(Items.FERN) || stack.isOf(Items.TALL_GRASS);
    }

    @Override
    public boolean canBeSaddled() {
        return this.isAlive() && !this.isBaby();
    }

    @Override
    public void saddle(ItemStack stack, @Nullable SoundCategory soundCategory) {
        this.dataTracker.set(SADDLED, true);
    }

    @Override
    public boolean isSaddled() {
        return this.dataTracker.get(SADDLED);
    }

    @Override
    protected void dropInventory() {
        super.dropInventory();
        if (this.isSaddled()) {
            this.dropItem(Items.SADDLE);
        }
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        if (this.isSaddled()) {
            Entity entity = this.getFirstPassenger();
            if (entity instanceof PlayerEntity playerEntity) {
                return playerEntity;
            }
        }

        return super.getControllingPassenger();
    }

    @Override
    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
        Direction direction = this.getMovementDirection();
        if (direction.getAxis() != Direction.Axis.Y) {
            int[][] offsets = Dismounting.getDismountOffsets(direction);
            BlockPos blockPos = this.getBlockPos();
            BlockPos.Mutable mutable = new BlockPos.Mutable();

            for (EntityPose entityPose : passenger.getPoses()) {
                Box box = passenger.getBoundingBox(entityPose);

                for (int[] offset : offsets) {
                    mutable.set(blockPos.getX() + offset[0], blockPos.getY(), blockPos.getZ() + offset[1]);
                    double d = this.getWorld().getDismountHeight(mutable);
                    if (Dismounting.canDismountInBlock(d)) {
                        Vec3d vec3d = Vec3d.ofCenter(mutable, d);
                        if (Dismounting.canPlaceEntityAt(this.getWorld(), passenger, box.offset(vec3d))) {
                            passenger.setPose(entityPose);
                            return vec3d;
                        }
                    }
                }
            }

        }
        return super.updatePassengerForDismount(passenger);
    }

    @Override
    protected void tickControlled(PlayerEntity controllingPlayer, Vec3d movementInput) {
        super.tickControlled(controllingPlayer, movementInput);
        this.setRotation(controllingPlayer.getYaw(), controllingPlayer.getPitch() * 0.5F);
        this.prevYaw = this.bodyYaw = this.headYaw = this.getYaw();
    }

    @Override
    protected Vec3d getControlledMovementInput(PlayerEntity controllingPlayer, Vec3d movementInput) {
        float f = controllingPlayer.sidewaysSpeed * 0.5F;
        float g = controllingPlayer.forwardSpeed;
        if (g <= 0.0F) {
            g *= 0.25F;
        }

        return new Vec3d(f, 0.0, g);
    }

    @Override
    protected float getSaddledSpeed(PlayerEntity controllingPlayer) {
        return (float) this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) + 0.1f;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source.isOf(DamageTypes.FALL)) {
            return false;
        }
        return super.damage(source, amount);
    }

    @Override
    protected int getXpToDrop() {
        return 4 + this.getWorld().getRandom().nextInt(3);
    }

    private void spawnPlayerReactionParticles(boolean positive) {
        ParticleEffect particleEffect = positive ? ParticleTypes.HEART : ParticleTypes.SMOKE;

        for (int i = 0; i < 7; i++) {
            double d = this.random.nextGaussian() * 0.02;
            double e = this.random.nextGaussian() * 0.02;
            double f = this.random.nextGaussian() * 0.02;
            ((ServerWorld) this.getWorld()).spawnParticles(particleEffect, this.getParticleX(1.0), this.getRandomBodyY() + 0.5, this.getParticleZ(1.0), 1, d, e, f, 0.0D);
        }
    }

}
