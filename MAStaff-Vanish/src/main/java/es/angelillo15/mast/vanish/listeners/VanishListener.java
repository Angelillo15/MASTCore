package es.angelillo15.mast.vanish.listeners;

import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.Permissions;
import es.angelillo15.mast.api.managers.StaffPlayersManagers;
import es.angelillo15.mast.api.vanish.VanishDataManager;
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

        boolean hasVanishPermission = player.hasPermission(Permissions.STAFF_VANISH_SEE.getPermission());

        VanishDataManager.getVanishedPlayers().forEach(vanishedPlayer -> {
            if (!hasVanishPermission) {
                player.hidePlayer(vanishedPlayer.getPlayer());
                vanishedPlayer.getVanishPlayer().addVanishedFor(player);
                return;
            }

            vanishedPlayer.getVanishPlayer().sendPlayerInfoChangeGameModePacket(true);
        });
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerLeave(PlayerQuitEvent event) {
        VanishDataManager.getVanishedPlayers().forEach(vanishedPlayer -> {
            vanishedPlayer.getVanishPlayer().removeVanishedFor(event.getPlayer());
        });
    }

    @EventHandler(ignoreCancelled = true)
    @SuppressWarnings("deprecation")
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (!StaffPlayersManagers.isStaffPlayer(player)) {
            return;
        }

        IStaffPlayer staffPlayer = StaffPlayersManagers.getStaffPlayer(player);

        if (!staffPlayer.getVanishPlayer().isVanished()) {
            return;
        }

        VanishDataManager.removeVanishedPlayer(staffPlayer);

        staffPlayer.getVanishPlayer().disableVanish();

    }
}
