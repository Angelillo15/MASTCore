package es.angelillo15.mast.api.punishments.data;

import java.util.UUID;

public abstract class AbstractDataManager {
    public void migrations() {

    }

    public void ban(String uuid, String ip, String reason, String banned_by_uuid, String banned_by_name, boolean active, long time, long until, boolean ipban) {

    }

    public boolean isPermBanned(UUID uuid) {
        return false;
    }

    public boolean isPermBanned(String username) {
        return false;
    }

    public boolean isPermBanned(String where, String value) {
        return false;
    }
}
