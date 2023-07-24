package dev.dhyces.testmod;

import com.mojang.serialization.Codec;
import dev.dhyces.biomeextensions.extension.BiomeExtension;
import dev.dhyces.biomeextensions.extension.BiomeExtensionType;
import net.minecraft.util.ExtraCodecs;

public class NothingExtension implements BiomeExtension {
    public static final Codec<BiomeExtension> NOTHING_CODEC = ExtraCodecs.lazyInitializedCodec(() -> Codec.STRING.xmap(
            s -> new NothingExtension(),
            Object::toString
    ));

    @Override
    public BiomeExtensionType<?> getType() {
        return ExtendedEffectsEntrypoint.NOTHING;
    }
}
