package dev.dhyces.biomeextensions.registry;

import dev.dhyces.biomeextensions.ApiAccess;
import dev.dhyces.biomeextensions.extension.ExtensionCollection;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DataPackRegistryEvent;

public class ExtendedBiomeRegistry {
    public static void init(IEventBus modBus) {
        modBus.addListener(ExtendedBiomeRegistry::createRegistry);
    }

    private static void createRegistry(final DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(ApiAccess.EXTENSION_REGISTRY_KEY, ExtensionCollection.CODEC, ExtensionCollection.CODEC);
    }
}
