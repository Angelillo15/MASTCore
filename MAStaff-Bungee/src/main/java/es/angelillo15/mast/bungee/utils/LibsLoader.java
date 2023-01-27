package es.angelillo15.mast.bungee.utils;

import es.angelillo15.mast.api.libs.LibsManager;
import es.angelillo15.mast.bungee.MASTBungee;
import net.byteflux.libby.BungeeLibraryManager;
import net.byteflux.libby.Library;
public class LibsLoader {
    public static void loadLibs() {
        BungeeLibraryManager manager = new BungeeLibraryManager(MASTBungee.getInstance());

        manager.addMavenCentral();
        manager.addJitPack();

        LibsManager.load();
        LibsManager.getLibs().forEach((Library lib) -> {
            manager.loadLibrary(lib);
        });
    }
}
