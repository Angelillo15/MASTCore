package es.angelillo15.mast.bungee.punishments;

import com.craftmend.storm.Storm;
import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.cmd.sender.CommandSender;
import es.angelillo15.mast.api.data.UserData;
import es.angelillo15.mast.api.database.PluginConnection;
import es.angelillo15.mast.api.managers.UserDataManager;
import es.angelillo15.mast.api.punishments.IPunishPlayer;
import es.angelillo15.mast.api.punishments.cache.BanCache;
import es.angelillo15.mast.api.punishments.config.Messages;
import es.angelillo15.mast.api.punishments.data.migrations.BansTable;
import es.angelillo15.mast.api.punishments.data.migrations.IpBansTable;
import lombok.SneakyThrows;

import java.util.Objects;

public class PunishPlayer implements IPunishPlayer {
    private final CommandSender player;

    public PunishPlayer(CommandSender player) {
        this.player = player;
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

        UserData data = UserDataManager.getUserData(target);

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

            if (Objects.equals(data.getLastIP(), UserData.UNKNOWN)) {
                player.sendMessage(
                        Messages.Commands.playerNotFound(target)
                );
                return;
            }

            IpBansTable ipBansTable = new IpBansTable();

            ipBansTable.setIp(data.getLastIP());
            ipBansTable.setBanId(bansTable.getId());

            storm.save(ipBansTable);
        }

        BanCache.addPunishment(target, bansTable);
    }
}
