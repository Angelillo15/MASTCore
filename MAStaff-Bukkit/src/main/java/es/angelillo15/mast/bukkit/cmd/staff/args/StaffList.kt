package es.angelillo15.mast.bukkit.cmd.staff.args

import com.nookure.mast.api.cmd.SubCommand
import com.nookure.mast.api.cmd.sender.CommandSender
import com.nookure.mast.api.cmd.sender.PlayerCommandSender
import es.angelillo15.mast.bukkit.gui.StaffListGUI

class StaffList : SubCommand() {
  override val name: String
    get() = "list"
  override val description: String
    get() = "Show the staff list"
  override val syntax: String
    get() = "/staff list"
  override val permission: String
    get() = "mast.staff.list"

  override fun onCommand(sender: CommandSender, label: String, args: Array<String>) {
    if (sender !is PlayerCommandSender) return
    StaffListGUI(sender.player).open()
  }
}