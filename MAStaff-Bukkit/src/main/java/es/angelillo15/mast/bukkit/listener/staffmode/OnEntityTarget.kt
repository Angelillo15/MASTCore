package es.angelillo15.mast.bukkit.listener.staffmode

import com.google.inject.Inject
import es.angelillo15.mast.api.managers.StaffManager
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityTargetEvent

class OnEntityTarget : Listener {
  @Inject
  private lateinit var staffManager: StaffManager
  @EventHandler
  fun onEntityTrack(event: EntityTargetEvent) {
    if (event.target == null) return
    if (event.target !is Player) return

    val player = event.target as Player

    if(!staffManager.isStaffPlayer(player)) return

    val staffPlayer = staffManager.getStaffPlayer(player)

    event.isCancelled = staffPlayer.isStaffMode
  }
}