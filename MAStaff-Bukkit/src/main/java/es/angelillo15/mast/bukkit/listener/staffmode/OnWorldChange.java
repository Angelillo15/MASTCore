package es.angelillo15.mast.bukkit.listener.staffmode;

import com.google.inject.Inject;
import com.nookure.mast.api.event.BukkitListener;
import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.managers.StaffManager;
import es.angelillo15.mast.bukkit.MAStaff;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class OnWorldChange implements BukkitListener<PlayerChangedWorldEvent> {
  @Inject
  private StaffManager staffManager;
  @Override
  @EventHandler
  public void handle(PlayerChangedWorldEvent event) {
    Player player = event.getPlayer();
    IStaffPlayer staffPlayer = staffManager.getStaffPlayer(player);

    if (staffPlayer == null) return;
    if (!staffPlayer.isStaffMode()) return;

    Bukkit.getScheduler().runTaskLater(MAStaff.getPlugin(), () -> {
      player.setAllowFlight(true);
    }, 20L);
  }
}
