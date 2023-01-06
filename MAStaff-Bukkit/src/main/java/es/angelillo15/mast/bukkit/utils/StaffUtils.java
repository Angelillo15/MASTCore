package es.angelillo15.mast.bukkit.utils;

import es.angelillo15.mast.api.Permissions;
import es.angelillo15.mast.bukkit.MAStaff;
import es.angelillo15.mast.bukkit.config.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Random;

public class StaffUtils {
    public static void playerRandomTeleport(Player player) {
        ArrayList<Player> players = new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach(p -> {
            if (!(p.hasPermission(Permissions.STAFF.getPermission()) || !(p.equals(player)))) {
                players.add(p);
            }
        });
        if (players.isEmpty()) {
            player.sendMessage(Messages.GET_NO_PLAYER_ONLINE_MESSAGE());
            return;
        }
        final Random random = new Random();
        player.teleport(players.get(random.nextInt(players.size())).getLocation());
    }

    public static boolean passThrough(Player player, Location clickedLocation) {
        Vector direction = player.getLocation().getDirection().normalize();
        int distance = 0;
        do {
            clickedLocation.add(direction);
            distance++;
        } while (clickedLocation.getBlock().getType().equals(Material.AIR) && distance < 6);
        player.teleport(clickedLocation);
        return true;
    }

    public static void asyncStaffBroadcastMessage(String message) {
        Bukkit.getScheduler().runTaskAsynchronously(MAStaff.getPlugin(), () -> {
            Bukkit.getOnlinePlayers().forEach(p -> {
                if (p.hasPermission("mast.staff")) {
                    p.sendMessage(message);
                }
            });
            MAStaff.getPlugin().getPLogger().info(message);
        });
    }

    public static void asyncStaffChatMessage(String message) {
        Bukkit.getScheduler().runTaskAsynchronously(MAStaff.getPlugin(), () -> {
            Bukkit.getOnlinePlayers().forEach(p -> {
                if (p.hasPermission("mast.staffchat")) {
                    p.sendMessage(message);
                }
            });
            MAStaff.getPlugin().getPLogger().info(message);
        });
    }
}

