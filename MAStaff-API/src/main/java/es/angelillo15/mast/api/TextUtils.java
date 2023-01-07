package es.angelillo15.mast.api;

import es.angelillo15.mast.api.chat.api.ChatColor;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtils {
    private static final String WITH_DELIMITER = "((?<=%1$s)|(?=%1$s))";
    private static final Pattern HEX_PATTERN = Pattern.compile("#[a-fA-F0-9]{6}}");

    public static String colorize(String text) {
        if (MAStaffInstance.version < 16) {
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
}
