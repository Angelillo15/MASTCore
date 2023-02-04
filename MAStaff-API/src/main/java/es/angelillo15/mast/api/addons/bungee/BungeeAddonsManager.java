package es.angelillo15.mast.api.addons.bungee;

import lombok.Getter;

import java.util.HashMap;

public class BungeeAddonsManager {
    @Getter
    private static HashMap<String, MAStaffBungeeAddon> addons = new HashMap<>();

    public static void registerAddon(MAStaffBungeeAddon addon) {
        addons.put(addon.getDescriptionFile().getName(), addon);
    }

    public static MAStaffBungeeAddon getAddon(String name) {
        return addons.get(name);
    }

    public static void unregisterAddon(MAStaffBungeeAddon addon) {
        addons.remove(addon.getDescriptionFile().getName());
    }

    public static void unregisterAddon(String name) {
        addons.remove(name);
    }

    public static void unregisterAllAddons() {
        addons.clear();
    }
}
