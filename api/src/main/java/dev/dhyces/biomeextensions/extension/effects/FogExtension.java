package dev.dhyces.biomeextensions.extension.effects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.dhyces.biomeextensions.extension.ExtensionElement;
import dev.dhyces.biomeextensions.extension.ExtensionElementType;

public class FogExtension implements ExtensionElement {
    public static final Codec<FogExtension> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.FLOAT.fieldOf("near_plane").forGetter(fogExtension -> fogExtension.nearPlane),
                    Codec.FLOAT.fieldOf("far_plane").forGetter(fogExtension -> fogExtension.farPlane),
                    Codec.FLOAT.flatXmap(
                            aFloat -> aFloat <= 0 ? DataResult.error(() -> "Must be non-zero positive!") : DataResult.success(aFloat),
                            aFloat -> aFloat <= 0 ? DataResult.error(() -> "Must be non-zero positive!") : DataResult.success(aFloat)
                    ).optionalFieldOf("delta", 999f).forGetter(fogExtension -> fogExtension.delta)
            ).apply(instance, FogExtension::new)
    );

    private final float nearPlane;
    private final float farPlane;
    private final float delta;

    public FogExtension(float nearPlane, float farPlane, float delta) {
        this.nearPlane = nearPlane;
        this.farPlane = farPlane;
        this.delta = delta;
    }

    public float getNearPlane() {
        return nearPlane;
    }

    public float getFarPlane() {
        return farPlane;
    }

    public float getDelta() {
        return delta;
    }

    @Override
    public ExtensionElementType<?> getType() {
        return ExtensionElementType.FOG;
    }
}
