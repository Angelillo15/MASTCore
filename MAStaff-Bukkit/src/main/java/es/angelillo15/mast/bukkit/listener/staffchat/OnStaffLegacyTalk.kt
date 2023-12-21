package es.angelillo15.mast.bukkit.listener.staffchat

import com.google.inject.Inject
import com.nookure.mast.api.cmd.sender.PlayerCommandSender
import com.nookure.mast.api.manager.cmd.CommandBukkitSenderManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

@Suppress("DEPRECATION")
class OnStaffLegacyTalk : OnStaffMessageEvent(), Listener {
  @Inject
  private lateinit var commandManager: CommandBukkitSenderManager

  @EventHandler
  fun onPlayerTalk(event: AsyncPlayerChatEvent) {
    if (!event.player.hasPermission("mast.staffchat")) return

    if (onStaffMessageEvent(commandManager.getSender(event.player) as PlayerCommandSender, event.message)) {
      event.isCancelled = true
    }
  }
}