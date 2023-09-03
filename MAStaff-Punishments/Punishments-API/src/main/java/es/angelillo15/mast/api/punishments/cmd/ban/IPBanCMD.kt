package es.angelillo15.mast.api.punishments.cmd.ban

import es.angelillo15.mast.api.cmd.CommandData
import es.angelillo15.mast.api.config.punishments.Messages
import es.angelillo15.mast.api.punishments.IPunishPlayer
import es.angelillo15.mast.api.punishments.cmd.PunishTargetReasonCommand

@CommandData(
    name = "ipban",
    permission = "mastaff.punishments.ipban",
    usage = "/ipban <ip> <reason>",
    description = "Ban an IP"
)
class IPBanCMD : PunishTargetReasonCommand(1, Messages.Default.defaultBanReason()) {
    override fun onCommand(
        sender: IPunishPlayer,
        target: String,
        label: String,
        args: Array<out String>,
        reason: String
    ) {
        if (args.isEmpty()) {
            sender.sendMessage(Messages.Commands.IpBan.usage())
            return
        }

        sender.ban(args[0], reason.toString(), true)
        sender.sendMessage(Messages.Commands.IpBan.success(target, reason.toString(), sender.name))
    }
}
