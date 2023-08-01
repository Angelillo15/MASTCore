package es.angelillo15.mast.api.utils;

public class ServerUtils {
    private static ServerType serverType;
    private static boolean protocolLibInstalled = false;
    public static ServerType getServerType() {
        if (serverType != null) {
            return serverType;
        }

        try {
            Class.forName("org.bukkit.Bukkit");
            serverType = ServerType.BUKKIT;
            return ServerType.BUKKIT;
        } catch (ClassNotFoundException e) {

            try {
                Class.forName("com.velocitypowered.api.proxy.ProxyServer");
                serverType = ServerType.VELOCITY;
            } catch (ClassNotFoundException e2) {
                serverType = ServerType.BUNGEE;
            }
        }

        return serverType;
    }

    public static boolean isProtocolLibInstalled() {
        try {
            Class.forName("com.comphenix.protocol.ProtocolLibrary");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public enum ServerType {
        BUKKIT,
        BUNGEE,
        VELOCITY
    }
}
