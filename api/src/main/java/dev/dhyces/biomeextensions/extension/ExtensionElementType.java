package dev.dhyces.biomeextensions.extension;

import com.mojang.serialization.Codec;
import dev.dhyces.biomeextensions.ApiEntrypoint;
import dev.dhyces.biomeextensions.extension.effects.SkyFogExtension;
import dev.dhyces.biomeextensions.extension.effects.TerrainFogExtension;

public interface ExtensionElementType<T extends ExtensionElement> {
    ExtensionElementType<TerrainFogExtension> TERRAIN_FOG = () -> TerrainFogExtension.CODEC;
    ExtensionElementType<SkyFogExtension> SKY_FOG = () -> SkyFogExtension.CODEC;

    Codec<T> getCodec();

    static void internalBootstrap(ApiEntrypoint.EffectTypeRegister collector) {
        collector.register("terrain_fog", TERRAIN_FOG);
        collector.register("sky_fog", SKY_FOG);
    }
}
