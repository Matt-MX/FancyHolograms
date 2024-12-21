package de.oliver.fancyholograms;

import de.oliver.fancyholograms.api.HologramManager;
import de.oliver.fancyholograms.api.data.ItemHologramData;
import de.oliver.fancyholograms.api.data.TextHologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import de.oliver.fancyholograms.api.hologram.HologramType;
import de.oliver.fancylib.MessageHelper;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Display;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.List;

public class Temp {

    public void test(@NotNull Player player) {
        HologramManager manager = FancyHolograms.get().getHologramsManager();

        Hologram hologram = Hologram.builder(HologramType.TEXT, "test2_new_api")
            .setText(List.of("Test"))
            .setBackground(Color.SILVER)
            .setLocation(player.getLocation())
            .getOrCreate(manager);

        // Functional approach
        hologram.consumeHologramData(TextHologramData.class, (textData) -> {
            textData.setText(List.of("New text!"))
                .setBackground(Color.AQUA.setAlpha(40))
                .setBillboard(Display.Billboard.VERTICAL)
                .setScale(new Vector3f(2f));
        });

        // Will not run since hologram is a text hologram
        hologram.consumeHologramData(ItemHologramData.class, (textData) -> {
            textData.setItemStack(new ItemStack(Material.TOTEM_OF_UNDYING));
        });

        // Procedural approach
        TextHologramData textData = hologram.getHologramData(TextHologramData.class);

        ItemHologramData itemData = hologram.getHologramDataNullable(ItemHologramData.class); // Will return null
        try {
            ItemHologramData itemDataError = hologram.getHologramData(ItemHologramData.class); // Will throw class cast exception
        } catch (ClassCastException ignored) {
            MessageHelper.success(player, "Threw ClassCastException as expected.");
        }
    }

}
