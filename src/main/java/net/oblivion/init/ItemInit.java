package net.oblivion.init;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.ComponentType;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.oblivion.OblivionMain;
import net.oblivion.item.OblivionArmorMaterials;
import net.oblivion.item.OblivionToolMaterials;

import java.util.ArrayList;
import java.util.function.UnaryOperator;

public class ItemInit {

    public static final ArrayList<Item> ITEMS = new ArrayList<Item>();
    public static final ArrayList<Item> TOOLS = new ArrayList<Item>();
    // Item Group
    public static final RegistryKey<ItemGroup> OBLIVION_ITEM_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, OblivionMain.identifierOf("item_group"));

    public static final Item RAW_OCTARINE = register("raw_octarine", new Item(new Item.Settings()));
    public static final Item OCTARINE_INGOT = register("octarine_ingot", new Item(new Item.Settings()));
    public static final Item OCTARINE_NUGGET = register("octarine_nugget", new Item(new Item.Settings()));
    public static final Item OCTARINE_HELMET = register("octarine_helmet",
            new ArmorItem(OblivionArmorMaterials.OCTARINE, ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(20))));
    public static final Item OCTARINE_CHESTPLATE = register("octarine_chestplate",
            new ArmorItem(OblivionArmorMaterials.OCTARINE, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(20))));
    public static final Item OCTARINE_LEGGINGS = register("octarine_leggings",
            new ArmorItem(OblivionArmorMaterials.OCTARINE, ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(20))));
    public static final Item OCTARINE_BOOTS = register("octarine_boots",
            new ArmorItem(OblivionArmorMaterials.OCTARINE, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(20))));

    public static final Item OCTARINE_SWORD = register("octarine_sword",
            new SwordItem(OblivionToolMaterials.OCTARINE, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(OblivionToolMaterials.OCTARINE, 3, -2.4f))));
    public static final Item OCTARINE_SHOVEL = register("octarine_shovel",
            new ShovelItem(OblivionToolMaterials.OCTARINE, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(OblivionToolMaterials.OCTARINE, 1.5f, -3.0f))));
    public static final Item OCTARINE_PICKAXE = register("octarine_pickaxe",
            new PickaxeItem(OblivionToolMaterials.OCTARINE, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(OblivionToolMaterials.OCTARINE, 1.0f, -2.8f))));
    public static final Item OCTARINE_AXE = register("octarine_axe",
            new AxeItem(OblivionToolMaterials.OCTARINE, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(OblivionToolMaterials.OCTARINE, 6.0f, -3.1f))));
    public static final Item OCTARINE_HOE = register("octarine_hoe",
            new HoeItem(OblivionToolMaterials.OCTARINE, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(OblivionToolMaterials.OCTARINE, -2.0f, -1.0f))));
    public static final Item OCTARINE_HORSE_ARMOR = register("octarine_horse_armor",
            new AnimalArmorItem(OblivionArmorMaterials.OCTARINE, AnimalArmorItem.Type.EQUESTRIAN, false, new Item.Settings().maxCount(1)));


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
                FabricItemGroup.builder().icon(() -> new ItemStack(ItemInit.OCTARINE_INGOT)).displayName(Text.translatable("item.oblivion.item_group")).build());

    }
}
