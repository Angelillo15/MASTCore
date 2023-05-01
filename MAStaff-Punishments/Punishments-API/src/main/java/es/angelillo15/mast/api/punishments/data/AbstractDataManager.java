package es.angelillo15.mast.api.punishments.data;

import es.angelillo15.mast.api.models.BanModel;

import java.util.UUID;

public abstract class AbstractDataManager {
    public void migrations() {

    }

    public void ban(String uuid, String username, String ip, String reason, String banned_by_uuid, String banned_by_name, boolean active, long time, long until, boolean ipban) {

    }

    public boolean isPermBanned(UUID uuid) {
        return false;
    }

    public boolean isPermBanned(String username) {
        return false;
    }

    public boolean isTempBanned(String where, String value) {
        return false;
    }

    public boolean isTempBanned(UUID uuid) {
        return false;
    }

    public boolean isTempBanned(String uuid) {
        return false;
    }

    public void setBanActive(String uuid, boolean active, String reason) {

    }

    public boolean isBanned(String name) {
        return false;
    }

    public boolean isPermBanned(String where, String value) {
        return false;
    }

    public BanModel getBan(UUID uuid) {
        return null;
    }

    public BanModel getBan(String username) {
        return null;
    }

    public void setUUID(String username, UUID uuid) {

    }

    public void setUsername(UUID uuid, String username) {

    }
}
