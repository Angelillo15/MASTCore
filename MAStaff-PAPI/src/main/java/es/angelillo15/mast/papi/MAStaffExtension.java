package es.angelillo15.mast.papi;

import com.google.inject.Inject;
import es.angelillo15.mast.api.Constants;
import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.papi.placeholders.ServerCountPlaceholder;
import es.angelillo15.mast.papi.placeholders.StaffCountPlaceholder;
import es.angelillo15.mast.papi.placeholders.StaffModePlaceholder;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class MAStaffExtension extends PlaceholderExpansion {
  @Inject private MAStaffInstance instance;

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
  public boolean register() {
    PlaceholderManager.registerPlaceholder(
        instance.getInjector().getInstance(StaffModePlaceholder.class));
    PlaceholderManager.registerPlaceholder(
        instance.getInjector().getInstance(StaffCountPlaceholder.class));
    PlaceholderManager.registerPlaceholder(
        instance.getInjector().getInstance(ServerCountPlaceholder.class));

    return super.register();
  }

  @Override
  public String onPlaceholderRequest(Player player, String params) {
    Placeholder placeholder = PlaceholderManager.getPlaceholder(params);

    if (placeholder == null) return "Placeholder not found";

    return placeholder.onPlaceholderRequest(player, params);
  }
}
