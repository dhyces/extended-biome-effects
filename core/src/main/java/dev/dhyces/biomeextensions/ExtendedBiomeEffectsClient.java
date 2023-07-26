package dev.dhyces.biomeextensions;

import dev.dhyces.biomeextensions.impl.ApiAccessImpl;
import dev.dhyces.biomeextensions.extension.ExtensionElementType;
import dev.dhyces.biomeextensions.extension.effects.FogExtension;
import dev.dhyces.biomeextensions.util.FogInterpolator;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogRenderer.FogMode;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.EnumMap;
import java.util.Map;

public class ExtendedBiomeEffectsClient {

    private static final Map<FogMode, FogInterpolator> FOG_INTERPOLATORS = Util.make(new EnumMap<>(FogMode.class), map -> {
        for (FogMode mode : FogMode.values()) {
            map.put(mode, new FogInterpolator());
        }
    });

    static void init(IEventBus modBus, IEventBus forgeBus) {
        forgeBus.addListener(EventPriority.HIGHEST, ExtendedBiomeEffectsClient::renderBiomeFog);
        forgeBus.addListener(ExtendedBiomeEffectsClient::invalidateInterpolators);
    }

    private static Minecraft client() {
        return Minecraft.getInstance();
    }

    private static void renderBiomeFog(final ViewportEvent.RenderFog event) {
        if (client().level == null || client().player == null) {
            return;
        }
        RegistryAccess registryAccess = client().level.registryAccess();
        Holder<Biome> currentBiome = client().level.getBiome(client().player.blockPosition());
        FogExtension extension = ApiAccessImpl.INSTANCE.getExtensionsOfType(registryAccess, currentBiome, ExtensionElementType.FOG)
                .orElse(null);

        FogInterpolator interpolator = FOG_INTERPOLATORS.get(event.getMode());
        if (interpolator.isInvalid()) {
            interpolator.setNearPlane(event.getNearPlaneDistance());
            interpolator.setFarPlane(event.getFarPlaneDistance());
            interpolator.setInvalid(false);
        }

        float targetNearPlane;
        float targetFarPlane;
        float delta;
        if (extension == null) {
            targetNearPlane = event.getNearPlaneDistance();
            targetFarPlane = event.getFarPlaneDistance();
            delta = 0.5f;
        } else {
            targetNearPlane = extension.getNearPlane();
            targetFarPlane = extension.getFarPlane();
            delta = extension.getDelta();
        }

        float partialTick = (float) event.getPartialTick();
        interpolator.calc(partialTick, delta, func -> func.apply(targetNearPlane), func -> func.apply(targetFarPlane));

        if (event.getNearPlaneDistance() != interpolator.getNearPlane() || event.getFarPlaneDistance() != interpolator.getFarPlane()) {
            event.setNearPlaneDistance(interpolator.getNearPlane());
            event.setFarPlaneDistance(interpolator.getFarPlane());
            event.setCanceled(true);
        }
    }

    private static void invalidateInterpolators(final ClientPlayerNetworkEvent.LoggingOut event) {
        FOG_INTERPOLATORS.values().forEach(fogInterpolator -> fogInterpolator.setInvalid(true));
    }
}
