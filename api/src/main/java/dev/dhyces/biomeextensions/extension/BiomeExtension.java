package dev.dhyces.biomeextensions.extension;

import com.mojang.serialization.Codec;
import dev.dhyces.biomeextensions.ApiAccess;

import java.util.List;

public interface BiomeExtension {
    Codec<BiomeExtension> DISPATCH_CODEC = ApiAccess.getInstance().getTypeCodec().dispatch(BiomeExtension::getType, BiomeExtensionType::getCodec);
    Codec<List<BiomeExtension>> LIST_CODEC = DISPATCH_CODEC.listOf().fieldOf("effects").codec();

    BiomeExtensionType<?> getType();
}
