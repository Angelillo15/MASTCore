package es.angelillo15.mast.bukkit.listener.staffmode;

import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.Permissions;
import es.angelillo15.mast.api.database.sql.CommonQueries;
import es.angelillo15.mast.api.managers.StaffPlayersManagers;
import es.angelillo15.mast.bukkit.MAStaff;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnJoinLeave implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        if(!player.hasPermission(Permissions.STAFF.getPermission())){
            return;
        }

        MAStaff.getPlugin().getPLogger().debug("Status: " + CommonQueries.isInStaffMode(player.getUniqueId()));

        IStaffPlayer staffPlayer = StaffPlayersManagers.getStaffPlayer(player);

        if(CommonQueries.isInStaffMode(player.getUniqueId())) staffPlayer.toggleStaffMode(false);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();

        if(!player.hasPermission(Permissions.STAFF.getPermission())){
            return;
        }

        IStaffPlayer staffPlayer = StaffPlayersManagers.getStaffPlayer(player);

        if(staffPlayer.existsData()){
            MAStaff.getPlugin().getPLogger().debug("Restoring inventory for player " + player.getName());
            staffPlayer.clearInventory();
            staffPlayer.restoreInventory();
        }

        staffPlayer.changeGamemode(GameMode.SURVIVAL);

        StaffPlayersManagers.removeStaffPlayer(staffPlayer);
    }
}
