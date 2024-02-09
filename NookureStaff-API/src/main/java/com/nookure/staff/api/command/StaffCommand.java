package com.nookure.staff.api.command;


import com.nookure.staff.api.StaffPlayerWrapper;
import com.nookure.staff.api.command.sender.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * This class represents a command that can be executed by a {@link com.nookure.staff.api.StaffPlayerWrapper}.
 * <p>
 * This class is abstract and must be extended.
 * The class must be annotated with {@link CommandData}.
 * </p>
 */
public abstract class StaffCommand extends Command {
  @Override
  public void onCommand(@NotNull CommandSender sender, @NotNull String label, @NotNull List<String> args) {
    if (sender instanceof StaffPlayerWrapper) {
      onStaffCommand((StaffPlayerWrapper) sender, label, args);

      return;
    }

    sender.sendMiniMessage("<red>Only staff members can execute this command.");
  }

  /**
   * This method is called when the command is executed
   * by a {@link com.nookure.staff.api.StaffPlayerWrapper}.
   *
   * @param sender The sender of the command
   * @param label  The label of the command
   * @param args   The arguments of the command
   */
  protected abstract void onStaffCommand(@NotNull StaffPlayerWrapper sender, @NotNull String label, @NotNull List<String> args);
}
