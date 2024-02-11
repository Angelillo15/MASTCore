package com.nookure.staff.paper.command.main;

import com.nookure.staff.api.command.Command;
import com.nookure.staff.api.command.CommandData;
import com.nookure.staff.api.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@CommandData(
    name = "reload",
    description = "Reloads the plugin"
)
public class ReloadSubCommand extends Command {
  @Override
  public void onCommand(@NotNull CommandSender sender, @NotNull String label, @NotNull List<String> args) {

  }
}
