package es.angelillo15.mast.api.punishments.cmd.ban

import es.angelillo15.mast.api.TextUtils
import es.angelillo15.mast.api.cmd.CommandData
import es.angelillo15.mast.api.config.punishments.Config
import es.angelillo15.mast.api.config.punishments.Messages
import es.angelillo15.mast.api.punishments.IPunishPlayer
import es.angelillo15.mast.api.punishments.cmd.PunishCommand
import es.angelillo15.mast.api.utils.NumberUtils

@CommandData(
        name = "tempipban",
        permission = "mast.ban.tempipban",
        aliases = ["tempipban", "tempipb"],
        usage = "&cUsage: &7/tempipban <player> <time> <reason>",
        description = "Temporarily IP ban a player"
)
class TempIPBanCMD : PunishCommand() {
    override fun onCommand(sender: IPunishPlayer, label: String, args: Array<String>) {
        if (args.size < 2) {
            sender.sendMessage(Messages.Commands.TempIpBan.usage())
            return
        }

        val target = args[0]
        val reason = StringBuilder()

        for (i in 2 until args.size) {
            reason.append(args[i]).append(" ")
        }
        if (reason.toString().isEmpty()) {
            reason.append(Messages.Default.defaultBanReason())
        }

        val time: Long = try {
            System.currentTimeMillis() + NumberUtils.parseToMilis(args[1])
        } catch (e: NumberFormatException) {
            sender.sendMessage("Invalid time")
            return
        }

        sender.ban(target, reason.toString(), time, true)
        sender.sendMessage(Messages.Commands.TempIpBan.success(
                target,
                TextUtils.formatDate(time, Config.dateFormat()),
                reason.toString(),
                sender.name)
        )
    }
}
