package es.angelillo15.mast.api.data.sql;

import com.craftmend.storm.Storm;
import com.craftmend.storm.api.enums.Where;
import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.data.AbstractDataManager;
import es.angelillo15.mast.api.models.ReportComments;
import es.angelillo15.mast.api.models.ReportModel;
import es.angelillo15.mast.api.models.UserModel;
import es.angelillo15.mast.api.database.PluginConnection;
import lombok.SneakyThrows;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

public class CommonSQL extends AbstractDataManager {
    @SneakyThrows
    @Override
    public void migrations() {
        MAStaffInstance.getLogger().debug("Running migrations");
        Storm storage = PluginConnection.getStorm();
        storage.registerModel(new UserModel());
        storage.runMigrations();
    }

    public void userDataTable() {

    }

    @Override
    public void insertUserData(String UUID, String username, String lastIP, String regIP, String firstLogin, String lastLogin) {
        try (PreparedStatement statement =
                     PluginConnection.getConnection().prepareStatement("INSERT INTO `mastaff_user_data` (`username`, `UUID`, `last_ip`, `reg_ip`, `first_login`, `last_login`) VALUES (?, ?, ?, ?, ?, ?)")) {
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
    public UserModel getUserData(String where, String value) {
        UserModel userModel = null;

        Storm storage = PluginConnection.getStorm();

        try {
            userModel = storage.buildQuery(UserModel.class)
                    .where(where, Where.EQUAL, value)
                    .execute()
                    .join()
                    .iterator()
                    .next();
        } catch (Exception e) {
            return null;
        }

        return userModel;
    }

    @Override
    public void updateUserData(String UUID, String username, String lastIP, String regIP, String firstLogin, String lastLogin) {
        super.updateUserData(UUID, username, lastIP, regIP, firstLogin, lastLogin);
    }

    @Override
    public void updateIP(String UUID, String lastIP) {
        try (PreparedStatement statement =
                     PluginConnection.getConnection().prepareStatement("UPDATE `mastaff_user_data` SET `last_ip` = ? WHERE `UUID` = ?")) {
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
                     PluginConnection.getConnection().prepareStatement("UPDATE `mastaff_user_data` SET `last_login` = ? WHERE `UUID` = ?")) {
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
                     PluginConnection.getConnection().prepareStatement("UPDATE `mastaff_user_data` SET `username` = ? WHERE `UUID` = ?")) {
            statement.setString(1, username);
            statement.setString(2, UUID);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public UserModel getUserData(UUID UUID) {
        return getUserData("UUID", UUID.toString());
    }

    @Override
    public UserModel getUserData(String username) {
        return getUserData("username", username);
    }


    @Override
    public boolean userExists(UUID uuid) {
        try (PreparedStatement statement =
                     PluginConnection.getConnection().prepareStatement("SELECT * FROM `mastaff_user_data` WHERE `UUID` = ?")) {
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
                     PluginConnection.getConnection().prepareStatement("SELECT * FROM `mastaff_user_data` WHERE `username` = ?")) {
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