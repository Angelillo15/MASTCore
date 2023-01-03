package es.angelillo15.mast.api.gui;

import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.api.material.XMaterial;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class GuiUtils {
    /**
     * @param player The player to get the head of
     * @return The head of the player
     */
    @SuppressWarnings("deprecation")
    public static ItemStack getPlayerHead(Player player) {
        ItemStack item = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial(), 1, (short) 3);

        SkullMeta meta = (SkullMeta) item.getItemMeta();

        meta.setDisplayName(player.getDisplayName());

        meta.setOwner(player.getName());

        item.setItemMeta(meta);

        return item;
    }

    /**
     * @param player The player to get the head of
     * @param lore The lore of the head
     * @return The head of the player
     */
    public static ItemStack getPlayerHead(Player player, List<String> lore){
        ItemStack item = getPlayerHead(player);

        List<String> parsedLore = new ArrayList<>();

        lore.forEach(line -> parsedLore.add(TextUtils.processPlaceholders(player, line)));

        ItemMeta meta = item.getItemMeta();

        meta.setLore(parsedLore);

        item.setItemMeta(meta);

        return item;
    }
}
