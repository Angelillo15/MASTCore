package es.angelillo15.mast.bukkit.listeners;

import es.angelillo15.mast.bukkit.MASTBukkitManager;
import es.angelillo15.mast.bukkit.api.BStaffPlayer;
import es.angelillo15.mast.database.SQLQueries;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.Connection;

public class OnStaffJoin implements Listener {
    private Connection connection = MASTBukkitManager.getInstance().getPluginConnection().getConnection();
    @EventHandler
    public void onStaffJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if(SQLQueries.existsData(connection, player.getUniqueId())){
            if(SQLQueries.getState(connection, player.getUniqueId()) == 1){
                MASTBukkitManager.getInstance().addStaffPlayer(new BStaffPlayer(player));
            }
        }


    }
}

