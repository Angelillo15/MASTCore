package es.angelillo15.mast.api.data;

import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.data.sql.CommonSQL;
import es.angelillo15.mast.api.database.PluginConnection;

public class DataManager {
    private static AbstractDataManager dataManager;

    public static AbstractDataManager getDataManager() {
        return dataManager;
    }

    public static void load() {
        dataManager = new CommonSQL();

        dataManager.migrations();

        MAStaffInstance.getLogger().debug("Migrations runed for " + PluginConnection.getDataProvider().name() + " data provider");
    }
}
