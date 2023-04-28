package es.angelillo15.mast.api.punishments.data.sql;

import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.database.PluginConnection;

import java.sql.PreparedStatement;

public class MySQL extends CommonSQL{
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
                             "CREATE TABLE `mastaff_punishments_bans`\n" +
                                     "(" +
                                     "  `ID` INT(11) NOT NULL AUTO_INCREMENT," +
                                     "  `UUID` VARCHAR(36) NOT NULL DEFAULT '0' COLLATE 'utf8mb4_general_ci'," +
                                     "  `IP` VARCHAR(50) NOT NULL DEFAULT '0' COLLATE 'utf8mb4_general_ci'," +
                                     "  `reason` VARCHAR(4096) NOT NULL DEFAULT '0'," +
                                     "  `banned_by_uuid` VARCHAR(36) NOT NULL DEFAULT '0'," +
                                     "  `banned_by_name` VARCHAR(25) NOT NULL DEFAULT '0'," +
                                     "  `removed_by_name` VARCHAR(25) NULL DEFAULT 0," +
                                     "  `removed_by_uuid` INT NULL DEFAULT 0," +
                                     "  `removed_by_reason` INT NULL DEFAULT 0," +
                                     "  `removed_by_date` BIGINT NULL DEFAULT 0," +
                                     "  `active` INT NOT NULL DEFAULT 0," +
                                     "  `time` BIGINT NOT NULL DEFAULT 0," +
                                     "  `until` BIGINT NOT NULL DEFAULT 0," +
                                     "  `ipban` INT NOT NULL DEFAULT 0," +
                                     "  PRIMARY KEY (`ID`) USING BTREE," +
                                     "  UNIQUE INDEX `ID` (`ID`) USING BTREE" +
                                     ");")
        ){
            statement.executeUpdate();
            MAStaffInstance.getLogger().debug("Created table mastaff_punishments_bans");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
