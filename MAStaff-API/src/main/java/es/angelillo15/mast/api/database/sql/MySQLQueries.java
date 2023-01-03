package es.angelillo15.mast.api.database.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQLQueries extends CommonQueries {
    private static Connection connection;

    public MySQLQueries() {
        MySQLQueries.connection = CommonQueries.getConnection();
    }

    @Override
    public void createTables() {
        try (PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `staffdata` (`ID` INT(11) NOT NULL AUTO_INCREMENT, `UUID` VARCHAR(70) NULL DEFAULT '' COLLATE 'utf8mb4_general_ci', `state` INT(1) NULL DEFAULT '0', `vanished` INT(1) NULL DEFAULT '0', PRIMARY KEY (`ID`) USING BTREE)")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
