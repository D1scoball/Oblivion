package net.oblivion.init;

import com.mojang.serialization.MapCodec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import net.oblivion.world.*;

public class WorldInit {

    public static final FoliagePlacerType<SlimTreeFoliagePlacer> SLIM_TREE_FOLIAGE_PLACER = register("slim_tree_foliage_placer", SlimTreeFoliagePlacer.CODEC);
    public static final FoliagePlacerType<IronTreeFoliagePlacer> IRON_TREE_FOLIAGE_PLACER = register("iron_tree_foliage_placer", IronTreeFoliagePlacer.CODEC);
    public static final FoliagePlacerType<RuneTreeFoliagePlacer> RUNE_TREE_FOLIAGE_PLACER = register("rune_tree_foliage_placer", RuneTreeFoliagePlacer.CODEC);

    private static <P extends FoliagePlacer> FoliagePlacerType<P> register(String id, MapCodec<P> codec) {
        return Registry.register(Registries.FOLIAGE_PLACER_TYPE, id, new FoliagePlacerType<>(codec));
    }

    public static void init() {
    }
}
