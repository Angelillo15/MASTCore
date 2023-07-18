package es.angelillo15.mast.papi;

import es.angelillo15.mast.api.Constants;
import es.angelillo15.mast.papi.placeholders.ServerCountPlaceholder;
import es.angelillo15.mast.papi.placeholders.StaffCountPlaceholder;
import es.angelillo15.mast.papi.placeholders.StaffModePlaceholder;
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

    public MAStaffExtension() {
        PlaceholderManager.registerPlaceholder(new StaffModePlaceholder());
        PlaceholderManager.registerPlaceholder(new StaffCountPlaceholder());
        PlaceholderManager.registerPlaceholder(new ServerCountPlaceholder());
    }

    @Override
    public String onPlaceholderRequest(Player player, String params) {
        Placeholder placeholder = PlaceholderManager.getPlaceholder(params);

        if (placeholder == null) return "Placeholder not found";

        return placeholder.onPlaceholderRequest(player, params);
    }
}
