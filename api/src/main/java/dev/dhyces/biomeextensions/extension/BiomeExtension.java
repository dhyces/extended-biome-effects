package dev.dhyces.biomeextensions.extension;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import net.minecraft.Util;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class BiomeExtension {
    public static final Codec<BiomeExtension> CODEC = ExtensionElement.LIST_CODEC.xmap(
            extensions -> new BiomeExtension(Util.make(new HashMap<>(), map -> extensions.forEach(extension -> map.put(extension.getType(), extension)))),
            collection -> new ArrayList<>(collection.storage.values())
    );

    private final Map<ExtensionElementType<?>, ? extends ExtensionElement> storage;

    public BiomeExtension(Map<ExtensionElementType<?>, ? extends ExtensionElement> backing) {
        this.storage = backing;
    }

    public static BiomeExtension single(ExtensionElement element) {
        ImmutableMap.Builder<ExtensionElementType<?>, ExtensionElement> builder = ImmutableMap.builder();
        builder.put(element.getType(), element);
        return new BiomeExtension(builder.build());
    }

    @Nullable
    public <T extends ExtensionElement> T get(ExtensionElementType<T> type) {
        return cast(storage.get(type));
    }

    public <T extends ExtensionElement> T getRequired(ExtensionElementType<T> type) {
        if (!storage.containsKey(type)) {
            throw new IllegalStateException("Missing required entry for " + type.getClass().getSimpleName());
        }
        return cast(storage.get(type));
    }

    public <T extends ExtensionElement> Optional<T> getOptional(ExtensionElementType<T> type) {
        return Optional.ofNullable(cast(storage.get(type)));
    }

    @SuppressWarnings("unchecked")
    private <T> T cast(Object o) {
        return (T)o;
    }
}
