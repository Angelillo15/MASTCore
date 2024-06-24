package com.nookure.staff.paper;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.nookure.staff.api.Logger;
import com.nookure.staff.api.NookureStaff;
import com.nookure.staff.api.PlayerWrapper;
import com.nookure.staff.api.util.ServerUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.objectmapping.meta.Comment;

import java.util.Set;
import java.util.UUID;

public class PaperPlayerWrapper implements PlayerWrapper {
  @Comment("Package protected value")
  Player player;
  @Inject
  private JavaPlugin plugin;
  @Inject
  private NookureStaff nookPlugin;
  @Inject
  private Logger logger;

  @Override
  public void sendMessage(@NotNull Component component) {
    if (ServerUtils.isPaper) {
      player.sendMessage(component);
      return;
    }

    player.sendMessage(LegacyComponentSerializer.legacySection().serialize(component));
  }

  @Override
  public void sendActionbar(@NotNull Component component) {
    if (!ServerUtils.isPaper) {
      return;
    }

    player.sendActionBar(component);
  }

  @Override
  public void sendPluginMessage(@NotNull String channel, byte @NotNull [] message) {
    logger.debug("Sending plugin message to " + player.getName() + " on channel " + channel);
    player.sendPluginMessage(plugin, channel, message);
  }

  @Override
  public void teleport(@NotNull PlayerWrapper to) {
    if (ServerUtils.isPaper)
      player.teleportAsync(((PaperPlayerWrapper) to).getPlayer().getLocation());
    else
      player.teleport(((PaperPlayerWrapper) to).getPlayer().getLocation());
  }

  @Override
  public void sendMiniMessage(@NotNull String message, String... placeholders) {
    PlayerWrapper.super.sendMiniMessage(message.replace("{prefix}", nookPlugin.getPrefix()), placeholders);
  }

  @Override
  public @NotNull Set<String> getListeningPluginChannels() {
    return player.getListeningPluginChannels();
  }

  @Override
  public int getPing() {
    return player.getPing();
  }

  @Override
  @SuppressWarnings("deprecation")
  public @NotNull Component getDisplayName() {
    if (ServerUtils.isPaper) {
      return player.displayName();
    }

    return Component.text(player.getDisplayName());
  }

  @Override
  public @NotNull String getName() {
    return player.getName();
  }

  @Override
  public @NotNull UUID getUniqueId() {
    return player.getUniqueId();
  }

  @Override
  public boolean hasPermission(@NotNull String permission) {
    return player.hasPermission(permission);
  }

  @Override
  public boolean isPlayer() {
    return true;
  }

  @NotNull
  public Player getPlayer() {
    if (player == null) {
      throw new IllegalStateException("Player cannot be null");
    }

    return player;
  }

  public static class Builder {
    private final PaperPlayerWrapper playerWrapper;

    private Builder(PaperPlayerWrapper playerWrapper) {
      this.playerWrapper = playerWrapper;
    }

    public static Builder create(Injector injector) {
      return new Builder(injector.getInstance(PaperPlayerWrapper.class));
    }

    public Builder setPlayer(Player player) {
      playerWrapper.player = player;
      return this;
    }

    public PaperPlayerWrapper build() {
      if (playerWrapper.player == null) {
        throw new IllegalStateException("Player cannot be null");
      }

      return playerWrapper;
    }
  }
}
