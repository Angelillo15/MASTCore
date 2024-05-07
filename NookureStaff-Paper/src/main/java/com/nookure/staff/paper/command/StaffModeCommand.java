package com.nookure.staff.paper.command;

import com.nookure.staff.api.Permissions;
import com.nookure.staff.api.StaffPlayerWrapper;
import com.nookure.staff.api.command.CommandData;
import com.nookure.staff.api.command.StaffCommand;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@CommandData(
    name = "staff",
    description = "Main command for staff",
    permission = Permissions.STAFF_PERMISSION
)
public class StaffModeCommand extends StaffCommand {
  @Override
  protected void onStaffCommand(@NotNull StaffPlayerWrapper sender, @NotNull String label, @NotNull List<String> args) {
    sender.toggleStaffMode();
  }
}
