package com.nookure.mast.api.cmd;

import com.google.inject.Inject;
import com.nookure.mast.api.cmd.sender.CommandSender;
import com.nookure.mast.api.cmd.sender.PlayerCommandSender;
import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.managers.StaffManager;
import org.jetbrains.annotations.NotNull;

public abstract class StaffCommand extends Command {
  @Inject
  private StaffManager staffManager;
  @Override
  public void onCommand(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
    if (sender.isConsole()) {
      sender.sendMessage("This command can only be executed by players");
      return;
    }

    if (!staffManager.isStaffPlayer(sender.getName())) return;

    if (sender instanceof PlayerCommandSender player) {
      IStaffPlayer staffPlayer = staffManager.getStaffPlayer(player.getPlayer());
      if (staffPlayer == null) return;

      onStaffCommand(staffPlayer, label, args);
    }
  }

  public abstract void onStaffCommand(@NotNull IStaffPlayer sender, @NotNull String label, @NotNull String[] args);
}
