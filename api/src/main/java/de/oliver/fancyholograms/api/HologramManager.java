package de.oliver.fancyholograms.api;

import de.oliver.fancyholograms.api.data.HologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;

public interface HologramManager {

    Optional<Hologram> getHologram(String name);

    default Hologram getOrCreateHologram(String name, Supplier<HologramData> orElse) {
        return getHologram(name).orElseGet(() -> create(orElse.get()));
    }

    Collection<Hologram> getPersistentHolograms();

    Collection<Hologram> getHolograms();

    void addHologram(Hologram hologram);

    void removeHologram(Hologram hologram);

    Hologram create(HologramData hologramData);

    void loadHolograms();

    void saveHolograms();

    void reloadHolograms();

}
