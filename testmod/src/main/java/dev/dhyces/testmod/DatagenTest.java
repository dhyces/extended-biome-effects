package dev.dhyces.testmod;

import dev.dhyces.biomeextensions.ApiAccess;
import dev.dhyces.biomeextensions.RegistryHelper;
import dev.dhyces.biomeextensions.extension.BiomeExtension;
import dev.dhyces.biomeextensions.extension.effects.BaseFogExtension;
import dev.dhyces.biomeextensions.extension.effects.TerrainFogExtension;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = "testmod", bus = Mod.EventBusSubscriber.Bus.MOD)
public class DatagenTest {

    @SubscribeEvent
    static void setupDatagen(final GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        RegistrySetBuilder builder = new RegistrySetBuilder()
                .add(ApiAccess.EXTENSION_REGISTRY_KEY, pContext -> {
                    pContext.register(RegistryHelper.registryKey(new ResourceLocation("nether_wastes")), BiomeExtension.single(new TerrainFogExtension(0, 999)));
                    pContext.register(RegistryHelper.registryKey(new ResourceLocation("plains")), BiomeExtension.single(new TerrainFogExtension(0, 60)));
                });

        generator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(packOutput, lookupProvider, builder, Set.of("minecraft")));
    }
}
