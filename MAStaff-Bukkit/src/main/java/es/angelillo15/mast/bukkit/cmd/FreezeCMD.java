package es.angelillo15.mast.bukkit.cmd;

import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.api.managers.StaffPlayersManagers;
import es.angelillo15.mast.api.managers.freeze.FreezeManager;
import es.angelillo15.mast.bukkit.cmd.utils.CommandManager;
import es.angelillo15.mast.bukkit.config.Config;
import es.angelillo15.mast.bukkit.config.Messages;
import es.angelillo15.mast.bukkit.gui.SelectTargetGUI;
import es.angelillo15.mast.bukkit.utils.FreezeUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FreezeCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return false;

        Player p = (Player) sender;

        if (!StaffPlayersManagers.isStaffPlayer(p.getName())) return false;

        IStaffPlayer staff = StaffPlayersManagers.getStaffPlayer(p.getName());

        if(!p.hasPermission("mast.freeze")){
            TextUtils.colorize(Messages.GET_NO_PERMISSION_MESSAGE(), p);
            return true;
        }

        if(args.length < 1){
            new SelectTargetGUI(p, (target) -> {
                execute(staff, target);
                p.closeInventory();
            }).open();
            return false;
        }

        if (args[0].equalsIgnoreCase("/remove")) {
            if (FreezeManager.isFrozen(args[1]))
                staff.unfreezePlayer(args[1]);
            return true;
        }

        if (args[0].equalsIgnoreCase("/exec")) {
            if (FreezeManager.isFrozen(args[1]))
                staff.executeFreezedPunishments(args[1]);
            return true;
        }

        if (onlineCheck(p, args, 0)) return true;

        execute(staff, Bukkit.getPlayer(args[0]));

        return true;
    }

    public void execute(IStaffPlayer staff, Player target){
        if (staff.isFreezed(target)) staff.unfreezePlayer(target.getName());
        else staff.freezePlayer(target);
    }

    public boolean onlineCheck(Player p, String[] args, int index){
        Player target = Bukkit.getPlayer(args[index]);

        if(target == null){
            TextUtils.colorize(Messages.GET_NO_PLAYER_FOUND_MESSAGE(), p);
            return true;
        }
        return false;
    }
}