package es.angelillo15.mast.bukkit.cmd.utils

import es.angelillo15.mast.api.MAStaffInstance
import es.angelillo15.mast.api.cmd.Command
import es.angelillo15.mast.api.cmd.CommandData
import es.angelillo15.mast.api.cmd.sender.BukkitConsoleCommandSender
import es.angelillo15.mast.api.cmd.sender.PlayerCommandSender
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CommandTemplate : org.bukkit.command.Command {
  private val command: Command

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
    val commandSender = if (sender is Player) {
      PlayerCommandSender(sender)
    } else {
      BukkitConsoleCommandSender()
    }

    command.onCommand(commandSender, commandLabel, args);
    return true
  }

  override fun tabComplete(sender: CommandSender, alias: String, args: Array<String>): MutableList<String> {
    val commandSender = if (sender is Player) {
      PlayerCommandSender(sender)
    } else {
      BukkitConsoleCommandSender()
    }

    return command.onTabComplete(commandSender, args).toMutableList()
  }

  companion object {
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

      CommandManager.registerIntoCommandMap(
        CommandTemplate(
          commandData.name,
          command,
          commandData.permission,
          *commandData.aliases
        )
      )
    }
  }
}