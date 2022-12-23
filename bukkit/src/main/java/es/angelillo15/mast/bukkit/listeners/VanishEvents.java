package es.angelillo15.mast.bukkit.listeners;

import es.angelillo15.mast.bukkit.MAStaffBukkitManager;
import es.angelillo15.mast.bukkit.api.BStaffPlayer;
import es.angelillo15.mast.bukkit.api.events.vanish.PlayerVanishDisableEvent;
import es.angelillo15.mast.bukkit.api.events.vanish.PlayerVanishEnableEvent;
import es.angelillo15.mast.bukkit.utils.Messages;
import es.angelillo15.mast.bukkit.utils.StaffUtils;
import es.angelillo15.mast.bukkit.utils.VanishUtils;
import es.angelillo15.mast.database.SQLQueries;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class VanishEvents implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerVanishEnable(PlayerVanishEnableEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        if(SQLQueries.existsData(MAStaffBukkitManager.getInstance().getPluginConnection().getConnection(),
                player.getUniqueId())) {
            SQLQueries.updateData(MAStaffBukkitManager.getInstance().getPluginConnection().getConnection()
                    ,player.getUniqueId(), 1, 1);
        }
        VanishUtils.enableVanish(player);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerVanishDisable(PlayerVanishDisableEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        VanishUtils.disableVanish(player);
        SQLQueries.updateData(MAStaffBukkitManager.getInstance().getPluginConnection().getConnection()
                ,player.getUniqueId(), 0, 0);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        MAStaffBukkitManager plugin = MAStaffBukkitManager.getInstance();
        Player player = event.getPlayer();
        if(player.hasPermission("mast.staff.use")){
            if(SQLQueries.getVanished(MAStaffBukkitManager.getInstance().getPluginConnection().getConnection(), player.getUniqueId()) == 1){
                VanishUtils.enableVanish(player);
                event.setJoinMessage("");
                StaffUtils.asyncStaffBroadcastMessage(Messages.GET_STAFF_VANISH_JOIN_MESSAGE().replace("{player}", player.getName()));
            }

            if(SQLQueries.existsData(plugin.getPluginConnection().getConnection(), player.getUniqueId())){
                if(SQLQueries.getState(plugin.getPluginConnection().getConnection(), player.getUniqueId()) == 1){
                    MAStaffBukkitManager.getInstance().addStaffPlayer(new BStaffPlayer(player));
                }
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
    public void onWorldChange(PlayerChangedWorldEvent event){
        if(MAStaffBukkitManager.getInstance().containsStaffPlayer(event.getPlayer().getUniqueId())){
            event.getPlayer().setGameMode(GameMode.CREATIVE);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if(player.hasPermission("mast.staff.use")){
            if(SQLQueries.getVanished(MAStaffBukkitManager.getInstance().getPluginConnection().getConnection(), player.getUniqueId()) == 1){
                if(MAStaffBukkitManager.getInstance().containsStaffPlayer(player.getUniqueId())){
                    MAStaffBukkitManager.getInstance().removeStaffPlayer(MAStaffBukkitManager.getInstance().getSStaffPlayer(player.getUniqueId()));
                }
                StaffUtils.sendStaffData(event.getPlayer(), false);
                event.setQuitMessage("");
                StaffUtils.asyncStaffBroadcastMessage(Messages.GET_STAFF_VANISH_LEAVE_MESSAGE().replace("{player}", player.getName()));
            }
        }
    }

}
