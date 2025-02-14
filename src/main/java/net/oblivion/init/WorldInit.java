package net.oblivion.init;

import com.mojang.serialization.MapCodec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import net.oblivion.OblivionMain;
import net.oblivion.world.*;
import net.oblivion.world.feature.GuidelightFeature;
import net.oblivion.world.feature.OblivionOreFeature;
import net.oblivion.world.feature.OblivionOreFeatureConfig;

public class WorldInit {

    public static final RegistryKey<World> OBLIVION_WORLD = RegistryKey.of(RegistryKeys.WORLD, OblivionMain.identifierOf("oblivion"));

    public static final FoliagePlacerType<SlimTreeFoliagePlacer> SLIM_TREE_FOLIAGE_PLACER = register("slim_tree_foliage_placer", SlimTreeFoliagePlacer.CODEC);
    public static final FoliagePlacerType<IronTreeFoliagePlacer> IRON_TREE_FOLIAGE_PLACER = register("iron_tree_foliage_placer", IronTreeFoliagePlacer.CODEC);
    public static final FoliagePlacerType<RuneTreeFoliagePlacer> RUNE_TREE_FOLIAGE_PLACER = register("rune_tree_foliage_placer", RuneTreeFoliagePlacer.CODEC);

    public static final Feature<OblivionOreFeatureConfig> OBLIVION_ORE = register("oblivion_ore", new OblivionOreFeature(OblivionOreFeatureConfig.CODEC));

    public static final Feature<DefaultFeatureConfig> GUIDELIGHT = register("guidelight", new GuidelightFeature(DefaultFeatureConfig.CODEC));

    private static <P extends FoliagePlacer> FoliagePlacerType<P> register(String id, MapCodec<P> codec) {
        return Registry.register(Registries.FOLIAGE_PLACER_TYPE, id, new FoliagePlacerType<>(codec));
    }

    private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return Registry.register(Registries.FEATURE, OblivionMain.identifierOf(name), feature);
    }

    // Todo: Goblin item drop
    // Set wood toolRequired and mixin into item isCorrectForDrops, maybe requires earlystage compat

    public static void init() {
    }
}
