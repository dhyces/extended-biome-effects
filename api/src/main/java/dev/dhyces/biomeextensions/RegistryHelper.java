package dev.dhyces.biomeextensions;

import dev.dhyces.biomeextensions.extension.BiomeExtension;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class RegistryHelper {
    public static ResourceKey<BiomeExtension> registryKey(ResourceLocation elementId) {
        return ResourceKey.create(ApiAccess.EXTENSION_REGISTRY_KEY, elementId);
    }
}
