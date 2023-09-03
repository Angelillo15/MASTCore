package es.angelillo15.mast.cmd

import com.google.inject.Inject
import es.angelillo15.mast.api.IServerUtils
import es.angelillo15.mast.api.cmd.CommandData
import es.angelillo15.mast.api.cmd.CooldownCommand
import es.angelillo15.mast.api.cmd.sender.CommandSender
import es.angelillo15.mast.api.config.common.CommonConfig
import es.angelillo15.mast.api.config.common.CommonMessages

@CommandData(
    name = "HelpOP",
    permission = "mast.helpop",
)
class HelpOP : CooldownCommand(60, CommonMessages.HelpOp.cooldown(), CommonConfig.Helpop.Cooldown.enabled()) {
    @Inject
    private lateinit var serverUtils: IServerUtils;

    override fun onCooldownCommand(sender: CommandSender, label: String, args: Array<String>) {
        if (args.isEmpty()) {
            sender.sendMessage(CommonMessages.HelpOp.correctUse())
            return
        }

        serverUtils.broadcastMessage(
            CommonMessages.HelpOp.format()
                .replace("{player}", sender.name)
                .replace("{msg}", args.joinToString(" "))
                .replace("{server}", sender.serverName),
            "mast.helpop.receive"
        )

        sender.sendMessage(CommonMessages.HelpOp.message())
    }
}