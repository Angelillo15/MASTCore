package es.angelillo15.mast.api.punishments.data.sql;

import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.database.PluginConnection;
import es.angelillo15.mast.api.models.IPBanModel;
import es.angelillo15.mast.api.punishments.data.AbstractDataManager;
import es.angelillo15.mast.api.models.BanModel;
import lombok.SneakyThrows;

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
    public void ban(String uuid, String username, String ip, String reason, String banned_by_uuid, String banned_by_name, boolean active, long time, long until, boolean ipban) {
        try (PreparedStatement statement =
                     PluginConnection.getConnection().prepareStatement(
                             "INSERT INTO `mastaff_punishments_bans` " +
                                     "(`UUID`, `reason`, `banned_by_uuid`, `banned_by_name`, `active`, `time`, `until`, `ipban`, `USERNAME`) " +
                                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);")
        ) {
            statement.setString(1, uuid);
            statement.setString(2, reason);
            statement.setString(3, banned_by_uuid);
            statement.setString(4, banned_by_name);
            statement.setBoolean(5, active);
            statement.setLong(6, time);
            statement.setLong(7, until);
            statement.setBoolean(8, ipban);
            statement.setString(9, username);

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
    public boolean isPermBanned(String username) {
        return isPermBanned("USERNAME", username);
    }

    @Override
    public boolean isPermBanned(String where, String value) {
        try (PreparedStatement statement = PluginConnection.getConnection().prepareStatement(
                "SELECT * FROM `mastaff_punishments_bans` WHERE `" + where + "` = ? AND `until` = 0 AND `active` = 1;")) {
            statement.setString(1, value);
            return statement.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isTempBanned(UUID uuid) {
        return isTempBanned("UUID", uuid.toString());
    }

    @Override
    public boolean isTempBanned(String username) {
        return isTempBanned("USERNAME", username);
    }


    @Override
    public boolean isTempBanned(String where, String value) {
        try (PreparedStatement statement = PluginConnection.getConnection().prepareStatement(
                "SELECT * FROM `mastaff_punishments_bans` WHERE `" + where + "` = ? AND `active` = 1;")) {
            statement.setString(1, value);
            return statement.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BanModel getBan(UUID uuid) {
        try (PreparedStatement statement = PluginConnection.getConnection().prepareStatement(
                "SELECT * FROM `mastaff_punishments_bans` WHERE `UUID` = ? AND `active` = 1;")) {
            statement.setString(1, uuid.toString());
            ResultSet rs = statement.executeQuery();

            return getBan(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BanModel getBan(String username) {
        try (PreparedStatement statement = PluginConnection.getConnection().prepareStatement(
                "SELECT * FROM `mastaff_punishments_bans` WHERE `USERNAME` = ? AND `active` = 1;")) {
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();

            return getBan(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BanModel getBan(int id) {
        try (PreparedStatement statement = PluginConnection.getConnection().prepareStatement(
                "SELECT * FROM `mastaff_punishments_bans` WHERE `ID` = ?")) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            return getBan(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BanModel getBan(int id, boolean active) {
        try (PreparedStatement statement = PluginConnection.getConnection().prepareStatement(
                "SELECT * FROM `mastaff_punishments_bans` WHERE `ID` = ? AND `active` = ?;")) {
            statement.setInt(1, id);
            statement.setBoolean(2, active);
            ResultSet rs = statement.executeQuery();

            return getBan(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    public BanModel getBan(ResultSet rs) {
        BanModel ban = new BanModel();

        if (!rs.next()) {
            MAStaffInstance.getLogger().debug("No ban found");
            return null;
        }

        ban.setId(rs.getInt("id"));
        try {
            ban.setUuid(UUID.fromString(rs.getString("UUID")));
        } catch (Exception e) {
            MAStaffInstance.getLogger().debug("UUID is null setting up");
        }
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
        ban.setUsername(rs.getString("USERNAME"));

        return ban;
    }

    @Override
    public void setBanActive(String username, boolean active, String reason) {
        try (PreparedStatement statement = PluginConnection.getConnection().prepareStatement(
                "UPDATE `mastaff_punishments_bans` SET `active` = ?, `removed_by_reason` = ? WHERE `USERNAME` = ? AND `active` = 1;")
        ) {
            statement.setBoolean(1, active);
            statement.setString(2, reason);
            statement.setString(3, username);

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void setUUID(String username, UUID uuid) {
        try (PreparedStatement statement = PluginConnection.getConnection().prepareStatement(
                "UPDATE `mastaff_punishments_bans` SET `UUID` = ? WHERE `USERNAME` = ?;")
        ) {
            statement.setString(1, uuid.toString());
            statement.setString(2, username);

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setUsername(UUID uuid, String username) {
        try (PreparedStatement statement = PluginConnection.getConnection().prepareStatement(
                "UPDATE `mastaff_punishments_bans` SET `USERNAME` = ? WHERE `USERNAME` = ?;")
        ) {
            statement.setString(1, username);
            statement.setString(2, uuid.toString());

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isBanned(String username) {
        return getBan(username) != null;
    }

    @Override
    public IPBanModel getIPBan(String ip) {
        try (PreparedStatement statement = PluginConnection.getConnection().prepareStatement(
                "SELECT * FROM `mastaff_punishments_ips_banned` WHERE `IP` = ?;")) {
            statement.setString(1, ip);
            ResultSet rs = statement.executeQuery();

            return getIPBan(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public IPBanModel getIPBan(int id) {
        try (PreparedStatement statement = PluginConnection.getConnection().prepareStatement(
                "SELECT * FROM `mastaff_punishments_ips_banned` WHERE `ban_id` = ?;")) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            return getIPBan(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    public IPBanModel getIPBan(ResultSet rs) {
        if (!rs.next()) {
            return null;
        }

        IPBanModel ban = new IPBanModel();
        ban.setIp(rs.getString("IP"));
        ban.setBan_id(rs.getInt("ban_id"));

        BanModel banModel = getBan(ban.getBan_id(), true);

        ban.setBanModel(banModel);

        return ban;
    }

    @Override
    public void IPBan(BanModel ban, String ip) {
        try (PreparedStatement statement = PluginConnection.getConnection().prepareStatement(
                "INSERT INTO `mastaff_punishments_ips_banned` (`IP`, `ban_id`) VALUES (?, ?);")
        ) {
            statement.setString(1, ip);
            statement.setInt(2, ban.getId());

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteIPBan(String ip) {
        try (PreparedStatement statement = PluginConnection.getConnection().prepareStatement(
                "DELETE FROM `mastaff_punishments_ips_banned` WHERE `IP` = ?;")
        ) {
            statement.setString(1, ip);

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isIPBanned(String ip) {
        try (PreparedStatement statement = PluginConnection.getConnection().prepareStatement(
                "SELECT * FROM `mastaff_punishments_ips_banned` WHERE `IP` = ?;")) {
            statement.setString(1, ip);
            ResultSet rs = statement.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
