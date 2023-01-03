package es.angelillo15.mast.bukkit.listener.staffmode;

import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.Permissions;
import es.angelillo15.mast.api.managers.StaffPlayersManagers;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class OnInventoryClick implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();

        if(!player.hasPermission(Permissions.STAFF.getPermission())){
            return;
        }

        IStaffPlayer staffPlayer = StaffPlayersManagers.getStaffPlayer(player);

        if(staffPlayer.isStaffMode()){
            event.setCancelled(true);
        }
    }
}
