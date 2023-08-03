package es.angelillo15.mast.api.punishments.cmd

import es.angelillo15.mast.api.punishments.IPunishPlayer

abstract class PunishTargetCommand : PunishCommand() {
    override fun onCommand(sender: IPunishPlayer?, label: String?, args: Array<out String>) {
        val argsParsed = if (args.isEmpty()) {
            ""
        } else {
            args[0]
        }

        onCommand(sender!!, argsParsed, label!!, args)
    }

    abstract fun onCommand(sender: IPunishPlayer, target: String, label: String, args:  Array<out String>)
}