package es.angelillo15.mast.velocity.listeners

import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.PostLoginEvent
import es.angelillo15.mast.api.managers.CommandVelocitySenderManager

class CommandManagerHandler {
  @Inject
  private lateinit var senderManager: CommandVelocitySenderManager;
  @Subscribe
  fun onJoin(event: PostLoginEvent) {
    senderManager.registerSender(event.player)
  }

  @Subscribe
  fun onQuit(event: PostLoginEvent) {
    senderManager.unregisterSender(event.player)
  }
}