package es.angelillo15.mast.bukkit.cmd;

import com.google.inject.Inject;
import com.nookure.mast.api.cmd.Command;
import com.nookure.mast.api.cmd.CommandData;
import com.nookure.mast.api.cmd.sender.CommandSender;
import com.nookure.mast.api.cmd.sender.PlayerCommandSender;
import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.api.config.bukkit.Messages;
import es.angelillo15.mast.api.managers.StaffManager;
import com.nookure.mast.api.manager.FreezeManager;
import es.angelillo15.mast.bukkit.gui.SelectTargetGUI;

import java.util.List;
import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@CommandData(
    name = "freeze",
    permission = "mast.freeze",
    aliases = {"fr"},
    usage = "/freeze <player>",
    description = "Freeze a player"
)
public class FreezeCMD extends Command {
  @Inject
  private StaffManager staffManager;
  @Inject
  private FreezeManager freezeManager;

  @Override
  public void onCommand(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
    if (!(sender instanceof PlayerCommandSender pcs)) return;

    Player p = pcs.getPlayer();

    if (!staffManager.isStaffPlayer(p.getName())) return;

    IStaffPlayer staff = staffManager.getStaffPlayer(p.getName());

    assert staff != null;

    if (!p.hasPermission("mast.freeze")) {
      TextUtils.colorize(Messages.GET_NO_PERMISSION_MESSAGE(), p);
      return;
    }

    if (args.length < 1) {
      new SelectTargetGUI(p).callback((target) -> {
        execute(staff, target);
        p.closeInventory();
      }).open();
      return;
    }

    if (args[0].equalsIgnoreCase("/remove")) {
      if (freezeManager.isFrozen(args[1]))
        staff.unfreezePlayer(args[1]);
      return;
    }

    if (args[0].equalsIgnoreCase("/exec")) {
      if (freezeManager.isFrozen(args[1]))
        staff.executeFreezedPunishments(args[1]);
      return;
    }

    if (onlineCheck(p, args, 0)) return;

    if (Objects.requireNonNull(Bukkit.getPlayer(args[0])).hasPermission("mast.freeze.bypass")) {
      TextUtils.colorize(Messages.GET_FREEZE_CANNOT_FREEZE_THAT_PLAYER_MESSAGE(), p);
      return;
    }

    execute(staff, Bukkit.getPlayer(args[0]));
  }

  public void execute(IStaffPlayer staff, Player target) {
    if (staff.isFreezed(target)) staff.unfreezePlayer(target.getName());
    else staff.freezePlayer(target);
  }

  public boolean onlineCheck(Player p, String[] args, int index) {
    Player target = Bukkit.getPlayer(args[index]);

    if (target == null) {
      TextUtils.colorize(Messages.GET_NO_PLAYER_FOUND_MESSAGE(), p);
      return true;
    }
    return false;
  }

  @NotNull
  @Override
  public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
    return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
  }
}