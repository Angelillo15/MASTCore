package com.nookure.staff.paper.command;

import com.google.inject.Inject;
import com.nookure.staff.api.Permissions;
import com.nookure.staff.api.PlayerWrapper;
import com.nookure.staff.api.StaffPlayerWrapper;
import com.nookure.staff.api.command.CommandData;
import com.nookure.staff.api.command.CommandSender;
import com.nookure.staff.api.command.StaffCommand;
import com.nookure.staff.api.config.ConfigurationContainer;
import com.nookure.staff.api.config.bukkit.BukkitConfig;
import com.nookure.staff.api.config.bukkit.BukkitMessages;
import com.nookure.staff.api.manager.FreezeManager;
import com.nookure.staff.api.manager.PlayerWrapperManager;
import com.nookure.staff.paper.extension.FreezePlayerExtension;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@CommandData(
    name = "freeze",
    permission = Permissions.STAFF_FREEZE
)
public class FreezeCommand extends StaffCommand {
  @Inject
  private PlayerWrapperManager<Player> playerWrapperManager;
  @Inject
  private ConfigurationContainer<BukkitMessages> messages;
  @Inject
  private FreezeManager freezeManager;
  @Inject
  private ConfigurationContainer<BukkitConfig> config;

  @Override
  protected void onStaffCommand(@NotNull StaffPlayerWrapper sender, @NotNull String label, @NotNull List<String> args) {
    Optional<FreezePlayerExtension> extension = sender.getExtension(FreezePlayerExtension.class);

    if (extension.isEmpty()) return;

    FreezePlayerExtension freezeExtension = extension.get();

    if (args.isEmpty()) {
      sender.sendMiniMessage(messages.get().freeze.freezeCommandUsage());
      return;
    }

    if (Objects.equals(args.get(0), "/exec") && args.size() > 1) {
      if (!config.get().freeze.askToExecuteCommandOnExit()) return;

      OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args.get(1));

      if (!freezeManager.isFrozen(offlinePlayer.getUniqueId())) {
        return;
      }

      Optional<FreezeManager.FreezeContainer> container = freezeManager.getFreezeContainer(offlinePlayer.getUniqueId());

      if (container.isEmpty()) {
        return;
      }

      FreezeManager.FreezeContainer freezeContainer = container.get();

      if (freezeContainer.staff() != sender.getUniqueId()) {
        return;
      }

      freezeExtension.executeFreezeCommands(sender, args.get(1));
      freezeManager.removeFreezeContainer(offlinePlayer.getUniqueId());
      sender.sendMiniMessage(messages.get().freeze.punishMessage(), "player", args.get(1));
      return;
    }

    if (Objects.equals(args.get(0), "/remove") && args.size() > 1) {
      OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args.get(1));

      if (!freezeManager.isFrozen(offlinePlayer.getUniqueId())) {
        return;
      }

      Optional<FreezeManager.FreezeContainer> container = freezeManager.getFreezeContainer(offlinePlayer.getUniqueId());

      if (container.isEmpty()) {
        return;
      }

      FreezeManager.FreezeContainer freezeContainer = container.get();

      if (freezeContainer.staff() != sender.getUniqueId()) {
        return;
      }

      freezeManager.removeFreezeContainer(offlinePlayer.getUniqueId());
      sender.sendMiniMessage(messages.get().freeze.forgiveMessage(), "player", args.get(1));
      return;
    }

    Player player = Bukkit.getPlayer(args.get(0));

    if (player == null) {
      sender.sendMiniMessage(messages.get().freeze.playerNotOnline());
      return;
    }

    if (player.hasPermission(Permissions.STAFF_FREEZE_BYPASS)) {
      sender.sendMiniMessage(messages.get().freeze.freezeBypassMessage());
      return;
    }

    Optional<PlayerWrapper> optionalTarget = playerWrapperManager.getPlayerWrapper(player);

    if (optionalTarget.isEmpty()) {
      return;
    }

    PlayerWrapper target = optionalTarget.get();

    if (args.size() >= 2 && args.get(1).equalsIgnoreCase("pause")) {
      freezeExtension.pauseFreeze(target, sender);
      return;
    }

    if (freezeManager.isFrozen(target.getUniqueId())) {
      freezeExtension.unfreezePlayer(target);
    } else {
      freezeExtension.freezePlayer(target);
    }
  }

  @Override
  public @NotNull List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String label, @NotNull List<String> args) {
    switch (args.size()) {
      case 1 -> {
        return getSuggestionFilter(Bukkit.getOnlinePlayers().stream().map(Player::getName).toList(), args.getFirst());
      }
      case 2 -> {
        return List.of("pause");
      }
    }

    return super.onTabComplete(sender, label, args);
  }
}
