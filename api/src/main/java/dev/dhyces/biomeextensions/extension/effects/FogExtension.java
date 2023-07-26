package dev.dhyces.biomeextensions.extension.effects;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.dhyces.biomeextensions.extension.ExtensionElement;
import dev.dhyces.biomeextensions.extension.ExtensionElementType;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;

import java.util.Optional;

public class FogExtension implements ExtensionElement {
    public static final Codec<FogShape> FOG_SHAPE_CODEC = Codec.STRING.comapFlatMap(
            s -> {
                try {
                    return DataResult.success(FogShape.valueOf(s));
                } catch (IllegalArgumentException e) {
                    return DataResult.error(e::getMessage);
                }
            },
            FogShape::name
    );
    public static final Codec<FogExtension> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.FLOAT.fieldOf("near_plane").forGetter(fogExtension -> fogExtension.nearPlane),
                    Codec.FLOAT.fieldOf("far_plane").forGetter(fogExtension -> fogExtension.farPlane),
                    Codec.FLOAT.flatXmap(
                            aFloat -> aFloat <= 0 ? DataResult.error(() -> "Must be non-zero positive!") : DataResult.success(aFloat),
                            aFloat -> aFloat <= 0 ? DataResult.error(() -> "Must be non-zero positive!") : DataResult.success(aFloat)
                    ).optionalFieldOf("delta", 999f).forGetter(fogExtension -> fogExtension.delta),
                    FOG_SHAPE_CODEC.optionalFieldOf("shape").forGetter(fogExtension -> fogExtension.shape)
            ).apply(instance, FogExtension::new)
    );

    private final float nearPlane;
    private final float farPlane;
    private final float delta;
    private final Optional<FogShape> shape;

    public FogExtension(float nearPlane, float farPlane, float delta, Optional<FogShape> shape) {
        this.nearPlane = nearPlane;
        this.farPlane = farPlane;
        this.delta = delta;
        this.shape = shape;
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

    public Optional<FogShape> getShape() {
        return shape;
    }

    @Override
    public ExtensionElementType<?> getType() {
        return ExtensionElementType.FOG;
    }
}
