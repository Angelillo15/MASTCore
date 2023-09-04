package es.angelillo15.mast.api;

import es.angelillo15.mast.api.chat.api.ChatColor;
import es.angelillo15.mast.api.config.bukkit.Messages;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Getter;
import lombok.Setter;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.bukkit.entity.Player;

public class TextUtils {
  private static final String WITH_DELIMITER = "((?<=%1$s)|(?=%1$s))";
  private static final Pattern HEX_PATTERN = Pattern.compile("#[a-fA-F0-9]{6}}");
  private static final long SECOND = 1000;
  private static final long MINUTE = 60 * SECOND;
  private static final long HOUR = 60 * MINUTE;
  private static final long DAY = 24 * HOUR;

  @Getter @Setter private static AudienceProvider audienceProvider;

  public static String colorize(String text) {
    if (MAStaffInstance.version() < 16) {
      return ChatColor.translateAlternateColorCodes('&', text);
    }

    String[] texts = text.split(String.format(WITH_DELIMITER, "&"));

    StringBuilder finalText = new StringBuilder();
    Matcher match = HEX_PATTERN.matcher(text);

    if (text.contains("&#")) {
      for (int i = 0; i < texts.length; i++) {
        if (texts[i].equalsIgnoreCase("&")) {
          i++;
          if (texts[i].charAt(0) == '#') {
            finalText.append(ChatColor.of(texts[i].substring(0, 7))).append(texts[i].substring(7));
          } else {
            finalText.append(ChatColor.translateAlternateColorCodes('&', "&" + texts[i]));
          }
        } else {
          while (match.find()) {
            String color = texts[i].substring(match.start(), match.end());
            texts[i] = texts[i].replace(color, ChatColor.of(color) + "");
            match = HEX_PATTERN.matcher(text);
          }
          finalText.append(texts[i]);
        }
      }

    } else {
      while (match.find()) {
        String color = text.substring(match.start(), match.end());
        text = text.replace(color, ChatColor.of(color) + "");
        match = HEX_PATTERN.matcher(text);
      }
      finalText.append(text);
    }

    return ChatColor.translateAlternateColorCodes('&', finalText.toString());
  }

  public static void colorize(String text, Player... audience) {
    if (text == null || text.isEmpty()) return;

    for (Player player : audience) {
      getAudience(player).sendMessage(toComponent(text));
    }
  }

  public static void colorize(String text, Audience... audience) {
    if (text == null || text.isEmpty()) return;

    for (Audience player : audience) {
      player.sendMessage(toComponent(text));
    }
  }

  public static void sendMessage(Player player, String text) {
    colorize(text, player);
  }

  public static String processPlaceholders(Player player, String text) {
    if (MAStaffInstance.placeholderCheck()) {
      return colorize(PlaceholderAPI.setPlaceholders(player, text));
    }
    return colorize(text);
  }

  public static String formatUptime(long uptime) {
    StringBuilder buf = new StringBuilder();
    if (uptime > DAY) {
      long days = (uptime - uptime % DAY) / DAY;
      buf.append(days);
      buf.append("d");
      uptime = uptime % DAY;
    }
    if (uptime > HOUR) {
      long hours = (uptime - uptime % HOUR) / HOUR;
      if (buf.length() > 0) {
        buf.append(", ");
      }
      buf.append(hours);
      buf.append("h");
      uptime = uptime % HOUR;
    }
    if (uptime > MINUTE) {
      long minutes = (uptime - uptime % MINUTE) / MINUTE;
      if (buf.length() > 0) {
        buf.append(", ");
      }
      buf.append(minutes);
      buf.append("m");
      uptime = uptime % MINUTE;
    }
    if (uptime > SECOND) {
      long seconds = (uptime - uptime % SECOND) / SECOND;
      if (buf.length() > 0) {
        buf.append(", ");
      }
      buf.append(seconds);
      buf.append("s");
      uptime = uptime % SECOND;
    }
    return buf.toString();
  }

  public static String toMM(String str) {
    StringBuilder sb = new StringBuilder(str);
    Matcher m = ChatColor.STRIP_COLOR_PATTERN.matcher(sb);
    while (m.find()) {
      sb.replace(m.start(), m.end(), sb.substring(m.start(), m.end()).toLowerCase());
    }
    return sb.toString()
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
        .replace("&o", "<i>")
        .replace("§0", "<reset><black>")
        .replace("§1", "<reset><dark_blue>")
        .replace("§2", "<reset><dark_green>")
        .replace("§3", "<reset><dark_aqua>")
        .replace("§4", "<reset><dark_red>")
        .replace("§5", "<reset><dark_purple>")
        .replace("§6", "<reset><gold>")
        .replace("§7", "<reset><grey>")
        .replace("§8", "<reset><dark_grey>")
        .replace("§9", "<reset><blue>")
        .replace("§a", "<reset><green>")
        .replace("§b", "<reset><aqua>")
        .replace("§c", "<reset><red>")
        .replace("§d", "<reset><light_purple>")
        .replace("§e", "<reset><yellow>")
        .replace("§f", "<reset><white>")
        .replace("§k", "<obf>")
        .replace("§l", "<b>")
        .replace("§m", "<st>")
        .replace("§n", "<u>")
        .replace("§r", "<reset>")
        .replace("§o", "<i>");
  }

  public static Component toComponent(String str) {
    return MiniMessage.miniMessage().deserialize(toMM(str));
  }

  public static String formatDate(long date, String format) {
    return new SimpleDateFormat(format).format(new Date(date));
  }

  public static String simpleColorize(String text) {
    return text.replace("&", "§");
  }

  public static Audience getAudience(Player player) {
    BukkitAudiences bukkitAudiences = (BukkitAudiences) audienceProvider;

    return bukkitAudiences.player(player);
  }

  public static Audience getAudience(ProxiedPlayer player) {
    BungeeAudiences bungeeAudiences = (BungeeAudiences) audienceProvider;

    return bungeeAudiences.player(player);
  }

  public static String parseBukkitMessage(String messages) {
    return TextUtils.colorize(messages.replace("{prefix}", Messages.PREFIX()));
  }
}
