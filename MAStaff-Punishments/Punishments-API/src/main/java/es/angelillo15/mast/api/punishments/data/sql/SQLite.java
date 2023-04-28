package es.angelillo15.mast.api.punishments.data.sql;

import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.database.PluginConnection;

import java.sql.PreparedStatement;

public class SQLite extends CommonSQL {
    @Override
    public void migrations() {
        super.migrations();
        if (!PluginConnection.tableExists("mastaff_punishments_bans")) {
            MAStaffInstance.getLogger().debug("Creating table mastaff_punishments_bans");
            bansMigration();
        }
    }

    public void bansMigration() {
        try (PreparedStatement statement =
                     PluginConnection.getConnection().prepareStatement(
                             "CREATE TABLE mastaff_punishments_bans (" +
                                     "    ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                                     "    UUID TEXT NOT NULL DEFAULT '0'," +
                                     "    IP TEXT NOT NULL DEFAULT '0'," +
                                     "    reason TEXT NOT NULL DEFAULT '0'," +
                                     "    banned_by_uuid TEXT NOT NULL DEFAULT '0'," +
                                     "    banned_by_name TEXT NOT NULL DEFAULT '0'," +
                                     "    removed_by_uuid TEXT NULL DEFAULT 0," +
                                     "    removed_by_uuid TEXT NULL DEFAULT 0," +
                                     "    removed_by_reason TEXT NULL DEFAULT 0," +
                                     "    removed_by_date INTEGER NULL DEFAULT 0," +
                                     "    active INTEGER NOT NULL DEFAULT 0," +
                                     "    time INTEGER NOT NULL DEFAULT 0," +
                                     "    until INTEGER NOT NULL DEFAULT 0," +
                                     "    ipban INTEGER NOT NULL DEFAULT 0," +
                                     "    UNIQUE(ID)," +
                                     ");")
        ){
            statement.executeUpdate();
            MAStaffInstance.getLogger().debug("Created table mastaff_punishments_bans");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
