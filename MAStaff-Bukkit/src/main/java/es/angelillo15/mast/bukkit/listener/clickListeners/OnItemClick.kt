package es.angelillo15.mast.bukkit.listener.clickListeners

import com.google.inject.Inject
import es.angelillo15.mast.api.ILogger
import es.angelillo15.mast.api.Permissions
import es.angelillo15.mast.api.items.IExecutableItem
import es.angelillo15.mast.api.items.IExecutableLocationItem
import es.angelillo15.mast.api.managers.StaffManager
import es.angelillo15.mast.api.nms.VersionSupport
import es.angelillo15.mast.api.thread.execute
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class OnItemClick : Listener {
  @Inject
  private lateinit var staffManager: StaffManager

  @Inject
  private lateinit var versionSupport: VersionSupport

  @Inject
  private lateinit var logger: ILogger

  @EventHandler
  fun onItemClick(event: PlayerInteractEvent) {
    val start = System.currentTimeMillis()
    val player = event.player

    if (!staffManager.isStaffPlayer(player)) {
      return
    }

    val staffPlayer = staffManager.getStaffPlayer(player) ?: return

    if (!staffPlayer.isStaffMode()) {
      return
    }

    if (event.item == null) {
      if (!player.hasPermission(Permissions.STAFF_BUILD.permission)) {
        event.setCancelled(true)
      }
      return
    }

    val tag = versionSupport.getTag(event.item!!, "mast-staff-item")

    if (tag == null) {
      event.setCancelled(true)
      return
    }

    logger.debug("Item " + tag + " clicked by " + player.name)

    val item = staffPlayer.getItems()[tag]

    if (item == null) {
      event.setCancelled(true)
      return
    }

    if (item is IExecutableItem) {
      (item as IExecutableItem).click(player)
    }

    if (item is IExecutableLocationItem) {
      if (event.clickedBlock == null) return
      val block = event.clickedBlock!!

      execute {
        (item as IExecutableLocationItem).click(player, block.location)
      }
    }
    logger.debug("Item " + item.getName() + " took " + (System.currentTimeMillis() - start) + "ms to execute")
    event.setCancelled(true)
  }
}
