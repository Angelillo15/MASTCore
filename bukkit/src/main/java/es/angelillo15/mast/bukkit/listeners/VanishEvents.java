package es.angelillo15.mast.bukkit.listeners;

import es.angelillo15.mast.bukkit.MASTBukkitManager;
import es.angelillo15.mast.bukkit.api.events.vanish.PlayerVanishDisableEvent;
import es.angelillo15.mast.bukkit.api.events.vanish.PlayerVanishEnableEvent;
import es.angelillo15.mast.bukkit.utils.VanishUtils;
import es.angelillo15.mast.database.SQLQueries;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.nio.Buffer;

public class VanishEvents implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerVanishEnable(PlayerVanishEnableEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        if(SQLQueries.existsData(MASTBukkitManager.getInstance().getPluginConnection().getConnection(),
                player.getUniqueId())) {
            SQLQueries.updateData(MASTBukkitManager.getInstance().getPluginConnection().getConnection()
                    ,player.getUniqueId(), 1, 1);
        }
        VanishUtils.enableVanish(player);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerVanishDisable(PlayerVanishDisableEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        VanishUtils.disableVanish(player);
        SQLQueries.updateData(MASTBukkitManager.getInstance().getPluginConnection().getConnection()
                ,player.getUniqueId(), 0, 0);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        MASTBukkitManager plugin = MASTBukkitManager.getInstance();
        Player player = event.getPlayer();
        if(player.hasPermission("mast.staff.use")){
            if(SQLQueries.getVanished(MASTBukkitManager.getInstance().getPluginConnection().getConnection(), player.getUniqueId()) == 1){

                VanishUtils.enableVanish(player);
                event.setJoinMessage("");
            }
        }

        if (player.hasPermission("mast.vanish.see")) return;

        plugin.getVanishedPlayers().forEach((uuid, bStaffPlayer) -> {
            if(!(bStaffPlayer.getPlayer() == null)){
                player.hidePlayer(bStaffPlayer.getPlayer());
            }
        });

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if(player.hasPermission("mast.staff.use")){
            if(SQLQueries.getVanished(MASTBukkitManager.getInstance().getPluginConnection().getConnection(), player.getUniqueId()) == 1){
                MASTBukkitManager.getInstance().removeStaffPlayer(MASTBukkitManager.getInstance().getSStaffPlayer(player.getUniqueId()));
                event.setQuitMessage("");
            }
        }
    }

}
