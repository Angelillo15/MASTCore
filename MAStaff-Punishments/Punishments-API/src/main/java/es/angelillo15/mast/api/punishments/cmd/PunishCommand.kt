package es.angelillo15.mast.api.punishments.cmd

import es.angelillo15.mast.api.cmd.Command
import es.angelillo15.mast.api.cmd.sender.CommandSender
import es.angelillo15.mast.api.punishments.IPunishPlayer
import es.angelillo15.mast.api.punishments.PunishPlayersManager
import es.angelillo15.mast.api.thread.execute

abstract class PunishCommand : Command() {
    override fun onCommand(sender: CommandSender, label: String, args: Array<String>) {
        if (!sender.hasPermission("mast.punishments")) return

        val punishPlayer = PunishPlayersManager.getPlayer(sender.uniqueId) ?: return
        execute { onCommand(punishPlayer, label, args) }
    }

    abstract fun onCommand(sender: IPunishPlayer, label: String, args: Array<String>)
}
