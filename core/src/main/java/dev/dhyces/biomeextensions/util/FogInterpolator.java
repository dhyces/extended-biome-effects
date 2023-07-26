package dev.dhyces.biomeextensions.util;

import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import it.unimi.dsi.fastutil.floats.FloatUnaryOperator;
import net.minecraft.util.Mth;

import java.util.function.Consumer;

public class FogInterpolator {
    private float nearPlane;
    private float farPlane;
    private boolean invalid; // leaving a world should invalidate these values.

    public FogInterpolator() {}

    public void calc(float partialTick, float delta, Consumer<FloatUnaryOperator> near, Consumer<FloatUnaryOperator> far) {
        near.accept(val -> nearPlane = lerpToward(partialTick, nearPlane, val, delta));
        far.accept(val -> farPlane = lerpToward(partialTick, farPlane, val, delta));
    }

    private float lerpToward(float partialTick, float current, float target, float delta) {
        if (Mth.equal(current, target)) {
            return target;
        }
        delta = target - current < 0 ? -delta : delta;
        float lerped = Mth.lerp(partialTick, current, current + delta);
        return delta < 0 ? Math.max(target, lerped) : Math.min(target, lerped);
    }

    public void setNearPlane(float nearPlane) {
        this.nearPlane = nearPlane;
    }

    public void setFarPlane(float farPlane) {
        this.farPlane = farPlane;
    }

    public float getNearPlane() {
        return nearPlane;
    }

    public float getFarPlane() {
        return farPlane;
    }

    public boolean isInvalid() {
        return invalid;
    }

    public void setInvalid(boolean state) {
        this.invalid = state;
    }
}
