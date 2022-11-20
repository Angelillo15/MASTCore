package es.angelillo15.mast.bukkit.vanish;

import es.angelillo15.mast.bukkit.utils.Messages;
import es.angelillo15.mast.bukkit.utils.VanishUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class VanishManager {
    private static final ArrayList<UUID> vanishedPlayers = new ArrayList<>();

    public static void setVanished(UUID playerUUID, boolean vanished) {
        if (vanished) {
            vanishedPlayers.add(playerUUID);
        } else {
            vanishedPlayers.remove(playerUUID);
        }
    }
    public static boolean isVanished(UUID playerUUID) {
        return vanishedPlayers.contains(playerUUID);
    }

    public static void removePlayer(UUID playerUUID) {
        vanishedPlayers.remove(playerUUID);
    }

    public static void clear() {
        vanishedPlayers.clear();
    }

    public static void toggleVanish(Player player) {
        Bukkit.getConsoleSender().sendMessage(isVanished(player.getUniqueId()) ? "true" : "false");
        if (!isVanished(player.getUniqueId())) {
            setVanished(player.getUniqueId(), true);
            Bukkit.getOnlinePlayers().forEach(people -> {
                if (!people.hasPermission("mast.vanish.see")) {
                    people.showPlayer(player);
                }
            });
            player.sendMessage(Messages.GET_VANISH_DISABLE_MESSAGE());
        } else {
            setVanished(player.getUniqueId(), false);
            Bukkit.getOnlinePlayers().forEach(people -> {
                if (people.hasPermission("mast.vanish.see")) {
                    people.showPlayer(player);
                } else {
                    people.hidePlayer(player);
                }
            });
            player.sendMessage(Messages.GET_VANISH_ENABLE_MESSAGE());
        }
    }
}
