package es.angelillo15.mast.bukkit.listener.staffmode;

import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.managers.StaffPlayersManagers;
import es.angelillo15.mast.bukkit.StaffPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class OnItemDrop implements Listener {
    @EventHandler
    public void onDrop(PlayerDropItemEvent event){
        Player player = event.getPlayer();

        if(!player.hasPermission("mast.staffmode")){
            return;
        }

        IStaffPlayer staffPlayer = StaffPlayersManagers.getStaffPlayer(player);

        if(staffPlayer.isStaffMode()){
            event.setCancelled(true);
            staffPlayer.setItems();
        }
    }
}
