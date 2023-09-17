package es.angelillo15.mast.bukkit.cmd.staff.args

import com.google.inject.Inject
import com.nookure.mast.api.cmd.SubCommand
import com.nookure.mast.api.cmd.sender.CommandSender
import es.angelillo15.mast.api.config.bukkit.Messages
import es.angelillo15.mast.api.managers.StaffManager

class StaffVault : SubCommand() {
  @Inject
  private lateinit var staffManager: StaffManager;
  override val name: String
    get() = "vault"
  override val description: String
    get() = "Open the staff vault"
  override val syntax: String
    get() = "/staff vault"
  override val permission: String
    get() = "mast.staff.vault"

  override fun onCommand(sender: CommandSender, label: String, args: Array<String>) {
    val staffPlayer = staffManager.getStaffPlayer(sender.name) ?: return

    if (staffPlayer.isStaffMode()) {
      sender.sendMessage(Messages.StaffVault.staffVaultInStaffMode())
      return
    }

    staffPlayer.openStaffVault()
  }
}