package es.angelillo15.mast.velocity.cmd

import com.google.inject.Inject
import com.velocitypowered.api.command.SimpleCommand
import com.velocitypowered.api.proxy.ConsoleCommandSource
import com.velocitypowered.api.proxy.Player
import es.angelillo15.mast.api.ILogger
import es.angelillo15.mast.api.cmd.Command
import es.angelillo15.mast.api.cmd.sender.CommandSender
import es.angelillo15.mast.api.cmd.sender.VelocityConsoleCommandSender
import es.angelillo15.mast.api.cmd.sender.VelocityPlayerCommandSender

class CustomCommand(private var command: Command, private var permission: String?) : SimpleCommand {
  @Inject
  private lateinit var logger: ILogger;
  override fun execute(invocation: SimpleCommand.Invocation?) {
    val sender: CommandSender = if (invocation!!.source() is ConsoleCommandSource) {
      VelocityConsoleCommandSender()
    } else if (invocation.source() is Player) {
      VelocityPlayerCommandSender(invocation.source() as Player)
    } else {
      logger.error("Unknown command sender: ${invocation.source()}")
      return
    }

    command.onCommand(sender, invocation.alias(), invocation.arguments())
  }

  override fun hasPermission(invocation: SimpleCommand.Invocation?): Boolean {
    if (permission.isNullOrBlank()) return true

    if (invocation is Player) {
      return invocation.hasPermission(permission)
    }

    return true
  }
}