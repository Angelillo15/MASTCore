package es.angelillo15.mast.api.config.punishments;

public class Config {
    public static int cacheRefreshTime() {
        return ConfigLoader.getConfig().getConfig().getInt("Config.cacheRefreshTime");
    }
    public static String language() {
        return ConfigLoader.getConfig().getConfig().getString("Config.language");
    }

    public static String dateFormat() {
        return ConfigLoader.getConfig().getConfig().getString("Config.date-format");
    }
}
