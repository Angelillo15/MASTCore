package es.angelillo15.mast.papi.placeholders;

import com.google.inject.Inject;
import com.nookure.mast.api.manager.FreezeManager;
import es.angelillo15.mast.papi.Placeholder;
import es.angelillo15.mast.papi.PlaceholderData;
import org.bukkit.entity.Player;

@PlaceholderData(key = "freeze_count")
public class FrozenPlayerCountPlaceholder extends Placeholder {
  @Inject
  private FreezeManager freezeManager;
  @Override
  public String onPlaceholderRequest(Player player, String params) {
    if (!freezeManager.getFrozenPlayers().isEmpty()) {
      return String.valueOf(freezeManager.getFrozenPlayers().size());
    }

    return "0";
  }
}
