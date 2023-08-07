package dev.dhyces.biomeextensions;

import dev.dhyces.biomeextensions.impl.ApiAccessImpl;
import dev.dhyces.biomeextensions.extension.ExtensionElementType;
import dev.dhyces.biomeextensions.extension.effects.BaseFogExtension;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogRenderer.FogMode;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.util.CubicSampler;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public class BiomeExtensionsModClient {

    private static final Map<FogMode, ExtensionElementType<? extends BaseFogExtension>> FOG_TYPES = Util.make(new EnumMap<>(FogMode.class), map -> {
        map.put(FogMode.FOG_SKY, ExtensionElementType.SKY_FOG);
        map.put(FogMode.FOG_TERRAIN, ExtensionElementType.TERRAIN_FOG);
    });

    static void init(IEventBus modBus, IEventBus forgeBus) {
        forgeBus.addListener(EventPriority.HIGHEST, BiomeExtensionsModClient::renderBiomeFog);
    }

    private static Minecraft client() {
        return Minecraft.getInstance();
    }

    private static void renderBiomeFog(final ViewportEvent.RenderFog event) {
        if (client().level == null || client().player == null || event.getType() != FogType.NONE) {
            return;
        }

        RegistryAccess registryAccess = client().level.registryAccess();

        Vec3 fog = CubicSampler.gaussianSampleVec3(event.getCamera().getPosition().subtract(2, 2, 2).scale(0.25), (pX, pY, pZ) -> {
            Holder<Biome> biome = client().level.getBiomeManager().getNoiseBiomeAtQuart(pX, pY, pZ);
            ExtensionElementType<? extends BaseFogExtension> type = FOG_TYPES.get(event.getMode());
            Optional<? extends BaseFogExtension> extension = ApiAccessImpl.INSTANCE.getExtensionsOfType(registryAccess, biome, type);
            return extension.map(baseFogExtension -> new Vec3(baseFogExtension.getNearPlane(), baseFogExtension.getFarPlane(), 0))
                    .orElse(new Vec3(event.getNearPlaneDistance(), event.getFarPlaneDistance(), 0));
        });

        float targetNearPlane = (float) fog.x;
        float targetFarPlane = (float) fog.y;

        if (event.getNearPlaneDistance() != targetNearPlane || event.getFarPlaneDistance() != targetFarPlane) {
            event.setNearPlaneDistance(targetNearPlane);
            event.setFarPlaneDistance(targetFarPlane);
            event.setCanceled(true);
        }
    }
}
