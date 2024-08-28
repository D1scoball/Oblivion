package net.oblivion.init;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.ComponentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.oblivion.OblivionMain;

import java.util.function.UnaryOperator;

public class ItemInit {

    // Item Group
    public static final RegistryKey<ItemGroup> OBLIVION_ITEM_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, OblivionMain.id("item_group"));


    private static Item register(String id, Item item) {
        return register(OblivionMain.id(id), item);
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
                FabricItemGroup.builder().icon(() -> new ItemStack(Items.STICK)).displayName(Text.translatable("item.oblivion.item_group")).build());

    }
}
