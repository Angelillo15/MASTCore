package es.angelillo15.mast.bukkit.listener;

import com.google.inject.Inject;
import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.managers.StaffManager;
import es.angelillo15.mast.bukkit.MAStaff;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoin implements Listener {
  @Inject private StaffManager staffManager;
  @Inject private MAStaffInstance instance;

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent e) {
    Player player = e.getPlayer();

    if (!(player.hasPermission("mast.staff"))) {
      return;
    }

    if (staffManager.isStaffPlayer(player)) {
      return;
    }

    staffManager.addStaffPlayer(instance.createStaffPlayer(player));
    MAStaff.getPlugin().getPLogger().debug("Added " + player.getName() + " to the map");
  }
}
