package es.angelillo15.mast.api.punishments.data.sql;

import es.angelillo15.mast.api.database.PluginConnection;
import es.angelillo15.mast.api.punishments.data.AbstractDataManager;
import es.angelillo15.mast.api.punishments.models.BanModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CommonSQL extends AbstractDataManager {
    @Override
    public void migrations() {
        super.migrations();
    }

    @Override
    public void ban(String uuid, String ip, String reason, String banned_by_uuid, String banned_by_name, boolean active, long time, long until, boolean ipban) {
        try (PreparedStatement statement =
                     PluginConnection.getConnection().prepareStatement(
                             "INSERT INTO `mastaff_punishments_bans` " +
                                     "(`UUID`, `IP`, `reason`, `banned_by_uuid`, `banned_by_name`, `active`, `time`, `until`, `ipban`) " +
                                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);")
        ){
            statement.setString(1, uuid);
            statement.setString(2, ip);
            statement.setString(3, reason);
            statement.setString(4, banned_by_uuid);
            statement.setString(5, banned_by_name);
            statement.setBoolean(6, active);
            statement.setLong(7, time);
            statement.setLong(8, until);
            statement.setBoolean(9, ipban);

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isPermBanned(UUID uuid) {
        return isPermBanned("UUID", uuid.toString());
    }

    @Override
    public boolean isPermBanned(String uuid) {
        return isPermBanned("UUID", uuid);
    }

    @Override
    public boolean isPermBanned(String where, String value) {
        try(PreparedStatement statement = PluginConnection.getConnection().prepareStatement(
                "SELECT * FROM `mastaff_punishments_bans` WHERE `" + where + "` = ? AND `until` = 0 AND `active` = 1;")) {
            statement.setString(1, value);
            return statement.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BanModel getBan(UUID uuid) {
        try(PreparedStatement statement = PluginConnection.getConnection().prepareStatement(
                "SELECT * FROM `mastaff_punishments_bans` WHERE `UUID` = ? AND `active` = 1;")) {
            statement.setString(1, uuid.toString());
            ResultSet rs = statement.executeQuery();

            BanModel ban = new BanModel();

            if (!rs.next()) return null;

            ban.setId(rs.getInt("id"));
            ban.setUuid(UUID.fromString(rs.getString("UUID")));
            ban.setReason(rs.getString("reason"));
            ban.setBannedByUUID(rs.getString("banned_by_uuid"));
            ban.setBannedBy(rs.getString("banned_by_name"));
            ban.setRemovedBy(rs.getString("removed_by_name"));
            ban.setRemovedByUUID(rs.getString("removed_by_uuid"));
            ban.setRemovedByDate(rs.getLong("removed_by_date"));
            ban.setActive(rs.getBoolean("active"));
            ban.setTime(rs.getLong("time"));
            ban.setUntil(rs.getLong("until"));
            ban.setIpBan(rs.getBoolean("ipban"));

            return ban;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
