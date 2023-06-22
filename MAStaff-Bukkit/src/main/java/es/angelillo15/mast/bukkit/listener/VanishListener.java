package es.angelillo15.mast.bukkit.listener;

import es.angelillo15.mast.api.Permissions;
import es.angelillo15.mast.api.managers.StaffPlayersManagers;
import es.angelillo15.mast.api.managers.VanishedPlayers;
import es.angelillo15.mast.bukkit.MAStaff;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class VanishListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    @SuppressWarnings("deprecation")
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if(player.hasPermission(Permissions.STAFF_VANISH_SEE.getPermission())) {
            return;
        }

        VanishedPlayers.getVanishedPlayers().forEach(vanishedPlayer -> {
            player.hidePlayer(vanishedPlayer);
        });
    }

    @EventHandler(ignoreCancelled = true)
    @SuppressWarnings("deprecation")
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (StaffPlayersManagers.isStaffPlayer(player)) {
            return;
        }

        VanishedPlayers.removePlayer(player);
    }

}
