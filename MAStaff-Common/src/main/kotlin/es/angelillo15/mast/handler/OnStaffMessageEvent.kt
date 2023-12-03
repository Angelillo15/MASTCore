package es.angelillo15.mast.handler

import com.google.inject.Inject
import com.nookure.mast.api.event.EventManager
import com.nookure.mast.api.event.staff.staffchat.StaffChatMessageSentEvent
import es.angelillo15.mast.api.IServerUtils
import es.angelillo15.mast.api.config.common.CommonConfig
import es.angelillo15.mast.api.config.common.CommonMessages
import es.angelillo15.mast.api.managers.StaffChatManager

open class OnStaffMessageEvent {
  @Inject
  private lateinit var staffChatManager: StaffChatManager

  @Inject
  private lateinit var serverUtils: IServerUtils

  @Inject
  private lateinit var eventManager: EventManager

  fun onStaffMessageEvent(player: String, message: String, server: String): Boolean {
    if (!CommonConfig.StaffChat.enabled()) return false
    if (!CommonConfig.StaffChat.Prefix.enabled()) return false

    val key = CommonConfig.StaffChat.Prefix.key()
    if (!message.startsWith(key) && !staffChatManager.isStaffChatEnabled(player)) return false

    val strippedMessage = message.substring(key.length)

    val formattedMessage = CommonMessages.StaffChat.format()
            .replace("{server}", server)
            .replace("{player}", player)
            .replace("{message}", strippedMessage)

    serverUtils.broadcastMessage(formattedMessage, "mast.staffchat")

    eventManager.fireEvent(StaffChatMessageSentEvent(message, player, server))

    return true
  }
}