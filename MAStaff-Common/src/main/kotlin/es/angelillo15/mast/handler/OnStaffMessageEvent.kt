package es.angelillo15.mast.handler

import com.google.inject.Inject
import es.angelillo15.mast.api.IServerUtils
import es.angelillo15.mast.api.config.common.CommonConfig
import es.angelillo15.mast.api.config.common.CommonMessages
import es.angelillo15.mast.api.managers.StaffChatManager

open class OnStaffMessageEvent {
    @Inject
    private lateinit var staffChatManager: StaffChatManager

    @Inject
    private lateinit var serverUtils: IServerUtils

    fun onStaffMessageEvent(player: String, message: String, server: String): Boolean {
        val key = CommonConfig.StaffChat.Prefix.key()
        var strippedMessage = message
        if (!message.startsWith(key) && !staffChatManager.isStaffChatEnabled(player)) return false

        if (message.startsWith(key)) {
            strippedMessage = message.substring(key.length)
        }

        val formattedMessage = CommonMessages.StaffChat.format()
            .replace("{server}", server)
            .replace("{player}", player)
            .replace("{message}", strippedMessage)

        serverUtils.broadcastMessage(formattedMessage, "mast.staffchat")

        return true
    }
}