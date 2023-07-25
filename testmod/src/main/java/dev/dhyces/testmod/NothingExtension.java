package dev.dhyces.testmod;

import com.mojang.serialization.Codec;
import dev.dhyces.biomeextensions.extension.ExtensionElement;
import dev.dhyces.biomeextensions.extension.ExtensionElementType;
import net.minecraft.util.ExtraCodecs;

public class NothingExtension implements ExtensionElement {
    public static final Codec<ExtensionElement> NOTHING_CODEC = ExtraCodecs.lazyInitializedCodec(() -> Codec.STRING.xmap(
            s -> new NothingExtension(),
            Object::toString
    ));

    @Override
    public ExtensionElementType<?> getType() {
        return ExtendedEffectsEntrypoint.NOTHING;
    }
}
