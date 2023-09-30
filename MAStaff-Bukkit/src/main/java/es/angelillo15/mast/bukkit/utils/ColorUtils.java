package es.angelillo15.mast.bukkit.utils;

import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.api.config.bukkit.ConfigLoader;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.simpleyaml.configuration.file.YamlFile;

public class ColorUtils {
  public static String tl(String path) {
    return TextUtils.toMM(raw(path));
  }

  public static String raw(String path) {
    return ConfigLoader.getMessages().getConfig().getString(path);
  }

  public static Component cmp(String path) {
    return MiniMessage.miniMessage().deserialize(tl(path));
  }

  public static String chc(String path) {
    return LegacyComponentSerializer.legacy('§').serialize(cmp(path));
  }

  public static boolean getBoolean(String path) {
    return ConfigLoader.getConfig().getConfig().getBoolean(path);
  }

  public static YamlFile getMessages() {
    return ConfigLoader.getMessages().getConfig();
  }

  public static YamlFile getConfig() {
    return ConfigLoader.getConfig().getConfig();
  }
}
