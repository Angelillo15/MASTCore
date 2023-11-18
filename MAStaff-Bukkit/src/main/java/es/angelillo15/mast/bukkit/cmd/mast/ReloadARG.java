package es.angelillo15.mast.bukkit.cmd.mast;

import com.nookure.mast.api.cmd.SubCommand;
import com.nookure.mast.api.cmd.sender.CommandSender;
import es.angelillo15.mast.api.config.bukkit.Messages;
import es.angelillo15.mast.bukkit.MAStaff;
import org.jetbrains.annotations.NotNull;

public class ReloadARG extends SubCommand {
  @Override
  public String getName() {
    return "reload";
  }

  @Override
  public String getDescription() {
    return "Reloads the plugin";
  }

  @Override
  public String getSyntax() {
    return "/mast reload";
  }

  @Override
  public String getPermission() {
    return "mast.reload";
  }

  @Override
  public void onCommand(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
    MAStaff.getPlugin().reload();
    sender.sendMessage(Messages.GET_RELOADED_MESSAGE());
  }
}
