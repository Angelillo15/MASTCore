package es.angelillo15.mast.bungee.listener

import com.google.inject.Inject
import com.nookure.mast.api.manager.cmd.CommandBungeeSenderManager
import net.md_5.bungee.api.event.PlayerDisconnectEvent
import net.md_5.bungee.api.event.PostLoginEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler

class CommandManagerHandler : Listener {
  @Inject
  private lateinit var senderManager: CommandBungeeSenderManager;
  @EventHandler
  fun onJoin(event: PostLoginEvent) {
    senderManager.registerSender(event.player)
  }

  @EventHandler
  fun onQuit(event: PlayerDisconnectEvent) {
    senderManager.unregisterSender(event.player)
  }
}