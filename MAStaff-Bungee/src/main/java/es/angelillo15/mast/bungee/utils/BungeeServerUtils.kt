package es.angelillo15.mast.bungee.utils;

import es.angelillo15.mast.api.IServerUtils;
import net.md_5.bungee.api.ProxyServer;

import java.util.UUID;

public class BungeeServerUtils implements IServerUtils {
    @Override
    public boolean isOnline(UUID uuid) {
        return ProxyServer.getInstance().getPlayer(uuid) != null;
    }

    @Override
    public boolean isOnline(String name) {
        return ProxyServer.getInstance().getPlayer(name) != null;
    }

    @Override
    public String getIP(UUID uuid) {
        return ProxyServer.getInstance().getPlayer(uuid).getAddress().getAddress().getHostAddress();
    }

    @Override
    public String getIP(String name) {
        return ProxyServer.getInstance().getPlayer(name).getAddress().getAddress().getHostAddress();
    }

    @Override
    public UUID getUUID(String name) {
        return ProxyServer.getInstance().getPlayer(name).getUniqueId();
    }

    @Override
    public String getName(UUID uuid) {
        return ProxyServer.getInstance().getPlayer(uuid).getName();
    }
}
