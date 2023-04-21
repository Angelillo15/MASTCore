package es.angelillo15.mast.api.data.sql;

import es.angelillo15.mast.api.database.PluginConnection;

import java.sql.PreparedStatement;

public class MySQL extends CommonSQL {
    @Override
    public void userDataTable() {
        try (PreparedStatement statement =
                     PluginConnection.getConnection().prepareStatement("CREATE TABLE `user_data` (" +
                             "`ID` INT NOT NULL AUTO_INCREMENT,\n" +
                             "`username` VARCHAR(50) NOT NULL DEFAULT '0',\n" +
                             "`UUID` VARCHAR(50) NOT NULL DEFAULT '0',\n" +
                             "`last-ip` VARCHAR(50) NOT NULL DEFAULT '0',\n" +
                             "`reg-ip` VARCHAR(50) NOT NULL DEFAULT '0',\n" +
                             "`first_login` BIGINT(20) NOT NULL DEFAULT '0',\n" +
                             "`last_login` BIGINT(20) NOT NULL DEFAULT '0',\n" +
                             "PRIMARY KEY (`ID`)\n" +
                             ")" +
                             "COLLATE='utf8mb4_general_ci'\n" +
                             ";")) {
            statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
