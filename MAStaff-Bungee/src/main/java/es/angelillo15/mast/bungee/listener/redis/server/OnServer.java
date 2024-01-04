package es.angelillo15.mast.bungee.listener.redis.server;

import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.api.event.EventHandler;
import es.angelillo15.mast.api.event.Listener;
import es.angelillo15.mast.api.redis.events.server.ServerConnectedEvent;
import es.angelillo15.mast.api.redis.events.server.ServerDisconnectedEvent;
import com.nookure.mast.bungee.MAStaff;
import es.angelillo15.mast.bungee.config.Messages;

public class OnServer implements Listener {
  @EventHandler
  public void onServerConnectedEvent(ServerConnectedEvent event) {
    MAStaff.getInstance()
        .getPLogger()
        .info(
            TextUtils.simpleColorize(
                Messages.getPrefix() + " &rServer connected: &b" + event.getServerName()));
  }

  @EventHandler
  public void onServerDisconnectedEvent(ServerDisconnectedEvent event) {
    MAStaff.getInstance()
        .getPLogger()
        .info(
            TextUtils.simpleColorize(
                Messages.getPrefix() + " &rServer disconnected: &b" + event.getServerName()));
  }
}
