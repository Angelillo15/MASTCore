package es.angelillo15.mast.bukkit.listener.staffmode;

import com.google.inject.Inject;
import es.angelillo15.mast.api.managers.StaffManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class OnPlayerInteractAtEntityEvent implements Listener {
  @Inject private StaffManager staffManager;

  @EventHandler
  public void onPlayerInteractAtEntityEvent(PlayerInteractAtEntityEvent event) {
    if (staffManager.isStaffPlayer(event.getPlayer())
        && staffManager.getStaffPlayer(event.getPlayer()).isStaffMode()) event.setCancelled(true);
  }
}
