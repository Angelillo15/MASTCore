package com.nookure.staff.paper.extension;

import com.google.inject.Inject;
import com.nookure.staff.api.Permissions;
import com.nookure.staff.api.PlayerWrapper;
import com.nookure.staff.api.StaffPlayerWrapper;
import com.nookure.staff.api.annotation.PluginMessageMessenger;
import com.nookure.staff.api.config.ConfigurationContainer;
import com.nookure.staff.api.config.bukkit.BukkitConfig;
import com.nookure.staff.api.config.bukkit.BukkitMessages;
import com.nookure.staff.api.event.server.BroadcastMessage;
import com.nookure.staff.api.event.server.ExecuteCommandAsProxy;
import com.nookure.staff.api.extension.StaffPlayerExtension;
import com.nookure.staff.api.manager.FreezeManager;
import com.nookure.staff.api.messaging.EventMessenger;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import static java.util.Objects.requireNonNull;

public class FreezePlayerExtension extends StaffPlayerExtension {
  private final StaffPlayerWrapper player;
  @Inject
  private FreezeManager freezeManager;
  @Inject
  private ConfigurationContainer<BukkitConfig> config;
  @Inject
  private ConfigurationContainer<BukkitMessages> messages;
  @Inject
  private EventMessenger eventMessenger;
  @Inject
  @PluginMessageMessenger
  private EventMessenger pluginMessageManager;

  public FreezePlayerExtension(StaffPlayerWrapper player) {
    super(player);
    this.player = player;
  }

  public void freezePlayer(PlayerWrapper target) {
    freezeManager.freeze(target, player, System.currentTimeMillis() + config.get().freeze.freezeTimer());

    target.sendMiniMessage(messages.get().freeze.frozenMessage());

    eventMessenger.publish(player, new BroadcastMessage(messages.get().freeze.staffFrozenMessage()
        .replace("{player}", target.getName())
        .replace("{staff}", player.getName()),
        Permissions.STAFF_FREEZE)
    );
  }

  public void unfreezePlayer(PlayerWrapper target) {
    freezeManager.removeFreezeContainer(target.getUniqueId());

    target.sendMiniMessage(messages.get().freeze.unfrozenMessage());

    eventMessenger.publish(player, new BroadcastMessage(messages.get().freeze.staffUnfrozenMessage()
        .replace("{player}", target.getName())
        .replace("{staff}", player.getName()),
        Permissions.STAFF_FREEZE)
    );
  }

  public void executeFreezeCommands(@NotNull PlayerWrapper target, @NotNull String name) {
    requireNonNull(target, "Target cannot be null");
    requireNonNull(name, "Name cannot be null");

    config.get().freeze.commands().forEach(command -> execute(target, command, name));
  }

  public void execute(@NotNull PlayerWrapper player, @NotNull String command, @NotNull String name) {
    requireNonNull(player, "Player cannot be null");
    requireNonNull(command, "Command cannot be null");
    requireNonNull(name, "Name cannot be null");

    if (command.contains("{player}")) {
      command = command.replace("{player}", name);
    }

    if (command.startsWith("/")) {
      command = command.substring(1);
    }

    if (command.startsWith("proxy:")) {
      command = command.substring(6);

      if (command.startsWith(" ")) command = command.substring(1);

      pluginMessageManager.publish(player, new ExecuteCommandAsProxy(command));
    }

    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
  }
}