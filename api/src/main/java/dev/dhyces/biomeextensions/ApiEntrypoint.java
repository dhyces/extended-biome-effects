package dev.dhyces.biomeextensions;

import com.mojang.serialization.Codec;
import dev.dhyces.biomeextensions.extension.BiomeExtension;
import dev.dhyces.biomeextensions.extension.BiomeExtensionType;

import java.util.function.Supplier;

public interface ApiEntrypoint {
    void registerTypes(EffectTypeRegister registration);

    @FunctionalInterface
    interface EffectTypeRegister {
        /**
         * This is a helper interface to register a {@link BiomeExtension}. It automatically fills the namespace
         * with the modid of your mod, so DO NOT include it here, or it will crash. This ensures you are registering it
         * only to your namespace and is generally helpful for brevity of code.
         * @param id
         * @param codec
         * @return
         * @param <T>
         */
        default <T extends BiomeExtension> BiomeExtensionType<T> register(String id, Supplier<Codec<T>> codec) {
            return register(id, (BiomeExtensionType<T>)(codec::get));
        }

        <T extends BiomeExtension> BiomeExtensionType<T> register(String id, BiomeExtensionType<T> type);
    }
}
