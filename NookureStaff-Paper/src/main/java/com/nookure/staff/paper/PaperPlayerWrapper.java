package com.nookure.staff.paper;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.nookure.staff.api.PlayerWrapper;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.objectmapping.meta.Comment;

import java.util.Set;
import java.util.UUID;

public class PaperPlayerWrapper implements PlayerWrapper {
  @Inject
  private JavaPlugin plugin;
  @Comment("Package protected value") Player player;

  @Override
  public void sendMessage(@NotNull Component component) {
    player.sendMessage(component);
  }

  @Override
  public void sendPluginMessage(@NotNull String channel, byte @NotNull [] message) {
    player.sendPluginMessage(plugin, channel, message);
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
  public @NotNull Component getDisplayName() {
    return player.displayName();
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

  @NotNull
  public Player getPlayer() {
    if (player == null) {
      throw new IllegalStateException("Player cannot be null");
    }

    return player;
  }
}