package es.angelillo15.mast.velocity.cmd.mastv

import com.google.inject.Inject
import es.angelillo15.mast.api.cmd.SubCommand
import es.angelillo15.mast.api.cmd.sender.CommandSender
import es.angelillo15.mast.api.config.velocity.Messages
import es.angelillo15.mast.api.utils.MAStaffInject

class ReloadArg : SubCommand() {
    @Inject
    private lateinit var plugin: MAStaffInject
    override val name: String
        get() = "reload"
    override val description: String
        get() = "Reloads the plugin"
    override val syntax: String
        get() = "/mastv reload"
    override val permission: String
        get() = "mast.reload"

    override fun onCommand(sender: CommandSender, label: String, args: Array<String>) {
        plugin.reload()
        sender!!.sendMessage(Messages.reloaded());
    }
}