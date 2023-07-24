package dev.dhyces.biomeextensions.extension.effects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.dhyces.biomeextensions.extension.BiomeExtension;
import dev.dhyces.biomeextensions.extension.BiomeExtensionType;

public class FogExtension implements BiomeExtension {
    public static final Codec<FogExtension> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.FLOAT.fieldOf("far_plane").forGetter(fogExtension -> fogExtension.farPlane),
                    Codec.FLOAT.fieldOf("delta").forGetter(fogExtension -> fogExtension.delta)
            ).apply(instance, FogExtension::new)
    );

    private final float farPlane;
    private final float delta;

    public FogExtension(float farPlane, float delta) {
        this.farPlane = farPlane;
        this.delta = delta;
    }

    public float getFarPlane() {
        return farPlane;
    }

    public float getDelta() {
        return delta;
    }

    @Override
    public BiomeExtensionType<?> getType() {
        return BiomeExtensionType.FOG;
    }
}
