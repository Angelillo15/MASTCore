package es.angelillo15.mast.api.punishments.cmd.warn

import com.google.inject.Inject
import es.angelillo15.mast.api.IServerUtils
import es.angelillo15.mast.api.cmd.CommandData
import es.angelillo15.mast.api.cmd.sender.CommandSender
import es.angelillo15.mast.api.config.punishments.Messages
import es.angelillo15.mast.api.punishments.IPunishPlayer
import es.angelillo15.mast.api.punishments.cmd.PunishCommand
import es.angelillo15.mast.api.punishments.cmd.PunishTargetReasonCommand
import es.angelillo15.mast.api.templates.managers.WarnTemplateManager

@CommandData(name = "warn", permission = "mast.warn")
class WarnCMD : PunishTargetReasonCommand(1, Messages.Default.defaultWarnReason()) {
    @Inject
    private lateinit var serverUtils: IServerUtils
    @Inject
    private lateinit var warnTemplateManager: WarnTemplateManager;
    override fun onCommand(sender: IPunishPlayer, target: String, label: String, args: Array<out String>, reason: String) {
        if (args.isEmpty()) {
            sender.sendMessage(Messages.Commands.Warn.usage())
            return
        }

        if (warnTemplateManager.getWarnTemplate(reason) != null)
            sender.tryWarnTemplate(target, reason)
        else
            sender.warn(target, reason)
    }

    override fun onTabComplete(sender: CommandSender, args: Array<String>): List<String> {
        if (args.size == 1) {
            return serverUtils.getOnlinePlayersNames()
        }

        if (args.size == 2) {
            return warnTemplateManager.getWarnTemplates().map { it.id }
        }

        return emptyList()
    }
}
