package dev.dhyces.biomeextensions.api;

import com.mojang.serialization.Codec;
import dev.dhyces.biomeextensions.ApiAccess;
import dev.dhyces.biomeextensions.extension.BiomeExtensionRegistry;
import dev.dhyces.biomeextensions.extension.ExtensionElementType;
import dev.dhyces.biomeextensions.extension.BiomeExtension;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

import java.util.Optional;

public class ApiAccessImpl implements ApiAccess {
    public static final ApiAccess INSTANCE = new ApiAccessImpl();

    @Override
    public Optional<BiomeExtension> getExtensionsFor(RegistryAccess registryAccess, Holder<Biome> biome) {
        HolderLookup.RegistryLookup<BiomeExtension> registry = registryAccess.lookupOrThrow(ApiAccess.EXTENSION_REGISTRY_KEY);
        Optional<Holder.Reference<BiomeExtension>> extensionCollection = registry.get(ResourceKey.create(ApiAccess.EXTENSION_REGISTRY_KEY, biome.unwrapKey().get().location()));
        return extensionCollection.map(Holder.Reference::get);
    }

    @Override
    public Codec<ExtensionElementType<?>> getTypeCodec() {
        return BiomeExtensionRegistry.CODEC;
    }
}
