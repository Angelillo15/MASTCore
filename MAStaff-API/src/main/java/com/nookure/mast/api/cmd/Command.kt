package com.nookure.mast.api.cmd

import com.nookure.mast.api.cmd.sender.CommandSender

abstract class Command {
  /**
   * This method is called when the command is executed
   *
   * @param sender The sender of the command
   * @param label The label of the command
   * @param args The arguments of the command
   */
  abstract fun onCommand(sender: CommandSender, label: String, args: Array<String>)

  /**
   * This method is called when the command is tab completed
   *
   * @param sender The sender of the command
   * @param args The arguments of the command
   * @return A list of possible completions
   */
  open fun onTabComplete(sender: CommandSender, args: Array<String>): List<String> {
    return ArrayList()
  }
}
