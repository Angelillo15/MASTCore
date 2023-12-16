package es.angelillo15.mast.bukkit.listener.staffchat

import com.google.inject.Inject
import com.nookure.mast.api.cmd.sender.PlayerCommandSender
import com.nookure.mast.api.event.EventManager
import com.nookure.mast.api.event.message.BackendStaffChatEvent
import com.nookure.mast.api.event.staff.staffchat.StaffChatMessageSentEvent
import es.angelillo15.mast.api.config.bukkit.Config
import es.angelillo15.mast.api.config.common.CommonConfig
import es.angelillo15.mast.api.config.common.CommonMessages
import es.angelillo15.mast.api.managers.StaffChatManager
import es.angelillo15.mast.bukkit.BukkitPluginMessageManager
import es.angelillo15.mast.bukkit.ServerUtils

open class OnStaffMessageEvent {
  @Inject
  lateinit var pmm: BukkitPluginMessageManager;

  @Inject
  private lateinit var staffChatManager: StaffChatManager;

  @Inject
  private lateinit var serverUtils: ServerUtils;

  @Inject
  private lateinit var eventManager: EventManager
  fun onStaffMessageEvent(player: PlayerCommandSender, message: String): Boolean {
    if (!CommonConfig.StaffChat.enabled()) return false
    if (!CommonConfig.StaffChat.Prefix.enabled()) return false

    val key = CommonConfig.StaffChat.Prefix.key()

    if (!message.startsWith(key) && !staffChatManager.isStaffChatEnabled(player.name)) return false

    val strippedMessage = if (message.startsWith(key)) message.substring(key.length) else message

    if (Config.isProxy()) {
      BackendStaffChatEvent(strippedMessage, player.name, player.serverName).let {
        pmm.sendEvent(it, player.player)
      }
    } else {
      val formattedMessage = CommonMessages.StaffChat.format()
          .replace("{server}", player.serverName)
          .replace("{player}", player.name)
          .replace("{message}", strippedMessage)

      serverUtils.broadcastMessage(formattedMessage, "mast.staffchat")

      eventManager.fireEvent(StaffChatMessageSentEvent(message, player.name, player.serverName))
    }

    return true
  }
}