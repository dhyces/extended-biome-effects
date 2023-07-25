package dev.dhyces.biomeextensions.extension;

import com.mojang.serialization.Codec;
import dev.dhyces.biomeextensions.ApiAccess;

import java.util.List;

public interface ExtensionElement {
    Codec<ExtensionElement> DISPATCH_CODEC = ApiAccess.getInstance().getTypeCodec().dispatch(ExtensionElement::getType, ExtensionElementType::getCodec);
    Codec<List<ExtensionElement>> LIST_CODEC = DISPATCH_CODEC.listOf().fieldOf("effects").codec();

    ExtensionElementType<?> getType();
}
