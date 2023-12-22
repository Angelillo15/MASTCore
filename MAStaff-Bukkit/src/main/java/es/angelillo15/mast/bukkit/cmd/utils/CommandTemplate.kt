package es.angelillo15.mast.bukkit.cmd.utils

import es.angelillo15.mast.api.MAStaffInstance
import com.nookure.mast.api.cmd.Command
import com.nookure.mast.api.cmd.CommandData
import com.nookure.mast.api.manager.cmd.CommandBukkitSenderManager
import es.angelillo15.mast.api.config.bukkit.ConfigLoader
import es.angelillo15.mast.bukkit.MAStaff
import org.bukkit.command.CommandSender
import java.util.concurrent.ConcurrentHashMap

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

    command.onCommand(pluginSender, commandLabel, args);
    return true
  }

  override fun tabComplete(sender: CommandSender, alias: String, args: Array<String>): MutableList<String> {
    val commandSender = senderManager.getSender(sender) ?: return mutableListOf()

    return command.onTabComplete(commandSender, args).toMutableList()
  }

  companion object {
    val commandMap = ConcurrentHashMap<Command, CommandTemplate>()
    fun registerCommand(command: Command) {
      MAStaffInstance.getLogger().debug("Registering command ${command.javaClass.simpleName}...")
      var commandData: CommandData?

      try {
        commandData = command.javaClass.getDeclaredAnnotation(CommandData::class.java)
      } catch (e: Exception) {
        e.printStackTrace()
        return
      }

      val commands = ConfigLoader.getCommands().config

      if (commands.contains("Commands.${commandData.name}")) {
        MAStaffInstance.getLogger().debug("Command ${command.javaClass.simpleName} has been found in config")

        if (!commands.getBoolean("Commands.${commandData.name}.enabled")) {
          MAStaffInstance.getLogger().debug("Command ${command.javaClass.simpleName} has been disabled from config")
          return
        }

        commandData = CommandData(
            name = commands.getString("Commands.${commandData.name}.name"),
            aliases = commands.getStringList("Commands.${commandData.name}.aliases").toTypedArray(),
            permission = commands.getString("Commands.${commandData.name}.permission"),
            usage = commands.getString("Commands.${commandData.name}.usage"),
            description = commands.getString("Commands.${commandData.name}.description")
        )
        MAStaffInstance.getLogger().debug("Command ${command.javaClass.simpleName} has been edited from config")
      }

      if (commandData != null) {
        insertIntoCommandFileIfNot(commandData)
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
      val bukkitCommand = commandMap[command] ?: return
      commandMap.remove(command)
      CommandManager.getCommandMap()?.let { bukkitCommand.unregister(it) }
    }

    fun insertIntoCommandFileIfNot(commandData: CommandData) {
      val commands = ConfigLoader.getCommands().config

      if (commands.contains("Commands.${commandData.name}")) {
        MAStaffInstance.getLogger().debug("Command ${commandData.name} has been found in config")
        return
      }

      commands.set("Commands.${commandData.name}.name", commandData.name)
      commands.set("Commands.${commandData.name}.aliases", commandData.aliases)
      commands.set("Commands.${commandData.name}.permission", commandData.permission)
      commands.set("Commands.${commandData.name}.usage", commandData.usage)
      commands.set("Commands.${commandData.name}.description", commandData.description)
      commands.set("Commands.${commandData.name}.enabled", true)

      ConfigLoader.getCommands().config.save()
    }
  }
}