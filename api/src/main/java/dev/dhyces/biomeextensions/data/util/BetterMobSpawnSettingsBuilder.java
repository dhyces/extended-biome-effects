package dev.dhyces.biomeextensions.data.util;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import org.jetbrains.annotations.NotNull;

/**
 * TODO: add method variants w/ a Weight arg
 * @see net.minecraft.util.random.Weight
 */
public class BetterMobSpawnSettingsBuilder {

    MobSpawnSettings.Builder delegate;

    BetterMobSpawnSettingsBuilder(@NotNull MobSpawnSettings.Builder builder) {
        this.delegate = builder;
    }

    public static BetterMobSpawnSettingsBuilder builder() {
        return new BetterMobSpawnSettingsBuilder(new MobSpawnSettings.Builder());
    }

    public static MobSpawnSettings none() {
        return MobSpawnSettings.EMPTY;
    }

    public BetterMobSpawnSettingsBuilder monster(@NotNull MobSpawnSettings.SpawnerData SpawnerData) {
        delegate.addSpawn(MobCategory.MONSTER, SpawnerData);
        return this;
    }

    public BetterMobSpawnSettingsBuilder monster(@NotNull EntityType<?> type, int weight, int minGroupSize, int maxGroupSize) {
        return monster(new MobSpawnSettings.SpawnerData(type, weight, minGroupSize, maxGroupSize));
    }

    public BetterMobSpawnSettingsBuilder creature(@NotNull MobSpawnSettings.SpawnerData SpawnerData) {
        delegate.addSpawn(MobCategory.CREATURE, SpawnerData);
        return this;
    }

    public BetterMobSpawnSettingsBuilder creature(@NotNull EntityType<?> type, int weight, int minGroupSize, int maxGroupSize) {
        return creature(new MobSpawnSettings.SpawnerData(type, weight, minGroupSize, maxGroupSize));
    }

    public BetterMobSpawnSettingsBuilder ambient(@NotNull MobSpawnSettings.SpawnerData SpawnerData) {
        delegate.addSpawn(MobCategory.AMBIENT, SpawnerData);
        return this;
    }

    public BetterMobSpawnSettingsBuilder ambient(@NotNull EntityType<?> type, int weight, int minGroupSize, int maxGroupSize) {

        return ambient(new MobSpawnSettings.SpawnerData(type, weight, minGroupSize, maxGroupSize));
    }

    public BetterMobSpawnSettingsBuilder axolotls(@NotNull MobSpawnSettings.SpawnerData SpawnerData) {
        delegate.addSpawn(MobCategory.AXOLOTLS, SpawnerData);
        return this;
    }

    public BetterMobSpawnSettingsBuilder axolotls(@NotNull EntityType<?> type, int weight, int minGroupSize, int maxGroupSize) {
        return axolotls(new MobSpawnSettings.SpawnerData(type, weight, minGroupSize, maxGroupSize));
    }

    public BetterMobSpawnSettingsBuilder undergroundWaterCreature(@NotNull MobSpawnSettings.SpawnerData SpawnerData) {
        delegate.addSpawn(MobCategory.UNDERGROUND_WATER_CREATURE, SpawnerData);
        return this;
    }

    public BetterMobSpawnSettingsBuilder undergroundWaterCreature(@NotNull EntityType<?> type, int weight, int minGroupSize, int maxGroupSize) {
        return undergroundWaterCreature(new MobSpawnSettings.SpawnerData(type, weight, minGroupSize, maxGroupSize));
    }

    public BetterMobSpawnSettingsBuilder waterCreature(@NotNull MobSpawnSettings.SpawnerData SpawnerData) {
        delegate.addSpawn(MobCategory.WATER_CREATURE, SpawnerData);
        return this;
    }

    public BetterMobSpawnSettingsBuilder waterCreature(@NotNull EntityType<?> type, int weight, int minGroupSize, int maxGroupSize) {
        return waterCreature(new MobSpawnSettings.SpawnerData(type, weight, minGroupSize, maxGroupSize));
    }

    public BetterMobSpawnSettingsBuilder waterAmbient(@NotNull MobSpawnSettings.SpawnerData SpawnerData) {
        delegate.addSpawn(MobCategory.WATER_AMBIENT, SpawnerData);
        return this;
    }

    public BetterMobSpawnSettingsBuilder waterAmbient(@NotNull EntityType<?> type, int weight, int minGroupSize, int maxGroupSize) {
        return waterAmbient(new MobSpawnSettings.SpawnerData(type, weight, minGroupSize, maxGroupSize));
    }

    public BetterMobSpawnSettingsBuilder misc(@NotNull MobSpawnSettings.SpawnerData SpawnerData) {
        delegate.addSpawn(MobCategory.MISC, SpawnerData);
        return this;
    }

    public BetterMobSpawnSettingsBuilder misc(@NotNull EntityType<?> type, int weight, int minGroupSize, int maxGroupSize) {
        return misc(new MobSpawnSettings.SpawnerData(type, weight, minGroupSize, maxGroupSize));
    }

    public BetterMobSpawnSettingsBuilder spawnCost(@NotNull EntityType<?> entityType, double mass, double gravityLimit) {
        delegate.addMobCharge(entityType, mass, gravityLimit);
        return this;
    }

    public BetterMobSpawnSettingsBuilder spawnChance(float probability) {
        delegate.creatureGenerationProbability(probability);
        return this;
    }

    public MobSpawnSettings build() {
        return delegate.build();
    }
}