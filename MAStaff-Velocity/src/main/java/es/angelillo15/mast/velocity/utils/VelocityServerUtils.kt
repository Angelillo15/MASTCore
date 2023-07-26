package es.angelillo15.mast.velocity.utils

import com.google.inject.Inject
import com.google.inject.Singleton
import com.velocitypowered.api.proxy.ProxyServer
import es.angelillo15.mast.api.ILogger
import es.angelillo15.mast.api.IServerUtils
import es.angelillo15.mast.api.TextUtils
import es.angelillo15.mast.api.thread.execute
import java.util.*

@Singleton
class VelocityServerUtils : IServerUtils {
    @Inject
    lateinit var proxy: ProxyServer;
    @Inject
    lateinit var logger: ILogger

    override fun isOnline(uuid: UUID): Boolean {
        return proxy.getPlayer(uuid).isPresent
    }

    override fun isOnline(name: String): Boolean {
       return proxy.getPlayer(name).isPresent
    }

    override fun getIP(uuid: UUID): String {
        return proxy.getPlayer(uuid).get().remoteAddress.hostString
    }

    override fun getIP(name: String): String {
        return proxy.getPlayer(name).get().remoteAddress.hostString
    }

    override fun getUUID(name: String): UUID {
        return proxy.getPlayer(name).get().uniqueId
    }

    override fun getName(uuid: UUID): String {
        return proxy.getPlayer(uuid).get().username
    }

    override fun broadcastMessage(message: String, permission: String) {
        execute {
            val players = proxy.allPlayers.filter { player -> player.hasPermission(permission) }

            players.forEach { player ->
                player.sendMessage(TextUtils.toComponent(message))
            }

            logger.info(message)
        }

    }
}
