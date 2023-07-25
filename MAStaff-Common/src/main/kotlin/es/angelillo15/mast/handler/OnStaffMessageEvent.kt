package es.angelillo15.mast.handler

import com.google.inject.Inject
import es.angelillo15.mast.api.IServerUtils
import es.angelillo15.mast.api.config.common.CommonMessages
import es.angelillo15.mast.api.config.common.Config
import es.angelillo15.mast.api.managers.StaffChatManager

class OnStaffMessageEvent {
    @Inject
    private lateinit var staffChatManager: StaffChatManager;
    @Inject
    private lateinit var serverUtils: IServerUtils;

    fun onStaffMessageEvent(player: String, message: String, server: String) : Boolean {
        val key = Config.StaffChat.Prefix.key();
        var strippedMessage = message;
        if (!message.startsWith(key) && !staffChatManager.isStaffChatEnabled(player)) return false

        if (message.startsWith(key)) {
            strippedMessage = message.substring(key.length)
        }

        var formattedMessage = CommonMessages.StaffChat.format()
            .replace("{server}", server)
            .replace("{player}", player)
            .replace("{message}", message)

        serverUtils.broadcastMessage(message, "mast.staffchat")

        return true
    }
}