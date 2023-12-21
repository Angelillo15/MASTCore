package es.angelillo15.mast.papi;

import com.google.inject.Inject;
import com.nookure.mast.api.MAStaff;
import es.angelillo15.mast.api.Constants;
import es.angelillo15.mast.papi.placeholders.FrozenPlayerCountPlaceholder;
import es.angelillo15.mast.papi.placeholders.ServerCountPlaceholder;
import es.angelillo15.mast.papi.placeholders.StaffCountPlaceholder;
import es.angelillo15.mast.papi.placeholders.StaffModePlaceholder;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MAStaffExtension extends PlaceholderExpansion {
  @Inject private MAStaff instance;

  @Override
  public @NotNull String getIdentifier() {
    return "mastaff";
  }

  @Override
  public @NotNull String getAuthor() {
    return "Angelillo15";
  }

  @Override
  public @NotNull String getVersion() {
    return Constants.VERSION;
  }

  @Override
  public boolean register() {
    PlaceholderManager.registerPlaceholder(
        instance.getInjector().getInstance(StaffModePlaceholder.class)
    );

    PlaceholderManager.registerPlaceholder(
        instance.getInjector().getInstance(StaffCountPlaceholder.class)
    );

    PlaceholderManager.registerPlaceholder(
        instance.getInjector().getInstance(ServerCountPlaceholder.class)
    );

    PlaceholderManager.registerPlaceholder(
        instance.getInjector().getInstance(FrozenPlayerCountPlaceholder.class)
    );

    return super.register();
  }

  @Override
  public String onPlaceholderRequest(Player player, @NotNull String params) {
    Placeholder placeholder = PlaceholderManager.getPlaceholder(params);

    if (placeholder == null) return "Placeholder not found";

    return placeholder.onPlaceholderRequest(player, params);
  }
}
