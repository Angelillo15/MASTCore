package es.angelillo15.mast.api.punishments.cmd.ban

import es.angelillo15.mast.api.cmd.CommandData
import es.angelillo15.mast.api.config.punishments.Messages
import es.angelillo15.mast.api.punishments.IPunishPlayer
import es.angelillo15.mast.api.punishments.cmd.PunishCommand

@CommandData(
        name = "ipban",
        permission = "mastaff.punishments.ipban",
        usage = "/ipban <ip> <reason>",
        description = "Ban an IP"
)
class IPBanCMD : PunishCommand() {
    override fun onCommand(sender: IPunishPlayer, label: String, args: Array<String>) {
        if (args.isEmpty()) {
            sender.sendMessage(Messages.Commands.IpBan.usage())
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

        sender.ban(args[0], reason.toString(), true)
        sender.sendMessage(Messages.Commands.IpBan.success(target, reason.toString(), sender.name))
    }
}
