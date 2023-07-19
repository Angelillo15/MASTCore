package es.angelillo15.mast.cmd

import es.angelillo15.mast.api.cmd.CooldownCommand
import es.angelillo15.mast.api.cmd.sender.CommandSender

class HelpOP : CooldownCommand(60) {
    override fun onCooldownCommand(sender: CommandSender, label: String, args: Array<out String>) {
        TODO("Not yet implemented")
    }
}