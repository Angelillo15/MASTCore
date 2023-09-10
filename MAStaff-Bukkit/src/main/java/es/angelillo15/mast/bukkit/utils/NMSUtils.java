package es.angelillo15.mast.bukkit.utils;

import es.angelillo15.mast.bukkit.nms.V1_20_1_R0;
import es.angelillo15.mast.bukkit.nms.VersionSupport;
import org.bukkit.Bukkit;

public class NMSUtils {
  private static final String VERSION = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
  private static VersionSupport versionSupport;

  public static String getVersion() {
    return VERSION;
  }

  public static VersionSupport getVersionSupport() {
    if (versionSupport != null) {
      return versionSupport;
    }

    return switch (VERSION) {
      case "v1_20_R1" -> versionSupport = new V1_20_1_R0();
      default -> throw new RuntimeException("Your server version is not supported by MAStaff");
    };
  }

}
