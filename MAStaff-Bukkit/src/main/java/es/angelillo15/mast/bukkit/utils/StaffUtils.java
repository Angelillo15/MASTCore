package es.angelillo15.mast.bukkit.utils;

import es.angelillo15.mast.api.Permissions;
import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.api.config.bukkit.Messages;
import es.angelillo15.mast.bukkit.MAStaff;
import es.angelillo15.mast.bukkit.utils.scheduler.Scheduler;
import io.papermc.lib.PaperLib;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class StaffUtils {
  private static final Random random = new Random();
  public static void playerRandomTeleport(Player player) {
    ArrayList<Player> players = new ArrayList<>();

    Bukkit.getOnlinePlayers().forEach(p -> {
      if (!(p.hasPermission(Permissions.STAFF.getPermission()))) {
        players.add(p);
      }
    });

    if (players.isEmpty()) {
      TextUtils.sendMessage(player, Messages.GET_NO_PLAYER_ONLINE_MESSAGE());
      return;
    }

    PaperLib.teleportAsync(player, players.get(random.nextInt(players.size())).getLocation());
  }

  public static boolean passThrough(Player player, Location clickedLocation) {
    Vector direction = player.getLocation().getDirection().normalize();

    int distance = 0;
    do {
      clickedLocation.add(direction);
      distance++;
    } while (clickedLocation.getBlock().getType().equals(Material.AIR) && distance < 6);

    PaperLib.teleportAsync(player, clickedLocation);
    return true;
  }

  public static void asyncStaffBroadcastMessage(String message) {
    Scheduler.executeAsync(() -> {
      Bukkit.getOnlinePlayers().forEach(p -> {
        if (p.hasPermission("mast.staff")) {
          TextUtils.sendMessage(p, message);
        }
      });
      MAStaff.getPlugin().getPLogger().info(message);
    });
  }

  public static void asyncBroadcastMessage(String message) {
    if (Objects.equals(message, "")) return;

    Scheduler.executeAsync(() -> {
      Bukkit.getOnlinePlayers().forEach(p -> {
        TextUtils.sendMessage(p, message);
      });
      MAStaff.getPlugin().getPLogger().info(message);
    });
  }

  public static void asyncStaffChatMessage(String message) {
    Scheduler.executeAsync(() -> {
      Bukkit.getOnlinePlayers().forEach(p -> {
        if (p.hasPermission("mast.staffchat")) {
          TextUtils.sendMessage(p, message);
        }
      });
      MAStaff.getPlugin().getPLogger().info(message);
    });
  }
}

