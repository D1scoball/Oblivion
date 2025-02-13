package net.oblivion.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public record OblivionOreFeatureConfig(BlockStateProvider blockStateProvider, int size, RuleTest target) implements FeatureConfig {

    public static final Codec<OblivionOreFeatureConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            BlockStateProvider.TYPE_CODEC.fieldOf("state_provider").forGetter(OblivionOreFeatureConfig::blockStateProvider),
                            Codecs.NONNEGATIVE_INT.fieldOf("size").orElse(2).forGetter(OblivionOreFeatureConfig::size),
                            RuleTest.TYPE_CODEC.fieldOf("target").forGetter(target -> target.target)
                    )
                    .apply(instance, OblivionOreFeatureConfig::new)
    );

}
