package es.angelillo15.mast.api.utils;

import org.bukkit.Bukkit;

public class VersionUtils  {
    public static final int version = Integer.parseInt(Bukkit.getBukkitVersion().split("-")[0].split("\\.")[1]);
    public static int getBukkitVersion() {
        return version;
    }
}
