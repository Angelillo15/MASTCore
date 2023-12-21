package es.angelillo15.mast.bukkit.listener.freeze;

import com.google.inject.Inject;
import com.nookure.mast.api.manager.FreezeManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class OnFreezeDamage implements Listener {
  @Inject
  private FreezeManager freezeManager;

  @EventHandler(
      ignoreCancelled = true,
      priority = EventPriority.HIGHEST
  )
  public void onAttacked(EntityDamageByEntityEvent event) {
    if (!(event.getEntity() instanceof Player player)) return;

    freezeManager.getFreezeOptional(player).ifPresent(freezeVector -> event.setCancelled(true));
  }

  @EventHandler(
      ignoreCancelled = true,
      priority = EventPriority.HIGHEST
  )
  public void onAttack(EntityDamageEvent event) {
    if (!(event.getEntity() instanceof Player player)) return;

    freezeManager.getFreezeOptional(player).ifPresent(freezeVector -> event.setCancelled(true));
  }
}
