package es.angelillo15.mast.bukkit.listener;

import es.angelillo15.mast.api.exceptions.AlreadyInTheMapException;
import es.angelillo15.mast.api.managers.StaffPlayersManagers;
import es.angelillo15.mast.bukkit.MAStaff;
import es.angelillo15.mast.bukkit.StaffPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();

        if(!(player.hasPermission("mast.staff"))){
            return;
        }

        try {
            StaffPlayersManagers.addStaffPlayer(new StaffPlayer(player));
            MAStaff.getPlugin().getPLogger().debug("Added " + player.getName() + " to the map");
        } catch (AlreadyInTheMapException ex) {
            throw new RuntimeException(ex);
        }
    }
}
