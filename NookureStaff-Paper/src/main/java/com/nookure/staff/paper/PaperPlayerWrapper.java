package com.nookure.staff.paper;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.nookure.staff.api.Logger;
import com.nookure.staff.api.NookureStaff;
import com.nookure.staff.api.PlayerWrapper;
import com.nookure.staff.api.model.PlayerModel;
import com.nookure.staff.api.state.PlayerState;
import com.nookure.staff.api.state.WrapperState;
import com.nookure.staff.api.util.DefaultFontInfo;
import com.nookure.staff.api.util.ServerUtils;
import io.ebean.Database;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class PaperPlayerWrapper implements PlayerWrapper {
  Player player;
  @Inject
  private JavaPlugin plugin;
  @Inject
  private NookureStaff nookPlugin;
  @Inject
  private Logger logger;
  @Inject
  private AtomicReference<Database> db;
  protected PlayerModel playerModel;
  private final WrapperState state = new WrapperState();

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
    if (message.isBlank()) return;

    if (placeholders.length % 2 != 0) {
      throw new IllegalArgumentException("Placeholders must be in pairs");
    }

    for (int i = 0; i < placeholders.length; i += 2) {
      message = message.replace("{" + placeholders[i] + "}", placeholders[i + 1]);
    }

    message = message.replace("{prefix}", nookPlugin.getPrefix());
    message = DefaultFontInfo.centerIfContains(message);

    sendMessage(MiniMessage.miniMessage().deserialize(message));
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
    private final HashMap<Class<? extends PlayerState>, PlayerState> states = new HashMap<>();

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

    public Builder addState(Class<? extends PlayerState> state) {
      PlayerState playerState;
      try {
        playerState = state.getConstructor(PlayerWrapper.class).newInstance(playerWrapper);
      } catch (Exception e) {
        throw new IllegalStateException("An error occurred while adding the state");
      }

      states.put(state, playerState);
      return this;
    }

    public Builder setModel(PlayerModel model) {
      if (model == null) {
        return this;
      }

      playerWrapper.playerModel = model;
      return this;
    }

    public PaperPlayerWrapper build() {
      if (playerWrapper.player == null) {
        throw new IllegalStateException("Player cannot be null");
      }

      if (playerWrapper.playerModel == null) {
        playerWrapper.playerModel = playerWrapper.getPlayerModel();
      }

      states.forEach((k, v) -> playerWrapper.state.setState(v));

      return playerWrapper;
    }
  }

  @Override
  public PlayerModel getPlayerModel() {
    if (playerModel == null) {
      throw new IllegalStateException("Player model module is disabled");
    }

    return playerModel;
  }

  @Override
  public void kick(@NotNull String reason) {
    kick(MiniMessage.miniMessage().deserialize(reason));
  }

  @Override
  public void kick(@NotNull Component reason) {
    if (ServerUtils.isPaper) {
      player.kick(reason);
    } else {
      //noinspection deprecation (for legacy servers)
      player.kickPlayer(LegacyComponentSerializer.legacySection().serialize(reason));
    }
  }

  @Override
  public @NotNull WrapperState getState() {
    return state;
  }
}
