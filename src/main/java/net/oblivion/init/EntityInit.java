package net.oblivion.init;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.oblivion.OblivionMain;
import net.oblivion.entity.*;

import java.util.ArrayList;

public class EntityInit {

    public static final ArrayList<Item> SPAWN_EGGS = new ArrayList<Item>();

    public static final EntityType<Shlama> SHLAMA = register("shlama", 4076852, 13810863, Shlama.createShlamaAttributes(), EntityType.Builder.create(Shlama::new, SpawnGroup.CREATURE).dimensions(1.6F, 1.9F).build());
    public static final EntityType<ElysianElk> ELYSIAN_ELK = register("elysian_elk", 14538161, 5269804, ElysianElk.createElysianElkAttributes(), EntityType.Builder.create(ElysianElk::new, SpawnGroup.CREATURE).dimensions(2.1F, 2.7F).build());
    public static final EntityType<ElysianShaman> ELYSIAN_SHAMAN = register("elysian_shaman", 14538161, 5269804, ElysianShaman.createElysianShamanAttributes(), EntityType.Builder.create(ElysianShaman::new, SpawnGroup.CREATURE).dimensions(1.2F, 2.6F).build());
    public static final EntityType<ElysianWolf> ELYSIAN_WOLF = register("elysian_wolf", 14538161, 5269804, ElysianWolf.createElysianWolfAttributes(), EntityType.Builder.create(ElysianWolf::new, SpawnGroup.CREATURE).dimensions(2.1F, 2.0F).build());
    public static final EntityType<Goblin> GOBLIN = register("goblin", 4617514, 6044466, Goblin.createGoblinAttributes(), EntityType.Builder.create(Goblin::new, SpawnGroup.CREATURE).dimensions(1.0F, 1.4F).build());
    public static final EntityType<Treeder> TREEDER = register("treeder", 4996390, 7625270, Treeder.createTreederAttributes(), EntityType.Builder.create(Treeder::new, SpawnGroup.CREATURE).dimensions(0.6F, 0.65F).build());

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
    }

}
