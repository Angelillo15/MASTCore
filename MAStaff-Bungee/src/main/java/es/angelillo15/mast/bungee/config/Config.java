package es.angelillo15.mast.bungee.config;

public class Config {
    public static class Redis {
        public boolean isEnabled() {
            return ConfigLoader.getConfig().getConfig().getBoolean("Redis.enabled");
        }

        public String getHost() {
            return ConfigLoader.getConfig().getConfig().getString("Redis.host");
        }

        public int getPort() {
            return ConfigLoader.getConfig().getConfig().getInt("Redis.port");
        }

        public String getPassword() {
            return ConfigLoader.getConfig().getConfig().getString("Redis.password");
        }

        public int getDatabase() {
            return ConfigLoader.getConfig().getConfig().getInt("Redis.database");
        }

        public int getTimeout() {
            return ConfigLoader.getConfig().getConfig().getInt("Redis.timeout");
        }

        public int getPoolSize() {
            return ConfigLoader.getConfig().getConfig().getInt("Redis.poolSize");
        }

        public String getPrefix() {
            return ConfigLoader.getConfig().getConfig().getString("Redis.prefix");
        }

        public String getChannel() {
            return ConfigLoader.getConfig().getConfig().getString("Redis.channel");
        }
    }
}
