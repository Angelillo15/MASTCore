package es.angelillo15.mast.bungee.utils;

import es.angelillo15.mast.api.libs.LibsManager;
import es.angelillo15.mast.bungee.MAStaff;
import net.byteflux.libby.BungeeLibraryManager;

public class LibsLoader {
    public static void loadLibs() {
        BungeeLibraryManager manager = new BungeeLibraryManager(MAStaff.getInstance());

        manager.addMavenCentral();
        manager.addJitPack();

        LibsManager.load();
        LibsManager.getLibs().forEach(manager::loadLibrary);
    }
}
