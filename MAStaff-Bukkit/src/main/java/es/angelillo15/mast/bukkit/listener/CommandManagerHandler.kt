package es.angelillo15.mast.bukkit.listener

import com.google.inject.Inject
import com.nookure.mast.api.manager.cmd.CommandBukkitSenderManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class CommandManagerHandler : Listener {
  @Inject
  private lateinit var senderManager: CommandBukkitSenderManager;

  @EventHandler
  fun onJoin(event: PlayerJoinEvent) {
    senderManager.registerSender(event.player)
  }

  @EventHandler
  fun onQuit(event: PlayerQuitEvent) {
    senderManager.unregisterSender(event.player)
  }
}