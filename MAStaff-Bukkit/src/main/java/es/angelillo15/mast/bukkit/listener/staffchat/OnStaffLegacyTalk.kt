package es.angelillo15.mast.bukkit.listener.staffchat

import es.angelillo15.mast.handler.OnStaffMessageEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

@Suppress("DEPRECATION")
class OnStaffLegacyTalk : OnStaffMessageEvent(), Listener {
  @EventHandler
  fun onPlayerTalk(event: AsyncPlayerChatEvent) {
    if (!event.player.hasPermission("mast.staffchat")) return

    if (onStaffMessageEvent(
        event.player.name, event.message, "Bukkit"
      )
    ) {
      event.isCancelled = true
    }
  }
}