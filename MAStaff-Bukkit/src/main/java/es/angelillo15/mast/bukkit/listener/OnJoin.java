package es.angelillo15.mast.bukkit.listener;

import com.google.inject.Inject;
import com.nookure.mast.api.event.EventManager;
import com.nookure.mast.api.event.staff.StaffJoinEvent;
import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.managers.StaffManager;
import es.angelillo15.mast.api.utils.MAStaffInject;
import es.angelillo15.mast.bukkit.MAStaff;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoin implements Listener {
  @Inject
  private StaffManager staffManager;
  @Inject
  private MAStaffInject instance;
  @Inject
  private EventManager eventManager;

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent e) {
    Player player = e.getPlayer();

    if (!(player.hasPermission("mast.staff"))) {
      return;
    }

    staffManager.addStaffPlayer(instance.createStaffPlayer(player));
    MAStaff.getPlugin().getPLogger().debug("Added " + player.getName() + " to the map");

    eventManager.fireEvent(new StaffJoinEvent(player.getName()));
  }
}
