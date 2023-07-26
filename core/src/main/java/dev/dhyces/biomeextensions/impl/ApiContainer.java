package dev.dhyces.biomeextensions.impl;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import dev.dhyces.biomeextensions.ApiEntrypoint;
import dev.dhyces.biomeextensions.ExtendedBiomeEffects;
import net.minecraftforge.fml.ModList;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.function.BiConsumer;

public class ApiContainer {
    private BiMap<String, ApiEntrypoint> entrypoints;

    public ApiContainer() {
        entrypoints = HashBiMap.create();
        load();
    }

    private void load() {
        ModList.get().getMods().forEach(modInfo -> {
            if (modInfo.getModProperties().containsKey(ExtendedBiomeEffects.MODID)) {
                if (modInfo.getModProperties().get(ExtendedBiomeEffects.MODID) instanceof String qualifiedEntrypoint) {
                    try {
                        Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(qualifiedEntrypoint);
                        if (Set.of(clazz.getInterfaces()).contains(ApiEntrypoint.class)) {
                            try {
                                ApiEntrypoint entrypoint = (ApiEntrypoint) clazz.getDeclaredConstructor().newInstance();
                                entrypoints.put(modInfo.getModId(), entrypoint);
                            } catch (NoSuchMethodException e) {
                                throw new RuntimeException("Failed to initialize ApiEntrypoint for %s. Must have implicit default constructor or a constructor without parameters.".formatted(modInfo.getModId()), e);
                            } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                                throw new RuntimeException("Failed to initialize ApiEntrypoint for %s: ".formatted(modInfo.getModId()), e);
                            }
                        } else {
                            throw new IllegalStateException("Mod properties for %s uses \"extendedbiomeeffects\", but does not implement ApiEntrypoint.".formatted(modInfo.getModId()));
                        }
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    public void forEach(BiConsumer<String, ApiEntrypoint> consumer) {
        entrypoints.forEach(consumer);
    }
}
