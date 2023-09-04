package es.angelillo15.mast.api.managers

import com.google.inject.Singleton

@Singleton
class PreviousServerManager {
  private val previousServer = HashMap<String, String>()

  fun setPreviousServer(player: String, server: String) {
    previousServer[player] = server
  }

  fun getPreviousServer(player: String): String {
    return previousServer[player] ?: return "Proxy"
  }

  fun removePreviousServer(player: String) {
    previousServer.remove(player)
  }

  fun hasPreviousServer(player: String): Boolean {
    return previousServer.containsKey(player)
  }

  fun clear() {
    previousServer.clear()
  }
}
