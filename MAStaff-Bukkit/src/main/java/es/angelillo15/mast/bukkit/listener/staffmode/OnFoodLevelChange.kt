package es.angelillo15.mast.bukkit.listener.staffmode

import com.google.inject.Inject
import es.angelillo15.mast.api.managers.StaffManager
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.FoodLevelChangeEvent

class OnFoodLevelChange : Listener {
  @Inject
  private lateinit var staffManager: StaffManager
  @EventHandler
  fun onFoodLevelChange(event: FoodLevelChangeEvent) {
    if (event.entity !is Player) return

    val player = event.entity as Player

    if(!staffManager.isStaffPlayer(player)) return

    val staffPlayer = staffManager.getStaffPlayer(player)

    event.isCancelled = staffPlayer.isStaffMode
  }
}