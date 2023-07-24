package dev.dhyces.biomeextensions.extension;

import com.mojang.serialization.Codec;
import net.minecraft.Util;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ExtensionCollection {
    public static final Codec<ExtensionCollection> CODEC = BiomeExtension.LIST_CODEC.xmap(
            extensions -> new ExtensionCollection(Util.make(new HashMap<>(), map -> extensions.forEach(extension -> map.put(extension.getType(), extension)))),
            collection -> new ArrayList<>(collection.storage.values())
    );

    private final Map<BiomeExtensionType<?>, ? extends BiomeExtension> storage;

    public ExtensionCollection(Map<BiomeExtensionType<?>, ? extends BiomeExtension> copied) {
        this.storage = copied;
    }

    @Nullable
    public <T extends BiomeExtension> T get(BiomeExtensionType<T> type) {
        return cast(storage.get(type));
    }

    public <T extends BiomeExtension> T getRequired(BiomeExtensionType<T> type) {
        if (!storage.containsKey(type)) {
            throw new IllegalStateException("Missing required entry for " + type.getClass().getSimpleName());
        }
        return cast(storage.get(type));
    }

    public <T extends BiomeExtension> Optional<T> getOptional(BiomeExtensionType<T> type) {
        return Optional.ofNullable(cast(storage.get(type)));
    }

    @SuppressWarnings("unchecked")
    private <T> T cast(Object o) {
        return (T)o;
    }
}
