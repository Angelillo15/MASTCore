package es.angelillo15.mast.cmd

import com.google.inject.Inject
import es.angelillo15.mast.api.ILogger
import es.angelillo15.mast.api.IServerUtils
import es.angelillo15.mast.api.cmd.Command
import es.angelillo15.mast.api.cmd.CommandData
import es.angelillo15.mast.api.cmd.sender.CommandSender
import es.angelillo15.mast.api.config.common.CommonMessages
import es.angelillo15.mast.api.managers.StaffChatManager

@CommandData(
    name = "StaffChat",
    permission = "mast.staffchat",
    aliases = ["sc"],
    description = "Send a message to all staff members.",
)
class StaffChat : Command() {
    @Inject
    private lateinit var serverUtils: IServerUtils;
    @Inject
    private lateinit var staffChatManager: StaffChatManager;
    @Inject
    private lateinit var logger: ILogger;
    override fun onCommand(sender: CommandSender?, label: String?, args: Array<String?>?) {
        if (args!!.isEmpty()) {
            if (staffChatManager.isStaffChatEnabled(sender!!.name)) {
                staffChatManager.setStaffChatEnabled(sender.name, false)
                sender.run { sendMessage(CommonMessages.StaffChat.disabled()) }
            } else {
                staffChatManager.setStaffChatEnabled(sender.name, true)
                sender.run { sendMessage(CommonMessages.StaffChat.enabled()) }
            }

            return
        }

        val message = args.joinToString(" ")

        val formattedMessage = CommonMessages.StaffChat.format()
            .replace("{server}", sender!!.serverName)
            .replace("{player}", sender.name)
            .replace("{message}", message)

        serverUtils.broadcastMessage(formattedMessage, "mast.staffchat")
    }
}