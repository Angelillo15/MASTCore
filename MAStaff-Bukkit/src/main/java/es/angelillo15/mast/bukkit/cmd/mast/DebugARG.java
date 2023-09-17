package es.angelillo15.mast.bukkit.cmd.mast;

import com.google.inject.Inject;
import com.nookure.mast.api.cmd.SubCommand;
import com.nookure.mast.api.cmd.sender.CommandSender;
import es.angelillo15.mast.api.managers.StaffManager;
import org.jetbrains.annotations.NotNull;

public class DebugARG extends SubCommand {
  @Inject
  private StaffManager staffManager;

  @Override
  public String getName() {
    return "debug";
  }

  @Override
  public String getDescription() {
    return "Debug command";
  }

  @Override
  public String getSyntax() {
    return "/mast debug";
  }

  @Override
  public String getPermission() {
    return "mast.debug";
  }

  @Override
  public void onCommand(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
    sender.sendMessage("-------------------------------------");
    sender.sendMessage("Staff players: " + staffManager.getStaffPlayers().size());
    sender.sendMessage("-------------------------------------");
    staffManager
        .getStaffPlayers()
        .forEach(
            (s, iStaffPlayer) -> {
              sender.sendMessage("Name: <gray>" + iStaffPlayer.getPlayer().getName());
              sender.sendMessage("Vanished: <gray>" + iStaffPlayer.isVanished());
              sender.sendMessage("Staff mode: <gray>" + iStaffPlayer.isStaffMode());
              sender.sendMessage("Object id <gray>" + iStaffPlayer.hashCode());
              sender.sendMessage("-------------------------------------");
            });
  }
}
