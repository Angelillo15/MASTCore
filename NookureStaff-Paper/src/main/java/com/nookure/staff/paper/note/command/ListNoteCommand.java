package com.nookure.staff.paper.note.command;

import com.nookure.staff.api.command.Command;
import com.nookure.staff.api.command.CommandData;
import com.nookure.staff.api.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@CommandData(
    name = "list",
    description = "List notes of a user",
    permission = "nookure.staff.note.list"
)
public class ListNoteCommand extends Command {
  @Override
  public void onCommand(@NotNull CommandSender sender, @NotNull String label, @NotNull List<String> args) {

  }
}
