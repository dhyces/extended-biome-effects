package dev.dhyces.biomeextensions.util;

import com.mojang.serialization.Codec;
import dev.dhyces.biomeextensions.BiomeExtensionsMod;
import net.minecraft.resources.ResourceLocation;

public class ModResourceLocation {
    public static final Codec<ResourceLocation> CODEC = Codec.STRING.comapFlatMap(s -> {
        if (!s.contains(":") || s.charAt(0) == ':') {
            s = BiomeExtensionsMod.MODID + s;
        }
        return ResourceLocation.read(s);
    }, ResourceLocation::toString);
}
