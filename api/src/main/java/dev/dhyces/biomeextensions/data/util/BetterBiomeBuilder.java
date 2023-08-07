package dev.dhyces.biomeextensions.data.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnegative;
import java.util.function.Consumer;

public class BetterBiomeBuilder {

    Biome.BiomeBuilder delegate;

    BetterBiomeBuilder(Biome.BiomeBuilder builder) {
        this.delegate = builder;
    }

    /**
     * @return A new BiomeBuilder
     * @exception IllegalStateException if the necessary precipitation, temperature, downfall, BiomeEffects,
     * SpawnSettings, and GenerationSettings values are not filled
     */
    public static BetterBiomeBuilder builder() {
        return new BetterBiomeBuilder(new Biome.BiomeBuilder());
    }

    /**
     * Only sets these related aspects
     * @param hasPrecipitation Determines things like weather & entity variants
     * @param temperature Determines if the biome is cold or hot, if ice can form, if snow can fall, etc
     * @param downfall Determines the humidity of the biome, which determines fire spread
     * @return A half-baked builder. MUST still fill BiomeEffects, SpawnSettings, and GenerationSettings
     * @exception IllegalStateException if the remaining necessary BiomeEffects, SpawnSettings, and GenerationSettings
     * values are not filled
     */
    public static BetterBiomeBuilder builder(boolean hasPrecipitation, float temperature, float downfall) {
        Biome.BiomeBuilder builder = new Biome.BiomeBuilder();
        builder.hasPrecipitation(hasPrecipitation);
        builder.temperature(temperature);
        builder.downfall(downfall);
        return new BetterBiomeBuilder(builder);
    }

    /**
     *
     * @param hasPrecipitation Determines things like weather & entity variants
     * @param temperature Determines if the biome is cold or hot, if ice can form, if snow can fall, etc
     * @param downfall Determines the humidity of the biome, which determines fire spread
     * @param biomeEffects BiomeEffects of the biome
     * @param spawnSettings SpawnSettings of the biome
     * @param generationSettings GenerationSettings of the biome
     * @see Biome#getPrecipitationAt(BlockPos) For further searching on how precipitation is used
     * @see Biome#getBaseTemperature() For further searching on how temperature is used
     * @see net.minecraft.world.level.Level(BlockPos) Downfall explanation
     * @return A BiomeBuilder with all the necessary values filled
     */
    public static BetterBiomeBuilder builder(boolean hasPrecipitation, float temperature, float downfall,
                                             @NotNull BiomeSpecialEffects biomeEffects, @NotNull MobSpawnSettings spawnSettings,
                                             @NotNull BiomeGenerationSettings generationSettings) {
        Biome.BiomeBuilder builder = new Biome.BiomeBuilder();
        builder.hasPrecipitation(hasPrecipitation);
        builder.temperature(temperature);
        builder.downfall(downfall);
        builder.specialEffects(biomeEffects);
        builder.mobSpawnSettings(spawnSettings);
        builder.generationSettings(generationSettings);
        return new BetterBiomeBuilder(builder);
    }

    /**
     * Makes a BiomeBuilder with the following defaults:
     * Precipitation: NONE,
     * Temperature: 0.0F,
     * Downfall: 0.0F,
     * Special Effects: Defaulted values from BetterBiomeSpecialEffectsBuilder,
     * Spawn Settings: Empty,
     * Generation Settings: Empty
     * @see BetterBiomeSpecialEffectsBuilder
     * @see BetterMobSpawnSettingsBuilder
     * @see BetterBiomeGenerationSettingsBuilder
     * @return A defaulted BiomeBuilder
     */
    public static BetterBiomeBuilder defaultedBuilder() {
        return builder(false, 0.0F, 0.0F, BetterBiomeSpecialEffectsBuilder.defaulted(),
                BetterMobSpawnSettingsBuilder.none(), BetterBiomeGenerationSettingsBuilder.none());
    }

    public BetterBiomeBuilder precipitation(boolean hasPrecipitation) {
        delegate.hasPrecipitation(hasPrecipitation);
        return this;
    }

    public BetterBiomeBuilder hasPrecipitation() {
        return precipitation(true);
    }

    /**
     * @param temperature 0.15 or higher results in rain and anything less is snow if the biome has precipitation
     */
    public BetterBiomeBuilder temperature(float temperature) {
        delegate.temperature(temperature);
        return this;
    }

    /**
     * @param temperature 0.15 or higher results in rain and anything less is snow if the biome has precipitation
     */
    public BetterBiomeBuilder temperature(double temperature) {
        delegate.temperature((float)temperature);
        return this;
    }

    public BetterBiomeBuilder tempModifier(Biome.TemperatureModifier temperatureModifier) {
        delegate.temperatureAdjustment(temperatureModifier);
        return this;
    }

    public BetterBiomeBuilder frozenModifier() {
        delegate.temperatureAdjustment(Biome.TemperatureModifier.FROZEN);
        return this;
    }

    public BetterBiomeBuilder downfall(float downfall) {
        delegate.downfall(downfall);
        return this;
    }

    public BetterBiomeBuilder downfall(double downfall) {
        delegate.downfall((float)downfall);
        return this;
    }

    public BetterBiomeBuilder biomeEffects(@NotNull BiomeSpecialEffects biomeEffects) {
        delegate.specialEffects(biomeEffects);
        return this;
    }

    public BetterBiomeBuilder biomeEffects(@Nonnegative int fogColor, @Nonnegative int waterColor, @Nonnegative int waterFogColor, @Nonnegative int skyColor) {
        return biomeEffects(BetterBiomeSpecialEffectsBuilder.builder(fogColor, waterColor, waterFogColor, skyColor).build());
    }

    public BetterBiomeBuilder biomeEffects(@Nonnegative int fogColor, @Nonnegative int waterColor,
                                     @Nonnegative int waterFogColor, @Nonnegative int skyColor,
                                     @NotNull Consumer<BetterBiomeSpecialEffectsBuilder> builderConsumer) {
        BetterBiomeSpecialEffectsBuilder builder = BetterBiomeSpecialEffectsBuilder.builder(fogColor, waterColor, waterFogColor, skyColor);
        builderConsumer.accept(builder);
        return biomeEffects(builder.build());
    }

    public BetterBiomeBuilder biomeEffects(@NotNull Consumer<BetterBiomeSpecialEffectsBuilder> builderConsumer) {
        BetterBiomeSpecialEffectsBuilder builder = BetterBiomeSpecialEffectsBuilder.builder();
        builderConsumer.accept(builder);
        delegate.specialEffects(builder.build());
        return this;
    }

    public BetterBiomeBuilder defaultedBiomeEffects(@NotNull Consumer<BetterBiomeSpecialEffectsBuilder> builderConsumer) {
        BetterBiomeSpecialEffectsBuilder builder = BetterBiomeSpecialEffectsBuilder.defaultedBuilder();
        builderConsumer.accept(builder);
        delegate.specialEffects(builder.build());
        return this;
    }

    public BetterBiomeBuilder noSpawnSettings() {
        delegate.mobSpawnSettings(BetterMobSpawnSettingsBuilder.none());
        return this;
    }

    public BetterBiomeBuilder spawnSettings(@NotNull MobSpawnSettings spawnSettings) {
        delegate.mobSpawnSettings(spawnSettings);
        return this;
    }

    public BetterBiomeBuilder spawnSettings(@NotNull Consumer<BetterMobSpawnSettingsBuilder> builderConsumer) {
        BetterMobSpawnSettingsBuilder builder = BetterMobSpawnSettingsBuilder.builder();
        builderConsumer.accept(builder);
        delegate.mobSpawnSettings(builder.build());
        return this;
    }

    public BetterBiomeBuilder noGenerationSettings() {
        delegate.generationSettings(BetterBiomeGenerationSettingsBuilder.none());
        return this;
    }

    public BetterBiomeBuilder generationSettings(@NotNull BiomeGenerationSettings generationSettings) {
        delegate.generationSettings(generationSettings);
        return this;
    }

    public BetterBiomeBuilder generationSettings(@NotNull Consumer<BetterBiomeGenerationSettingsBuilder> builderConsumer) {
        BetterBiomeGenerationSettingsBuilder builder = BetterBiomeGenerationSettingsBuilder.builder();
        builderConsumer.accept(builder);
        delegate.generationSettings(builder.build());
        return this;
    }

    public Biome build() {
        return delegate.build();
    }
}