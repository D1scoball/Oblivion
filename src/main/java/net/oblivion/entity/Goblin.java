package net.oblivion.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.oblivion.entity.goal.ComeTogetherGoal;
import net.oblivion.init.SoundInit;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class Goblin extends HostileEntity {

    public static final TrackedData<Integer> SIZE = DataTracker.registerData(Goblin.class, TrackedDataHandlerRegistry.INTEGER);
    public static final TrackedData<Boolean> DEFENDING = DataTracker.registerData(Goblin.class, TrackedDataHandlerRegistry.BOOLEAN);

    private int shieldCooldown = 5;

    public Goblin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createGoblinAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23F)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0)
                .add(EntityAttributes.GENERIC_ARMOR, 2.0);
    }

    public static boolean isValidNaturalSpawn(EntityType<? extends HostileEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        boolean bl = SpawnReason.isTrialSpawner(spawnReason) || world.getBaseLightLevel(pos, 0) > 8;
        return world.getBlockState(pos.down()).isIn(BlockTags.ANIMALS_SPAWNABLE_ON) && bl;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.1, false));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(3, new ComeTogetherGoal(this, 1.0));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));

        this.targetSelector.add(1, new RevengeGoal(this).setGroupRevenge(Goblin.class));
        this.targetSelector.add(2, new ActiveTargetGoal(this, PlayerEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal(this, MerchantEntity.class, false));
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(SIZE, 1);
        builder.add(DEFENDING, false);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setSize(nbt.getInt("Size"), false);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Size", this.getSize());
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
        return SoundInit.GOBLIN_IDLE_EVENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundInit.GOBLIN_HURT_EVENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundInit.GOBLIN_DEATH_EVENT;
    }

//    @Override
//    protected void playStepSound(BlockPos pos, BlockState state) {
//        this.playSound(SoundInit.ORC_STEP_EVENT, 1.0F, 1.0F);
//    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        this.setSize(this.getRandom().nextInt(3) + 1, true);
        if (this.getRandom().nextFloat() <= 0.5f) {
            this.equipStack(EquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
            Optional<RegistryEntry<Item>> optional = Registries.ITEM.getRandomEntry(ItemTags.SWORDS, this.getRandom());
            optional.ifPresent(itemRegistryEntry -> this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(itemRegistryEntry)));
            if (this.getRandom().nextFloat() <= 0.5f) {
                this.equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
            }
        }
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    @Override
    protected EntityDimensions getBaseDimensions(EntityPose pose) {
        return super.getBaseDimensions(pose).scaled(0.8F + 0.1F * (float) this.getSize());
    }

    @Override
    public boolean blockedByShield(DamageSource source) {
        Entity entity = source.getSource();
        boolean bl = entity instanceof PersistentProjectileEntity persistentProjectileEntity && persistentProjectileEntity.getPierceLevel() > 0;
        if (!source.isIn(DamageTypeTags.BYPASSES_SHIELD) && !bl) {
            Vec3d vec3d = source.getPosition();
            if (vec3d != null) {
                Vec3d vec3d2 = this.getRotationVector(0.0F, this.getHeadYaw());
                Vec3d vec3d3 = vec3d.relativize(this.getPos());
                vec3d3 = new Vec3d(vec3d3.x, 0.0, vec3d3.z).normalize();
                return vec3d3.dotProduct(vec3d2) < 0.0;
            }
        }
        return false;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (!this.getWorld().isClient() && this.getDataTracker().get(DEFENDING) && this.blockedByShield(source)) {
            if (!source.isIn(DamageTypeTags.IS_PROJECTILE) && source.getSource() instanceof LivingEntity livingEntity) {
                this.knockback(livingEntity);
            }
            this.playSound(SoundEvents.ITEM_SHIELD_BLOCK, 1.0F, 0.8F + this.getWorld().getRandom().nextFloat() * 0.4F);

            this.getDataTracker().set(DEFENDING, false);
            this.shieldCooldown = 6 + this.getWorld().getRandom().nextInt(5);
            return false;
        }
        return super.damage(source, amount);
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (!this.getWorld().isClient() && this.getWorld().getTime() % 20 == 0 && this.getEquippedStack(EquipmentSlot.OFFHAND).isOf(Items.SHIELD)) {
            if (this.isAttacking() && this.getTarget() != null) {
                if (this.shieldCooldown > 0) {
                    this.shieldCooldown--;
                } else {
                    this.getDataTracker().set(DEFENDING, true);
                }
            } else if (this.getDataTracker().get(DEFENDING)) {
                this.getDataTracker().set(DEFENDING, false);
                this.shieldCooldown = 6 + this.getWorld().getRandom().nextInt(5);
            }
        }
    }

    @Override
    protected int getXpToDrop() {
        return 6 + this.getWorld().getRandom().nextInt(3);
    }

    public int getSize() {
        return this.dataTracker.get(SIZE);
    }

    // Todo: Set correct base values
    public void setSize(int size, boolean heal) {
        this.dataTracker.set(SIZE, size);
        this.refreshPosition();
        this.calculateDimensions();
        this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue((double) (35D + (float) size * 6D));
        this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue((double) (0.26F - 0.012F * (float) size));
        this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue((double) size * 2D + 3D);
        if (heal) {
            this.setHealth(this.getMaxHealth());
        }
    }

}
