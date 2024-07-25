package de.oliver.fancyholograms.commands.hologram;

import de.oliver.fancyholograms.FancyHolograms;
import de.oliver.fancyholograms.HologramManagerImpl;
import de.oliver.fancyholograms.api.data.TextHologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import de.oliver.fancyholograms.api.hologram.HologramType;
import de.oliver.fancyholograms.commands.Subcommand;
import de.oliver.fancylib.MessageHelper;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DebugGetOrCreateHologram implements Subcommand {
    @Override
    public List<String> tabcompletion(@NotNull CommandSender player, @Nullable Hologram hologram, @NotNull String[] args) {
        return null;
    }

    @Override
    public boolean run(@NotNull CommandSender player, @Nullable Hologram hologram, @NotNull String[] args) {
        if (!(player instanceof Player finalPlayer)) return false;

        HologramManagerImpl manager = FancyHolograms.get().getHologramsManager();

        Hologram created = manager.<TextHologramData>getOrCreateHologram("goc-test", HologramType.TEXT,
            (b) -> b
                .setText(List.of("Test"))
                .setLocation(finalPlayer.getLocation().clone())
        );

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            created.updateShownStateFor(onlinePlayer);
        }

        manager.addHologram(created);

        MessageHelper.success(player, "Hologram acquired or created!");

        return true;
    }
}
