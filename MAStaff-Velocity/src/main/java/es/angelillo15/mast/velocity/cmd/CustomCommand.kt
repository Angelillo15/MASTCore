package es.angelillo15.mast.velocity.cmd

import com.velocitypowered.api.command.SimpleCommand
import es.angelillo15.mast.api.ILogger
import es.angelillo15.mast.api.cmd.Command
import es.angelillo15.mast.api.managers.CommandVelocitySenderManager
import java.util.concurrent.CompletableFuture

class CustomCommand(
  private var logger: ILogger,
  private val senderManager: CommandVelocitySenderManager,
  private var command: Command,
  private var permission: String
) : SimpleCommand {
  override fun execute(invocation: SimpleCommand.Invocation) {
    val sender = senderManager.getSender(invocation.source())

    if (sender == null) {
      logger.error("An error occurred while executing a command. Sender is null.")
      return
    }

    if (invocation.source().hasPermission(permission)) {
      command.onCommand(sender, invocation.alias(), invocation.arguments())
    }
  }

  override fun hasPermission(invocation: SimpleCommand.Invocation): Boolean {
    return invocation.source().hasPermission(permission)
  }

  override fun suggestAsync(invocation: SimpleCommand.Invocation): CompletableFuture<MutableList<String>> {
    val sender = senderManager.getSender(invocation.source())

    if (sender == null) {
      logger.error("An error occurred while executing a command. Sender is null.")
      return CompletableFuture.completedFuture(mutableListOf())
    }

    return CompletableFuture.completedFuture(command.onTabComplete(sender, invocation.arguments()).toMutableList())
  }
}