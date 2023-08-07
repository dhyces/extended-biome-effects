package dev.dhyces.biomeextensions.data.util;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnegative;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class BetterBiomeGenerationSettingsBuilder {

    private static final Object2IntMap<Holder<PlacedFeature>> FEATURE_PLACE = new Object2IntOpenHashMap<>();
    BiomeGenerationSettings.PlainBuilder delegate;
    List<Pair<Integer, Holder<PlacedFeature>>> features = new LinkedList<>();

    BetterBiomeGenerationSettingsBuilder(@NotNull BiomeGenerationSettings.PlainBuilder builder) {
        this.delegate = builder;
    }

    /**
     *
     * @return a new BetterBiomeGenerationSettingsBuilder with nothing added
     */
    public static BetterBiomeGenerationSettingsBuilder builder() {
        return new BetterBiomeGenerationSettingsBuilder(new BiomeGenerationSettings.PlainBuilder());
    }

    /**
     *
     * @return BiomeGenerationSettings with nothing added
     */
    public static BiomeGenerationSettings none() {
        return BiomeGenerationSettings.EMPTY;
    }

    public BetterBiomeGenerationSettingsBuilder rawGenFeature(@NotNull Holder<PlacedFeature> featureEntry) {
        return addFeature(GenerationStep.Decoration.RAW_GENERATION, featureEntry);
    }

    public BetterBiomeGenerationSettingsBuilder lakesFeature(@NotNull Holder<PlacedFeature> featureEntry) {
        return addFeature(GenerationStep.Decoration.LAKES, featureEntry);
    }

    public BetterBiomeGenerationSettingsBuilder localModFeature(@NotNull Holder<PlacedFeature> featureEntry) {
        return addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, featureEntry);
    }

    public BetterBiomeGenerationSettingsBuilder undergroudStructFeature(@NotNull Holder<PlacedFeature> featureEntry) {
        return addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, featureEntry);
    }

    public BetterBiomeGenerationSettingsBuilder surfaceStructFeature(@NotNull Holder<PlacedFeature> featureEntry) {
        return addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, featureEntry);
    }

    public BetterBiomeGenerationSettingsBuilder strongholdsFeature(@NotNull Holder<PlacedFeature> featureEntry) {
        return addFeature(GenerationStep.Decoration.STRONGHOLDS, featureEntry);
    }

    public BetterBiomeGenerationSettingsBuilder undergroundOresFeature(@NotNull Holder<PlacedFeature> featureEntry) {
        return addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, featureEntry);
    }

    public BetterBiomeGenerationSettingsBuilder undergroundDecoFeature(@NotNull Holder<PlacedFeature> featureEntry) {
        return addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, featureEntry);
    }

    public BetterBiomeGenerationSettingsBuilder fluidSpringsFeature(@NotNull Holder<PlacedFeature> featureEntry) {
        return addFeature(GenerationStep.Decoration.FLUID_SPRINGS, featureEntry);
    }

    public BetterBiomeGenerationSettingsBuilder vegetalDecoFeature(@NotNull Holder<PlacedFeature> featureEntry) {
        return addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, featureEntry);
    }

    public BetterBiomeGenerationSettingsBuilder topLayerFeature(@NotNull Holder<PlacedFeature> featureEntry) {
        return addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, featureEntry);
    }

    public BetterBiomeGenerationSettingsBuilder addFeature(@Nonnegative int step, @NotNull Holder<PlacedFeature> featureEntry) {
        FEATURE_PLACE.putIfAbsent(featureEntry, FEATURE_PLACE.size());
        features.add(Pair.of(step, featureEntry));
        return this;
    }

    protected BetterBiomeGenerationSettingsBuilder addFeature(@NotNull GenerationStep.Decoration step, @NotNull Holder<PlacedFeature> featureEntry) {
        return addFeature(step.ordinal(), featureEntry);
    }

    public BetterBiomeGenerationSettingsBuilder add(Consumer<BiomeGenerationSettings.PlainBuilder> builderConsumer) {
        BiomeGenerationSettings.PlainBuilder temp = new BiomeGenerationSettings.PlainBuilder();
        builderConsumer.accept(temp);
        BiomeGenerationSettings tempBuilt = temp.build();
        List<HolderSet<PlacedFeature>> featureList = tempBuilt.features();
        for (int i = 0; i < featureList.size(); i++) {
            for (Holder<PlacedFeature> feature : featureList.get(i)) {
                addFeature(i, feature);
            }
        }
        for (Holder<ConfiguredWorldCarver<?>> carver : tempBuilt.getCarvers(GenerationStep.Carving.AIR)) {
            airCarver(carver);
        }
        for (Holder<ConfiguredWorldCarver<?>> carver : tempBuilt.getCarvers(GenerationStep.Carving.LIQUID)) {
            liquidCarver(carver);
        }
        return this;
    }

    public BetterBiomeGenerationSettingsBuilder airCarver(@NotNull Holder<ConfiguredWorldCarver<?>> configuredCarverEntry) {
        delegate.addCarver(GenerationStep.Carving.AIR, configuredCarverEntry);
        return this;
    }

    public BetterBiomeGenerationSettingsBuilder liquidCarver(@NotNull Holder<ConfiguredWorldCarver<?>> configuredCarverEntry) {
        delegate.addCarver(GenerationStep.Carving.LIQUID, configuredCarverEntry);
        return this;
    }

    public BiomeGenerationSettings build() {
        features.stream().sorted(Comparator.comparingInt(o -> FEATURE_PLACE.getInt(o.getSecond()))).forEach(pair -> delegate.addFeature(pair.getFirst(), pair.getSecond()));
        return delegate.build();
    }
}