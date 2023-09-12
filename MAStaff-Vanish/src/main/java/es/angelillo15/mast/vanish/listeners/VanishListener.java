package es.angelillo15.mast.vanish.listeners;

import com.google.inject.Inject;
import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.managers.StaffManager;
import es.angelillo15.mast.vanish.MAStaffVanish;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class VanishListener implements Listener {
  @Inject
  private StaffManager staffManager;

  public static void hide(Player staff, Player player) {
    if (MAStaffInstance.version() > 12) {
      player.hidePlayer(MAStaffVanish.getInstance().getPluginInstance(), staff);
    } else {
      player.hidePlayer(staff);
    }
  }

  public static void show(Player staff, Player player) {
    if (MAStaffInstance.version() > 12) {
      player.showPlayer(MAStaffVanish.getInstance().getPluginInstance(), staff);
    } else {
      player.showPlayer(staff);
    }
  }

  @EventHandler(ignoreCancelled = true)
  public void onJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();

    Bukkit.getOnlinePlayers().forEach(p -> {
      show(p, player);
      show(player, p);
    });

    if (player.hasPermission("mast.vanish.see")) return;

    staffManager.getStaffPlayers().forEach((name, staffPlayer) -> {
      if (!staffPlayer.isVanished()) return;
      hide(staffPlayer.getPlayer(), player);
    });
  }

  @EventHandler(ignoreCancelled = true)
  public void onQuit(PlayerQuitEvent event) {
    if (!staffManager.isStaffPlayer(event.getPlayer().getName())) return;

    Bukkit.getOnlinePlayers().forEach(player -> {
      show(event.getPlayer(), player);
    });
  }
}
