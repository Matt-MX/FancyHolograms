package de.oliver.fancyholograms.api.hologram.tasks;

import de.oliver.fancyholograms.api.hologram.Hologram;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class HologramAnimationManager {
    private static final HologramAnimationManager instance = new HologramAnimationManager();

    private final Set<HologramScaleAnimation> ongoing = Collections.synchronizedSet(new HashSet<>());

    public void register(@NotNull HologramScaleAnimation animation) {
        this.ongoing.add(animation);
    }

    public void remove(@NotNull HologramScaleAnimation animation) {
        this.ongoing.remove(animation);
    }

    public boolean isCurrentlyAnimating(@NotNull Player viewer, @NotNull Hologram hologram) {
        synchronized (ongoing) {
            Iterator<HologramScaleAnimation> it = ongoing.iterator();
            while (it.hasNext()) {
                HologramScaleAnimation current = it.next();

                if (current.getHologram() == hologram && current.getDisplayingFor().contains(viewer)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static @NotNull HologramAnimationManager getInstance() {
        return instance;
    }
}
