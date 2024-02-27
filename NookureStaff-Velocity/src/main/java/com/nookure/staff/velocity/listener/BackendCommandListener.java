package com.nookure.staff.velocity.listener;

import com.google.inject.Inject;
import com.nookure.staff.api.event.NookSubscribe;
import com.nookure.staff.api.event.server.ExecuteCommandAsProxy;
import com.velocitypowered.api.proxy.ProxyServer;

public class BackendCommandListener {
  @Inject
  private ProxyServer server;
  @NookSubscribe
  public void onCommand(ExecuteCommandAsProxy event) {
    server.getCommandManager().executeAsync(server.getConsoleCommandSource(), event.command());
  }
}
