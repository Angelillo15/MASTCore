package es.angelillo15.mast.bukkit.cmd;

import es.angelillo15.mast.api.TextUtils;
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

        if(!p.hasPermission("mast.freeze")){
            TextUtils.colorize(Messages.GET_NO_PERMISSION_MESSAGE(), p);
            return true;
        }

        if(args.length < 1){
            new SelectTargetGUI(p, (target) -> {
                execute(p, target);
                p.closeInventory();
            }).open();
            return false;
        }

        execute(p, Bukkit.getPlayer(args[0]));

        return true;
    }

    public void execute(Player p, Player target){
        FreezeUtils.toggleFrozen(p, target);
    }
}