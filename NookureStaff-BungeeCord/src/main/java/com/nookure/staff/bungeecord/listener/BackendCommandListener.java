package com.nookure.staff.bungeecord.listener;

import com.nookure.staff.api.event.NookSubscribe;
import com.nookure.staff.api.event.server.ExecuteCommandAsProxy;
import net.md_5.bungee.api.ProxyServer;

public class BackendCommandListener {
  @NookSubscribe
  public void onCommand(ExecuteCommandAsProxy event) {
    ProxyServer.getInstance().getPluginManager().dispatchCommand(ProxyServer.getInstance().getConsole(), event.command());
  }
}
