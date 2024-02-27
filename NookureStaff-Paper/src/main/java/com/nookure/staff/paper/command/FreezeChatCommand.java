package com.nookure.staff.paper.command;

import com.google.inject.Inject;
import com.nookure.staff.api.Permissions;
import com.nookure.staff.api.StaffPlayerWrapper;
import com.nookure.staff.api.command.CommandData;
import com.nookure.staff.api.command.StaffCommand;
import com.nookure.staff.api.config.ConfigurationContainer;
import com.nookure.staff.api.config.bukkit.BukkitMessages;
import com.nookure.staff.api.manager.FreezeManager;
import com.nookure.staff.api.manager.PlayerWrapperManager;
import com.nookure.staff.api.util.ServerUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

@CommandData(
    name = "freezechat",
    permission = Permissions.STAFF_FREEZE,
    usage = "/freezechat <player>"
)
public class FreezeChatCommand extends StaffCommand {
  @Inject
  private ConfigurationContainer<BukkitMessages> messages;
  @Inject
  private PlayerWrapperManager<Player> playerWrapperManager;
  @Inject
  private FreezeManager freezeManager;
  @Inject
  private ServerUtils serverUtils;

  @Override
  protected void onStaffCommand(@NotNull StaffPlayerWrapper sender, @NotNull String label, @NotNull List<String> args) {
    if (args.size() < 2) {
      sender.sendMiniMessage(messages.get().freeze.freezeChatUsage());
      return;
    }

    String playerName = args.get(0);
    String messageArg = String.join(" ", args.subList(1, args.size()));

    Player player = Bukkit.getPlayer(playerName);

    if (player == null) {
      sender.sendMiniMessage(messages.get().freeze.playerNotOnline());
      return;
    }

    Optional<FreezeManager.FreezeContainer> optionalContainer = freezeManager
        .getFreezeContainer(player.getUniqueId());

    if (optionalContainer.isEmpty()) {
      sender.sendMiniMessage(messages.get().freeze.playerNotOnline());
      return;
    }

    FreezeManager.FreezeContainer container = optionalContainer.get();

    String message = messages.get().freeze.freezeStaffChatFormat()
        .replace("{player}", sender.getName())
        .replace("{target}", playerName)
        .replace("{message}", messageArg);

    playerWrapperManager.getPlayerWrapper(container.target()).ifPresent(playerWrapper -> {
      playerWrapper.sendMiniMessage(message);
    });

    serverUtils.broadcast(message, Permissions.STAFF_FREEZE);
  }
}
