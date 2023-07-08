package es.angelillo15.mast.glow;

import es.angelillo15.mast.api.Constants;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class MAStaffExtension extends PlaceholderExpansion {
    @Override
    public String getIdentifier() {
        return "mastaff";
    }

    @Override
    public String getAuthor() {
        return "Angelillo15";
    }

    @Override
    public String getVersion() {
        return Constants.VERSION;
    }

    @Override
    public String onPlaceholderRequest(Player player, String params) {
        Placeholder placeholder = PlaceholderManager.getPlaceholder(params);

        if (placeholder == null) return "Placeholder not found";

        return placeholder.onPlaceholderRequest(player, params);
    }
}
