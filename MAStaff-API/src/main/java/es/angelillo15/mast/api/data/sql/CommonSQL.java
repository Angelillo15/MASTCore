package es.angelillo15.mast.api.data.sql;

import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.data.AbstractDataManager;
import es.angelillo15.mast.api.data.UserData;
import es.angelillo15.mast.api.database.PluginConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

public class CommonSQL extends AbstractDataManager {
    @Override
    public void migrations() {
        if (!PluginConnection.tableExists("user_data")) {
            MAStaffInstance.getLogger().info("Creating user_data table...");
            userDataTable();
        }

    }

    public void userDataTable() {

    }

    @Override
    public void insertUserData(String UUID, String username, String lastIP, String regIP, String firstLogin, String lastLogin) {
        try (PreparedStatement statement =
                     PluginConnection.getConnection().prepareStatement("INSERT INTO `user_data` (`username`, `UUID`, `last-ip`, `reg-ip`, `first_login`, `last_login`) VALUES (?, ?, ?, ?, ?, ?)")) {
            statement.setString(1, username);
            statement.setString(2, UUID);
            statement.setString(3, lastIP);
            statement.setString(4, regIP);
            statement.setString(5, firstLogin);
            statement.setString(6, lastLogin);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserData getUserData(String where, String value) {
        UserData userData = null;

        try (PreparedStatement statement =
                     PluginConnection.getConnection().prepareStatement("SELECT * FROM `user_data` WHERE `where` = ?"
                             .replace("`where`", "`" + where + "`"))
        ) {
            statement.setString(1, value);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                userData = new UserData(
                        resultSet.getInt("ID"),
                        resultSet.getString("UUID"),
                        resultSet.getString("username"),
                        resultSet.getString("last-ip"),
                        resultSet.getString("reg-ip"),
                        resultSet.getLong("first_login"),
                        resultSet.getLong("last_login")
                );
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return userData;
    }

    @Override
    public void updateUserData(String UUID, String username, String lastIP, String regIP, String firstLogin, String lastLogin) {
        super.updateUserData(UUID, username, lastIP, regIP, firstLogin, lastLogin);
    }

    @Override
    public void updateIP(String UUID, String lastIP) {
        try (PreparedStatement statement =
                     PluginConnection.getConnection().prepareStatement("UPDATE `user_data` SET `last-ip` = ? WHERE `UUID` = ?")) {
            statement.setString(1, lastIP);
            statement.setString(2, UUID);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateLastLogin(String UUID, String lastLogin) {
        try (PreparedStatement statement =
                     PluginConnection.getConnection().prepareStatement("UPDATE `user_data` SET `last_login` = ? WHERE `UUID` = ?")) {
            statement.setString(1, lastLogin);
            statement.setString(2, UUID);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateUsername(String UUID, String username) {
        try (PreparedStatement statement =
                     PluginConnection.getConnection().prepareStatement("UPDATE `user_data` SET `username` = ? WHERE `UUID` = ?")) {
            statement.setString(1, username);
            statement.setString(2, UUID);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public UserData getUserData(UUID UUID) {
        return getUserData("UUID", UUID.toString());
    }

    @Override
    public UserData getUserData(String username) {
        return getUserData("username", username);
    }


    @Override
    public boolean userExists(UUID uuid) {
        try (PreparedStatement statement =
                     PluginConnection.getConnection().prepareStatement("SELECT * FROM `user_data` WHERE `UUID` = ?")) {
            statement.setString(1, uuid.toString());
            ResultSet result = statement.executeQuery();
            boolean rb = result.next();
            statement.close();
            MAStaffInstance.getLogger().debug("User exists: " + rb);
            return rb;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean userExists(String username) {
        try (PreparedStatement statement =
                     PluginConnection.getConnection().prepareStatement("SELECT * FROM `user_data` WHERE `username` = ?")) {
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();
            boolean exists = result.next();
            MAStaffInstance.getLogger().debug("User exists: " + exists);
            return exists;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}