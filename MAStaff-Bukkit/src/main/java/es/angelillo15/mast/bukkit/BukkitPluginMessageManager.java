package es.angelillo15.mast.bukkit;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nookure.mast.api.event.Channels;
import com.nookure.mast.api.event.Event;
import com.nookure.mast.api.event.PluginMessageManager;
import es.angelillo15.mast.api.ILogger;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Objects;

@Singleton
public class BukkitPluginMessageManager extends PluginMessageManager<Player> {
  @Inject
  private MAStaff plugin;
  @Inject
  private ILogger logger;

  @Override
  public void sendEvent(@NotNull Event event, @NotNull Player player) {
    Objects.requireNonNull(event);
    Objects.requireNonNull(player);

    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
      objectOutputStream.writeObject(event);

      player.sendPluginMessage(plugin, Channels.EVENTS, outputStream.toByteArray());
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
