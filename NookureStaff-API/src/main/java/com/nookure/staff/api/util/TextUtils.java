package com.nookure.staff.api.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.ArrayList;
import java.util.List;

public abstract class TextUtils {
  /**
   * Converts a string to a mini message string
   *
   * @param str the string to convert
   * @return the mini message string
   */
  public static String toMM(String str) {
    if (!str.contains("&") && !str.contains("§")) return str;

    return str
        .replace("§", "&")
        .replace("&0", "<reset><black>")
        .replace("&1", "<reset><dark_blue>")
        .replace("&2", "<reset><dark_green>")
        .replace("&3", "<reset><dark_aqua>")
        .replace("&4", "<reset><dark_red>")
        .replace("&5", "<reset><dark_purple>")
        .replace("&6", "<reset><gold>")
        .replace("&7", "<reset><grey>")
        .replace("&8", "<reset><dark_grey>")
        .replace("&9", "<reset><blue>")
        .replace("&a", "<reset><green>")
        .replace("&b", "<reset><aqua>")
        .replace("&c", "<reset><red>")
        .replace("&d", "<reset><light_purple>")
        .replace("&e", "<reset><yellow>")
        .replace("&f", "<reset><white>")
        .replace("&k", "<obf>")
        .replace("&l", "<b>")
        .replace("&m", "<st>")
        .replace("&n", "<u>")
        .replace("&r", "<reset>")
        .replace("&o", "<i>");
  }

  /**
   * Converts a string to a component
   *
   * @param str the string to convert
   * @return the component
   */
  public static Component toComponent(String str) {
    return MiniMessage.miniMessage().deserialize(toMM(str));
  }

  /**
   * Converts a list of strings to a list of components
   *
   * @param str the list of strings to convert
   * @return the list of components
   */
  public static List<Component> toComponent(List<String> str) {
    List<Component> components = new ArrayList<>();

    for (String s : str) {
      components.add(toComponent(s));
    }

    return components;
  }
}
