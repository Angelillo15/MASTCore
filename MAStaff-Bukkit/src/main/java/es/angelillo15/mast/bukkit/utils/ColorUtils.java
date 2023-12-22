package es.angelillo15.mast.bukkit.utils;

import es.angelillo15.mast.api.Replace;
import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.api.config.bukkit.ConfigLoader;
import es.angelillo15.mast.api.config.bukkit.Messages;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.simpleyaml.configuration.file.YamlFile;

public class ColorUtils {
  public static String tl(String path) {
    return TextUtils.toMM(raw(path));
  }

  public static String raw(String path) {
    if (getMessages().contains(path)) {
      return getMessages().getString(path).replace("{prefix}", Messages.PREFIX());
    } else {
      return path;
    }
  }

  public static Component cmp(String path) {
    return MiniMessage.miniMessage().deserialize(tl(path));
  }

  public static Component cmp(String path, Replace... replace) {
    String text = tl(path);
    for (Replace r : replace) {
      text = text.replace( "{" + r.search() + "}", r.replace());
    }

    text = text.replace("{prefix}", Messages.PREFIX());
    return MiniMessage.miniMessage().deserialize(text);
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
