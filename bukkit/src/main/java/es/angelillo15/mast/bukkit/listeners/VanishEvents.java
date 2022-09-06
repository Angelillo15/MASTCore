package es.angelillo15.mast.bukkit.listeners;

import es.angelillo15.mast.bukkit.MASTBukkitManager;
import es.angelillo15.mast.bukkit.api.BStaffPlayer;
import es.angelillo15.mast.bukkit.api.events.vanish.PlayerVanishDisableEvent;
import es.angelillo15.mast.bukkit.api.events.vanish.PlayerVanishEnableEvent;
import es.angelillo15.mast.bukkit.utils.VanishUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class VanishEvents implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerVanishEnable(PlayerVanishEnableEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        VanishUtils.enableVanish(player);
        player.sendMessage("Vanish activado");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerVanishDisable(PlayerVanishDisableEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        VanishUtils.disableVanish(player);
        player.sendMessage("vanish deactivado");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        MASTBukkitManager plugin = MASTBukkitManager.getInstance();
        Player player = event.getPlayer();

        if (player.hasPermission("mast.vanish.see")) return;

        plugin.getVanishedPlayers().forEach((uuid, bStaffPlayer) -> {
            player.hidePlayer(bStaffPlayer.getPlayer());
        });
    }
}
