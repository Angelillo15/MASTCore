package es.angelillo15.mast.bukkit.listener.staffmode;

import com.google.inject.Inject;
import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.Permissions;
import es.angelillo15.mast.api.managers.StaffManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class OnSwapHand implements Listener {
  @Inject private StaffManager staffManager;

  @EventHandler
  public void onSwapHand(PlayerSwapHandItemsEvent event) {
    Player player = event.getPlayer();

    if (!player.hasPermission(Permissions.STAFF.getPermission())) {
      return;
    }

    IStaffPlayer staffPlayer = staffManager.getStaffPlayer(player);

    if (staffPlayer.isStaffMode()) event.setCancelled(true);
  }
}
