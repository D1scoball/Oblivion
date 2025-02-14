package net.oblivion.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryCodecs;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class QuicksandFeatureConfig implements FeatureConfig {

    public static final Codec<QuicksandFeatureConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            BlockStateProvider.TYPE_CODEC.fieldOf("state_provider").forGetter(config -> config.stateProvider),
                            Codec.BOOL.fieldOf("requires_block_below").orElse(true).forGetter(config -> config.requiresBlockBelow),
                            Codec.INT.fieldOf("size").orElse(2).forGetter(config -> config.size),
                            Codec.INT.fieldOf("depth").orElse(2).forGetter(config -> config.depth),
                            RegistryCodecs.entryList(RegistryKeys.BLOCK).fieldOf("valid_blocks").forGetter(config -> config.validBlocks)
                    ).apply(instance, QuicksandFeatureConfig::new));

    public final BlockStateProvider stateProvider;
    public final boolean requiresBlockBelow;
    public final int size;
    public final int depth;
    public final RegistryEntryList<Block> validBlocks;

    public QuicksandFeatureConfig(BlockStateProvider stateProvider, boolean requiresBlockBelow, int size, int depth, RegistryEntryList<Block> validBlocks) {
        this.stateProvider = stateProvider;
        this.requiresBlockBelow = requiresBlockBelow;
        this.size = size;
        this.depth = depth;
        this.validBlocks = validBlocks;
    }
}

