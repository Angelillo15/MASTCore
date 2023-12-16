package es.angelillo15.mast.bukkit.listener.vanish;

import com.google.inject.Inject;
import es.angelillo15.mast.api.managers.StaffManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class OnVanishAttack implements Listener {
  @Inject
  private StaffManager staffManager;
  @EventHandler(
      ignoreCancelled = true,
      priority = EventPriority.HIGHEST
  )
  public void onAttack(EntityDamageByEntityEvent event) {
    if (!(event.getDamager() instanceof Player player)) return;

    if (!staffManager.isStaffPlayer(player)) return;

    if (player.hasPermission("mast.vanish.attack.bypass")) return;

    staffManager.getOptionalStaffPlayer(player).ifPresent(staffPlayer -> {
      if (staffPlayer.isVanished()) event.setCancelled(true);
    });
  }
}
