package es.angelillo15.mast.bukkit.listener.staffmode;

import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.Permissions;
import es.angelillo15.mast.api.database.sql.CommonQueries;
import es.angelillo15.mast.api.managers.StaffPlayersManagers;
import es.angelillo15.mast.bukkit.MAStaff;
import es.angelillo15.mast.bukkit.StaffPlayer;
import es.angelillo15.mast.bukkit.config.Config;
import es.angelillo15.mast.bukkit.config.Messages;
import es.angelillo15.mast.bukkit.utils.StaffUtils;
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

        // MAStaff.getPlugin().getServer().getPluginManager().callEvent(new StaffJoinEvent(player));

        if(CommonQueries.isInStaffMode(player.getUniqueId())){
            MAStaff.getPlugin().getPLogger().debug("Player " + player.getName() + " previous state: " + staffPlayer.wasInStaffMode());
            if(!staffPlayer.wasInStaffMode()) staffPlayer.toggleStaffMode(true);
            else staffPlayer.toggleStaffMode(false);
        }

        if(staffPlayer.isStaffMode()){
            StaffUtils.asyncStaffChatMessage(Messages.GET_STAFF_VANISH_JOIN_MESSAGE()
                    .replace("{player}", player.getName()));
            event.setJoinMessage("");
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();

        if(!player.hasPermission(Permissions.STAFF.getPermission())){
            return;
        }

        //MAStaff.getPlugin().getServer().getPluginManager().callEvent(new StaffLeaveEvent(player));

        StaffPlayer staffPlayer = (StaffPlayer) StaffPlayersManagers.getStaffPlayer(player);

        if(staffPlayer.isStaffMode()){
            StaffUtils.asyncStaffChatMessage(Messages.GET_STAFF_VANISH_LEAVE_MESSAGE()
                    .replace("{player}", player.getName()));
            event.setQuitMessage("");
        }

        if (Config.disableStaffModeOnExit()) {
            staffPlayer.setQuit(true);
            staffPlayer.toggleStaffMode(true);
        }

        if(staffPlayer.existsData() && staffPlayer.isStaffMode()){
            staffPlayer.clearInventory();
            staffPlayer.restoreInventory();
        }

        staffPlayer.changeGamemode(GameMode.SURVIVAL);

        StaffPlayersManagers.removeStaffPlayer(staffPlayer);
    }
}
