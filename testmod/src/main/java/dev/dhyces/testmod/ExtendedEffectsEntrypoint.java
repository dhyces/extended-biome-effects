package dev.dhyces.testmod;

import dev.dhyces.biomeextensions.ApiEntrypoint;
import dev.dhyces.biomeextensions.extension.ExtensionElement;
import dev.dhyces.biomeextensions.extension.ExtensionElementType;

public class ExtendedEffectsEntrypoint implements ApiEntrypoint {
    public static final ExtensionElementType<ExtensionElement> NOTHING = () -> NothingExtension.NOTHING_CODEC;

    @Override
    public void registerTypes(EffectTypeRegister registration) {
        registration.register("nothing", NOTHING);
    }
}
