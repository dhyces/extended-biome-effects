package dev.dhyces.biomeextensions;

import com.mojang.serialization.Codec;
import dev.dhyces.biomeextensions.extension.ExtensionElement;
import dev.dhyces.biomeextensions.extension.ExtensionElementType;

import java.util.function.Supplier;

public interface ApiEntrypoint {
    void registerTypes(EffectTypeRegister registration);

    @FunctionalInterface
    interface EffectTypeRegister {
        /**
         * This is a helper interface to register a {@link ExtensionElement}. It automatically fills the namespace
         * with the modid of your mod, so DO NOT include it here, or it will crash. This ensures you are registering it
         * only to your namespace and is generally helpful for brevity of code.
         * @param id
         * @param codec
         * @return
         * @param <T>
         */
        default <T extends ExtensionElement> ExtensionElementType<T> register(String id, Supplier<Codec<T>> codec) {
            return register(id, (ExtensionElementType<T>)(codec::get));
        }

        <T extends ExtensionElement> ExtensionElementType<T> register(String id, ExtensionElementType<T> type);
    }
}
