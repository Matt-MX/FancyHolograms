package de.oliver.fancyholograms.api.hologram;

import de.oliver.fancyholograms.api.data.BlockHologramData;
import de.oliver.fancyholograms.api.data.ItemHologramData;
import de.oliver.fancyholograms.api.data.TextHologramData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

public class HologramTypes {
    private static final HashMap<String, HologramType<?>> registry = new HashMap<>();

    public static final String TEXT_IDENTIFIER = "text";
    public static final HologramType<TextHologramData> TEXT = new HologramType<>(
        TEXT_IDENTIFIER,
        List.of("background", "textshadow", "textalignment", "seethrough", "setline", "removeline", "addline", "insertbefore", "insertafter", "updatetextinterval"),
        TextHologramData::new
    );
    public static final String ITEM_IDENTIFIER = "item";
    public static final HologramType<ItemHologramData> ITEM = new HologramType<>(ITEM_IDENTIFIER, List.of("item"), ItemHologramData::new);
    public static final String BLOCK_IDENTIFIER = "block";
    public static final HologramType<BlockHologramData> BLOCK = new HologramType<>(BLOCK_IDENTIFIER, List.of("block"), BlockHologramData::new);

    public static @Nullable HologramType<?> getByName(@NotNull String name) {
        return registry.get(name);
    }

    public static void registerBuiltInTypes() {
        registry.putIfAbsent(TEXT.getIdentifier(), TEXT);
        registry.putIfAbsent(ITEM.getIdentifier(), ITEM);
        registry.putIfAbsent(BLOCK.getIdentifier(), BLOCK);
    }
}
