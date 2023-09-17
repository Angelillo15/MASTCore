package com.nookure.mast.api.cmd

import es.angelillo15.mast.api.Constants
import es.angelillo15.mast.api.TextUtils
import com.nookure.mast.api.cmd.sender.CommandSender

abstract class CommandParent : Command() {
  val subCommands: MutableMap<String?, SubCommand> = HashMap()
  override fun onCommand(sender: CommandSender, label: String, args: Array<String>) {
    if (subCommands.isEmpty())
      registerSubCommands()

    if (args.isEmpty()) {
      sendHelp(sender)
      return
    }
    val subCommand = getSubCommand(args[0])
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

  abstract fun registerSubCommands();

  fun sendHelp(sender: CommandSender?) {
    sender!!.sendMessage(TextUtils.simpleColorize("&a&lMAS&r&ltaff &7- &r&fv${Constants.getVersion()}"))
    for (subCommand in subCommands.values) {
      if (sender.hasPermission(subCommand.permission)) {
        sender.sendMessage(TextUtils.simpleColorize("&a&l> &r" + subCommand.syntax + " &7- &7" + subCommand.description))
      }
    }
  }

  fun registerSubCommand(subCommand: SubCommand) {
    subCommands[subCommand.name] = subCommand
  }

  fun unregisterSubCommand(subCommand: SubCommand) {
    subCommands.remove(subCommand.name)
  }

  fun unregisterSubCommand(name: String?) {
    subCommands.remove(name)
  }

  fun getSubCommand(name: String?): SubCommand? {
    return subCommands[name]
  }

  fun hasSubCommand(name: String?): Boolean {
    return subCommands.containsKey(name)
  }

  companion object {
    var noPermission = "&cYou don't have permission to execute this command."
  }

  fun registerHelpSubCommand(prefix: String) {
    registerSubCommand(object : SubCommand() {
      override val name: String
        get() = "help"
      override val description: String
        get() = "Shows this help message"
      override val syntax: String
        get() = "$prefix help"
      override val permission: String
        get() = ""

      override fun onCommand(sender: CommandSender, label: String, args: Array<String>) {
        sendHelp(sender)
      }
    })
  }
}
