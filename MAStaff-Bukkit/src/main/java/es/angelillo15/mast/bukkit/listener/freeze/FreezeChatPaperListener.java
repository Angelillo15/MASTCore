package es.angelillo15.mast.bukkit.listener.freeze;

import com.nookure.mast.api.event.BukkitListener;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;

public class FreezeChatPaperListener extends FreezeCommonChatListener implements BukkitListener<AsyncChatEvent> {
  @Override
  @EventHandler
  public void handle(AsyncChatEvent event) {
    if (handleMessage(event.getPlayer(), event.message())) event.setCancelled(true);
  }
}
