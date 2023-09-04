package es.angelillo15.mast.bukkit.cmd.staff;

import es.angelillo15.mast.api.Permissions;
import es.angelillo15.mast.api.cmd.LegacySubCommand;
import es.angelillo15.mast.bukkit.gui.StaffListGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffListArg extends LegacySubCommand {
  @Override
  public String getName() {
    return "stafflist";
  }

  @Override
  public String getDescription() {
    return "Shows the staff list";
  }

  @Override
  public String getSyntax() {
    return "/staff stafflist";
  }

  @Override
  public String getPermission() {
    return Permissions.STAFF_LIST.getPermission();
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (!(sender instanceof Player)) return;
    new StaffListGUI((Player) sender).open();
  }
}
