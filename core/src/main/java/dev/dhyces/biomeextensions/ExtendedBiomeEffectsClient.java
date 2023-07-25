package dev.dhyces.biomeextensions;

import dev.dhyces.biomeextensions.api.ApiAccessImpl;
import dev.dhyces.biomeextensions.extension.ExtensionElementType;
import dev.dhyces.biomeextensions.extension.effects.FogExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class ExtendedBiomeEffectsClient {

    static void init(IEventBus modBus, IEventBus forgeBus) {
        forgeBus.addListener(ExtendedBiomeEffectsClient::onFogRender);
    }

    private static Minecraft client() {
        return Minecraft.getInstance();
    }

    private static void onFogRender(final ViewportEvent.RenderFog event) {
        if (client().level == null || client().player == null) {
            return;
        }
        RegistryAccess registryAccess = client().level.registryAccess();
        Holder<Biome> currentBiome = client().level.getBiome(client().player.blockPosition());
        FogExtension extension = ApiAccessImpl.INSTANCE.getExtensionsFor(registryAccess, currentBiome)
                .map(extensionCollection -> extensionCollection.get(ExtensionElementType.FOG))
                .orElse(null);
        if (extension == null) {
            return;
        }

        float currentFarPlane = event.getFarPlaneDistance();
        float targetFarPlane = extension.getFarPlane();
        if (Mth.equal(currentFarPlane, targetFarPlane)) {
            return;
        }

        float delta = targetFarPlane < 0 ? -extension.getDelta() : extension.getDelta();
        float partialTick = client().getFrameTime();
        float lerped = Mth.lerp(partialTick, currentFarPlane, currentFarPlane + delta);
        event.setFarPlaneDistance(lerped);
    }

}
