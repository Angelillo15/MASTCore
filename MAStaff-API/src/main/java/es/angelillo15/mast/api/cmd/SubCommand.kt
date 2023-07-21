package es.angelillo15.mast.api.cmd

import es.angelillo15.mast.api.cmd.sender.CommandSender

abstract class SubCommand {
    abstract val name: String?
    abstract val description: String?
    abstract val syntax: String?
    abstract val permission: String?
    abstract fun onCommand(sender: CommandSender?, label: String?, args: Array<String?>?)
}