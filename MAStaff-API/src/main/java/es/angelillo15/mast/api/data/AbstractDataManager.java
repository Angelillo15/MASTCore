package es.angelillo15.mast.api.data;

import es.angelillo15.mast.api.database.PluginConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

public abstract class AbstractDataManager {
    public void migrations() {

    }

    public void insertUserData(String UUID, String username, String lastIP, String regIP, String firstLogin, String lastLogin) {

    }

    public void updateUserData(String UUID, String username, String lastIP, String regIP, String firstLogin, String lastLogin) {

    }

    public void updateIP(String UUID, String lastIP) {

    }

    public void updateLastLogin(String UUID, String lastLogin) {

    }

    public void updateUsername(String UUID, String username) {

    }

    public UserData getUserData(String where, String value) {
        return null;
    }

    public UserData getUserData(UUID UUID) {
        return null;
    }

    public UserData getUserData(String username) {
        return null;
    }

    public boolean userExists(String where, String value) {
        return false;
    }

    public boolean userExists(UUID UUID) {
        return false;
    }

    public boolean userExists(String username) {
        return false;
    }
}
