package dev.dhyces.biomeextensions.extension.effects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.dhyces.biomeextensions.extension.ExtensionElementType;

public class TerrainFogExtension extends BaseFogExtension {
    public static final Codec<TerrainFogExtension> CODEC = RecordCodecBuilder.create(instance ->
            baseCodec(instance).apply(instance, TerrainFogExtension::new)
    );

    public TerrainFogExtension(float nearPlane, float farPlane) {
        super(nearPlane, farPlane);
    }

    @Override
    public ExtensionElementType<?> getType() {
        return ExtensionElementType.TERRAIN_FOG;
    }
}
