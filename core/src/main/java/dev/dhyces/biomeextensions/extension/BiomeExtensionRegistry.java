package dev.dhyces.biomeextensions.extension;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.serialization.Codec;
import dev.dhyces.biomeextensions.ApiEntrypoint;
import dev.dhyces.biomeextensions.ExtendedBiomeEffects;
import dev.dhyces.biomeextensions.util.ModResourceLocation;
import net.minecraft.resources.ResourceLocation;

// Holds the extension types that must be present on both sides
public class BiomeExtensionRegistry {
    private static final BiMap<ResourceLocation, ExtensionElementType<?>> TYPE_MAP = HashBiMap.create();
    public static final Codec<ExtensionElementType<?>> CODEC = ModResourceLocation.CODEC.xmap(TYPE_MAP::get, TYPE_MAP.inverse()::get);

    public static void init() {
        ExtensionElementType.internalBootstrap(BiomeExtensionRegistry::conductInternalRegistration);
        ExtendedBiomeEffects.API_CONTAINER.forEach((modid, apiEntrypoint) -> {
            apiEntrypoint.registerTypes(new ApiEntrypoint.EffectTypeRegister() {
                @Override
                public <T extends ExtensionElement> ExtensionElementType<T> register(String id, ExtensionElementType<T> type) {
                    return BiomeExtensionRegistry.register(new ResourceLocation(modid, id), type);
                }
            });
        });
    }

    private static <T extends ExtensionElement> ExtensionElementType<T> conductInternalRegistration(String id, ExtensionElementType<T> type) {
        return BiomeExtensionRegistry.register(ExtendedBiomeEffects.id(id), type);
    }

    public static <T extends ExtensionElement> ExtensionElementType<T> register(ResourceLocation id, ExtensionElementType<T> type) {
        TYPE_MAP.put(id, type);
        return type;
    }
}
