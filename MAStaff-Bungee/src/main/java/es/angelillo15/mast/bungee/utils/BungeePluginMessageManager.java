package es.angelillo15.mast.bungee.utils;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nookure.mast.api.event.Channels;
import com.nookure.mast.api.event.Event;
import com.nookure.mast.api.event.PluginMessageManager;
import es.angelillo15.mast.api.ILogger;
import com.nookure.mast.bungee.MAStaff;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Objects;

@Singleton
public class BungeePluginMessageManager extends PluginMessageManager<ProxiedPlayer> {
  @Inject
  private ILogger logger;
  @Inject
  private MAStaff plugin;

  @Override
  public void sendEvent(@NotNull Event event, @NotNull ProxiedPlayer player) {
    Objects.requireNonNull(event);
    Objects.requireNonNull(player);

    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
      objectOutputStream.writeObject(event);

      player.getServer().getInfo().sendData(Channels.EVENTS, outputStream.toByteArray());
      logger.debug(
          "Sent event " +
              event.getClass().getSimpleName() +
              " using player " + player.getName() +
              " on channel " + Channels.EVENTS
      );
    } catch (IOException e) {
      logger.error(
          "Error while sending event " +
              event.getClass().getSimpleName() +
              " using player " +
              player.getName() +
              " on channel " +
              Channels.EVENTS
      );
      if (plugin.isDebug()) {
        throw new RuntimeException(e);
      }
    }
  }
}
