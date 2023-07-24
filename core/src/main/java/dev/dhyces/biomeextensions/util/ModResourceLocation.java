package dev.dhyces.biomeextensions.util;

import com.mojang.serialization.Codec;
import dev.dhyces.biomeextensions.ExtendedBiomeEffects;
import net.minecraft.resources.ResourceLocation;

public class ModResourceLocation {
    public static final Codec<ResourceLocation> CODEC = Codec.STRING.comapFlatMap(s -> {
        if (!s.contains(":") || s.charAt(0) == ':') {
            s = ExtendedBiomeEffects.MODID + s;
        }
        return ResourceLocation.read(s);
    }, ResourceLocation::toString);
}
