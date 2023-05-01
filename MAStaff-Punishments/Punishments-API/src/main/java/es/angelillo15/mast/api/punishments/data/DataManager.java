package es.angelillo15.mast.api.punishments.data;

import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.database.PluginConnection;
import es.angelillo15.mast.api.punishments.data.sql.CommonSQL;
import es.angelillo15.mast.api.punishments.data.sql.MySQL;
import es.angelillo15.mast.api.punishments.data.sql.SQLite;

public class DataManager {
    private static AbstractDataManager dataManager;

    public static AbstractDataManager getDataManager() {
        return dataManager;
    }

    public static void load() {/*

        switch (PluginConnection.getDataProvider()) {
            case MYSQL:
                dataManager = new MySQL();
                break;
            case SQLITE:
                dataManager = new SQLite();
                break;
            default:
                MAStaffInstance.getLogger().error("Invalid data provider!");
                break;
        }

        dataManager.migrations();

        MAStaffInstance.getLogger().debug("Migrations runed for " + PluginConnection.getDataProvider().name() + " data provider");
        */
    }
}
