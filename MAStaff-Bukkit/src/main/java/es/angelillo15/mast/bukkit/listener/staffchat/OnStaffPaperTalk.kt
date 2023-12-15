package es.angelillo15.mast.bukkit.listener.staffchat

import com.google.inject.Inject
import com.nookure.mast.api.cmd.sender.PlayerCommandSender
import com.nookure.mast.api.manager.cmd.CommandBukkitSenderManager
import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

class OnStaffPaperTalk : OnStaffMessageEvent(), Listener {
  @Inject
  private lateinit var commandManager: CommandBukkitSenderManager
  @EventHandler(
      ignoreCancelled = true,
      priority = EventPriority.HIGH
  )
  fun onPlayerTalk(event: AsyncChatEvent) {
    if (!event.player.hasPermission("mast.staffchat")) return

    if (onStaffMessageEvent(commandManager.getSender(event.player) as PlayerCommandSender,
            MiniMessage.miniMessage().serialize(event.message())
    )) {
      event.isCancelled = true
    }
  }
}