package net.oblivion.item;

import net.minecraft.block.Block;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.oblivion.init.ItemInit;
import net.oblivion.init.TagInit;

public class OblivionToolMaterials implements ToolMaterial {

    public static final ToolMaterial FIERY_NETHERITE = new OblivionToolMaterials(TagInit.INCORRECT_FOR_FIERY_NETHERITE_TOOL, 2331, 9.5F, 4.5F, 16, Ingredient.ofItems(ItemInit.FIERY_NETHERITE_INGOT), "fiery_netherite");
//    public static final ToolMaterial SCARLET = new OblivionToolMaterials(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 2331, 9.0F, 4.0F, 15, Ingredient.ofItems(ItemInit.SCARLET_INGOT), "scarlet");
//    public static final ToolMaterial OCTARINE = new OblivionToolMaterials(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 2931, 12.0F, 5.0F, 20, Ingredient.ofItems(ItemInit.OCTARINE_INGOT), "octarine");

    private final TagKey<Block> inverseTag;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Ingredient repairIngredient;
    private final String name;

    private OblivionToolMaterials(TagKey<Block> inverseTag, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Ingredient repairIngredient, String name) {
        this.inverseTag = inverseTag;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = repairIngredient;
        this.name = name;
    }

    @Override
    public int getDurability() {
        return this.itemDurability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public TagKey<Block> getInverseTag() {
        return this.inverseTag;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
