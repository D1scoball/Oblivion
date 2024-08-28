package net.oblivion.init;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.oblivion.OblivionMain;
import net.oblivion.entity.ElysianElk;
import net.oblivion.entity.Shlama;

import java.util.ArrayList;

public class EntityInit {

    public static final ArrayList<Item> SPAWN_EGGS = new ArrayList<Item>();

    public static final EntityType<Shlama> SHLAMA = register("shlama", 4076852, 13810863, EntityType.Builder.create(Shlama::new, SpawnGroup.CREATURE).dimensions(1.6F, 1.9F).build());
    public static final EntityType<ElysianElk> ELYSIAN_ELK = register("elysian_elk", 14538161, 5269804, EntityType.Builder.create(ElysianElk::new, SpawnGroup.CREATURE).dimensions(2.1F, 2.7F).build());


    private static <T extends Entity> EntityType<T> register(String id, int primaryColor, int secondaryColor, EntityType<T> entityType) {
        if (primaryColor != 0) {
            Item item = Registry.register(Registries.ITEM, OblivionMain.id( "spawn_" + id),
                    new SpawnEggItem((EntityType<? extends MobEntity>) entityType, primaryColor, secondaryColor, new Item.Settings()));
            SPAWN_EGGS.add(item);
            ItemGroupEvents.modifyEntriesEvent(ItemInit.OBLIVION_ITEM_GROUP).register(entries -> entries.add(item));
        }
        return Registry.register(Registries.ENTITY_TYPE, OblivionMain.id( id), entityType);
    }

    public static void init(){
        FabricDefaultAttributeRegistry.register(SHLAMA, Shlama.createShlamaAttributes());
        FabricDefaultAttributeRegistry.register(ELYSIAN_ELK, ElysianElk.createElysianElkAttributes());
    }

}
