package es.angelillo15.mast.api.punishments

import es.angelillo15.mast.api.punishments.cmd.PunishCommand

abstract class PunishTargetCommand : PunishCommand() {
    override fun onCommand(sender: IPunishPlayer?, label: String?, args: Array<out String>?) {
        onCommand(sender!!, args!![0], label!!, args)
    }

    abstract fun onCommand(sender: IPunishPlayer, target: String, label: String, args:  Array<out String>?)
}