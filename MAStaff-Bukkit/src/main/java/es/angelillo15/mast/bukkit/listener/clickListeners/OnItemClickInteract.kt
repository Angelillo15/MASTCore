package es.angelillo15.mast.bukkit.listener.clickListeners

import com.google.inject.Inject
import es.angelillo15.mast.api.ILogger
import es.angelillo15.mast.api.items.IPlayerInteractItem
import es.angelillo15.mast.api.managers.StaffManager
import es.angelillo15.mast.api.nms.VersionSupport
import es.angelillo15.mast.api.thread.execute
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEntityEvent

@Suppress("deprecation")
class OnItemClickInteract : Listener {
  @Inject
  private lateinit var staffManager: StaffManager
  @Inject
  private lateinit var versionSupport: VersionSupport
  @Inject
  private lateinit var logger: ILogger

  private var clicked = false

  @EventHandler
  fun onEntityInteract(event: PlayerInteractEntityEvent) {
    val start = System.currentTimeMillis()
    val player = event.player

    if (!staffManager.isStaffPlayer(player)) {
      return
    }

    val staffPlayer = staffManager.getStaffPlayer(player) ?: return

    if (!staffPlayer.isStaffMode()) {
      return
    }

    if (event.rightClicked !is Player) return
    if (player.itemInHand.type == Material.AIR) return

    val tag = versionSupport.getTag(player.itemInHand, "mast-staff-item") ?: return

    logger.debug("Item " + tag + " clicked by " + player.name)

    val item = staffPlayer.getItems()[tag] ?: return

    if (item is IPlayerInteractItem) {
      if (clicked) {
        clicked = false
        return
      }

      execute {
        item.interact(player, event.rightClicked as Player)
      }

      clicked = true
    }

    logger.debug("Item " + tag + " clicked by " + player.name + " took " + (System.currentTimeMillis() - start) + "ms")
  }
}
