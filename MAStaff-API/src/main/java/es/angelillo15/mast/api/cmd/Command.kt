package es.angelillo15.mast.api.cmd

import es.angelillo15.mast.api.cmd.sender.CommandSender

abstract class Command {
    abstract fun onCommand(sender: CommandSender?, label: String?, args: Array<String?>?)
}
