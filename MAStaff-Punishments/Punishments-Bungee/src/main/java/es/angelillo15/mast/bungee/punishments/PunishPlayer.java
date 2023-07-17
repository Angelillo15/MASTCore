package es.angelillo15.mast.bungee.punishments;

import com.craftmend.storm.Storm;
import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.cmd.sender.CommandSender;
import es.angelillo15.mast.api.config.punishments.Config;
import es.angelillo15.mast.api.models.UserModel;
import es.angelillo15.mast.api.database.PluginConnection;
import es.angelillo15.mast.api.exceptions.PlayerNotBannedException;
import es.angelillo15.mast.api.exceptions.user.PlayerNotOnlineException;
import es.angelillo15.mast.api.managers.UserDataManager;
import es.angelillo15.mast.api.models.WarnModel;
import es.angelillo15.mast.api.punishments.IPunishPlayer;
import es.angelillo15.mast.api.cache.BanCache;
import es.angelillo15.mast.api.config.punishments.Messages;
import es.angelillo15.mast.api.models.BansTable;
import es.angelillo15.mast.api.models.IpBansTable;
import es.angelillo15.mast.api.punishments.events.EventManager;
import es.angelillo15.mast.api.utils.NumberUtils;
import lombok.SneakyThrows;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.BiConsumer;

public class PunishPlayer implements IPunishPlayer {
    private final CommandSender player;
    private final UserModel data;

    public PunishPlayer(CommandSender player) {
        this.player = player;
        this.data = UserDataManager.getUserData(player.getName());
    }

    @Override
    public String getUUID() {
        return player.getUniqueId();
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public CommandSender getPlayer() {
        return player;
    }

    public void getUserModel(BiConsumer<UserModel, UserModel> callback, String target) {
        try {
            UserModel targetUser = UserDataManager.getUserData(target);
            callback.accept(data, targetUser);
        } catch (Exception e) {
            MAStaffInstance.getLogger().debug("Error while getting user data: " + e.getMessage() + " - " + e.getCause() + " - " + target);
            player.sendMessage(Messages.Commands.playerNotFound(target));
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
    @Override
    public void ban(String target, String reason, long until, boolean ipban) {
        Storm storm = PluginConnection.getStorm();

        BansTable bansTable = new BansTable();

        if (BansTable.isPermBanned(target)) {
            player.sendMessage(
                    Messages.Commands.playerAlreadyBanned(target)
            );
            return;
        }

        UserModel data = UserDataManager.getUserData(target);

        bansTable.setUsername(data.getUsername());
        bansTable.setUuid(data.getUUID() == null ? "unknown" : data.getUUID());
        bansTable.setIpban(ipban);
        bansTable.setTime(System.currentTimeMillis());
        bansTable.setReason(reason);
        bansTable.setUntil(until);
        bansTable.setBanned_by_name(player.getName());
        bansTable.setBanned_by_uuid(player.getUniqueId().toString());
        bansTable.setActive(true);

        storm.save(bansTable);

        MAStaffInstance.getLogger().debug("Banned " + target +
                " for " + reason +
                " by " + player.getName() +
                " (" + player.getUniqueId() + ")" +
                " with id " + bansTable.getId() +
                " until " + until + " ipban " + ipban
        );

        if (ipban) {

            if (Objects.equals(data.getLastIp(), UserModel.UNKNOWN)) {
                player.sendMessage(
                        Messages.Commands.playerNotFound(target)
                );
                return;
            }

            IpBansTable ipBansTable = new IpBansTable();

            ipBansTable.setIp(data.getLastIp());
            ipBansTable.setBanId(bansTable.getId());

            storm.save(ipBansTable);
        }

        BanCache.addPunishment(target, bansTable);
        EventManager.getEventManager().sendPlayerBannedEvent(bansTable, player);
    }

    /**
     * Unban the player with the default reason
     * @param target the target of the unban
     */
    @SneakyThrows
    @Override
    public void unban(String target, String reason) {
        BansTable bansTable = null;

        if (!BansTable.isPermBanned(target)) {
            player.sendMessage(
                    Messages.Commands.playerNotBanned(target)
            );

            return;
        }

        bansTable = BansTable.getBan(target);

        if (bansTable == null) {
            return;
        }

        bansTable.unBan(player.getName(), reason, player.getUniqueId());

        MAStaffInstance.getLogger().debug("Unbanned " + target +
                " by " + player.getName() +
                " (" + player.getUniqueId() + ")" +
                " with id " + bansTable.getId()
        );

        BanCache.removePunishment(target);
    }

    /**
     * Kick the player with a custom reason
     * @param target the target of the kick
     * @param reason the reason of the kick
     */
    @Override
    public void kick(String target, String reason) {
        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(target);

        if (player == null) {
            throw new PlayerNotOnlineException("Player " + target + " is not online");
        }

        player.disconnect(new TextComponent(
                Messages.Kick.kickMessage(target, this.player.getName(), reason
        )));
    }

    /**
     * Warn the player with the config default time and a custom reason
     * @param target the target of the warn
     * @param reason the reason of the warn
     */
    @Override
    public void warn(String target, String reason) {
        getUserModel((senderUser, targetUser) -> {
            WarnModel warn = new WarnModel();

            warn.setActive(1);
            warn.setReason(reason);
            warn.setWarnedBy(data.getId());
            warn.setUntil(System.currentTimeMillis() + NumberUtils.parseToMilis(Config.Warn.expireAfter()));
            warn.setTime(System.currentTimeMillis());
            warn.setUser(targetUser.getId());

            try {
                PluginConnection.getStorm().save(warn);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            player.sendMessage(Messages.Commands.Warn.success(target, reason, player.getName()));
        }, target);
    }

    /**
     * UnWarn the player
     * @param target the target of the unwarn
     * @param reason the reason of the unwarn
     */
    @Override
    public void unWarn(String target, String reason) {
        getUserModel((senderUser, targetUser) -> {
            ArrayList<WarnModel> warns = WarnModel.getActiveWarns(targetUser);

            MAStaffInstance.getLogger().debug("Warns: " + warns.size());

            if (warns.isEmpty()) {
                player.sendMessage(
                        Messages.Commands.playerNotWarned(target)
                );
                return;
            }

            WarnModel warn = warns.get(0);

            warn.setActive(0);

            player.sendMessage(Messages.Commands.UnWarn.success(target, reason, player.getName()));

            try {
                PluginConnection.getStorm().save(warn);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, target);
    }

}
