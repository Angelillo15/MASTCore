package es.angelillo15.mast.api.utils;

import org.bukkit.Server;



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
            serverType = ServerType.BUNGEE;
            return ServerType.BUNGEE;
        }
    }

    private static boolean isProtocolLibInstalled() {
        try {
            Class.forName("com.comphenix.protocol.ProtocolLibrary");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public enum ServerType {
        BUKKIT,
        BUNGEE
    }
}
