package es.angelillo15.mast.api.database.sql;

import es.angelillo15.mast.api.MAStaffInstance;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CommonQueries {
    @Getter
    @Setter
    private static Connection connection;
    public void createTables() {

    }
    public static void changeData(UUID uuid, int state) {
        if(!existsData(uuid)){
            insertData(uuid, state);
            return;
        }
        updateData(uuid, state);
    }

    public static void insertData(UUID uuid, int state) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO `staffdata` (`ID`, `UUID`, `state`, `vanished`) VALUES (NULL, ?, ?, ?);")) {
            statement.setString(1, uuid.toString());
            statement.setString(2, String.valueOf(state));
            statement.setString(3, String.valueOf(0));
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateData(UUID uuid, int state) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE `staffdata` SET `state` = ?, `vanished` = ? WHERE `UUID` = ?;")) {
            statement.setString(1, String.valueOf(state));
            statement.setString(2, String.valueOf(0));
            statement.setString(3, uuid.toString());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean existsData(UUID uuid) {
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

    public static int getStateInt(UUID uuid) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM `staffdata` WHERE `UUID` = ?;")) {
            statement.setString(1, uuid.toString());
            ResultSet result = statement.executeQuery();
            result.next();
            return result.getInt("state");
        } catch (SQLException e) {
            MAStaffInstance.getLogger().debug(e.getMessage());
        }
        return 0;
    }

    public static boolean isInStaffMode(UUID uuid){
        if(getStateInt(uuid) == 1){
            return true;
        }
        return false;
    }

    public static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void updateAsync(UUID uuid, int state) {
        new Thread(() -> {
            changeData(uuid, state);
        }).start();
    }

}
