package net.oblivion.init;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
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
import net.minecraft.util.math.Direction;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.oblivion.OblivionMain;
import net.oblivion.block.DrillBlock;
import net.oblivion.block.GuidelightBlock;
import net.oblivion.block.MultiOreBlock;
import net.oblivion.block.ShimmeringGrassBlock;
import net.oblivion.block.entity.DrillBlockEntity;
import net.oblivion.block.entity.GuidelightBlockEntity;
import net.oblivion.block.entity.MultiOreBlockEntity;

import java.util.*;

public class BlockInit {

    public static final List<Block> DATAGEN_BLOCKS = new ArrayList<Block>();
    public static final Map<Block, Integer> WOOD_BLOCKS = new HashMap<>();

    public static final BlockSetType IRON_WOOD_SET_TYPE = new BlockSetTypeBuilder().register(OblivionMain.identifierOf("iron_wood"));
    public static final BlockSetType SILVER_BIRCH_SET_TYPE = new BlockSetTypeBuilder().register(OblivionMain.identifierOf("silver_birch"));
    public static final BlockSetType RUNE_WOOD_SET_TYPE = new BlockSetTypeBuilder().register(OblivionMain.identifierOf("rune_wood"));

    public static final WoodType IRON_WOOD_TYPE = new WoodTypeBuilder().register(OblivionMain.identifierOf("iron_wood"), IRON_WOOD_SET_TYPE);
    public static final WoodType SILVER_BIRCH_TYPE = new WoodTypeBuilder().register(OblivionMain.identifierOf("silver_birch"), SILVER_BIRCH_SET_TYPE);
    public static final WoodType RUNE_WOOD_TYPE = new WoodTypeBuilder().register(OblivionMain.identifierOf("rune_wood"), RUNE_WOOD_SET_TYPE);

    public static final SaplingGenerator IRON_WOOD_SAPLING_GENERATOR = new SaplingGenerator("iron_wood", Optional.empty(), Optional.of(ConfiguredFeatures.of("iron_wood")), Optional.empty());
    public static final SaplingGenerator SILVER_BIRCH_SAPLING_GENERATOR = new SaplingGenerator("silver_birch", Optional.empty(), Optional.of(ConfiguredFeatures.of("silver_birch")), Optional.empty());
    public static final SaplingGenerator RUNE_WOOD_SAPLING_GENERATOR = new SaplingGenerator("rune_wood", Optional.empty(), Optional.of(ConfiguredFeatures.of("rune_wood")), Optional.empty());


    public static final Block IRON_WOOD_PLANKS = register("iron_wood_planks", 0, true, false, new Block(AbstractBlock.Settings.create().mapColor(MapColor.BROWN).instrument(NoteBlockInstrument.BASS).strength(4.0F, 6.0F).sounds(BlockSoundGroup.WOOD).burnable().requiresTool()));
    public static final Block IRON_WOOD_SAPLING = register("iron_wood_sapling", 0, true, false, new SaplingBlock(IRON_WOOD_SAPLING_GENERATOR, AbstractBlock.Settings.create().mapColor(MapColor.PINK).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CHERRY_SAPLING).pistonBehavior(PistonBehavior.DESTROY).requiresTool()));
    public static final Block IRON_WOOD_LOG = register("iron_wood_log", 0, true, false, new PillarBlock(AbstractBlock.Settings.create().mapColor(state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? MapColor.BROWN : MapColor.TERRACOTTA_GRAY).instrument(NoteBlockInstrument.BASS).strength(4.0F).sounds(BlockSoundGroup.WOOD).burnable().requiresTool()));
    public static final Block STRIPPED_IRON_WOOD_LOG = register("stripped_iron_wood_log", 0, true, false, new PillarBlock(AbstractBlock.Settings.create().mapColor(state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? MapColor.BROWN : MapColor.TERRACOTTA_GRAY).instrument(NoteBlockInstrument.BASS).strength(4.0F).sounds(BlockSoundGroup.WOOD).burnable().requiresTool()));
    public static final Block IRON_WOOD = register("iron_wood", 0, true, false, new PillarBlock(AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_GRAY).instrument(NoteBlockInstrument.BASS).strength(4.0F).sounds(BlockSoundGroup.WOOD).burnable().requiresTool()));
    public static final Block STRIPPED_IRON_WOOD = register("stripped_iron_wood", 0, true, false, new PillarBlock(AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_PINK).instrument(NoteBlockInstrument.BASS).strength(4.0F).sounds(BlockSoundGroup.WOOD).burnable().requiresTool()));
    public static final Block IRON_WOOD_LEAVES = register("iron_wood_leaves", 0, true, false, new LeavesBlock(AbstractBlock.Settings.create().mapColor(MapColor.PINK).strength(0.4F).ticksRandomly().sounds(BlockSoundGroup.CHERRY_LEAVES).nonOpaque().allowsSpawning(Blocks::canSpawnOnLeaves).suffocates(Blocks::never).blockVision(Blocks::never).burnable().pistonBehavior(PistonBehavior.DESTROY).solidBlock(Blocks::never).requiresTool()));
    public static final Block IRON_WOOD_SIGN = register("iron_wood_sign", 0, true, false, new SignBlock(IRON_WOOD_TYPE, AbstractBlock.Settings.create().mapColor(IRON_WOOD_PLANKS.getDefaultMapColor()).solid().instrument(NoteBlockInstrument.BASS).noCollision().strength(2.0F).burnable().requiresTool()));
    public static final Block IRON_WOOD_WALL_SIGN = register("iron_wood_wall_sign", 0, false, false, new WallSignBlock(IRON_WOOD_TYPE, AbstractBlock.Settings.create().mapColor(IRON_WOOD_LOG.getDefaultMapColor()).solid().instrument(NoteBlockInstrument.BASS).noCollision().strength(2.0F).dropsLike(IRON_WOOD_SIGN).burnable().requiresTool()));
    public static final Block IRON_WOOD_HANGING_SIGN = register("iron_wood_hanging_sign", 0, true, false, new HangingSignBlock(IRON_WOOD_TYPE, AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_PINK).solid().instrument(NoteBlockInstrument.BASS).noCollision().strength(2.0F).burnable().requiresTool()));
    public static final Block IRON_WOOD_WALL_HANGING_SIGN = register("iron_wood_wall_hanging_sign", 0, false, false, new WallHangingSignBlock(IRON_WOOD_TYPE, AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_PINK).solid().instrument(NoteBlockInstrument.BASS).noCollision().strength(2.0F).burnable().dropsLike(IRON_WOOD_HANGING_SIGN).requiresTool()));
    public static final Block IRON_WOOD_PRESSURE_PLATE = register("iron_wood_pressure_plate", 0, true, false, new PressurePlateBlock(IRON_WOOD_SET_TYPE, AbstractBlock.Settings.create().mapColor(IRON_WOOD_PLANKS.getDefaultMapColor()).solid().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).burnable().pistonBehavior(PistonBehavior.DESTROY).requiresTool()));
    public static final Block IRON_WOOD_TRAPDOOR = register("iron_wood_trapdoor", 0, true, false, new TrapdoorBlock(IRON_WOOD_SET_TYPE, AbstractBlock.Settings.create().mapColor(MapColor.BROWN).instrument(NoteBlockInstrument.BASS).strength(6.0F).nonOpaque().allowsSpawning(Blocks::never).burnable().requiresTool()));
    public static final Block POTTED_IRON_WOOD_SAPLING = register("potted_iron_wood_sapling", 0, false, false, Blocks.createFlowerPotBlock(IRON_WOOD_SAPLING));
    public static final Block IRON_WOOD_BUTTON = register("iron_wood_button", 0, true, false, Blocks.createWoodenButtonBlock(IRON_WOOD_SET_TYPE));
    public static final Block IRON_WOOD_STAIRS = register("iron_wood_stairs", 0, true, false, createStairsBlock(IRON_WOOD_PLANKS));
    public static final Block IRON_WOOD_SLAB = register("iron_wood_slab", 0, true, false, new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.BROWN).instrument(NoteBlockInstrument.BASS).strength(4.0F, 6.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block IRON_WOOD_FENCE_GATE = register("iron_wood_fence_gate", 0, true, false, new FenceGateBlock(IRON_WOOD_TYPE, AbstractBlock.Settings.create().mapColor(IRON_WOOD_PLANKS.getDefaultMapColor()).solid().instrument(NoteBlockInstrument.BASS).strength(4.0F, 6.0F).burnable()));
    public static final Block IRON_WOOD_FENCE = register("iron_wood_fence", 0, true, false, new FenceBlock(AbstractBlock.Settings.create().mapColor(IRON_WOOD_PLANKS.getDefaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(4.0F, 6.0F).burnable().sounds(BlockSoundGroup.WOOD)));
    public static final Block IRON_WOOD_DOOR = register("iron_wood_door", 0, true, false, new DoorBlock(IRON_WOOD_SET_TYPE, AbstractBlock.Settings.create().mapColor(IRON_WOOD_PLANKS.getDefaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(6.0F).nonOpaque().burnable().pistonBehavior(PistonBehavior.DESTROY)));

    public static final Block SILVER_BIRCH_PLANKS = register("silver_birch_planks", 1, true, false, new Block(AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block SILVER_BIRCH_SAPLING = register("silver_birch_sapling", 1, true, false, new SaplingBlock(SILVER_BIRCH_SAPLING_GENERATOR, AbstractBlock.Settings.create().mapColor(MapColor.PINK).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CHERRY_SAPLING).pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block SILVER_BIRCH_LOG = register("silver_birch_log", 1, true, false, Blocks.createLogBlock(MapColor.PALE_YELLOW, MapColor.TERRACOTTA_GRAY, BlockSoundGroup.WOOD));
    public static final Block STRIPPED_SILVER_BIRCH_LOG = register("stripped_silver_birch_log", 1, true, false, Blocks.createLogBlock(MapColor.PALE_YELLOW, MapColor.TERRACOTTA_PINK, BlockSoundGroup.WOOD));
    public static final Block SILVER_BIRCH = register("silver_birch", 1, true, false, new PillarBlock(AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_GRAY).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block STRIPPED_SILVER_BIRCH = register("stripped_silver_birch", 1, true, false, new PillarBlock(AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_PINK).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block SILVER_BIRCH_LEAVES = register("silver_birch_leaves", 1, true, false, new LeavesBlock(AbstractBlock.Settings.create().mapColor(MapColor.PINK).strength(0.2F).ticksRandomly().sounds(BlockSoundGroup.CHERRY_LEAVES).nonOpaque().allowsSpawning(Blocks::canSpawnOnLeaves).suffocates(Blocks::never).blockVision(Blocks::never).burnable().pistonBehavior(PistonBehavior.DESTROY).solidBlock(Blocks::never)));
    public static final Block SILVER_BIRCH_SIGN = register("silver_birch_sign", 1, true, false, new SignBlock(SILVER_BIRCH_TYPE, AbstractBlock.Settings.create().mapColor(SILVER_BIRCH_PLANKS.getDefaultMapColor()).solid().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).burnable()));
    public static final Block SILVER_BIRCH_WALL_SIGN = register("silver_birch_wall_sign", 1, false, false, new WallSignBlock(SILVER_BIRCH_TYPE, AbstractBlock.Settings.create().mapColor(SILVER_BIRCH_LOG.getDefaultMapColor()).solid().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).dropsLike(SILVER_BIRCH_SIGN).burnable()));
    public static final Block SILVER_BIRCH_HANGING_SIGN = register("silver_birch_hanging_sign", 1, true, false, new HangingSignBlock(SILVER_BIRCH_TYPE, AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_PINK).solid().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).burnable()));
    public static final Block SILVER_BIRCH_WALL_HANGING_SIGN = register("silver_birch_wall_hanging_sign", 1, false, false, new WallHangingSignBlock(SILVER_BIRCH_TYPE, AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_PINK).solid().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).burnable().dropsLike(SILVER_BIRCH_HANGING_SIGN)));
    public static final Block SILVER_BIRCH_PRESSURE_PLATE = register("silver_birch_pressure_plate", 1, true, false, new PressurePlateBlock(SILVER_BIRCH_SET_TYPE, AbstractBlock.Settings.create().mapColor(SILVER_BIRCH_PLANKS.getDefaultMapColor()).solid().instrument(NoteBlockInstrument.BASS).noCollision().strength(0.5F).burnable().pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block SILVER_BIRCH_TRAPDOOR = register("silver_birch_trapdoor", 1, true, false, new TrapdoorBlock(SILVER_BIRCH_SET_TYPE, AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).instrument(NoteBlockInstrument.BASS).strength(3.0F).nonOpaque().allowsSpawning(Blocks::never).burnable()));
    public static final Block POTTED_SILVER_BIRCH_SAPLING = register("potted_silver_birch_sapling", 1, false, false, Blocks.createFlowerPotBlock(SILVER_BIRCH_SAPLING));
    public static final Block SILVER_BIRCH_BUTTON = register("silver_birch_button", 1, true, false, Blocks.createWoodenButtonBlock(SILVER_BIRCH_SET_TYPE));
    public static final Block SILVER_BIRCH_STAIRS = register("silver_birch_stairs", 1, true, false, createStairsBlock(SILVER_BIRCH_PLANKS));
    public static final Block SILVER_BIRCH_SLAB = register("silver_birch_slab", 1, true, false, new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block SILVER_BIRCH_FENCE_GATE = register("silver_birch_fence_gate", 1, true, false, new FenceGateBlock(SILVER_BIRCH_TYPE, AbstractBlock.Settings.create().mapColor(SILVER_BIRCH_PLANKS.getDefaultMapColor()).solid().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).burnable()));
    public static final Block SILVER_BIRCH_FENCE = register("silver_birch_fence", 1, true, false, new FenceBlock(AbstractBlock.Settings.create().mapColor(SILVER_BIRCH_PLANKS.getDefaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).burnable().sounds(BlockSoundGroup.WOOD)));
    public static final Block SILVER_BIRCH_DOOR = register("silver_birch_door", 1, true, false, new DoorBlock(SILVER_BIRCH_SET_TYPE, AbstractBlock.Settings.create().mapColor(SILVER_BIRCH_PLANKS.getDefaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(3.0F).nonOpaque().burnable().pistonBehavior(PistonBehavior.DESTROY)));

    public static final Block RUNE_WOOD_PLANKS = register("rune_wood_planks", 2, true, false, new Block(AbstractBlock.Settings.create().mapColor(MapColor.PALE_PURPLE).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block RUNE_WOOD_SAPLING = register("rune_wood_sapling", 2, true, false, new SaplingBlock(RUNE_WOOD_SAPLING_GENERATOR, AbstractBlock.Settings.create().mapColor(MapColor.PINK).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CHERRY_SAPLING).pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block RUNE_WOOD_LOG = register("rune_wood_log", 2, true, false, Blocks.createLogBlock(MapColor.PALE_PURPLE, MapColor.TERRACOTTA_GRAY, BlockSoundGroup.WOOD));
    public static final Block STRIPPED_RUNE_WOOD_LOG = register("stripped_rune_wood_log", 2, true, false, Blocks.createLogBlock(MapColor.PALE_PURPLE, MapColor.TERRACOTTA_PINK, BlockSoundGroup.WOOD));
    public static final Block RUNE_WOOD = register("rune_wood", 2, true, false, new PillarBlock(AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_GRAY).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block STRIPPED_RUNE_WOOD = register("stripped_rune_wood", 2, true, false, new PillarBlock(AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_PINK).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block RUNE_WOOD_LEAVES = register("rune_wood_leaves", 2, true, false, new LeavesBlock(AbstractBlock.Settings.create().mapColor(MapColor.PINK).strength(0.2F).ticksRandomly().sounds(BlockSoundGroup.CHERRY_LEAVES).nonOpaque().allowsSpawning(Blocks::canSpawnOnLeaves).suffocates(Blocks::never).blockVision(Blocks::never).burnable().pistonBehavior(PistonBehavior.DESTROY).solidBlock(Blocks::never)));
    public static final Block RUNE_WOOD_SIGN = register("rune_wood_sign", 2, true, false, new SignBlock(RUNE_WOOD_TYPE, AbstractBlock.Settings.create().mapColor(RUNE_WOOD_PLANKS.getDefaultMapColor()).solid().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).burnable()));
    public static final Block RUNE_WOOD_WALL_SIGN = register("rune_wood_wall_sign", 2, false, false, new WallSignBlock(RUNE_WOOD_TYPE, AbstractBlock.Settings.create().mapColor(RUNE_WOOD_LOG.getDefaultMapColor()).solid().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).dropsLike(RUNE_WOOD_SIGN).burnable()));
    public static final Block RUNE_WOOD_HANGING_SIGN = register("rune_wood_hanging_sign", 2, true, false, new HangingSignBlock(RUNE_WOOD_TYPE, AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_PINK).solid().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).burnable()));
    public static final Block RUNE_WOOD_WALL_HANGING_SIGN = register("rune_wood_wall_hanging_sign", 2, false, false, new WallHangingSignBlock(RUNE_WOOD_TYPE, AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_PINK).solid().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).burnable().dropsLike(RUNE_WOOD_HANGING_SIGN)));
    public static final Block RUNE_WOOD_PRESSURE_PLATE = register("rune_wood_pressure_plate", 2, true, false, new PressurePlateBlock(RUNE_WOOD_SET_TYPE, AbstractBlock.Settings.create().mapColor(RUNE_WOOD_PLANKS.getDefaultMapColor()).solid().instrument(NoteBlockInstrument.BASS).noCollision().strength(0.5F).burnable().pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block RUNE_WOOD_TRAPDOOR = register("rune_wood_trapdoor", 2, true, false, new TrapdoorBlock(RUNE_WOOD_SET_TYPE, AbstractBlock.Settings.create().mapColor(MapColor.PALE_PURPLE).instrument(NoteBlockInstrument.BASS).strength(3.0F).nonOpaque().allowsSpawning(Blocks::never).burnable()));
    public static final Block POTTED_RUNE_WOOD_SAPLING = register("potted_rune_wood_sapling", 2, false, false, Blocks.createFlowerPotBlock(RUNE_WOOD_SAPLING));
    public static final Block RUNE_WOOD_BUTTON = register("rune_wood_button", 2, true, false, Blocks.createWoodenButtonBlock(RUNE_WOOD_SET_TYPE));
    public static final Block RUNE_WOOD_STAIRS = register("rune_wood_stairs", 2, true, false, createStairsBlock(RUNE_WOOD_PLANKS));
    public static final Block RUNE_WOOD_SLAB = register("rune_wood_slab", 2, true, false, new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.PALE_PURPLE).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block RUNE_WOOD_FENCE_GATE = register("rune_wood_fence_gate", 2, true, false, new FenceGateBlock(RUNE_WOOD_TYPE, AbstractBlock.Settings.create().mapColor(RUNE_WOOD_PLANKS.getDefaultMapColor()).solid().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).burnable()));
    public static final Block RUNE_WOOD_FENCE = register("rune_wood_fence", 2, true, false, new FenceBlock(AbstractBlock.Settings.create().mapColor(RUNE_WOOD_PLANKS.getDefaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).burnable().sounds(BlockSoundGroup.WOOD)));
    public static final Block RUNE_WOOD_DOOR = register("rune_wood_door", 2, true, false, new DoorBlock(RUNE_WOOD_SET_TYPE, AbstractBlock.Settings.create().mapColor(RUNE_WOOD_PLANKS.getDefaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(3.0F).nonOpaque().burnable().pistonBehavior(PistonBehavior.DESTROY)));

    public static final Block DRILL = register("drill", -1, true, false, new DrillBlock(AbstractBlock.Settings.copy(Blocks.BARREL).nonOpaque().pistonBehavior(PistonBehavior.IGNORE)));
    public static final Block GUIDELIGHT = register("guidelight", -1, true, false, new GuidelightBlock(AbstractBlock.Settings.copy(Blocks.BEACON).strength(240.0f, 3600.0f)));

//    public static final Block OCTARINE_ORE = register("octarine_ore", true, true, new ExperienceDroppingBlock(ConstantIntProvider.create(0), AbstractBlock.Settings.create().mapColor(MapColor.WATER_BLUE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(3.0F, 3.0F)));
//    public static final Block DEEPSLATE_IRON_ORE = register("deepslate_octarine_ore", true, true, new ExperienceDroppingBlock(ConstantIntProvider.create(0), AbstractBlock.Settings.copyShallow(OCTARINE_ORE).mapColor(MapColor.WATER_BLUE).strength(4.5F, 3.0F).sounds(BlockSoundGroup.DEEPSLATE)));
//    public static final Block OCTARINE_BLOCK = register("octarine_block", true, true, new Block(AbstractBlock.Settings.create().mapColor(MapColor.WATER_BLUE).instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresTool().strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL)));
//    public static final Block RAW_OCTARINE_BLOCK = register("raw_octarine_block", true, true, new Block(AbstractBlock.Settings.create().mapColor(MapColor.RAW_IRON_PINK).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(5.0F, 6.0F)));

//    public static final Block SCARLET_ORE = register("scarlet_ore", true, true, new MultiOreBlock(20, AbstractBlock.Settings.create().mapColor(MapColor.WATER_BLUE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(3.0F, 3.0F)));
//    public static final Block SCARLET_BLOCK = register("scarlet_block", true, true, new Block(AbstractBlock.Settings.create().mapColor(MapColor.GRAY).instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresTool().strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL)));

    public static final Block FIERY_ANCIENT_DEBRIS = register("fiery_ancient_debris", -1, true, false, new MultiOreBlock(20, AbstractBlock.Settings.create().mapColor(MapColor.WATER_BLUE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(80.0F, 2400.0F)));
    public static final Block FIERY_NETHERITE_BLOCK = register("fiery_netherite_block", -1, true, true, new Block(AbstractBlock.Settings.create().mapColor(MapColor.GRAY).instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresTool().strength(80.0F, 1200.0F).sounds(BlockSoundGroup.NETHERITE)));

    public static final Block SCARLET_BLOCK = register("scarlet_block", -1, true, true, new Block(AbstractBlock.Settings.create().mapColor(MapColor.GRAY).instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresTool().strength(100.0F, 2600.0F).sounds(BlockSoundGroup.NETHERITE)));
    public static final Block RAW_SCARLET_BLOCK = register("raw_scarlet_block", -1, true, true, new MultiOreBlock(20, AbstractBlock.Settings.create().mapColor(MapColor.GRAY).instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresTool().strength(100.0F, 1400.0F).sounds(BlockSoundGroup.NETHERITE)));

    public static final Block SOLARITE_BLOCK = register("solarite_block", -1, true, true, new Block(AbstractBlock.Settings.create().mapColor(MapColor.GRAY).instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresTool().strength(120.0F, 3000.0F).sounds(BlockSoundGroup.NETHERITE)));
    public static final Block RAW_SOLARITE_BLOCK = register("raw_solarite_block", -1, true, true, new MultiOreBlock(20, AbstractBlock.Settings.create().mapColor(MapColor.GRAY).instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresTool().strength(120.0F, 1600.0F).sounds(BlockSoundGroup.NETHERITE)));

    public static final Block SHIMMERING_GRASS = register("shimmering_grass", -1, true, false, new ShimmeringGrassBlock(AbstractBlock.Settings.create().mapColor(MapColor.DARK_GREEN).replaceable().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ).burnable().pistonBehavior(PistonBehavior.DESTROY).ticksRandomly()
            .luminance(Blocks.createLightLevelFromLitBlockState(9))));

    public static BlockEntityType<DrillBlockEntity> DRILL_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, OblivionMain.identifierOf("drill_entity"),
            BlockEntityType.Builder.create(DrillBlockEntity::new, DRILL).build(null));
    public static BlockEntityType<MultiOreBlockEntity> MULTI_ORE_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, OblivionMain.identifierOf("multi_ore_entity"),
            BlockEntityType.Builder.create(MultiOreBlockEntity::new, FIERY_ANCIENT_DEBRIS, RAW_SCARLET_BLOCK, RAW_SOLARITE_BLOCK).build(null));
    public static BlockEntityType<GuidelightBlockEntity> GUIDELIGHT_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, OblivionMain.identifierOf("guidelight_entity"),
            BlockEntityType.Builder.create(GuidelightBlockEntity::new, GUIDELIGHT).build(null));

    private static Block register(String id, int woodTag, boolean registerItem, boolean datagenModel, Block block) {
        if (datagenModel) {
            DATAGEN_BLOCKS.add(block);
        }
        if (woodTag >= 0) {
            WOOD_BLOCKS.put(block, woodTag);
        }
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

        StrippableBlockRegistry.register(IRON_WOOD, STRIPPED_IRON_WOOD);
        StrippableBlockRegistry.register(IRON_WOOD_LOG, STRIPPED_IRON_WOOD_LOG);
        StrippableBlockRegistry.register(SILVER_BIRCH, STRIPPED_SILVER_BIRCH);
        StrippableBlockRegistry.register(SILVER_BIRCH_LOG, STRIPPED_SILVER_BIRCH_LOG);
        StrippableBlockRegistry.register(RUNE_WOOD, STRIPPED_RUNE_WOOD);
        StrippableBlockRegistry.register(RUNE_WOOD_LOG, STRIPPED_RUNE_WOOD_LOG);

        FlammableBlockRegistry.getDefaultInstance().add(STRIPPED_IRON_WOOD_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(IRON_WOOD_PLANKS, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(IRON_WOOD_SLAB, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(IRON_WOOD_FENCE_GATE, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(IRON_WOOD_FENCE, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(IRON_WOOD_STAIRS, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(IRON_WOOD, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(IRON_WOOD_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(IRON_WOOD_LEAVES, 30, 60);

        FlammableBlockRegistry.getDefaultInstance().add(STRIPPED_SILVER_BIRCH_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(SILVER_BIRCH_PLANKS, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(SILVER_BIRCH_SLAB, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(SILVER_BIRCH_FENCE_GATE, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(SILVER_BIRCH_FENCE, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(SILVER_BIRCH_STAIRS, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(SILVER_BIRCH, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(SILVER_BIRCH_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(SILVER_BIRCH_LEAVES, 30, 60);

        FlammableBlockRegistry.getDefaultInstance().add(STRIPPED_RUNE_WOOD_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(RUNE_WOOD_PLANKS, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RUNE_WOOD_SLAB, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RUNE_WOOD_FENCE_GATE, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RUNE_WOOD_FENCE, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RUNE_WOOD_STAIRS, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RUNE_WOOD, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(RUNE_WOOD_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(RUNE_WOOD_LEAVES, 30, 60);
    }
}
