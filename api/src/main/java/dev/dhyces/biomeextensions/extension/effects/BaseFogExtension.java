package dev.dhyces.biomeextensions.extension.effects;

import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.dhyces.biomeextensions.extension.ExtensionElement;

public abstract class BaseFogExtension implements ExtensionElement {
    public static <T extends BaseFogExtension> Products.P2<RecordCodecBuilder.Mu<T>, Float, Float> baseCodec(RecordCodecBuilder.Instance<T> instance) {
        return instance.group(
                Codec.FLOAT.fieldOf("near_plane").forGetter(BaseFogExtension::getNearPlane),
                Codec.FLOAT.fieldOf("far_plane").forGetter(BaseFogExtension::getFarPlane)
        );
    }

    private final float nearPlane;
    private final float farPlane;

    public BaseFogExtension(float nearPlane, float farPlane) {
        this.nearPlane = nearPlane;
        this.farPlane = farPlane;
    }

    public float getNearPlane() {
        return nearPlane;
    }

    public float getFarPlane() {
        return farPlane;
    }
}
