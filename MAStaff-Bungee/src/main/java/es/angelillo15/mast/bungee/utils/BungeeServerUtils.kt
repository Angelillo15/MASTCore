package es.angelillo15.mast.bungee.utils

import com.google.inject.Singleton
import es.angelillo15.mast.api.IServerUtils
import es.angelillo15.mast.api.TextUtils
import es.angelillo15.mast.api.thread.execute
import net.md_5.bungee.api.ProxyServer
import java.util.*

@Singleton
class BungeeServerUtils : IServerUtils {
    override fun isOnline(uuid: UUID): Boolean {
        return ProxyServer.getInstance().getPlayer(uuid) != null
    }

    override fun isOnline(name: String): Boolean {
        return ProxyServer.getInstance().getPlayer(name) != null
    }

    override fun getIP(uuid: UUID): String {
        return ProxyServer.getInstance().getPlayer(uuid).address.address.hostAddress
    }

    override fun getIP(name: String): String {
        return ProxyServer.getInstance().getPlayer(name).address.address.hostAddress
    }

    override fun getUUID(name: String): UUID {
        return ProxyServer.getInstance().getPlayer(name).uniqueId
    }

    override fun getName(uuid: UUID): String {
        return ProxyServer.getInstance().getPlayer(uuid).name
    }

    override fun broadcastMessage(message: String, permission: String) {
        execute {
            ProxyServer.getInstance().players.forEach { player ->
                if (!player.hasPermission(permission)) return@forEach

                TextUtils.getAudience(player).sendMessage(TextUtils.toComponent(message))
            }
        }
    }
}
