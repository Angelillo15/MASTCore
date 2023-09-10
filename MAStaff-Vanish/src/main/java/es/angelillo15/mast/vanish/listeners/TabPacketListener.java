package es.angelillo15.mast.vanish.listeners;

import com.google.inject.Inject;
import es.angelillo15.mast.api.vanish.VanishDataManager;
import es.angelillo15.mast.bukkit.nms.LightInjector;
import io.netty.channel.Channel;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;

public class TabPacketListener extends LightInjector {
  @Inject
  public TabPacketListener(@NotNull Plugin plugin) {
    super(plugin);
  }

  @Override
  protected @Nullable Object onPacketReceiveAsync(@Nullable Player sender, @NotNull Channel channel, @NotNull Object packet) {
    return packet;
  }

  @Override
  protected @Nullable Object onPacketSendAsync(@Nullable Player receiver, @NotNull Channel channel, @NotNull Object packet) {
    if (!(packet instanceof ClientboundPlayerInfoUpdatePacket packetUpdate)) {
      return packet;
    }

    AtomicBoolean continuePacket = new AtomicBoolean(false);

    packetUpdate.a().forEach(action -> {
      if (action == ClientboundPlayerInfoUpdatePacket.a.a) {
        continuePacket.set(true);
      }
    });

    if (!continuePacket.get()) return packet;

    packetUpdate.d().forEach(playerInfoData -> {
      VanishDataManager.getVanishedPlayers().forEach(vanishedPlayer -> {
        if (playerInfoData.b().getId().equals(vanishedPlayer.getPlayer().getUniqueId())) {
          packetUpdate.d().remove(playerInfoData);
        }
      });
    });

    return packet;
  }
}
