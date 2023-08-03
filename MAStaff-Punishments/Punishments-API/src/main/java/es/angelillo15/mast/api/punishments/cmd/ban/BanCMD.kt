package es.angelillo15.mast.api.punishments.cmd.ban

import com.google.inject.Inject
import es.angelillo15.mast.api.IServerUtils
import es.angelillo15.mast.api.cmd.CommandData
import es.angelillo15.mast.api.cmd.sender.CommandSender
import es.angelillo15.mast.api.config.punishments.Messages
import es.angelillo15.mast.api.punishments.IPunishPlayer
import es.angelillo15.mast.api.punishments.cmd.PunishTargetReasonCommand
import es.angelillo15.mast.api.templates.managers.BanTemplatesManager

@CommandData(
    name = "ban",
    description = "Ban a player",
    usage = "/ban <player> <reason>",
    permission = "mast.punishments.ban",
    aliases = ["b"]
)
class BanCMD : PunishTargetReasonCommand(1, Messages.Default.defaultBanReason()) {
    @Inject
    private lateinit var serverUtils: IServerUtils
    @Inject
    private lateinit var banTemplatesManager: BanTemplatesManager

    override fun onCommand(sender: IPunishPlayer, target: String, label: String, args: Array<out String>, reason: String) {
        if (args.isEmpty()) {
            sender.sendMessage(Messages.Commands.Ban.usage())
            return
        }

        if (banTemplatesManager.getBanTemplate(reason) != null)
            sender.tryBanTemplate(target, reason)
        else
            sender.ban(target, reason)

        sender.sendMessage(Messages.Commands.Ban.success(target, reason, sender.name))
    }

    override fun onTabComplete(sender: CommandSender?, args: Array<String?>?): List<String?> {
        if (args!!.size == 1) {
            return serverUtils.getOnlinePlayersNames()
        }

        if (args.size == 2) {
            return banTemplatesManager.getBanTemplates().map { it.id }
        }

        return emptyList()
    }
}
