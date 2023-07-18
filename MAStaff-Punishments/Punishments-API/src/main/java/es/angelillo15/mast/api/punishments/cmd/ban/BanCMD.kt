package es.angelillo15.mast.api.punishments.cmd.ban

import es.angelillo15.mast.api.cmd.CommandData
import es.angelillo15.mast.api.config.punishments.Messages
import es.angelillo15.mast.api.punishments.IPunishPlayer
import es.angelillo15.mast.api.punishments.PunishTargetReasonCommand

@CommandData(
    name = "ban",
    description = "Ban a player",
    usage = "/ban <player> <reason>",
    permission = "mast.punishments.ban",
    aliases = ["b"]
)
class BanCMD : PunishTargetReasonCommand(1, Messages.Default.defaultBanReason()) {
    override fun onCommand(sender: IPunishPlayer, target: String, label: String, args: Array<out String>, reason: String) {
        if (args.isEmpty()) {
            sender.sendMessage(Messages.Commands.Ban.usage())
            return
        }

        sender.ban(target, reason)
        sender.sendMessage(Messages.Commands.Ban.success(target, reason, sender.name))
    }
}
