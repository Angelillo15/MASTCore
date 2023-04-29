package es.angelillo15.mast.api.punishments.config;

public class Config {
    public static String cacheRefreshTime() {
        return ConfigLoader.getConfig().getConfig().getString("Config.cacheRefreshTime");
    }
    public static String language() {
        return ConfigLoader.getConfig().getConfig().getString("Config.language");
    }
}
