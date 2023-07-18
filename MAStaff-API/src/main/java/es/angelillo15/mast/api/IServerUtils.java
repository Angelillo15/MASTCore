package es.angelillo15.mast.api;

import java.util.UUID;

public interface IServerUtils {
    public boolean isOnline(UUID uuid);

    public boolean isOnline(String name);

    public String getIP(UUID uuid);

    public String getIP(String name);

    public UUID getUUID(String name);

    public String getName(UUID uuid);
}
