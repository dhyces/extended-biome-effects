package dev.dhyces.biomeextensions;

import com.mojang.serialization.Codec;
import dev.dhyces.biomeextensions.extension.ExtensionElement;
import dev.dhyces.biomeextensions.extension.ExtensionElementType;
import dev.dhyces.biomeextensions.extension.BiomeExtension;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

import java.util.Optional;

public interface ApiAccess {
    ResourceKey<Registry<BiomeExtension>> EXTENSION_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation("biomeextensions", "extensions"));


    static ApiAccess getInstance() {
        throw new AssertionError("Implemented with mixins. Something has gone horribly wrong if this is thrown.");
    };

    Optional<BiomeExtension> getExtensionsFor(RegistryAccess registryAccess, Holder<Biome> biome);


    <T extends ExtensionElement> Optional<T> getExtensionsOfType(RegistryAccess registryAccess, Holder<Biome> biome, ExtensionElementType<T> type);

    /**
     * @return The codec that maps ResourceLocations to a registered BiomeExtensionType<?>
     */
    Codec<ExtensionElementType<?>> getTypeCodec();
}
