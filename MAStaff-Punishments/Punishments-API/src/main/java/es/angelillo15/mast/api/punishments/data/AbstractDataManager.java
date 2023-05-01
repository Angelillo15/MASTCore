package es.angelillo15.mast.api.punishments.data;

import es.angelillo15.mast.api.models.BanModel;
import es.angelillo15.mast.api.models.IPBanModel;

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

    public void setBanActive(String username, boolean active, String reason) {

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

    public BanModel getBan(int id) {
        return null;
    }

    public BanModel getBan(int id, boolean active) {
        return null;
    }

    public void setUUID(String username, UUID uuid) {

    }

    public void setUsername(UUID uuid, String username) {

    }

    public IPBanModel getIPBan(String ip) {
        return null;
    }

    public IPBanModel getIPBan(int id) {
        return null;
    }

    public void IPBan(BanModel ban, String ip) {

    }

    public void deleteIPBan(String ip) {

    }

    public boolean isIPBanned(String ip) {
        return false;
    }
}
