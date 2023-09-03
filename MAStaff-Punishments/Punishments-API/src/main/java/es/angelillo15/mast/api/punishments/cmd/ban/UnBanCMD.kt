package es.angelillo15.mast.api.punishments.cmd.ban

import es.angelillo15.mast.api.cmd.CommandData
import es.angelillo15.mast.api.config.punishments.Messages
import es.angelillo15.mast.api.punishments.IPunishPlayer
import es.angelillo15.mast.api.punishments.cmd.PunishTargetReasonCommand

@CommandData(
    name = "unban",
    description = "Unban a player",
    usage = "/unban <player>",
    permission = "mast.punishments.unban",
    aliases = ["ub"]
)
class UnBanCMD : PunishTargetReasonCommand(1, Messages.Default.defaultUnbanReason()) {
    override fun onCommand(
        sender: IPunishPlayer,
        target: String,
        label: String,
        args: Array<out String>,
        reason: String
    ) {
        if (args.isEmpty()) {
            sender.sendMessage(Messages.Commands.Unban.usage())
            return
        }

        sender.unban(target, reason)
    }
}
