package com.nookure.mast.bungee.listener;

import com.google.inject.Inject;
import com.nookure.mast.api.event.EventManager;
import com.nookure.mast.api.event.MastSubscribe;
import com.nookure.mast.api.event.message.BackendStaffChatEvent;
import com.nookure.mast.api.event.staff.staffchat.StaffChatMessageSentEvent;
import es.angelillo15.mast.api.IServerUtils;
import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.api.config.common.CommonMessages;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;

public class OnPlayerChat {
  @Inject
  private IServerUtils serverUtils;
  @Inject
  private EventManager eventManager;
  private final ProxyServer proxy = ProxyServer.getInstance();
  @MastSubscribe
  public void onPlayerChat(BackendStaffChatEvent event) {
    String server = event.server();

    ProxiedPlayer player = proxy.getPlayer(event.player());

    if (player == null) {
      return;
    }

    Server bungeeServer = player.getServer();

    if (!(bungeeServer == null)) {
      server = bungeeServer.getInfo().getName();
    }

    String formattedMessage = CommonMessages.StaffChat.INSTANCE.format()
        .replace("{server}", server)
        .replace("{player}", event.player())
        .replace("{message}", TextUtils.colorize(event.message()));

    serverUtils.broadcastMessage(formattedMessage, "mast.staffchat");

    eventManager.fireEvent(new StaffChatMessageSentEvent(event.message(), event.player(), server));
  }
}
