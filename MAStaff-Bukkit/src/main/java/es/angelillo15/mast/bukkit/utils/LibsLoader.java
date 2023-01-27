package es.angelillo15.mast.bukkit.utils;

import es.angelillo15.mast.api.libs.LibsManager;
import es.angelillo15.mast.bukkit.MAStaff;
import net.byteflux.libby.BukkitLibraryManager;
import net.byteflux.libby.Library;

public class LibsLoader {
    public static void loadLibs() {
        BukkitLibraryManager manager = new BukkitLibraryManager(MAStaff.getPlugin());

        manager.addMavenCentral();
        manager.addJitPack();

        LibsManager.load();
        LibsManager.getLibs().forEach((Library lib) -> {
            manager.loadLibrary(lib);
        });

    }


}
