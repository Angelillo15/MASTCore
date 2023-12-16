package es.angelillo15.mast.bukkit.listener.staffmode;

import com.google.inject.Inject;
import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.managers.StaffManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class OnDamage implements Listener {
  @Inject
  private StaffManager staffManager;

  @EventHandler(
      ignoreCancelled = true,
      priority = EventPriority.HIGHEST
  )
  public void onAttacked(EntityDamageByEntityEvent event) {
    if (!(event.getEntity() instanceof Player player)) return;

    if (!staffManager.isStaffPlayer(player)) return;

    IStaffPlayer staffPlayer = staffManager.getStaffPlayer(player);

    assert staffPlayer != null;
    if (staffPlayer.isStaffMode()) event.setCancelled(true);
  }

  @EventHandler(
      ignoreCancelled = true,
      priority = EventPriority.HIGHEST
  )
  public void onAttack(EntityDamageEvent event) {
    if (!(event.getEntity() instanceof Player player)) return;

    if (!staffManager.isStaffPlayer(player)) return;

    IStaffPlayer staffPlayer = staffManager.getStaffPlayer(player);

    assert staffPlayer != null;
    if (staffPlayer.isStaffMode()) event.setCancelled(true);
  }
}
