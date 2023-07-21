package es.angelillo15.mast.vanish.listeners;

import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.managers.StaffPlayersManagers;
import es.angelillo15.mast.api.vanish.VanishDataManager;
import es.angelillo15.mast.vanish.MAStaffVanish;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@SuppressWarnings("deprecation")
public class VanishListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Bukkit.getOnlinePlayers().forEach(p -> {
            show(p, player);
            show(player, p);
        });

        if (player.hasPermission("mast.vanish.see")) return;

        VanishDataManager.getVanishedPlayers().forEach(vanishedPlayer -> {
            hide(vanishedPlayer.getPlayer(), player);
        });
    }

    @EventHandler(ignoreCancelled = true)
    public void onQuit(PlayerQuitEvent event) {
        if (!VanishDataManager.isVanished(event.getPlayer().getName())) return;
        IStaffPlayer staffPlayer = StaffPlayersManagers.getStaffPlayer(event.getPlayer());

        if (staffPlayer == null) return;

        Bukkit.getOnlinePlayers().forEach(player -> {
            show(staffPlayer.getPlayer(), player);
        });

        VanishDataManager.removeVanishedPlayer(staffPlayer);
    }

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
}
