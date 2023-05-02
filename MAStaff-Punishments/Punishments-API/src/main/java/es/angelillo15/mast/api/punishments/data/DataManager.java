package es.angelillo15.mast.api.punishments.data;

import com.craftmend.storm.Storm;
import es.angelillo15.mast.api.database.PluginConnection;
import es.angelillo15.mast.api.models.BansTable;
import es.angelillo15.mast.api.models.IpBansTable;
import lombok.SneakyThrows;

public class DataManager {

    @SneakyThrows
    public static void load() {
        Storm storm = PluginConnection.getStorm();

        storm.registerModel(new BansTable());
        storm.registerModel(new IpBansTable());

        storm.runMigrations();
    }
}
