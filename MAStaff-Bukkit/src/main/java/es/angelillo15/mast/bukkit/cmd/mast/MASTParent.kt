package es.angelillo15.mast.bukkit.cmd.mast

import com.google.inject.Inject
import es.angelillo15.mast.api.cmd.CommandData
import es.angelillo15.mast.api.cmd.CommandParent
import es.angelillo15.mast.api.cmd.sender.CommandSender
import es.angelillo15.mast.api.utils.MAStaffInject

@CommandData(
  name = "mast",
  aliases = ["mastaff", "mastaff-bukkit"],
  description = "MAStaff-Bukkit main command",
  permission = "mast.admin",
)
class MASTParent : CommandParent() {
  @Inject
  private lateinit var instance: MAStaffInject

  override fun registerSubCommands() {
    registerSubCommand(instance.injector.getInstance(DumpARG::class.java))
    registerSubCommand(instance.injector.getInstance(ReloadARG::class.java))
    registerSubCommand(instance.injector.getInstance(DebugARG::class.java))
    registerHelpSubCommand("/mast")
  }

  override fun onTabComplete(sender: CommandSender, args: Array<String>): List<String> {
    if (args.size == 1) {
      return subCommands.keys.toList().requireNoNulls().filter { it.startsWith(args[0]) }
    }

    return emptyList()
  }
}