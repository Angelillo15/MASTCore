package es.angelillo15.mast.api.punishments.cmd.warn

import es.angelillo15.mast.api.cmd.CommandData
import es.angelillo15.mast.api.config.punishments.Messages
import es.angelillo15.mast.api.punishments.IPunishPlayer
import es.angelillo15.mast.api.punishments.cmd.PunishTargetReasonCommand

@CommandData(name = "unwarn", permission = "mast.unwarn")
class UnWarnCMD : PunishTargetReasonCommand(1, Messages.Default.defaultUnWarnReason()) {
    override fun onCommand(
        sender: IPunishPlayer,
        target: String,
        label: String,
        args: Array<out String>,
        reason: String
    ) {
        if (args.isEmpty()) {
            sender.sendMessage(Messages.Commands.UnWarn.usage())
            return
        }

        sender.unWarn(target, reason)
    }
}
