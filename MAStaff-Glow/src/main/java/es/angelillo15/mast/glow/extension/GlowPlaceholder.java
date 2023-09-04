package es.angelillo15.mast.glow.extension;

import com.google.inject.Inject;
import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.managers.StaffManager;
import es.angelillo15.mast.papi.Placeholder;
import es.angelillo15.mast.papi.PlaceholderData;
import org.bukkit.entity.Player;

@PlaceholderData(key = "glow:color")
public class GlowPlaceholder extends Placeholder {
  @Inject private StaffManager staffManager;

  @Override
  public String onPlaceholderRequest(Player player, String params) {
    if (player == null) return "";

    if (!staffManager.isStaffPlayer(player)) {
      return "";
    }

    IStaffPlayer staffPlayer = staffManager.getStaffPlayer(player);

    if (staffPlayer.isStaffMode()) {
      return staffPlayer.getGlowPlayer().getColor().toString();
    }

    return "";
  }
}
