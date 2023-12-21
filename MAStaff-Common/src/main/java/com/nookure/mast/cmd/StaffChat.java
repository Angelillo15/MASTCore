package com.nookure.mast.cmd;

import com.google.inject.Inject;
import com.nookure.mast.api.cmd.Command;
import com.nookure.mast.api.cmd.CommandData;
import com.nookure.mast.api.cmd.sender.*;
import com.nookure.mast.api.event.EventManager;
import com.nookure.mast.api.event.PluginMessageManager;
import com.nookure.mast.api.event.message.BackendStaffChatEnableDisableEvent;
import com.nookure.mast.api.event.staff.staffchat.StaffChatMessageSentEvent;
import es.angelillo15.mast.api.IServerUtils;
import es.angelillo15.mast.api.config.common.CommonMessages;
import es.angelillo15.mast.api.managers.StaffChatManager;
import org.jetbrains.annotations.NotNull;

@CommandData(
    name = "StaffChat",
    permission = "mast.staffchat",
    aliases = {"sc"},
    description = "Send a message to all staff members."
)
public class StaffChat extends Command {
  @Inject
  private IServerUtils serverUtils;
  @Inject
  private StaffChatManager staffChatManager;
  @Inject
  private EventManager eventManager;
  @Inject
  @SuppressWarnings("rawtypes")
  private PluginMessageManager pluginMessageManager;

  @Override
  @SuppressWarnings({"unchecked"})
  public void onCommand(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
    if (args.length == 0) {
      if (sender instanceof PlayerCommandSender) {
        staffChatManager.toggleAndAdvert(sender);
        return;
      }

      if (sender instanceof ConsoleCommandSender) {
        sender.sendMessage("<red>Usage: /staffchat <message>");
        return;
      }

      if (sender instanceof ProxiedPlayerCommandSender proxyPlayer) {
        pluginMessageManager.sendEvent(new BackendStaffChatEnableDisableEvent(sender.getName()), proxyPlayer.getPlayer());
        return;
      }

      if (sender instanceof VelocityPlayerCommandSender velocityPlayer) {
        pluginMessageManager.sendEvent(new BackendStaffChatEnableDisableEvent(sender.getName()), velocityPlayer.getPlayer());
        return;
      }
    }

    String message = String.join(" ", args);

    String formattedMessage = CommonMessages.StaffChat.INSTANCE.format()
        .replace("{server}", sender.getServerName())
        .replace("{player}", sender.getName())
        .replace("{message}", message);

    serverUtils.broadcastMessage(formattedMessage, "mast.staffchat");

    eventManager.fireEvent(new StaffChatMessageSentEvent(message, sender.getName(), sender.getServerName()));
  }
}
