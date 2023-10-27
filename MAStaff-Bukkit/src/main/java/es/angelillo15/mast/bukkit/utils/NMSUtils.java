package es.angelillo15.mast.bukkit.utils;

import es.angelillo15.bukkit.nms.V1_12_2_R0;
import es.angelillo15.bukkit.nms.V1_8_8_R0;
import es.angelillo15.mast.bukkit.MAStaff;
import es.angelillo15.mast.api.nms.VersionSupport;
import es.angelillo15.mast.bukkit.nms.*;
import com.nookure.mast.bukkit.nms.V1_20_2_R0;
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
      case "v1_20_R2" -> versionSupport = new V1_20_2_R0(MAStaff.getPlugin());
      case "v1_20_R1" -> versionSupport = new V1_20_1_R0(MAStaff.getPlugin());
      case "v1_18_R2" -> versionSupport = new V1_18_2_R0(MAStaff.getPlugin());
      case "v1_19_R3" -> versionSupport = new V1_19_4_R0(MAStaff.getPlugin());
      case "v1_17_R1" -> versionSupport = new V1_17_1_R0(MAStaff.getPlugin());
      case "v1_16_R3" -> versionSupport = new V1_16_5_R0(MAStaff.getPlugin());
      case "v1_12_R1" -> versionSupport = new V1_12_2_R0(MAStaff.getPlugin());
      case "v1_8_R3" -> versionSupport = new V1_8_8_R0(MAStaff.getPlugin());
      default -> throw new RuntimeException("Your server version " + VERSION + " is not supported by MAStaff");
    };
  }

}
