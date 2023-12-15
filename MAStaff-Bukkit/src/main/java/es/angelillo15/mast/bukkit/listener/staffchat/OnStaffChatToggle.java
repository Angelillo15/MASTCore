package es.angelillo15.mast.bukkit.listener.staffchat;

import com.google.inject.Inject;
import com.nookure.mast.api.event.MastSubscribe;
import com.nookure.mast.api.event.message.BackendStaffChatEnableDisableEvent;
import com.nookure.mast.api.manager.cmd.CommandBukkitSenderManager;
import es.angelillo15.mast.api.managers.StaffChatManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class OnStaffChatToggle {
  @Inject
  private StaffChatManager staffChatManager;
  @Inject
  private CommandBukkitSenderManager commandBukkitSenderManager;

  @MastSubscribe
  public void onStaffChatToggle(BackendStaffChatEnableDisableEvent event) {
    Player player = Bukkit.getPlayer(event.username());

    if (player == null) {
      return;
    }

    staffChatManager.toggleAndAdvert(commandBukkitSenderManager.getSender(player));
  }
}
