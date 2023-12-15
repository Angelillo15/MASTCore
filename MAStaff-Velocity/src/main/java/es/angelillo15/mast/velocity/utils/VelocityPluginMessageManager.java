package es.angelillo15.mast.velocity.utils;

import com.google.inject.Inject;
import com.nookure.mast.api.event.Channels;
import com.nookure.mast.api.event.Event;
import com.nookure.mast.api.event.PluginMessageManager;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import es.angelillo15.mast.api.ILogger;
import es.angelillo15.mast.velocity.MAStaff;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Objects;

public class VelocityPluginMessageManager extends PluginMessageManager<Player> {
  @Inject
  private MAStaff plugin;
  @Inject
  private ILogger logger;

  private final static MinecraftChannelIdentifier CHANNEL_IDENTIFIER = MinecraftChannelIdentifier.from(Channels.EVENTS);

  @Override
  public void sendEvent(@NotNull Event event, @NotNull Player player) {
    Objects.requireNonNull(event);
    Objects.requireNonNull(player);

    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
      objectOutputStream.writeObject(event);

      player.sendPluginMessage(CHANNEL_IDENTIFIER, outputStream.toByteArray());
    } catch (IOException e) {
      logger.error(
          "Error while sending event " +
              event.getClass().getSimpleName() +
              " using player " +
              player.getUsername() +
              " on channel " +
              Channels.EVENTS
      );
      if (plugin.isDebug()) {
        throw new RuntimeException(e);
      }
    }
  }
}
