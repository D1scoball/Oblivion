package net.oblivion.item;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.oblivion.OblivionMain;
import net.oblivion.init.ItemInit;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class OblivionArmorMaterials {

//    public static final RegistryEntry<ArmorMaterial> OCTARINE = register("octarine", Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
//        map.put(ArmorItem.Type.BOOTS, 3);
//        map.put(ArmorItem.Type.LEGGINGS, 6);
//        map.put(ArmorItem.Type.CHESTPLATE, 8);
//        map.put(ArmorItem.Type.HELMET, 3);
//        map.put(ArmorItem.Type.BODY, 11);
//    }), 25, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 3.5f, 0.1f, () -> Ingredient.ofItems(ItemInit.OCTARINE_INGOT));

    public static final RegistryEntry<ArmorMaterial> SOLARITE = register("solarite", Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 5);
        map.put(ArmorItem.Type.LEGGINGS, 8);
        map.put(ArmorItem.Type.CHESTPLATE, 10);
        map.put(ArmorItem.Type.HELMET, 5);
        map.put(ArmorItem.Type.BODY, 13);
    }), 25, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 4.0f, 0.25f, () -> Ingredient.ofItems(ItemInit.SOLARITE_INGOT));

    public static final RegistryEntry<ArmorMaterial> SCARLET = register("scarlet", Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 4);
        map.put(ArmorItem.Type.LEGGINGS, 7);
        map.put(ArmorItem.Type.CHESTPLATE, 9);
        map.put(ArmorItem.Type.HELMET, 4);
        map.put(ArmorItem.Type.BODY, 12);
    }), 25, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 3.75f, 0.2f, () -> Ingredient.ofItems(ItemInit.SCARLET_INGOT));

    public static final RegistryEntry<ArmorMaterial> FIERY_NETHERITE = register("fiery_netherite", Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 3);
        map.put(ArmorItem.Type.LEGGINGS, 6);
        map.put(ArmorItem.Type.CHESTPLATE, 8);
        map.put(ArmorItem.Type.HELMET, 3);
        map.put(ArmorItem.Type.BODY, 11);
    }), 25, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 3.5f, 0.15f, () -> Ingredient.ofItems(ItemInit.FIERY_NETHERITE_INGOT));

    private static RegistryEntry<ArmorMaterial> register(String id, EnumMap<ArmorItem.Type, Integer> defense, int enchantability, RegistryEntry<SoundEvent> equipSound, float toughness,
            float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        List<ArmorMaterial.Layer> list = List.of(new ArmorMaterial.Layer(OblivionMain.identifierOf(id)));

        EnumMap<ArmorItem.Type, Integer> enumMap = new EnumMap<ArmorItem.Type, Integer>(ArmorItem.Type.class);
        for (ArmorItem.Type type : ArmorItem.Type.values()) {
            enumMap.put(type, defense.get(type));
        }
        return Registry.registerReference(Registries.ARMOR_MATERIAL, Identifier.of(id),
                new ArmorMaterial(enumMap, enchantability, equipSound, repairIngredient, list, toughness, knockbackResistance));
    }

}
