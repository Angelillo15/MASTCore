package es.angelillo15.mast.api.punishments.cmd.ban

import es.angelillo15.mast.api.MAStaffInstance
import es.angelillo15.mast.api.cmd.CommandData
import es.angelillo15.mast.api.config.punishments.Messages
import es.angelillo15.mast.api.punishments.IPunishPlayer
import es.angelillo15.mast.api.punishments.cmd.PunishCommand

@CommandData(
        name = "unban",
        description = "Unban a player",
        usage = "/unban <player>",
        permission = "mast.punishments.unban",
        aliases = ["ub"]
)
class UnBanCMD : PunishCommand() {
    override fun onCommand(sender: IPunishPlayer, label: String, args: Array<String>) {

        if (args.isEmpty()) {
            sender.sendMessage(Messages.Commands.Unban.usage())
            return
        }

        val reason = StringBuilder()

        for (i in 1 until args.size) {
            reason.append(args[i]).append(" ")
        }

        if (reason.toString().isEmpty()) {
            reason.append(Messages.Default.defaultUnbanReason())
        }

        val target = args[0]

        try {
            sender.unban(target, reason.toString())
        } catch (e: Exception) {
            MAStaffInstance.getLogger().debug("Error while unbanning player " + target + ": " + e.message)
            return
        }

        sender.sendMessage(Messages.Commands.Unban.success(
                target,
                reason.toString(),
                sender.name
        ))
    }
}
