package es.angelillo15.mast.api.punishments.cmd.ban

import es.angelillo15.mast.api.TextUtils
import com.nookure.mast.api.cmd.CommandData
import es.angelillo15.mast.api.config.punishments.Config
import es.angelillo15.mast.api.config.punishments.Messages
import es.angelillo15.mast.api.punishments.IPunishPlayer
import es.angelillo15.mast.api.punishments.cmd.PunishTargetReasonCommand
import es.angelillo15.mast.api.utils.NumberUtils

@CommandData(
        name = "tempipban",
        permission = "mast.ban.tempipban",
        aliases = ["tempipban", "tempipb"],
        usage = "&cUsage: &7/tempipban <player> <time> <reason>",
        description = "Temporarily IP ban a player"
)
class TempIPBanCMD : PunishTargetReasonCommand(2, Messages.Default.defaultBanReason()) {

  override fun onCommand(
          sender: IPunishPlayer,
          target: String,
          label: String,
          args: Array<out String>,
          reason: String
  ) {
    if (args.size < 2) {
      sender.sendMessage(Messages.Commands.TempIpBan.usage())
      return
    }

    val time: Long = try {
      System.currentTimeMillis() + NumberUtils.parseToMilis(args[1])
    } catch (e: NumberFormatException) {
      sender.sendMessage("Invalid time")
      return
    }

    sender.ban(target, reason, time, true)
    sender.sendMessage(
            Messages.Commands.TempIpBan.success(
                    target,
                    TextUtils.formatDate(time, Config.dateFormat()),
                    reason,
                    sender.name
            )
    )
  }

}
