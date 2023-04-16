package es.angelillo15.mast.api;

import es.angelillo15.mast.api.chat.api.ChatColor;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtils {
    private static final String WITH_DELIMITER = "((?<=%1$s)|(?=%1$s))";
    private static final Pattern HEX_PATTERN = Pattern.compile("#[a-fA-F0-9]{6}}");
    private static final long SECOND = 1000;
    private static final long MINUTE = 60 * SECOND;
    private static final long HOUR = 60 * MINUTE;
    private static final long DAY = 24 * HOUR;

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

        }else {
            while (match.find()) {
                String color = text.substring(match.start(), match.end());
                text = text.replace(color, ChatColor.of(color) + "");
                match = HEX_PATTERN.matcher(text);
            }
            finalText.append(text);
        }

        return ChatColor.translateAlternateColorCodes('&', finalText.toString());
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
        if (uptime > 0) {
            if (buf.length() > 0) {
                buf.append(", ");
            }
            buf.append(uptime);
            buf.append("ms");
        }
        return buf.toString();
    }

    public static String simpleColorize(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
