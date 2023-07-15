package es.angelillo15.mast.api.punishments.cmd.ban

import es.angelillo15.mast.api.cmd.CommandData
import es.angelillo15.mast.api.config.punishments.Messages
import es.angelillo15.mast.api.punishments.IPunishPlayer
import es.angelillo15.mast.api.punishments.cmd.PunishCommand

@CommandData(
        name = "ban",
        description = "Ban a player",
        usage = "/ban <player> <reason>",
        permission = "mast.punishments.ban",
        aliases = ["b"]
)
class BanCMD : PunishCommand() {
    override fun onCommand(punishPlayer: IPunishPlayer, label: String, args: Array<String>) {
        if (args.isEmpty()) {
            punishPlayer.sendMessage(Messages.Commands.Ban.usage())
            return
        }

        val target = args[0]
        val reason = StringBuilder()

        for (i in 1 until args.size) {
            reason.append(args[i]).append(" ")
        }

        if (reason.toString().isEmpty()) {
            reason.append(Messages.Default.defaultBanReason())
        }

        punishPlayer.ban(args[0], reason.toString())
        punishPlayer.sendMessage(Messages.Commands.Ban.success(target, reason.toString(), punishPlayer.name))
    }
}
