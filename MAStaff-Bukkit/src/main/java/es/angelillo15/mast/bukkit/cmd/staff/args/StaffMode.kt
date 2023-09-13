package es.angelillo15.mast.bukkit.cmd.staff.args

import com.google.inject.Inject
import es.angelillo15.mast.api.cmd.SubCommand
import es.angelillo15.mast.api.cmd.sender.CommandSender
import es.angelillo15.mast.api.cmd.sender.PlayerCommandSender
import es.angelillo15.mast.api.managers.StaffManager

class StaffMode : SubCommand() {
  @Inject
  private lateinit var staffManager: StaffManager;
  override val name: String
    get() = "mode"
  override val description: String
    get() = "Enable or disable staff mode"
  override val syntax: String
    get() = "/staff mode"
  override val permission: String
    get() = "mast.staff"

  override fun onCommand(sender: CommandSender, label: String, args: Array<String>) {
    if (sender !is PlayerCommandSender) {
      return
    }

    val player = sender.player;

    val staff = staffManager.getStaffPlayer(player) ?: return

    staff.toggleStaffMode()
  }
}