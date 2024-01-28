package com.nookure.staff.api.command;

import com.nookure.staff.api.command.sender.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * This class represents a command.
 * <p>
 * This class is abstract and must be extended.
 * The class must be annotated with {@link CommandData}.
 * </p>
 *
 * @see CommandData
 * @since 1.0.0
 */
public abstract class Command {
  /**
   * This method is called when the command is executed
   *
   * @param sender The sender of the command
   * @param label  The label of the command
   * @param args   The arguments of the command
   */
  public abstract void onCommand(@NotNull CommandSender sender, @NotNull String label, @NotNull List<String> args);

  /**
   * This method is called when the command is tab completed
   *
   * @param sender The sender of the command
   * @param args   The arguments of the command
   * @return A list of possible completions
   */
  @NotNull
  public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String label, @NotNull List<String> args) {
    return List.of();
  }

  /**
   * Get the command data of the command
   *
   * @return the command data of the command
   */
  @NotNull
  public CommandData getCommandData() {
    CommandData commandData = getClass().getAnnotation(CommandData.class);

    if (commandData == null) {
      throw new IllegalStateException("CommandData annotation not found");
    }

    return commandData;
  }
}
