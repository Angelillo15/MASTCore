package es.angelillo15.mast.bukkit.utils;

import es.angelillo15.mast.bukkit.MASTBukkitManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Inventory {
    public static void saveInventory(Player player) {

        final File f = new File(MASTBukkitManager.getInstance().getDataFolder().getAbsolutePath() + "/data/inventory", player.getUniqueId() + "_inventory.yml");
        final FileConfiguration c = YamlConfiguration.loadConfiguration(f);
        c.set("inventory.armor", player.getInventory().getArmorContents());
        c.set("inventory.content", player.getInventory().getContents());
        try {
            c.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }

        player.getInventory().clear();

    }

    @SuppressWarnings("unchecked")
    public static void restoreInventory(Player player) {
        final File f = new File(MASTBukkitManager.getInstance().getDataFolder().getAbsolutePath() + "/data/inventory", player.getUniqueId() + "_inventory.yml");
        FileConfiguration c = YamlConfiguration.loadConfiguration(f);
        ItemStack[] content = ((List<ItemStack>) c.get("inventory.armor")).toArray(new ItemStack[0]);
        player.getInventory().setArmorContents(content);
        content = ((List<ItemStack>) c.get("inventory.content")).toArray(new ItemStack[0]);
        player.getInventory().setContents(content);

    }
}
