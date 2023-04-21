package es.angelillo15.mast.api.punishments.data.sql;

import es.angelillo15.mast.api.database.PluginConnection;
import es.angelillo15.mast.api.punishments.data.AbstractDataManager;

import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
