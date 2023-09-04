package es.angelillo15.mast.api.punishments.data;

import com.craftmend.storm.Storm;
import es.angelillo15.mast.api.database.PluginConnection;
import es.angelillo15.mast.api.models.*;
import lombok.SneakyThrows;

public class DataManager {

  @SneakyThrows
  public static void load() {
    Storm storm = PluginConnection.getStorm();

    storm.registerModel(new BansTable());
    storm.runMigrations();
    storm.registerModel(new IpBansTable());
    storm.runMigrations();
    storm.registerModel(new ReportModel());
    storm.runMigrations();
    storm.registerModel(new ReportComments());
    storm.runMigrations();
    storm.registerModel(new WarnModel());
    storm.runMigrations();
  }
}
