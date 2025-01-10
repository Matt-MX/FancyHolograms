package de.oliver.fancyholograms.api.hologram;

import de.oliver.fancyholograms.api.data.HologramData;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

public class HologramType<D extends HologramData> {
    private final @NotNull String identifier;
    private final @NotNull List<String> commands;
    private final @NotNull BiFunction<String, Location, D> supplier;
    private @Nullable Function<Object, Object> platformEntitySupplier = null;

    public HologramType(
        @NotNull String identifier,
        @NotNull List<String> commands,
        @NotNull BiFunction<String, Location, D> dataSupplier
    ) {
        this.identifier = identifier;
        this.commands = commands;
        this.supplier = dataSupplier;
    }

    public D createData(@NotNull String hologramName, @Nullable Location location) {
        location = location == null
            ? new Location(null, 0.0, 0.0, 0.0)
            : location;

        return supplier.apply(hologramName, location);
    }

    public @NotNull String getIdentifier() {
        return identifier;
    }

    public @NotNull List<String> getCommands() {
        return commands;
    }

    public @NotNull Object createPlatformObject(Object craftWorldObject) {
        Objects.requireNonNull(this.platformEntitySupplier, "There is no platformEntitySupplier supplied for this version.");
        return platformEntitySupplier.apply(craftWorldObject);
    }

    public HologramType<D> platformEntitySupplier(@NotNull Function<Object, Object> function) {
        this.platformEntitySupplier = function;
        return this;
    }

    public static @Nullable HologramType<?> getByName(String name) {
        return HologramTypes.getByName(name);
    }
}
