package dev.dhyces.testmod;

import dev.dhyces.biomeextensions.ApiEntrypoint;
import dev.dhyces.biomeextensions.extension.BiomeExtension;
import dev.dhyces.biomeextensions.extension.BiomeExtensionType;

public class ExtendedEffectsEntrypoint implements ApiEntrypoint {
    public static final BiomeExtensionType<BiomeExtension> NOTHING = () -> NothingExtension.NOTHING_CODEC;

    @Override
    public void registerTypes(EffectTypeRegister registration) {
        registration.register("nothing", NOTHING);
    }
}
