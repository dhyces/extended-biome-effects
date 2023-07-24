package dev.dhyces.biomeextensions;

import com.mojang.serialization.Codec;
import dev.dhyces.biomeextensions.extension.BiomeExtension;
import dev.dhyces.biomeextensions.extension.BiomeExtensionType;
import dev.dhyces.biomeextensions.extension.ExtensionCollection;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

import java.util.Optional;

public interface ApiAccess {
    ResourceKey<Registry<ExtensionCollection>> EXTENSION_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation("biomeextensions", "biomeextensions/extensions"));


    static ApiAccess getInstance() {
        throw new AssertionError("Implemented with mixins. Something has gone horribly wrong if this is thrown.");
    };

    Optional<ExtensionCollection> getExtensionsFor(RegistryAccess registryAccess, Holder<Biome> biome);

    /**
     * @return The codec that maps ResourceLocations to a registered BiomeExtensionType<?>
     */
    Codec<BiomeExtensionType<?>> getTypeCodec();
}
