package dev.dhyces.biomeextensions.extension.effects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.dhyces.biomeextensions.extension.ExtensionElementType;

public class SkyFogExtension extends BaseFogExtension {
    public static final Codec<SkyFogExtension> CODEC = RecordCodecBuilder.create(instance ->
            baseCodec(instance).apply(instance, SkyFogExtension::new)
    );

    public SkyFogExtension(float nearPlane, float farPlane) {
        super(nearPlane, farPlane);
    }

    @Override
    public ExtensionElementType<?> getType() {
        return ExtensionElementType.SKY_FOG;
    }
}
