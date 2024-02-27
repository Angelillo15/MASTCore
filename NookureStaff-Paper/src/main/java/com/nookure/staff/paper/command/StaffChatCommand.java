package com.nookure.staff.paper.command;

import com.google.inject.Inject;
import com.nookure.staff.api.Permissions;
import com.nookure.staff.api.StaffPlayerWrapper;
import com.nookure.staff.api.command.CommandData;
import com.nookure.staff.api.command.StaffCommand;
import com.nookure.staff.api.config.ConfigurationContainer;
import com.nookure.staff.api.config.bukkit.BukkitConfig;
import com.nookure.staff.api.config.bukkit.BukkitMessages;
import com.nookure.staff.api.util.ServerUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@CommandData(
    name = "staffchat",
    permission = Permissions.STAFF_CHAT,
    aliases = {"sc"},
    usage = "/staffchat <message>",
    description = "Send a message to all staff members"
)
public class StaffChatCommand extends StaffCommand {
  @Inject
  private ServerUtils serverUtils;
  @Inject
  private ConfigurationContainer<BukkitMessages> messages;
  @Inject
  private ConfigurationContainer<BukkitConfig> config;

  @Override
  protected void onStaffCommand(@NotNull StaffPlayerWrapper sender, @NotNull String label, @NotNull List<String> args) {
    if (args.isEmpty()) {
      if (sender.isStaffChatAsDefault()) {
        sender.setStaffChatAsDefault(false);
        sender.sendMiniMessage(messages.get().staffChat.disable());
        return;
      }

      sender.setStaffChatAsDefault(true);
      sender.sendMiniMessage(messages.get().staffChat.enable());

      return;
    }

    String messageArgs = String.join(" ", args);

    String message = messages.get().staffChat.format()
        .replace("{player}", sender.getName())
        .replace("{server}", config.get().getServerName())
        .replace("{message}", messageArgs);

    serverUtils.broadcast(message, Permissions.STAFF_CHAT);
  }
}
