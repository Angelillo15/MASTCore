package es.angelillo15.mast.bukkit.cmd.teleport;

import com.nookure.mast.api.cmd.CommandData;
import com.nookure.mast.api.cmd.StaffCommand;
import com.nookure.mast.api.cmd.sender.CommandSender;
import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.Replace;
import es.angelillo15.mast.api.config.bukkit.Messages;
import io.papermc.lib.PaperLib;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static es.angelillo15.mast.bukkit.utils.ColorUtils.cmp;

@CommandData(
    name = "mtph",
    permission = "mast.teleport.tph",
    usage = "/mtph <player> [target]",
    description = "Teleport you to the player location"
)
public class TphCommand extends StaffCommand {
  @Override
  public void onStaffCommand(@NotNull IStaffPlayer sender, @NotNull String label, @NotNull String[] args) {
    if (args.length < 1) {
      sender.sendMessage(cmp("Teleport.tph.badUsage"));
      return;
    }

    Player target = Bukkit.getPlayer(args[0]);

    if (target == null) {
      sender.sendMessage(cmp(Messages.GET_NO_PLAYER_FOUND_MESSAGE()));
      return;
    }

    PaperLib.teleportAsync(target, sender.getPlayer().getLocation());

    sender.sendMessage(cmp("Teleport.tph.teleported",
        new Replace("player", target.getName()))
    );
  }

  @NotNull
  @Override
  public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String @NotNull [] args) {
    return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
  }
}