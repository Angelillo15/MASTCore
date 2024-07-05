package com.nookure.staff.paper.command.staff;

import com.nookure.staff.api.Permissions;
import com.nookure.staff.api.StaffPlayerWrapper;
import com.nookure.staff.api.command.CommandData;
import com.nookure.staff.api.command.CommandParent;
import com.nookure.staff.api.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@CommandData(
    name = "staff",
    description = "Staff mode main command",
    permission = Permissions.STAFF_MODE_PERMISSION,
    subCommands = {
      StaffListCommand.class
    }
)
public class StaffCommandParent extends CommandParent {
  @Override
  public void onCommand(@NotNull CommandSender sender, @NotNull String label, @NotNull List<String> args) {
    if (args.isEmpty()) {
      if (sender instanceof StaffPlayerWrapper staff) {
        staff.toggleStaffMode();
        return;
      }

      sender.sendMiniMessage("<red>Only staff members can execute this command.");
      return;
    }

    super.onCommand(sender, label, args);
  }

  @Override
  public void prepare() {
    super.prepare();
    getSubCommandData().remove("help");
    getSubCommands().remove("help");
    getSubCommandData().put(" ", getCommandData());
  }
}
