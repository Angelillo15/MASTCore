package es.angelillo15.mast.velocity.utils;

import es.angelillo15.mast.api.libs.LibsManager;
import es.angelillo15.mast.velocity.MAStaff;
import net.byteflux.libby.VelocityLibraryManager;

public class LibsLoader {
  public static void load() {
    VelocityLibraryManager<MAStaff> manager =
        new VelocityLibraryManager<MAStaff>(
            MAStaff.getInstance().getSlf4jLogger(),
            MAStaff.getInstance().getPluginDataFolder().toPath(),
            MAStaff.getInstance().getProxyServer().getPluginManager(),
            MAStaff.getInstance());

    manager.addMavenCentral();
    manager.addJitPack();
    manager.addSonatype();
    manager.addRepository("https://jitpack.io");

    LibsManager.load();
    LibsManager.getLibs().forEach((manager::loadLibrary));
  }
}
