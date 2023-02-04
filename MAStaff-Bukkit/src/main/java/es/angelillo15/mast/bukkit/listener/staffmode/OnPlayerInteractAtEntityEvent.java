package es.angelillo15.mast.bukkit.listener.staffmode;

import es.angelillo15.mast.api.managers.StaffPlayersManagers;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class OnPlayerInteractAtEntityEvent implements Listener {
    @EventHandler
    public void OnPlayerInteractAtEntityEvent(PlayerInteractAtEntityEvent event) {
        if (StaffPlayersManagers.isStaffPlayer(event.getPlayer()) && StaffPlayersManagers.getStaffPlayer(event.getPlayer()).isStaffMode())
            event.setCancelled(true);

    }
}
