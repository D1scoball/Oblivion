package net.oblivion.init;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.TreeConfiguredFeatures;
import net.oblivion.OblivionMain;
import net.oblivion.block.DrillBlock;
import net.oblivion.block.MultiOreBlock;
import net.oblivion.block.entity.DrillBlockEntity;
import net.oblivion.block.entity.MultiOreBlockEntity;

import java.util.Optional;

public class BlockInit {

    public static final BlockSetType IRON_WOOD_SET_TYPE = new BlockSetTypeBuilder().register(OblivionMain.identifierOf("iron_wood"));
    public static final BlockSetType SILVER_BIRCH_SET_TYPE = new BlockSetTypeBuilder().register(OblivionMain.identifierOf("silver_birch"));
    public static final BlockSetType RUNE_WOOD_SET_TYPE = new BlockSetTypeBuilder().register(OblivionMain.identifierOf("rune_wood"));

    public static final WoodType IRON_WOOD_TYPE = new WoodTypeBuilder().register(OblivionMain.identifierOf("iron_wood"), IRON_WOOD_SET_TYPE);
    public static final WoodType SILVER_BIRCH_TYPE = new WoodTypeBuilder().register(OblivionMain.identifierOf("silver_birch"), SILVER_BIRCH_SET_TYPE);
    public static final WoodType RUNE_WOOD_TYPE = new WoodTypeBuilder().register(OblivionMain.identifierOf("rune_wood"), RUNE_WOOD_SET_TYPE);

    public static final SaplingGenerator IRON_WOOD_SAPLING_GENERATOR = new SaplingGenerator("iron_wood", Optional.empty(), Optional.of(ConfiguredFeatures.of("iron_wood")), Optional.empty());
    public static final SaplingGenerator SILVER_BIRCH_SAPLING_GENERATOR = new SaplingGenerator("silver_birch", Optional.empty(), Optional.of(ConfiguredFeatures.of("silver_birch")), Optional.empty());
    public static final SaplingGenerator RUNE_WOOD_SAPLING_GENERATOR = new SaplingGenerator("rune_wood", Optional.empty(), Optional.of(ConfiguredFeatures.of("rune_wood")), Optional.empty());


    public static final Block IRON_WOOD_PLANKS = register("iron_wood_planks", true, new Block(AbstractBlock.Settings.create().mapColor(MapColor.BROWN).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block IRON_WOOD_SAPLING = register("iron_wood_sapling", true, new SaplingBlock(IRON_WOOD_SAPLING_GENERATOR, AbstractBlock.Settings.create().mapColor(MapColor.PINK).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CHERRY_SAPLING).pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block IRON_WOOD_LOG = register("iron_wood_log", true, Blocks.createLogBlock(MapColor.BROWN, MapColor.TERRACOTTA_GRAY, BlockSoundGroup.WOOD));
    public static final Block STRIPPED_IRON_WOOD_LOG = register("stripped_iron_wood_log", true, Blocks.createLogBlock(MapColor.BROWN, MapColor.TERRACOTTA_PINK, BlockSoundGroup.WOOD));
    public static final Block IRON_WOOD = register("iron_wood", true, new PillarBlock(AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_GRAY).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block STRIPPED_IRON_WOOD = register("stripped_iron_wood", true, new PillarBlock(AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_PINK).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block IRON_WOOD_LEAVES = register("iron_wood_leaves", true, new LeavesBlock(AbstractBlock.Settings.create().mapColor(MapColor.PINK).strength(0.2F).ticksRandomly().sounds(BlockSoundGroup.CHERRY_LEAVES).nonOpaque().allowsSpawning(Blocks::canSpawnOnLeaves).suffocates(Blocks::never).blockVision(Blocks::never).burnable().pistonBehavior(PistonBehavior.DESTROY).solidBlock(Blocks::never)));
    public static final Block IRON_WOOD_SIGN = register("iron_wood_sign", true, new SignBlock(IRON_WOOD_TYPE, AbstractBlock.Settings.create().mapColor(IRON_WOOD_PLANKS.getDefaultMapColor()).solid().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).burnable()));
    public static final Block IRON_WOOD_WALL_SIGN = register("iron_wood_wall_sign", false, new WallSignBlock(IRON_WOOD_TYPE, AbstractBlock.Settings.create().mapColor(IRON_WOOD_LOG.getDefaultMapColor()).solid().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).dropsLike(IRON_WOOD_SIGN).burnable()));
    public static final Block IRON_WOOD_HANGING_SIGN = register("iron_wood_hanging_sign", true, new HangingSignBlock(IRON_WOOD_TYPE, AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_PINK).solid().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).burnable()));
    public static final Block IRON_WOOD_WALL_HANGING_SIGN = register("iron_wood_wall_hanging_sign", false, new WallHangingSignBlock(IRON_WOOD_TYPE, AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_PINK).solid().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).burnable().dropsLike(IRON_WOOD_HANGING_SIGN)));
    public static final Block IRON_WOOD_PRESSURE_PLATE = register("iron_wood_pressure_plate", true, new PressurePlateBlock(IRON_WOOD_SET_TYPE, AbstractBlock.Settings.create().mapColor(IRON_WOOD_PLANKS.getDefaultMapColor()).solid().instrument(NoteBlockInstrument.BASS).noCollision().strength(0.5F).burnable().pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block IRON_WOOD_TRAPDOOR = register("iron_wood_trapdoor", true, new TrapdoorBlock(IRON_WOOD_SET_TYPE, AbstractBlock.Settings.create().mapColor(MapColor.BROWN).instrument(NoteBlockInstrument.BASS).strength(3.0F).nonOpaque().allowsSpawning(Blocks::never).burnable()));
    public static final Block POTTED_IRON_WOOD_SAPLING = register("potted_iron_wood_sapling", false, Blocks.createFlowerPotBlock(IRON_WOOD_SAPLING));
    public static final Block IRON_WOOD_BUTTON = register("iron_wood_button", true, Blocks.createWoodenButtonBlock(IRON_WOOD_SET_TYPE));
    public static final Block IRON_WOOD_STAIRS = register("iron_wood_stairs", true, createStairsBlock(IRON_WOOD_PLANKS));
    public static final Block IRON_WOOD_SLAB = register("iron_wood_slab", true, new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.BROWN).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block IRON_WOOD_FENCE_GATE = register("iron_wood_fence_gate", true, new FenceGateBlock(IRON_WOOD_TYPE, AbstractBlock.Settings.create().mapColor(IRON_WOOD_PLANKS.getDefaultMapColor()).solid().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).burnable()));
    public static final Block IRON_WOOD_FENCE = register("iron_wood_fence", true, new FenceBlock(AbstractBlock.Settings.create().mapColor(IRON_WOOD_PLANKS.getDefaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).burnable().sounds(BlockSoundGroup.WOOD)));
    public static final Block IRON_WOOD_DOOR = register("iron_wood_door", true, new DoorBlock(IRON_WOOD_SET_TYPE, AbstractBlock.Settings.create().mapColor(IRON_WOOD_PLANKS.getDefaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(3.0F).nonOpaque().burnable().pistonBehavior(PistonBehavior.DESTROY)));


    public static final Block SILVER_BIRCH_PLANKS = register("silver_birch_planks", true, new Block(AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block SILVER_BIRCH_SAPLING = register("silver_birch_sapling", true, new SaplingBlock(SILVER_BIRCH_SAPLING_GENERATOR, AbstractBlock.Settings.create().mapColor(MapColor.PINK).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CHERRY_SAPLING).pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block SILVER_BIRCH_LOG = register("silver_birch_log", true, Blocks.createLogBlock(MapColor.PALE_YELLOW, MapColor.TERRACOTTA_GRAY, BlockSoundGroup.WOOD));
    public static final Block STRIPPED_SILVER_BIRCH_LOG = register("stripped_silver_birch_log", true, Blocks.createLogBlock(MapColor.PALE_YELLOW, MapColor.TERRACOTTA_PINK, BlockSoundGroup.WOOD));
    public static final Block SILVER_BIRCH = register("silver_birch", true, new PillarBlock(AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_GRAY).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block STRIPPED_SILVER_BIRCH = register("stripped_silver_birch", true, new PillarBlock(AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_PINK).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block SILVER_BIRCH_LEAVES = register("silver_birch_leaves", true, new LeavesBlock(AbstractBlock.Settings.create().mapColor(MapColor.PINK).strength(0.2F).ticksRandomly().sounds(BlockSoundGroup.CHERRY_LEAVES).nonOpaque().allowsSpawning(Blocks::canSpawnOnLeaves).suffocates(Blocks::never).blockVision(Blocks::never).burnable().pistonBehavior(PistonBehavior.DESTROY).solidBlock(Blocks::never)));
    public static final Block SILVER_BIRCH_SIGN = register("silver_birch_sign", true, new SignBlock(SILVER_BIRCH_TYPE, AbstractBlock.Settings.create().mapColor(SILVER_BIRCH_PLANKS.getDefaultMapColor()).solid().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).burnable()));
    public static final Block SILVER_BIRCH_WALL_SIGN = register("silver_birch_wall_sign", false, new WallSignBlock(SILVER_BIRCH_TYPE, AbstractBlock.Settings.create().mapColor(SILVER_BIRCH_LOG.getDefaultMapColor()).solid().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).dropsLike(SILVER_BIRCH_SIGN).burnable()));
    public static final Block SILVER_BIRCH_HANGING_SIGN = register("silver_birch_hanging_sign", true, new HangingSignBlock(SILVER_BIRCH_TYPE, AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_PINK).solid().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).burnable()));
    public static final Block SILVER_BIRCH_WALL_HANGING_SIGN = register("silver_birch_wall_hanging_sign", false, new WallHangingSignBlock(SILVER_BIRCH_TYPE, AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_PINK).solid().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).burnable().dropsLike(SILVER_BIRCH_HANGING_SIGN)));
    public static final Block SILVER_BIRCH_PRESSURE_PLATE = register("silver_birch_pressure_plate", true, new PressurePlateBlock(SILVER_BIRCH_SET_TYPE, AbstractBlock.Settings.create().mapColor(SILVER_BIRCH_PLANKS.getDefaultMapColor()).solid().instrument(NoteBlockInstrument.BASS).noCollision().strength(0.5F).burnable().pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block SILVER_BIRCH_TRAPDOOR = register("silver_birch_trapdoor", true, new TrapdoorBlock(SILVER_BIRCH_SET_TYPE, AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).instrument(NoteBlockInstrument.BASS).strength(3.0F).nonOpaque().allowsSpawning(Blocks::never).burnable()));
    public static final Block POTTED_SILVER_BIRCH_SAPLING = register("potted_silver_birch_sapling", false, Blocks.createFlowerPotBlock(SILVER_BIRCH_SAPLING));
    public static final Block SILVER_BIRCH_BUTTON = register("silver_birch_button", true, Blocks.createWoodenButtonBlock(SILVER_BIRCH_SET_TYPE));
    public static final Block SILVER_BIRCH_STAIRS = register("silver_birch_stairs", true, createStairsBlock(SILVER_BIRCH_PLANKS));
    public static final Block SILVER_BIRCH_SLAB = register("silver_birch_slab", true, new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block SILVER_BIRCH_FENCE_GATE = register("silver_birch_fence_gate", true, new FenceGateBlock(SILVER_BIRCH_TYPE, AbstractBlock.Settings.create().mapColor(SILVER_BIRCH_PLANKS.getDefaultMapColor()).solid().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).burnable()));
    public static final Block SILVER_BIRCH_FENCE = register("silver_birch_fence", true, new FenceBlock(AbstractBlock.Settings.create().mapColor(SILVER_BIRCH_PLANKS.getDefaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).burnable().sounds(BlockSoundGroup.WOOD)));
    public static final Block SILVER_BIRCH_DOOR = register("silver_birch_door", true, new DoorBlock(SILVER_BIRCH_SET_TYPE, AbstractBlock.Settings.create().mapColor(SILVER_BIRCH_PLANKS.getDefaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(3.0F).nonOpaque().burnable().pistonBehavior(PistonBehavior.DESTROY)));

    public static final Block RUNE_WOOD_PLANKS = register("rune_wood_planks", true, new Block(AbstractBlock.Settings.create().mapColor(MapColor.PALE_PURPLE).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block RUNE_WOOD_SAPLING = register("rune_wood_sapling", true, new SaplingBlock(RUNE_WOOD_SAPLING_GENERATOR, AbstractBlock.Settings.create().mapColor(MapColor.PINK).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CHERRY_SAPLING).pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block RUNE_WOOD_LOG = register("rune_wood_log", true, Blocks.createLogBlock(MapColor.PALE_PURPLE, MapColor.TERRACOTTA_GRAY, BlockSoundGroup.WOOD));
    public static final Block STRIPPED_RUNE_WOOD_LOG = register("stripped_rune_wood_log", true, Blocks.createLogBlock(MapColor.PALE_PURPLE, MapColor.TERRACOTTA_PINK, BlockSoundGroup.WOOD));
    public static final Block RUNE_WOOD = register("rune_wood", true, new PillarBlock(AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_GRAY).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block STRIPPED_RUNE_WOOD = register("stripped_rune_wood", true, new PillarBlock(AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_PINK).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block RUNE_WOOD_LEAVES = register("rune_wood_leaves", true, new LeavesBlock(AbstractBlock.Settings.create().mapColor(MapColor.PINK).strength(0.2F).ticksRandomly().sounds(BlockSoundGroup.CHERRY_LEAVES).nonOpaque().allowsSpawning(Blocks::canSpawnOnLeaves).suffocates(Blocks::never).blockVision(Blocks::never).burnable().pistonBehavior(PistonBehavior.DESTROY).solidBlock(Blocks::never)));
    public static final Block RUNE_WOOD_SIGN = register("rune_wood_sign", true, new SignBlock(RUNE_WOOD_TYPE, AbstractBlock.Settings.create().mapColor(RUNE_WOOD_PLANKS.getDefaultMapColor()).solid().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).burnable()));
    public static final Block RUNE_WOOD_WALL_SIGN = register("rune_wood_wall_sign", false, new WallSignBlock(RUNE_WOOD_TYPE, AbstractBlock.Settings.create().mapColor(RUNE_WOOD_LOG.getDefaultMapColor()).solid().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).dropsLike(RUNE_WOOD_SIGN).burnable()));
    public static final Block RUNE_WOOD_HANGING_SIGN = register("rune_wood_hanging_sign", true, new HangingSignBlock(RUNE_WOOD_TYPE, AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_PINK).solid().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).burnable()));
    public static final Block RUNE_WOOD_WALL_HANGING_SIGN = register("rune_wood_wall_hanging_sign", false, new WallHangingSignBlock(RUNE_WOOD_TYPE, AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_PINK).solid().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).burnable().dropsLike(RUNE_WOOD_HANGING_SIGN)));
    public static final Block RUNE_WOOD_PRESSURE_PLATE = register("rune_wood_pressure_plate", true, new PressurePlateBlock(RUNE_WOOD_SET_TYPE, AbstractBlock.Settings.create().mapColor(RUNE_WOOD_PLANKS.getDefaultMapColor()).solid().instrument(NoteBlockInstrument.BASS).noCollision().strength(0.5F).burnable().pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block RUNE_WOOD_TRAPDOOR = register("rune_wood_trapdoor", true, new TrapdoorBlock(RUNE_WOOD_SET_TYPE, AbstractBlock.Settings.create().mapColor(MapColor.PALE_PURPLE).instrument(NoteBlockInstrument.BASS).strength(3.0F).nonOpaque().allowsSpawning(Blocks::never).burnable()));
    public static final Block POTTED_RUNE_WOOD_SAPLING = register("potted_rune_wood_sapling", false, Blocks.createFlowerPotBlock(RUNE_WOOD_SAPLING));
    public static final Block RUNE_WOOD_BUTTON = register("rune_wood_button", true, Blocks.createWoodenButtonBlock(RUNE_WOOD_SET_TYPE));
    public static final Block RUNE_WOOD_STAIRS = register("rune_wood_stairs", true, createStairsBlock(RUNE_WOOD_PLANKS));
    public static final Block RUNE_WOOD_SLAB = register("rune_wood_slab", true, new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.PALE_PURPLE).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block RUNE_WOOD_FENCE_GATE = register("rune_wood_fence_gate", true, new FenceGateBlock(RUNE_WOOD_TYPE, AbstractBlock.Settings.create().mapColor(RUNE_WOOD_PLANKS.getDefaultMapColor()).solid().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).burnable()));
    public static final Block RUNE_WOOD_FENCE = register("rune_wood_fence", true, new FenceBlock(AbstractBlock.Settings.create().mapColor(RUNE_WOOD_PLANKS.getDefaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).burnable().sounds(BlockSoundGroup.WOOD)));
    public static final Block RUNE_WOOD_DOOR = register("rune_wood_door", true, new DoorBlock(RUNE_WOOD_SET_TYPE, AbstractBlock.Settings.create().mapColor(RUNE_WOOD_PLANKS.getDefaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(3.0F).nonOpaque().burnable().pistonBehavior(PistonBehavior.DESTROY)));

    public static final Block DRILL = register("drill", true, new DrillBlock(AbstractBlock.Settings.copy(Blocks.BARREL).nonOpaque().pistonBehavior(PistonBehavior.IGNORE)));

    public static final Block TEST = register("test", true, new MultiOreBlock(AbstractBlock.Settings.copy(Blocks.DIAMOND_ORE)));

    public static BlockEntityType<DrillBlockEntity> DRILL_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, OblivionMain.identifierOf("drill_entity"),
            BlockEntityType.Builder.create(DrillBlockEntity::new, DRILL).build(null));
    public static BlockEntityType<MultiOreBlockEntity> MULTI_ORE_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, OblivionMain.identifierOf("multi_ore_entity"),
            BlockEntityType.Builder.create(MultiOreBlockEntity::new, TEST).build(null));

    private static Block register(String id, boolean registerItem, Block block) {
        return register(OblivionMain.identifierOf(id), registerItem, block);
    }

    private static Block register(Identifier id, boolean registerItem, Block block) {
        if (registerItem) {
            Item item = Registry.register(Registries.ITEM, id, new BlockItem(block, new Item.Settings()));
            ItemGroupEvents.modifyEntriesEvent(ItemInit.OBLIVION_ITEM_GROUP).register(entries -> entries.add(item));
        }
        return Registry.register(Registries.BLOCK, id, block);
    }

    private static Block createStairsBlock(Block base) {
        return new StairsBlock(base.getDefaultState(), AbstractBlock.Settings.copy(base));
    }

    public static void init() {
        BlockEntityType.SIGN.addSupportedBlock(IRON_WOOD_SIGN);
        BlockEntityType.SIGN.addSupportedBlock(IRON_WOOD_WALL_SIGN);
        BlockEntityType.HANGING_SIGN.addSupportedBlock(IRON_WOOD_HANGING_SIGN);
        BlockEntityType.HANGING_SIGN.addSupportedBlock(IRON_WOOD_WALL_HANGING_SIGN);

        BlockEntityType.SIGN.addSupportedBlock(SILVER_BIRCH_SIGN);
        BlockEntityType.SIGN.addSupportedBlock(SILVER_BIRCH_WALL_SIGN);
        BlockEntityType.HANGING_SIGN.addSupportedBlock(SILVER_BIRCH_HANGING_SIGN);
        BlockEntityType.HANGING_SIGN.addSupportedBlock(SILVER_BIRCH_WALL_HANGING_SIGN);

        BlockEntityType.SIGN.addSupportedBlock(RUNE_WOOD_SIGN);
        BlockEntityType.SIGN.addSupportedBlock(RUNE_WOOD_WALL_SIGN);
        BlockEntityType.HANGING_SIGN.addSupportedBlock(RUNE_WOOD_HANGING_SIGN);
        BlockEntityType.HANGING_SIGN.addSupportedBlock(RUNE_WOOD_WALL_HANGING_SIGN);

//        FlammableBlockRegistry.getDefaultInstance().add();
    }
}
