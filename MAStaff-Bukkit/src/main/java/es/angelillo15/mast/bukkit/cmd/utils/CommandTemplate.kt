package es.angelillo15.mast.bukkit.cmd.utils

import es.angelillo15.mast.api.MAStaffInstance
import com.nookure.mast.api.cmd.Command
import com.nookure.mast.api.cmd.CommandData
import com.nookure.mast.api.manager.cmd.CommandBukkitSenderManager
import es.angelillo15.mast.bukkit.MAStaff
import org.bukkit.command.CommandSender

class CommandTemplate : org.bukkit.command.Command {
  private val command: Command
  private val senderManager: CommandBukkitSenderManager = MAStaff
    .getPlugin()
    .injector
    .getInstance(CommandBukkitSenderManager::class.java)

  constructor(name: String, command: Command) : super(name) {
    this.command = command
  }

  constructor(name: String, command: Command, permission: String) : super(name) {
    this.command = command
    setPermission(permission)
  }

  constructor(name: String, command: Command, permission: String, vararg aliases: String) : super(name) {
    this.command = command
    setPermission(permission)
    setAliases(aliases.toList())
  }

  override fun execute(sender: CommandSender, commandLabel: String, args: Array<String>): Boolean {
    val pluginSender = senderManager.getSender(sender)

    command.onCommand(pluginSender!!, commandLabel, args);
    return true
  }

  override fun tabComplete(sender: CommandSender, alias: String, args: Array<String>): MutableList<String> {
    val commandSender = senderManager.getSender(sender) ?: return mutableListOf()

    return command.onTabComplete(commandSender, args).toMutableList()
  }

  companion object {
    private val commandMap = mutableMapOf<Command, CommandTemplate>()
    fun registerCommand(command: Command) {
      val commandData: CommandData?

      try {
        commandData = command.javaClass.getDeclaredAnnotation(CommandData::class.java)
      } catch (e: Exception) {
        e.printStackTrace()
        return
      }

      if (commandData == null) {
        MAStaffInstance.getLogger().warn("Command ${command.javaClass.simpleName} doesn't have CommandData annotation")
        return
      }

      if (commandData.aliases.isEmpty() && commandData.permission.isEmpty()) {
        CommandManager.registerIntoCommandMap(CommandTemplate(commandData.name, command))
      }

      if (commandData.aliases.isEmpty() && commandData.permission.isNotEmpty()) {
        CommandManager.registerIntoCommandMap(CommandTemplate(commandData.name, command, commandData.permission))
      }
      val commandTemplate = CommandTemplate(
        commandData.name,
        command,
        commandData.permission,
        *commandData.aliases
      )

      CommandManager.registerIntoCommandMap(commandTemplate)

      commandMap[command] = commandTemplate
    }

    fun unregisterCommand(command: Command) {
      val commandTemplate = commandMap[command] ?: return
      commandMap.remove(command)
    }
  }
}