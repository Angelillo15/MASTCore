package es.angelillo15.mast.bukkit.listeners;

import es.angelillo15.mast.bukkit.MAStaffBukkitManager;
import es.angelillo15.mast.bukkit.api.BStaffPlayer;
import es.angelillo15.mast.bukkit.utils.Inventory;
import es.angelillo15.mast.bukkit.utils.StaffUtils;
import es.angelillo15.mast.database.SQLQueries;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.Connection;

public class OnStaffJoin implements Listener {
    private Connection connection = MAStaffBukkitManager.getInstance().getPluginConnection().getConnection();
    @EventHandler
    public void onStaffJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if(SQLQueries.existsData(connection, player.getUniqueId())){
            if(SQLQueries.getState(connection, player.getUniqueId()) == 1){
                StaffUtils.enableStaff(player);
            }
        }
    }

    @EventHandler
    public void onStaffLeave(PlayerQuitEvent e){
        Player player = e.getPlayer();
        if(player.hasPermission("mast.staff.use")){
            if(MAStaffBukkitManager.getInstance().containsStaffPlayer(player.getUniqueId())){
                Inventory.restoreInventory(player);
                MAStaffBukkitManager.getInstance().removeStaffPlayer(
                        MAStaffBukkitManager.getInstance().getSStaffPlayer(player.getUniqueId())
                );
                player.setGameMode(GameMode.SURVIVAL);

            }
        }
    }
}

