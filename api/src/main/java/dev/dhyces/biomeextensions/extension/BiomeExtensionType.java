package dev.dhyces.biomeextensions.extension;

import com.mojang.serialization.Codec;
import dev.dhyces.biomeextensions.ApiEntrypoint;
import dev.dhyces.biomeextensions.extension.effects.FogExtension;

public interface BiomeExtensionType<T extends BiomeExtension> {
    BiomeExtensionType<FogExtension> FOG = () -> FogExtension.CODEC;

    Codec<T> getCodec();

    static void internalBootstrap(ApiEntrypoint.EffectTypeRegister collector) {
        collector.register("fog", FOG);
    }
}
