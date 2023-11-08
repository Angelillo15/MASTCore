package es.angelillo15.mast.bukkit.listener.freeze;

import com.google.inject.Inject;
import com.nookure.mast.api.manager.FreezeManager;
import es.angelillo15.mast.api.IServerUtils;
import es.angelillo15.mast.api.config.bukkit.Messages;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;

public abstract class FreezeCommonChatListener {
  @Inject
  private IServerUtils serverUtils;

  @Inject
  private FreezeManager freezeManager;

  public boolean handleMessage(Player player, String message) {
    if (!freezeManager.isFrozen(player)) {
      return false;
    }

    String formattedMessage = Messages.FREEZE_STAFF_CHAT_FORMAT()
        .replace("{player}", player.getName())
        .replace("{message}", message);

    serverUtils.broadcastMessage(formattedMessage, "mast.freeze");
    player.sendMessage(formattedMessage);
    return true;
  }

  public boolean handleMessage(Player player, Component cmp) {
    return handleMessage(player, LegacyComponentSerializer.legacySection().serialize(cmp));
  }
}
