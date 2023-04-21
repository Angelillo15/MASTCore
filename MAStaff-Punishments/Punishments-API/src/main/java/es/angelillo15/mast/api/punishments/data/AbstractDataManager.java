package es.angelillo15.mast.api.punishments.data;

import es.angelillo15.mast.api.database.PluginConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class AbstractDataManager {
    public void migrations() {

    }

    public void ban(String uuid, String ip, String reason, String banned_by_uuid, String banned_by_name, boolean active, long time, long until, boolean ipban) {

    }
}
