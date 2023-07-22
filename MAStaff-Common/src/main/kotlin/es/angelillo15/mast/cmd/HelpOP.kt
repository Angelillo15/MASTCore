package es.angelillo15.mast.cmd

import es.angelillo15.mast.api.cmd.CommandData
import es.angelillo15.mast.api.cmd.CooldownCommand
import es.angelillo15.mast.api.cmd.sender.CommandSender

@CommandData(
    name = "HelpOP",
    permission = "mast.helpop",
)
class HelpOP : CooldownCommand(60) {
    override fun onCooldownCommand(sender: CommandSender, label: String, args: Array<String?>) {
        sender.sendMessage("HelpOP command!")
    }
}