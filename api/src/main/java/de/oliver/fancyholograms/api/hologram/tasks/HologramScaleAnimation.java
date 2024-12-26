package de.oliver.fancyholograms.api.hologram.tasks;

import de.oliver.fancyholograms.api.FancyHologramsPlugin;
import de.oliver.fancyholograms.api.HologramManager;
import de.oliver.fancyholograms.api.data.DisplayHologramData;
import de.oliver.fancyholograms.api.data.property.Visibility;
import de.oliver.fancyholograms.api.hologram.Hologram;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import org.joml.Vector3f;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class HologramScaleAnimation {
    private final @NotNull Set<Player> displayingFor;
    private final @NotNull Hologram hologram;
    private final @NotNull Vector3f startingScale;
    private int delay;
    private int duration;

    public HologramScaleAnimation(
        @NotNull Set<Player> displayingFor,
        @NotNull Hologram hologram,
        @NotNull Vector3f startingScale
    ) {
        this.displayingFor = displayingFor;
        this.hologram = hologram;
        this.startingScale = startingScale;
    }

    public void run() {
        HologramAnimationManager.getInstance().register(this);

        HologramManager manager = FancyHologramsPlugin.get().getHologramManager();
        this.displayingFor.forEach(hologram::forceHideHologram);

        DisplayHologramData baseData = hologram.getHologramData(DisplayHologramData.class);
        DisplayHologramData cloneData = baseData.copy(UUID.randomUUID().toString())
            .setInterpolationDuration(duration)
            .setScale(this.startingScale);

        cloneData.setPersistent(false).setVisibility(Visibility.MANUAL);

        Hologram clone = cloneData.create(manager);

        for (Player viewer : this.displayingFor) {
            clone.forceShowHologram(viewer);
        }

        FancyHologramsPlugin.get()
            .getHologramThread()
            .schedule(() -> {
                cloneData.setScale(baseData.getScale());
                clone.forceUpdate();
                clone.refreshHologram(this.displayingFor);
            }, 50L, TimeUnit.MILLISECONDS);

        long durationMillis = this.duration * 50L;
        FancyHologramsPlugin.get()
            .getHologramThread()
            .schedule(() -> {
                this.displayingFor.forEach(clone::forceHideHologram);

                this.displayingFor.forEach(hologram::forceShowHologram);

                HologramAnimationManager.getInstance().remove(this);
            }, durationMillis, TimeUnit.MILLISECONDS);
    }

    public @NotNull HologramScaleAnimation delay(@Range(from = 0, to = 60) int delay) {
        // TODO not supported yet
        this.delay = delay;
        return this;
    }

    public @NotNull HologramScaleAnimation duration(@Range(from = 0, to = 60) int duration) {
        this.duration = duration;
        return this;
    }

    public @NotNull Set<Player> getDisplayingFor() {
        return displayingFor;
    }

    public @NotNull Hologram getHologram() {
        return hologram;
    }
}
