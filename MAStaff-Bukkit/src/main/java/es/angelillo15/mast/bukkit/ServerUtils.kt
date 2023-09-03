package es.angelillo15.mast.bukkit

import com.google.inject.Singleton
import es.angelillo15.mast.api.IServerUtils
import es.angelillo15.mast.api.TextUtils
import es.angelillo15.mast.api.thread.execute
import org.bukkit.Bukkit
import java.util.*

@Singleton
class ServerUtils : IServerUtils {
  override fun isOnline(uuid: UUID): Boolean {
    val player = Bukkit.getPlayer(uuid);

    return player != null && player.isOnline
  }

  override fun isOnline(name: String): Boolean {
    val player = Bukkit.getPlayer(name);

    return player != null && player.isOnline
  }

  override fun getIP(uuid: UUID): String {
    val player = Bukkit.getPlayer(uuid);

    return player?.address?.hostString ?: ""
  }

  override fun getIP(name: String): String {
    val player = Bukkit.getPlayer(name);

    return player?.address?.hostString ?: ""
  }

  override fun getUUID(name: String): UUID {
    val player = Bukkit.getPlayer(name);

    return player?.uniqueId ?: UUID.randomUUID()
  }

  override fun getName(uuid: UUID): String {
    val player = Bukkit.getPlayer(uuid);

    return player?.name ?: ""
  }

  override fun broadcastMessage(message: String, permission: String) {
    execute {
      Bukkit.getOnlinePlayers().forEach { player ->
        if (!player.hasPermission(permission)) return@forEach

        TextUtils.getAudience(player).sendMessage(TextUtils.toComponent(message))
      }
    }
  }

  override fun kickPlayer(uuid: UUID, reason: String): Boolean {
    return try {
      Bukkit.getPlayer(uuid)!!.kickPlayer(TextUtils.colorize(reason))
      true
    } catch (e: Exception) {
      false
    }
  }

  override fun getOnlinePlayersNames(): List<String> {
    return Bukkit.getOnlinePlayers().map { it.name }
  }

  override fun getOnlinePlayersUUIDs(): List<UUID> {
    return Bukkit.getOnlinePlayers().map { it.uniqueId }
  }

  override fun executeCommand(command: String) {
    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command)
  }
}