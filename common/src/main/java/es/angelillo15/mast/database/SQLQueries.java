package es.angelillo15.mast.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLQueries {
    public static boolean staffDataCreate(Connection connection) {
        try (PreparedStatement statement = connection.prepareStatement("SHOW TABLES LIKE 'staffdata';")) {
            ResultSet result = statement.executeQuery();
            boolean rb = result.next();
            statement.close();
            return rb;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public static void createStaffDataTableSQLite(Connection connection) {
        try (PreparedStatement statement = connection.prepareStatement("CREATE TABLE \"staffdata\" (\"ID\"    INTEGER, \"UUID\"    TEXT, \"state\"    INTEGER, \"vanished\"    INTEGER, PRIMARY KEY(\"ID\" AUTOINCREMENT) );")) {
            statement.executeUpdate();
        } catch (SQLException ignored) {

        }
    }

    public static void createStaffDataTableMySQL(Connection connection) {
        try (PreparedStatement statement = connection.prepareStatement("CREATE TABLE `staffdata` (`ID` INT(11) NOT NULL AUTO_INCREMENT, `UUID` VARCHAR(70) NULL DEFAULT '' COLLATE 'utf8mb4_general_ci', `state` INT(1) NULL DEFAULT '0', `vanished` INT(1) NULL DEFAULT '0', PRIMARY KEY (`ID`) USING BTREE)")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void insertData(Connection connection, UUID uuid, int state, int vanished) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO `staffdata` (`ID`, `UUID`, `state`, `vanished`) VALUES (NULL, ?, ?, ?);")) {
            statement.setString(1, uuid.toString());
            statement.setString(2, String.valueOf(state));
            statement.setString(3, String.valueOf(vanished));
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateData(Connection connection, UUID uuid, int state, int vanished) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE `staffdata` SET `state` = ?, `vanished` = ? WHERE `UUID` = ?;")) {
            statement.setString(1, String.valueOf(state));
            statement.setString(2, String.valueOf(vanished));
            statement.setString(3, uuid.toString());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteData(Connection connection, UUID uuid) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM `staffdata` WHERE `UUID` = ?;")) {
            statement.setString(1, uuid.toString());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean existsData(Connection connection, UUID uuid) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM `staffdata` WHERE `UUID` = ?;")) {
            statement.setString(1, uuid.toString());
            ResultSet result = statement.executeQuery();
            boolean rb = result.next();
            statement.close();
            return rb;
        } catch (SQLException e) {
            System.out.println(e);

        }
        return false;
    }

    public static int getState(Connection connection, UUID uuid) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM `staffdata` WHERE `UUID` = ?;")) {
            statement.setString(1, uuid.toString());
            ResultSet result = statement.executeQuery();
            result.next();
            //statement.close();
            return result.getInt("state");
        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("Error getting state");
        }
        return 0;
    }

    public static int getVanished(Connection connection, UUID uuid) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM `staffdata` WHERE `UUID` = ?;")) {
            statement.setString(1, uuid.toString());
            ResultSet result = statement.executeQuery();
            result.next();
            //statement.close();
            return result.getInt("vanished");
        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("Error getting vanished");
        }
        return 0;
    }

    public static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //player in db
    public static boolean playerInDB(Connection connection, UUID uuid) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM `staffdata` WHERE `UUID` = ?;")) {
            statement.setString(1, uuid.toString());
            ResultSet result = statement.executeQuery();
            return result.next();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    // check if the player is vanished
    public static boolean isVanished(Connection connection, UUID uuid) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM `staffdata` WHERE `UUID` = ?;")) {
            statement.setString(1, uuid.toString());
            ResultSet result = statement.executeQuery();
            result.next();
            return result.getInt("vanished") == 1;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

}
