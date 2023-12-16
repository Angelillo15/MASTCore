package es.angelillo15.mast.velocity.listeners.staffchat

import com.google.inject.Inject
import com.nookure.mast.api.event.EventManager
import com.nookure.mast.api.event.MastSubscribe
import com.nookure.mast.api.event.message.BackendStaffChatEvent
import com.nookure.mast.api.event.staff.staffchat.StaffChatMessageSentEvent
import com.velocitypowered.api.proxy.ProxyServer
import es.angelillo15.mast.api.IServerUtils
import es.angelillo15.mast.api.config.common.CommonMessages

class OnPlayerChat {
  @Inject private lateinit var serverUtils: IServerUtils;
  @Inject private lateinit var eventManager: EventManager;
  @Inject private lateinit var proxy: ProxyServer;

  @MastSubscribe
  fun onBackEndPlayerChatEvent(event: BackendStaffChatEvent) {
    var server = event.server()

    proxy.getPlayer(event.player()).ifPresent {
      it.currentServer.ifPresent { s ->  server = s.serverInfo.name }
    }

    val formattedMessage = CommonMessages.StaffChat.format()
        .replace("{server}", server)
        .replace("{player}", event.player())
        .replace("{message}", event.message())

    serverUtils.broadcastMessage(formattedMessage, "mast.staffchat")

    eventManager.fireEvent(StaffChatMessageSentEvent(event.message(), event.player(), server))
  }
}