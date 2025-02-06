package net.oblivion.init;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.ComponentType;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.oblivion.OblivionMain;
import net.oblivion.item.ElysianBoneMeal;
import net.oblivion.item.OblivionArmorMaterials;
import net.oblivion.item.OblivionToolMaterials;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class ItemInit {

    public static final ArrayList<Item> ITEMS = new ArrayList<Item>();
    public static final ArrayList<Item> TOOLS = new ArrayList<Item>();
    // Item Group
    public static final RegistryKey<ItemGroup> OBLIVION_ITEM_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, OblivionMain.identifierOf("item_group"));

//    public static final Item RAW_OCTARINE = register("raw_octarine", new Item(new Item.Settings()));
//    public static final Item OCTARINE_INGOT = register("octarine_ingot", new Item(new Item.Settings()));
//    public static final Item OCTARINE_NUGGET = register("octarine_nugget", new Item(new Item.Settings()));
//    public static final Item OCTARINE_HELMET = register("octarine_helmet",
//            new ArmorItem(OblivionArmorMaterials.OCTARINE, ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(20))));
//    public static final Item OCTARINE_CHESTPLATE = register("octarine_chestplate",
//            new ArmorItem(OblivionArmorMaterials.OCTARINE, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(20))));
//    public static final Item OCTARINE_LEGGINGS = register("octarine_leggings",
//            new ArmorItem(OblivionArmorMaterials.OCTARINE, ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(20))));
//    public static final Item OCTARINE_BOOTS = register("octarine_boots",
//            new ArmorItem(OblivionArmorMaterials.OCTARINE, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(20))));
//
//    public static final Item OCTARINE_SWORD = register("octarine_sword",
//            new SwordItem(OblivionToolMaterials.OCTARINE, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(OblivionToolMaterials.OCTARINE, 3, -2.4f))));
//    public static final Item OCTARINE_SHOVEL = register("octarine_shovel",
//            new ShovelItem(OblivionToolMaterials.OCTARINE, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(OblivionToolMaterials.OCTARINE, 1.5f, -3.0f))));
//    public static final Item OCTARINE_PICKAXE = register("octarine_pickaxe",
//            new PickaxeItem(OblivionToolMaterials.OCTARINE, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(OblivionToolMaterials.OCTARINE, 1.0f, -2.8f))));
//    public static final Item OCTARINE_AXE = register("octarine_axe",
//            new AxeItem(OblivionToolMaterials.OCTARINE, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(OblivionToolMaterials.OCTARINE, 6.0f, -3.1f))));
//    public static final Item OCTARINE_HOE = register("octarine_hoe",
//            new HoeItem(OblivionToolMaterials.OCTARINE, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(OblivionToolMaterials.OCTARINE, -2.0f, -1.0f))));
//    public static final Item OCTARINE_HORSE_ARMOR = register("octarine_horse_armor",
//            new AnimalArmorItem(OblivionArmorMaterials.OCTARINE, AnimalArmorItem.Type.EQUESTRIAN, false, new Item.Settings().maxCount(1)));

//    public static final Item RAW_SCARLET = register("raw_scarlet", new Item(new Item.Settings()));
//    public static final Item SCARLET_INGOT = register("scarlet_ingot", new Item(new Item.Settings()));
////    public static final Item SCARLET_NUGGET = register("scarlet_nugget", new Item(new Item.Settings()));
//    public static final Item SCARLET_HELMET = register("scarlet_helmet",
//            new ArmorItem(OblivionArmorMaterials.SCARLET, ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(20))));
//    public static final Item SCARLET_CHESTPLATE = register("scarlet_chestplate",
//            new ArmorItem(OblivionArmorMaterials.SCARLET, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(20))));
//    public static final Item SCARLET_LEGGINGS = register("scarlet_leggings",
//            new ArmorItem(OblivionArmorMaterials.SCARLET, ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(20))));
//    public static final Item SCARLET_BOOTS = register("scarlet_boots",
//            new ArmorItem(OblivionArmorMaterials.SCARLET, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(20))));
//
//    public static final Item SCARLET_SWORD = register("scarlet_sword",
//            new SwordItem(OblivionToolMaterials.SCARLET, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(OblivionToolMaterials.SCARLET, 3, -2.4f))));
//    public static final Item SCARLET_SHOVEL = register("scarlet_shovel",
//            new ShovelItem(OblivionToolMaterials.SCARLET, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(OblivionToolMaterials.SCARLET, 1.5f, -3.0f))));
//    public static final Item SCARLET_PICKAXE = register("scarlet_pickaxe",
//            new PickaxeItem(OblivionToolMaterials.SCARLET, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(OblivionToolMaterials.SCARLET, 1.0f, -2.8f))));
//    public static final Item SCARLET_AXE = register("scarlet_axe",
//            new AxeItem(OblivionToolMaterials.SCARLET, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(OblivionToolMaterials.SCARLET, 6.0f, -3.1f))));
//    public static final Item SCARLET_HOE = register("scarlet_hoe",
//            new HoeItem(OblivionToolMaterials.SCARLET, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(OblivionToolMaterials.SCARLET, -2.0f, -1.0f))));
////    public static final Item SCARLET_HORSE_ARMOR = register("scarlet_horse_armor",
////            new AnimalArmorItem(OblivionArmorMaterials.SCARLET, AnimalArmorItem.Type.EQUESTRIAN, false, new Item.Settings().maxCount(1)));

    public static final Item RAW_FIERY_NETHERITE = register("raw_fiery_netherite", new Item(new Item.Settings()));
    public static final Item FIERY_NETHERITE_SCRAP = register("fiery_netherite_scrap", new Item(new Item.Settings()));
    public static final Item FIERY_NETHERITE_INGOT = register("fiery_netherite_ingot", new Item(new Item.Settings()));
    public static final Item FIERY_NETHERITE_HELMET = register("fiery_netherite_helmet",
            new ArmorItem(OblivionArmorMaterials.FIERY_NETHERITE, ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(20))));
    public static final Item FIERY_NETHERITE_CHESTPLATE = register("fiery_netherite_chestplate",
            new ArmorItem(OblivionArmorMaterials.FIERY_NETHERITE, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(20))));
    public static final Item FIERY_NETHERITE_LEGGINGS = register("fiery_netherite_leggings",
            new ArmorItem(OblivionArmorMaterials.FIERY_NETHERITE, ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(20))));
    public static final Item FIERY_NETHERITE_BOOTS = register("fiery_netherite_boots",
            new ArmorItem(OblivionArmorMaterials.FIERY_NETHERITE, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(20))));

    public static final Item FIERY_NETHERITE_SWORD = register("fiery_netherite_sword",
            new SwordItem(OblivionToolMaterials.FIERY_NETHERITE, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(OblivionToolMaterials.FIERY_NETHERITE, 3, -2.4f))));
    public static final Item FIERY_NETHERITE_SHOVEL = register("fiery_netherite_shovel",
            new ShovelItem(OblivionToolMaterials.FIERY_NETHERITE, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(OblivionToolMaterials.FIERY_NETHERITE, 1.5f, -3.0f))));
    public static final Item FIERY_NETHERITE_PICKAXE = register("fiery_netherite_pickaxe",
            new PickaxeItem(OblivionToolMaterials.FIERY_NETHERITE, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(OblivionToolMaterials.FIERY_NETHERITE, 1.0f, -2.8f))));
    public static final Item FIERY_NETHERITE_AXE = register("fiery_netherite_axe",
            new AxeItem(OblivionToolMaterials.FIERY_NETHERITE, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(OblivionToolMaterials.FIERY_NETHERITE, 6.0f, -3.1f))));
    public static final Item FIERY_NETHERITE_HOE = register("fiery_netherite_hoe",
            new HoeItem(OblivionToolMaterials.FIERY_NETHERITE, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(OblivionToolMaterials.FIERY_NETHERITE, -2.0f, -1.0f))));
//    public static final Item FIERY_NETHERITE_HORSE_ARMOR = register("fiery_netherite_horse_armor",
//            new AnimalArmorItem(OblivionArmorMaterials.FIERY_NETHERITE, AnimalArmorItem.Type.EQUESTRIAN, false, new Item.Settings().maxCount(1)));

    public static final Item FIERY_NETHERITE_UPGRADE_SMITHING_TEMPLATE = register("fiery_netherite_upgrade_smithing_template",
            new SmithingTemplateItem(Text.translatable("item.minecraft.smithing_template.fiery_netherite_upgrade.applies_to").formatted(Formatting.BLUE),
                    Text.translatable("item.minecraft.smithing_template.fiery_netherite_upgrade.ingredients").formatted(Formatting.BLUE),
                    Text.translatable("item.oblivion.fiery_netherite_upgrade").formatted(Formatting.GRAY), Text.translatable("item.smithing_template.fiery_netherite_upgrade.base_slot_description"),
                    Text.translatable("item.oblivion.smithing_template.fiery_netherite_upgrade.additions_slot_description"), List.of(Identifier.of("item/empty_armor_slot_helmet"),
                    Identifier.of("item/empty_armor_slot_chestplate"), Identifier.of("item/empty_armor_slot_leggings"), Identifier.of("item/empty_armor_slot_boots")),
                    List.of(OblivionMain.identifierOf("item/empty_slot_fiery_netherite_netherite_fragment"))));

    public static final Item SHLAMB = register("shlamb", new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(3).saturationModifier(0.6F).build())));
    public static final Item COOKED_SHLAMB = register("cooked_shlamb", new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(7).saturationModifier(1.6F).build())));
    public static final Item VENISON = register("venison", new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(3).saturationModifier(0.6F).build())));
    public static final Item COOKED_VENISON = register("cooked_venison", new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(7).saturationModifier(1.6F).build())));
    public static final Item BOAR_MEAT = register("boar_meat", new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(3).saturationModifier(0.6F).build())));
    public static final Item COOKED_BOAR_MEAT = register("cooked_boar_meat", new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(8).saturationModifier(1.6F).build())));
    public static final Item TURKEY = register("turkey", new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(3).saturationModifier(0.6F).build())));
    public static final Item COOKED_TURKEY = register("cooked_turkey", new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(7).saturationModifier(1.2F).build())));

    public static final Item ELYSIAN_BONE = register("elysian_bone", new Item(new Item.Settings()));
    public static final Item ELYSIAN_BONE_MEAL = register("elysian_bone_meal", new ElysianBoneMeal(new Item.Settings()));

    private static Item register(String id, Item item) {
        if (item instanceof ToolItem) {
            TOOLS.add(item);
        } else {
            ITEMS.add(item);
        }
        return register(OblivionMain.identifierOf(id), item);
    }

    private static Item register(Identifier id, Item item) {
        ItemGroupEvents.modifyEntriesEvent(OBLIVION_ITEM_GROUP).register(entries -> entries.add(item));
        return Registry.register(Registries.ITEM, id, item);
    }

    private static <T> ComponentType<T> registerComponent(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, id, builderOperator.apply(ComponentType.builder()).build());
    }

    public static void init() {
        Registry.register(Registries.ITEM_GROUP, OBLIVION_ITEM_GROUP,
                FabricItemGroup.builder().icon(() -> new ItemStack(ItemInit.FIERY_NETHERITE_INGOT)).displayName(Text.translatable("item.oblivion.item_group")).build());
    }
}
