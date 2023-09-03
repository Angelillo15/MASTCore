package es.angelillo15.mast.velocity.listeners

import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.player.ServerConnectedEvent
import com.velocitypowered.api.proxy.Player
import es.angelillo15.mast.api.ILogger
import es.angelillo15.mast.api.data.DataManager
import es.angelillo15.mast.api.managers.UserDataManager
import es.angelillo15.mast.api.thread.execute

class OnPlayerJoin {
  @Inject
  lateinit var logger: ILogger

  @Inject
  lateinit var userDataManager: UserDataManager;

  @Subscribe
  fun onPlayerJoin(event: ServerConnectedEvent) {
    val player = event.player

    val userExists = DataManager.getDataManager().userExists(player.uniqueId);

    userDataManager.removeUserData(player.uniqueId.toString())
    userDataManager.removeUserData(player.username)

    if (userExists) execute {
      userExistsUpdate(player)
    } else execute {
      userNotExistsUpdate(player)
    }

    logger.debug("User ${player.username} joined the server and was registered into the database")
  }

  private fun userExistsUpdate(player: Player) {
    val userModel = DataManager.getDataManager().getUserData(player.uniqueId)

    DataManager.getDataManager()
            .updateIP(player.uniqueId.toString(), player.remoteAddress.hostName)

    DataManager.getDataManager().updateLastLogin(player.uniqueId.toString(), System.currentTimeMillis().toString())

    if (userModel.username != player.username) {
      DataManager.getDataManager().updateUsername(player.uniqueId.toString(), player.username)
    }
  }

  private fun userNotExistsUpdate(player: Player) {
    DataManager.getDataManager().insertUserData(
            player.uniqueId.toString(),
            player.username,
            player.remoteAddress.hostName,
            player.remoteAddress.hostName,
            System.currentTimeMillis().toString(),
            System.currentTimeMillis().toString()
    )
  }
}