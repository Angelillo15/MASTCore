package es.angelillo15.mast.api.config.velocity;

public class Config {
    public static String dateFormat() {
        return ConfigLoader.getConfig().getConfig().getString("Config.date-format");
    }

    public static class Database {
        public static String type() {
            return ConfigLoader.getConfig().getConfig().getString("Database.type");
        }

        public static String host() {
            return ConfigLoader.getConfig().getConfig().getString("Database.host");
        }

        public static int port() {
            return ConfigLoader.getConfig().getConfig().getInt("Database.port");
        }

        public static String database() {
            return ConfigLoader.getConfig().getConfig().getString("Database.database");
        }

        public static String username() {
            return ConfigLoader.getConfig().getConfig().getString("Database.user");
        }

        public static String password() {
            return ConfigLoader.getConfig().getConfig().getString("Database.password");
        }
    }
}
