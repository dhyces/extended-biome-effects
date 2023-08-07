package dev.dhyces.biomeextensions;

import dev.dhyces.biomeextensions.impl.ApiContainer;
import dev.dhyces.biomeextensions.extension.BiomeExtensionRegistry;
import dev.dhyces.biomeextensions.registry.ExtendedBiomeRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;

@Mod(BiomeExtensionsMod.MODID)
public class BiomeExtensionsMod {
    public static final String MODID = "biomeextensions";
    public static ResourceLocation id(String id) {
        return new ResourceLocation(MODID, id);
    }

    public static final ApiContainer API_CONTAINER = new ApiContainer();

    public BiomeExtensionsMod() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        BiomeExtensionRegistry.init();

        ExtendedBiomeRegistry.init(modBus);

        if (FMLLoader.getDist().isClient()) {
            BiomeExtensionsModClient.init(modBus, forgeBus);
        }
    }
}
