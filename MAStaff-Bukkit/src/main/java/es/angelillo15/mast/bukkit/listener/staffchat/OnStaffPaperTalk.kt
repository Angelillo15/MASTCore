package es.angelillo15.mast.bukkit.listener.staffchat

import es.angelillo15.mast.handler.OnStaffMessageEvent
import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class OnStaffPaperTalk : OnStaffMessageEvent(), Listener {
  @EventHandler
  fun onPlayerTalk(event: AsyncChatEvent) {
    if (!event.player.hasPermission("mast.staffchat")) return

    if (onStaffMessageEvent(
            event.player.name, MiniMessage.miniMessage().serialize(event.message()), "Paper"
          )
    ) {
      event.isCancelled = true
    }
  }
}