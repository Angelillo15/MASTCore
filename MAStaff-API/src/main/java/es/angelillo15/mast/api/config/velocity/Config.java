package es.angelillo15.mast.api.config.velocity;

import com.google.inject.Inject;

public class Config {
    @Inject
    private static VelocityConfig config;

    public static String dateFormat() {
        return config.getConfig().getConfig().getString("Config.date-format");
    }

    public static class Database {
        public static String type() {
            return config.getConfig().getConfig().getString("Database.type");
        }

        public static String host() {
            return config.getConfig().getConfig().getString("Database.host");
        }

        public static int port() {
            return config.getConfig().getConfig().getInt("Database.port");
        }

        public static String database() {
            return config.getConfig().getConfig().getString("Database.database");
        }

        public static String username() {
            return config.getConfig().getConfig().getString("Database.user");
        }

        public static String password() {
            return config.getConfig().getConfig().getString("Database.password");
        }
    }
}
