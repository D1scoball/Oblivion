package net.oblivion.init;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.oblivion.OblivionMain;
import net.oblivion.entity.*;

import java.util.ArrayList;

public class EntityInit {

    public static final ArrayList<Item> SPAWN_EGGS = new ArrayList<Item>();

    public static final EntityType<Shlama> SHLAMA = register("shlama", 4076852, 13810863, Shlama.createShlamaAttributes(), EntityType.Builder.create(Shlama::new, SpawnGroup.CREATURE).dimensions(1.6F, 1.9F).build());
    public static final EntityType<ElysianElk> ELYSIAN_ELK = register("elysian_elk", 14538161, 5269804, ElysianElk.createElysianElkAttributes(), EntityType.Builder.create(ElysianElk::new, SpawnGroup.CREATURE).dimensions(2.1F, 2.7F).build());
    public static final EntityType<ElysianShaman> ELYSIAN_SHAMAN = register("elysian_shaman", 14538161, 5269804, ElysianShaman.createElysianShamanAttributes(), EntityType.Builder.create(ElysianShaman::new, SpawnGroup.MONSTER).dimensions(1.5F, 3.0F).build());
    public static final EntityType<ElysianWolf> ELYSIAN_WOLF = register("elysian_wolf", 14538161, 5269804, ElysianWolf.createElysianWolfAttributes(), EntityType.Builder.create(ElysianWolf::new, SpawnGroup.MONSTER).dimensions(2.1F, 2.0F).build());
    public static final EntityType<Goblin> GOBLIN = register("goblin", 4617514, 6044466, Goblin.createGoblinAttributes(), EntityType.Builder.create(Goblin::new, SpawnGroup.MONSTER).dimensions(1.0F, 1.4F).build());
    public static final EntityType<Treeder> TREEDER = register("treeder", 4996390, 7625270, Treeder.createTreederAttributes(), EntityType.Builder.create(Treeder::new, SpawnGroup.CREATURE).dimensions(0.6F, 0.65F).build());
    public static final EntityType<Shroom> SHROOM = register("shroom", 14930887, 13382711, Shroom.createShroomAttributes(), EntityType.Builder.create(Shroom::new, SpawnGroup.MONSTER).dimensions(1.2F, 1.9F).eyeHeight(1.3f).build());
    public static final EntityType<Elk> ELK = register("elk", 5779211, 9724212, Elk.createElkAttributes(), EntityType.Builder.create(Elk::new, SpawnGroup.CREATURE).dimensions(1.5F, 2.2F).build());
    public static final EntityType<Boar> BOAR = register("boar", 4001544, 9724212, Boar.createBoarAttributes(), EntityType.Builder.create(Boar::new, SpawnGroup.CREATURE).dimensions(1.5F, 1.2F).build());
    public static final EntityType<Turkey> TURKEY = register("turkey", 1777190, 4801347, Turkey.createTurkeyAttributes(), EntityType.Builder.create(Turkey::new, SpawnGroup.CREATURE).dimensions(1.5F, 2.6F).passengerAttachments(new Vec3d(0.0f, 1.4f, 0.0f)).build());
    public static final EntityType<WoolyCow> WOOLY_COW = register("wooly_cow", 13926720, 16767926, WoolyCow.createWoolyCowAttributes(), EntityType.Builder.create(WoolyCow::new, SpawnGroup.CREATURE).dimensions(0.9F, 1.4F).eyeHeight(1.3F).passengerAttachments(1.36875F).build());
    public static final EntityType<Piranha> PIRANHA = register("piranha", 3430802, 16035176, Piranha.createPiranhaAttributes(), EntityType.Builder.create(Piranha::new, SpawnGroup.WATER_AMBIENT).dimensions(0.5F, 0.4F).build());
    public static final EntityType<HornedSheep> HORNED_SHEEP = register("horned_sheep", 14606046, 5915187, HornedSheep.createHornedSheepAttributes(), EntityType.Builder.create(HornedSheep::new, SpawnGroup.CREATURE).dimensions(0.9F, 1.3F).eyeHeight(1.235F).passengerAttachments(1.2375F).build());

    @SuppressWarnings("unchecked")
    private static <T extends Entity> EntityType<T> register(String id, int primaryColor, int secondaryColor, DefaultAttributeContainer.Builder builder, EntityType<T> entityType) {
        if (primaryColor != 0) {
            Item item = Registry.register(Registries.ITEM, OblivionMain.identifierOf("spawn_" + id),
                    new SpawnEggItem((EntityType<? extends MobEntity>) entityType, primaryColor, secondaryColor, new Item.Settings()));
            SPAWN_EGGS.add(item);
            ItemGroupEvents.modifyEntriesEvent(ItemInit.OBLIVION_ITEM_GROUP).register(entries -> entries.add(item));
        }
        FabricDefaultAttributeRegistry.register((EntityType<? extends LivingEntity>) entityType, builder);

        return Registry.register(Registries.ENTITY_TYPE, OblivionMain.identifierOf(id), entityType);
    }

    public static void init() {
        SpawnRestriction.register(EntityInit.SHLAMA, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
        SpawnRestriction.register(EntityInit.ELYSIAN_ELK, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ElysianElk::canSpawnInDark);
        SpawnRestriction.register(EntityInit.ELYSIAN_SHAMAN, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);
        SpawnRestriction.register(EntityInit.ELYSIAN_WOLF, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);
        SpawnRestriction.register(EntityInit.GOBLIN, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, Goblin::isValidNaturalSpawn);
        SpawnRestriction.register(EntityInit.TREEDER, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, Treeder::isValidNaturalSpawn);
        SpawnRestriction.register(EntityInit.SHROOM, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, Shroom::isValidNaturalSpawn);
        SpawnRestriction.register(EntityInit.ELK, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
        SpawnRestriction.register(EntityInit.BOAR, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
        SpawnRestriction.register(EntityInit.TURKEY, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
        SpawnRestriction.register(EntityInit.WOOLY_COW, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
        SpawnRestriction.register(EntityInit.PIRANHA, SpawnLocationTypes.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, Piranha::canPiranhaSpawn);
        SpawnRestriction.register(EntityInit.HORNED_SHEEP, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
    }

}
