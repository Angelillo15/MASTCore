package es.angelillo15.mast.bukkit.cmd.staff.args

import com.nookure.mast.api.cmd.SubCommand
import com.nookure.mast.api.cmd.sender.CommandSender
import com.nookure.mast.api.cmd.sender.PlayerCommandSender
import es.angelillo15.mast.bukkit.gui.PunishmentsGUI
import es.angelillo15.mast.bukkit.gui.SelectTargetGUI
import org.bukkit.entity.Player

class Punishments : SubCommand() {
  override val name: String
    get() = "punishments"
  override val description: String
    get() = "Open the punishments GUI"
  override val syntax: String
    get() = "/staff punishments"
  override val permission: String
    get() = "mast.staff.punishments"

  override fun onCommand(sender: CommandSender, label: String, args: Array<String>) {
    if (sender !is PlayerCommandSender) return
    val player = sender.player

    SelectTargetGUI(player).callback { target: Player ->
      PunishmentsGUI(player, target, 1).open()
    }.open()
  }
}