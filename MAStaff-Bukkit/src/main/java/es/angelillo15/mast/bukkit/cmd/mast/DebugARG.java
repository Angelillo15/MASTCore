package es.angelillo15.mast.bukkit.cmd.mast;

import com.google.inject.Inject;
import es.angelillo15.mast.api.cmd.LegacySubCommand;
import es.angelillo15.mast.api.managers.StaffManager;
import org.bukkit.command.CommandSender;

public class DebugARG extends LegacySubCommand {
  @Inject private StaffManager staffManager;

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
  public void execute(CommandSender sender, String[] args) {
    staffManager
        .getStaffPlayers()
        .forEach(
            (s, iStaffPlayer) -> {
              sender.sendMessage("-------------------------------------");
              sender.sendMessage("Name: " + iStaffPlayer.getPlayer().getName());
              sender.sendMessage("Vanished: " + iStaffPlayer.isVanished());
              sender.sendMessage("Staff mode: " + iStaffPlayer.isStaffMode());
              sender.sendMessage("Object id " + iStaffPlayer.hashCode());
            });
  }
}
