package es.angelillo15.mast.bungee.config;

public class Config {
    public static String dateFormat() {
        return ConfigLoader.getConfig().getConfig().getString("Config.date-format");
    }

    public static class Modules {
        public static boolean isPunishmentsEnabled() {
            return ConfigLoader.getConfig().getConfig().getBoolean("Modules.punishments");
        }
    }
    public static class Redis {
        public static boolean isEnabled() {
            return ConfigLoader.getConfig().getConfig().getBoolean("Redis.enabled");
        }

        public static String getHost() {
            return ConfigLoader.getConfig().getConfig().getString("Redis.host");
        }

        public static int getPort() {
            return ConfigLoader.getConfig().getConfig().getInt("Redis.port");
        }

        public static String getPassword() {
            return ConfigLoader.getConfig().getConfig().getString("Redis.password");
        }

        public static int getDatabase() {
            return ConfigLoader.getConfig().getConfig().getInt("Redis.database");
        }

        public static int getTimeout() {
            return ConfigLoader.getConfig().getConfig().getInt("Redis.timeout");
        }

        public static int getPoolSize() {
            return ConfigLoader.getConfig().getConfig().getInt("Redis.poolSize");
        }

        public static String getPrefix() {
            return ConfigLoader.getConfig().getConfig().getString("Redis.prefix");
        }

        public static String getChannel() {
            return ConfigLoader.getConfig().getConfig().getString("Redis.channel");
        }

        public static String getServerName() {
            return ConfigLoader.getConfig().getConfig().getString("Redis.serverID");
        }
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
