package dev.dhyces.biomeextensions.data.util;

import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.biome.AmbientAdditionsSettings;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnegative;

public class BetterBiomeSpecialEffectsBuilder {

    BiomeSpecialEffects.Builder delegate;

    BetterBiomeSpecialEffectsBuilder(@NotNull BiomeSpecialEffects.Builder builder) {
        this.delegate = builder;
    }

    /**
     * @return A new BetterBiomeSpecialEffectsBuilder
     * @exception IllegalStateException if the necessary fog, water, water fog, and sky color values are not filled
     */
    public static BetterBiomeSpecialEffectsBuilder builder() {
        return new BetterBiomeSpecialEffectsBuilder(new BiomeSpecialEffects.Builder());
    }

    /**
     * Default values are as follows:
     * Fog Color: 0x000000
     * Water Color: 0x000000
     * Water Fog Color: 0x000000
     * Sky Color: 0x000000
     * @return BetterBiomeSpecialEffectsBuilder with defaulted values
     */
    public static BetterBiomeSpecialEffectsBuilder defaultedBuilder() {
        return builder(0x000000, 0x000000, 0x000000, 0x000000);
    }

    /**
     *
     * @param fogColor Color of the fog when in the biome
     * @param waterColor Color of the water when in the biome
     * @param waterFogColor Color of the fog underwater when in the biome
     * @param skyColor Color of the sky when in the biome
     * @return BetterBiomeSpecialEffectsBuilder with given required values
     */
    public static BetterBiomeSpecialEffectsBuilder builder(@Nonnegative int fogColor, @Nonnegative int waterColor, @Nonnegative int waterFogColor, @Nonnegative int skyColor) {
        BiomeSpecialEffects.Builder builder = new BiomeSpecialEffects.Builder();
        builder.fogColor(fogColor);
        builder.waterColor(waterColor);
        builder.waterFogColor(waterFogColor);
        builder.skyColor(skyColor);
        return new BetterBiomeSpecialEffectsBuilder(builder);
    }

    /**
     *
     * @return BiomeSpecialEffects returns the result of a defaulted builder
     */
    public static BiomeSpecialEffects defaulted() {
        return builder().build();
    }

    public BetterBiomeSpecialEffectsBuilder fogColor(@Nonnegative int fogColor) {
        delegate.fogColor(fogColor);
        return this;
    }

    public BetterBiomeSpecialEffectsBuilder waterColor(@Nonnegative int waterColor) {
        delegate.waterColor(waterColor);
        return this;
    }

    public BetterBiomeSpecialEffectsBuilder waterFogColor(@Nonnegative int waterFogColor) {
        delegate.waterFogColor(waterFogColor);
        return this;
    }

    public BetterBiomeSpecialEffectsBuilder skyColor(@Nonnegative int skyColor) {
        delegate.skyColor(skyColor);
        return this;
    }

    public BetterBiomeSpecialEffectsBuilder foliageColor(@Nonnegative int foliageColor) {
        delegate.foliageColorOverride(foliageColor);
        return this;
    }

    public BetterBiomeSpecialEffectsBuilder grassColor(@Nonnegative int grassColor) {
        delegate.grassColorOverride(grassColor);
        return this;
    }

    public BetterBiomeSpecialEffectsBuilder grassColorModifier(@NotNull BiomeSpecialEffects.GrassColorModifier grassColorModifier) {
        delegate.grassColorModifier(grassColorModifier);
        return this;
    }

    public BetterBiomeSpecialEffectsBuilder particleConfig(@NotNull AmbientParticleSettings particleConfig) {
        delegate.ambientParticle(particleConfig);
        return this;
    }

    public BetterBiomeSpecialEffectsBuilder particleConfig(@NotNull ParticleOptions effect, float probability) {
        delegate.ambientParticle(new AmbientParticleSettings(effect, probability));
        return this;
    }

    public BetterBiomeSpecialEffectsBuilder loopSound(@NotNull Holder<SoundEvent> sound) {
        delegate.ambientLoopSound(sound);
        return this;
    }

    public BetterBiomeSpecialEffectsBuilder moodSound(@NotNull AmbientMoodSettings moodSound) {
        delegate.ambientMoodSound(moodSound);
        return this;
    }

    public BetterBiomeSpecialEffectsBuilder additionsSound(@NotNull AmbientAdditionsSettings additionsSound) {
        delegate.ambientAdditionsSound(additionsSound);
        return this;
    }

    public BetterBiomeSpecialEffectsBuilder music(@NotNull Music music) {
        delegate.backgroundMusic(music);
        return this;
    }

    public BiomeSpecialEffects build() {
        return delegate.build();
    }
}