package es.angelillo15.mast.api.punishments

import com.google.inject.Inject
import es.angelillo15.mast.api.IServerUtils
import es.angelillo15.mast.api.MAStaffInstance
import es.angelillo15.mast.api.cache.BanCache
import es.angelillo15.mast.api.cmd.sender.CommandSender
import es.angelillo15.mast.api.config.punishments.Config
import es.angelillo15.mast.api.config.punishments.Messages
import es.angelillo15.mast.api.database.PluginConnection
import es.angelillo15.mast.api.exceptions.user.PlayerNotOnlineException
import es.angelillo15.mast.api.managers.LegacyUserDataManager
import es.angelillo15.mast.api.models.BansTable
import es.angelillo15.mast.api.models.IpBansTable
import es.angelillo15.mast.api.models.UserModel
import es.angelillo15.mast.api.models.WarnModel
import es.angelillo15.mast.api.punishments.events.EventManager
import es.angelillo15.mast.api.templates.managers.BanTemplatesManager
import es.angelillo15.mast.api.utils.NumberUtils
import lombok.SneakyThrows
import java.sql.SQLException
import java.util.function.BiConsumer

class PunishPlayer : IPunishPlayer {
    @Inject
    private lateinit var serverUtils: IServerUtils
    @Inject
    private lateinit var banTemplatesManager: BanTemplatesManager
    private lateinit var player: CommandSender
    private lateinit var data: UserModel
    fun setPlayer(player: CommandSender): PunishPlayer {
        this.player = player
        data = LegacyUserDataManager.getUserData(player.getName())
        return this
    }

    override fun getUUID(): String {
        return player.getUniqueId()
    }

    override fun getName(): String {
        return player.getName()
    }

    override fun getPlayer(): CommandSender {
        return player
    }

    private fun getUserModel(callback: BiConsumer<UserModel?, UserModel>, target: String) {
        try {
            val targetUser = LegacyUserDataManager.getUserData(target)
            callback.accept(data, targetUser)
        } catch (e: Exception) {
            MAStaffInstance.getLogger()
                .debug("Error while getting user data: " + e.message + " - " + e.cause + " - " + target)
            player.sendMessage(Messages.Commands.playerNotFound(target))
        }
    }

    /**
     * Ban the player for a certain amount of time with the default reason
     * @param target the target of the ban
     * @param reason the reason of the ban
     * @param until the time in milliseconds when the ban will expire
     * @param ipban if the ban should be an ipban
     */
    @SneakyThrows
    override fun ban(target: String, reason: String, until: Long, ipban: Boolean) {
        val storm = PluginConnection.getStorm()
        val bansTable = BansTable()
        if (BansTable.isPermBanned(target)) {
            player.sendMessage(
                Messages.Commands.playerAlreadyBanned(target)
            )
            return
        }
        val data = LegacyUserDataManager.getUserData(target)
        bansTable.username = data.username
        bansTable.uuid = if (data.uuid == null) "unknown" else data.uuid
        bansTable.ipban = ipban
        bansTable.time = System.currentTimeMillis()
        bansTable.reason = reason
        bansTable.until = until
        bansTable.banned_by_name = player.getName()
        bansTable.banned_by_uuid = player.getUniqueId().toString()
        bansTable.active = true
        storm.save(bansTable)
        MAStaffInstance.getLogger().debug(
            "Banned " + target +
                    " for " + reason +
                    " by " + player.getName() +
                    " (" + player.getUniqueId() + ")" +
                    " with id " + bansTable.id +
                    " until " + until + " ipban " + ipban
        )
        if (ipban) {
            if (data.lastIp == UserModel.UNKNOWN) {
                player.sendMessage(
                    Messages.Commands.playerNotFound(target)
                )
                return
            }
            val ipBansTable = IpBansTable()
            ipBansTable.ip = data.lastIp
            ipBansTable.banId = bansTable.id
            storm.save(ipBansTable)
        }
        BanCache.addPunishment(target, bansTable)
        EventManager.getEventManager().sendPlayerBannedEvent(bansTable, player)
    }

    /**
     * Unban the player with the default reason
     * @param target the target of the unban
     */
    @SneakyThrows
    override fun unban(target: String, reason: String) {
        var bansTable: BansTable? = null
        if (!BansTable.isPermBanned(target)) {
            player.sendMessage(
                Messages.Commands.playerNotBanned(target)
            )
            return
        }
        bansTable = BansTable.getBan(target)
        if (bansTable == null) {
            return
        }
        bansTable.unBan(player.getName(), reason, player.getUniqueId())
        MAStaffInstance.getLogger().debug(
            "Unbanned " + target +
                    " by " + player.getName() +
                    " (" + player.getUniqueId() + ")" +
                    " with id " + bansTable.id
        )
        BanCache.removePunishment(target)
    }

    /**
     * Kick the player with a custom reason
     * @param target the target of the kick
     * @param reason the reason of the kick
     */
    override fun kick(target: String, reason: String) {
        if (!serverUtils.isOnline(target)) {
            throw PlayerNotOnlineException("Player $target is not online")
        }
        Messages.Kick.kickMessage(target, player.getName(), reason)
    }

    /**
     * Warn the player with the config default time and a custom reason
     * @param target the target of the warn
     * @param reason the reason of the warn
     */
    override fun warn(target: String, reason: String, template: String) {
        getUserModel({ senderUser: UserModel?, targetUser: UserModel ->
            val warn = WarnModel()
            warn.active = 1
            warn.reason = reason
            warn.warnedBy = data.id
            warn.until = System.currentTimeMillis() + NumberUtils.parseToMilis(Config.Warn.expireAfter())
            warn.time = System.currentTimeMillis()
            warn.user = targetUser.id
            try {
                PluginConnection.getStorm().save(warn)
            } catch (e: SQLException) {
                throw RuntimeException(e)
            }
            player.sendMessage(Messages.Commands.Warn.success(target, reason, player!!.getName()))
        }, target)
    }

    /**
     * UnWarn the player
     * @param target the target of the unwarn
     * @param reason the reason of the unwarn
     */
    override fun unWarn(target: String, reason: String) {
        getUserModel({ _: UserModel?, targetUser: UserModel? ->
            val warns = WarnModel.getActiveWarns(targetUser)
            MAStaffInstance.getLogger().debug("Warns: " + warns.size)
            if (warns.isEmpty()) {
                player.sendMessage(
                    Messages.Commands.playerNotWarned(target)
                )
                return@getUserModel
            }
            val warn = warns[0]
            warn.active = 0
            player.sendMessage(Messages.Commands.UnWarn.success(target, reason, player!!.getName()))
            try {
                PluginConnection.getStorm().save(warn)
            } catch (e: SQLException) {
                throw RuntimeException(e)
            }
        }, target)
    }

    override fun tryBanTemplate(target: String, template: String): Boolean {
        val banTemplate = banTemplatesManager.getBanTemplate(template) ?: return false

        if (player.hasPermission(banTemplate.permission)) {
            ban(target, banTemplate)
            return true
        }

        return false
    }
}
