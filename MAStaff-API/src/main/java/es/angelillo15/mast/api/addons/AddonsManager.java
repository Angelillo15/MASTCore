package es.angelillo15.mast.api.addons;

import java.util.HashMap;

public class AddonsManager {
    private static HashMap<String, MAStaffAddon> addons = new HashMap<>();

    public static void registerAddon(MAStaffAddon addon) {
        addons.put(addon.getDescriptionFile().getName(), addon);
    }

    public static MAStaffAddon getAddon(String name) {
        return addons.get(name);
    }

    public static HashMap<String, MAStaffAddon> getAddons() {
        return addons;
    }

    public static void unregisterAddon(String name) {
        addons.remove(name);
    }

    public static void unregisterAddon(MAStaffAddon addon) {
        addons.remove(addon.getDescriptionFile().getName());
    }

    public static void unregisterAllAddons() {
        addons.clear();
    }

    public static boolean isAddonRegistered(String name) {
        return addons.containsKey(name);
    }

    public static boolean isAddonRegistered(MAStaffAddon addon) {
        return addons.containsValue(addon);
    }

    public static boolean isAddonRegistered(Class<? extends MAStaffAddon> addonClass) {
        return addons.containsValue(addonClass);
    }
}
