package es.angelillo15.mast.bukkit.cmd.staff

import com.google.inject.Inject
import es.angelillo15.mast.api.TextUtils
import com.nookure.mast.api.cmd.CommandData
import com.nookure.mast.api.cmd.CommandParent
import com.nookure.mast.api.cmd.sender.CommandSender
import es.angelillo15.mast.api.utils.MAStaffInject
import es.angelillo15.mast.bukkit.cmd.staff.args.Punishments
import es.angelillo15.mast.bukkit.cmd.staff.args.StaffList
import es.angelillo15.mast.bukkit.cmd.staff.args.StaffMode
import es.angelillo15.mast.bukkit.cmd.staff.args.StaffVault

@CommandData(
  name = "staff",
  aliases = ["mod", "modmode", "staffmode"],
  permission = "mast.staff"
)
class StaffParent : CommandParent() {
  @Inject
  private lateinit var inject: MAStaffInject;

  override fun onCommand(sender: CommandSender, label: String, args: Array<String>) {
    if (subCommands.isEmpty())
      registerSubCommands()

    if (sender.isConsole) {
      sender.sendMessage("<red>Only players can execute this command.")
      return
    }

    val subCommand = if (args.isEmpty()) {
      getSubCommand("mode")
    } else {
      getSubCommand(args[0])
    }

    if (subCommand == null) {
      sendHelp(sender)
      return
    }

    if (!sender.hasPermission(subCommand.permission)) {
      sender.sendMessage(TextUtils.simpleColorize(noPermission))
      return
    }

    subCommand.onCommand(sender, label, args)
  }

  override fun registerSubCommands() {
    registerHelpSubCommand("/staff")
    registerSubCommand(inject.injector.getInstance(Punishments::class.java))
    registerSubCommand(inject.injector.getInstance(StaffList::class.java))
    registerSubCommand(inject.injector.getInstance(StaffVault::class.java))
    registerSubCommand(inject.injector.getInstance(StaffMode::class.java))
  }

  override fun onTabComplete(sender: CommandSender, args: Array<String>): List<String> {
    if (args.size == 1) {
      return subCommands.keys.toList().requireNoNulls().filter { it.startsWith(args[0]) }
    }

    return emptyList()
  }
}