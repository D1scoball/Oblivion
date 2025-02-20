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
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.DyeColor;
import net.minecraft.world.World;
import net.oblivion.init.EntityInit;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class HornedSheep extends SheepEntity implements Angerable {

    private static final TrackedData<Integer> ANGER_TIME = DataTracker.registerData(HornedSheep.class, TrackedDataHandlerRegistry.INTEGER);

    @Nullable
    private UUID angryAt;

    private int eatGrassTimer;
    private EatGrassGoal eatGrassGoal;

    public HornedSheep(EntityType<? extends HornedSheep> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createHornedSheepAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 60.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.22D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 20.0);
    }

    @Override
    protected void initGoals() {
        this.eatGrassGoal = new EatGrassGoal(this);
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.2, true));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.25, hornedSheep -> hornedSheep.isBaby() ? DamageTypeTags.PANIC_CAUSES : DamageTypeTags.PANIC_ENVIRONMENTAL_CAUSES));
        this.goalSelector.add(2, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(3, new TemptGoal(this, 1.1, stack -> stack.isIn(ItemTags.SHEEP_FOOD), false));
        this.goalSelector.add(4, new FollowParentGoal(this, 1.1));
        this.goalSelector.add(5, this.eatGrassGoal);
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));

        this.targetSelector.add(1, new HornedSheep.ProtectBabiesGoal());
        this.targetSelector.add(2, new RevengeGoal(this));
        this.targetSelector.add(3, new ActiveTargetGoal(this, PlayerEntity.class, 10, true, false, livingEntity -> this.shouldAngerAt((LivingEntity) livingEntity)));
    }

    @Override
    protected void mobTick() {
        this.eatGrassTimer = this.eatGrassGoal.getTimer();
        if (this.getBreedingAge() != 0) {
            this.resetLoveTicks();
        }
    }


    @Override
    public void tickMovement() {
        if (this.getWorld().isClient()) {
            this.eatGrassTimer = Math.max(0, this.eatGrassTimer - 1);
        } else {
            this.tickAngerLogic((ServerWorld) this.getWorld(), true);
        }

        super.tickMovement();
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(ANGER_TIME, 0);
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


    @Override
    @Nullable
    public SheepEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        HornedSheep hornedSheep = EntityInit.HORNED_SHEEP.create(serverWorld);
        if (hornedSheep != null) {
            hornedSheep.setColor(this.getChildColor(this, (AnimalEntity) passiveEntity));
        }

        return hornedSheep;
    }

    private DyeColor getChildColor(AnimalEntity firstParent, AnimalEntity secondParent) {
        DyeColor dyeColor = ((SheepEntity) firstParent).getColor();
        DyeColor dyeColor2 = ((SheepEntity) secondParent).getColor();
        CraftingRecipeInput craftingRecipeInput = createChildColorRecipeInput(dyeColor, dyeColor2);
        return this.getWorld()
                .getRecipeManager()
                .getFirstMatch(RecipeType.CRAFTING, craftingRecipeInput, this.getWorld())
                .map(recipe -> ((CraftingRecipe) recipe.value()).craft(craftingRecipeInput, this.getWorld().getRegistryManager()))
                .map(ItemStack::getItem)
                .filter(DyeItem.class::isInstance)
                .map(DyeItem.class::cast)
                .map(DyeItem::getColor)
                .orElseGet(() -> this.getWorld().random.nextBoolean() ? dyeColor : dyeColor2);
    }

    private static CraftingRecipeInput createChildColorRecipeInput(DyeColor firstColor, DyeColor secondColor) {
        return CraftingRecipeInput.create(2, 1, List.of(new ItemStack(DyeItem.byColor(firstColor)), new ItemStack(DyeItem.byColor(secondColor))));
    }

    private class ProtectBabiesGoal extends ActiveTargetGoal<PlayerEntity> {
        public ProtectBabiesGoal() {
            super(HornedSheep.this, PlayerEntity.class, 20, true, true, null);
        }

        @Override
        public boolean canStart() {
            if (!HornedSheep.this.isBaby()) {
                if (super.canStart()) {
                    for (HornedSheep hornedSheep : HornedSheep.this.getWorld()
                            .getNonSpectatingEntities(HornedSheep.class, HornedSheep.this.getBoundingBox().expand(8.0, 4.0, 8.0))) {
                        if (hornedSheep.isBaby()) {
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
