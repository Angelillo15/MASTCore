package es.angelillo15.mast.velocity.listeners

import com.google.common.io.ByteStreams
import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.PluginMessageEvent
import com.velocitypowered.api.proxy.ProxyServer

class CommandBackendExecutor {
  @Inject
  lateinit var server: ProxyServer

  @Subscribe
  fun onCommand(event: PluginMessageEvent) {
    if (event.identifier.id != "mastaff:commands") return

    val input = ByteStreams.newDataInput(event.data)

    val subChannel = input.readUTF()

    if (subChannel != "mast-command") return

    var command = input.readUTF()

    if (command.startsWith("bungee:")) {
      command = command.replace("bungee:", "")
    }

    server.commandManager.executeAsync(server.consoleCommandSource, command)
  }
}