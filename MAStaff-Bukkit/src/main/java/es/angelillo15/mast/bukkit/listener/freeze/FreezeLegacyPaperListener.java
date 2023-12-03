package es.angelillo15.mast.bukkit.listener.freeze;

import com.nookure.mast.api.event.BukkitListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@SuppressWarnings("deprecation")
public class FreezeLegacyPaperListener extends FreezeCommonChatListener implements BukkitListener<AsyncPlayerChatEvent> {
  @Override
  @EventHandler
  public void handle(AsyncPlayerChatEvent event) {
    if (handleMessage(event.getPlayer(), event.getMessage())) event.setCancelled(true);
  }
}
