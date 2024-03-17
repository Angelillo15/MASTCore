package com.nookure.staff.paper.command;

import com.nookure.staff.api.StaffPlayerWrapper;
import com.nookure.staff.api.command.CommandData;
import com.nookure.staff.api.command.StaffCommand;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@CommandData(
    name = "vanish",
    aliases = {"v"},
    permission = "nookure.staff.vanish",
    usage = "/vanish",
    description = "Toggle vanish mode"
)
public class VanishCommand extends StaffCommand {
  @Override
  protected void onStaffCommand(@NotNull StaffPlayerWrapper sender, @NotNull String label, @NotNull List<String> args) {
    sender.toggleVanish();
  }
}
