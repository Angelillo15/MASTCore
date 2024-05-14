package com.nookure.staff.paper.note.command;

import com.nookure.staff.api.command.Command;
import com.nookure.staff.api.command.CommandData;
import com.nookure.staff.api.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@CommandData(
    name = "remove",
    description = "Remove a note from a user"
)
public class RemoveNoteCommand extends Command {
  @Override
  public void onCommand(@NotNull CommandSender sender, @NotNull String label, @NotNull List<String> args) {

  }
}
