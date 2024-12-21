package de.oliver.fancyholograms.api.hologram;

import de.oliver.fancyholograms.api.data.BlockHologramData;
import de.oliver.fancyholograms.api.data.HologramData;
import de.oliver.fancyholograms.api.data.ItemHologramData;
import de.oliver.fancyholograms.api.data.TextHologramData;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public class HologramType<T extends HologramData> {
    public static final HologramType<TextHologramData> TEXT = new HologramType<>(
        "TEXT",
        Arrays.asList(
            "background",
            "textshadow",
            "textalignment",
            "seethrough",
            "setline",
            "removeline",
            "addline",
            "insertbefore",
            "insertafter",
            "updatetextinterval"
        ),
        TextHologramData::new
    );
    public static final HologramType<ItemHologramData> ITEM = new HologramType<>("ITEM", List.of("item"), ItemHologramData::new);
    public static final HologramType<BlockHologramData> BLOCK = new HologramType<>("BLOCK", List.of("block"), BlockHologramData::new);

    private final String name;
    private final List<String> commands;
    private final BiFunction<String, Location, T> supplier;

    HologramType(String name, List<String> commands, BiFunction<String, Location, T> supplier) {
        this.name = name;
        this.commands = commands;
        this.supplier = supplier;
    }

    public static List<HologramType<?>> values() {
        return List.of(TEXT, ITEM, BLOCK);
    }

    public static HologramType<?> getByName(String name) {
        for (HologramType<?> type : values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }

        return null;
    }

    public String name() {
        return this.name;
    }

    public List<String> getCommands() {
        return commands;
    }

    public @NotNull T create(@NotNull String id, @NotNull Location location) {
        return this.supplier.apply(id, location);
    }

}
