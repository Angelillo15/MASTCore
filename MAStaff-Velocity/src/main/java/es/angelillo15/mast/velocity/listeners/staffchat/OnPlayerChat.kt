package es.angelillo15.mast.velocity.listeners.staffchat

import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.player.PlayerChatEvent
import es.angelillo15.mast.handler.OnStaffMessageEvent

class OnPlayerChat : OnStaffMessageEvent() {
  @Subscribe
  fun onPlayerChat(event: PlayerChatEvent) {
    if (onStaffMessageEvent(
                    event.player.username, event.message, event.player.currentServer.get().serverInfo.name
            )
    ) {
      event.result = PlayerChatEvent.ChatResult.denied()
    }
  }
}