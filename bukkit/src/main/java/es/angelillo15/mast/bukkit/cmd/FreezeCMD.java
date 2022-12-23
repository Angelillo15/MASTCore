package es.angelillo15.mast.bukkit.cmd;

import es.angelillo15.mast.bukkit.utils.FreezeUtils;
import es.angelillo15.mast.bukkit.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FreezeCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length < 1){
            return false;
        }
        Player p = (Player) sender;

        if(!p.hasPermission("mast.freeze")){
            p.sendMessage(Messages.GET_NO_PERMISSION_MESSAGE());
            return true;
        }

        if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[0]))){
            FreezeUtils.toggleVanish(p, Bukkit.getPlayer(args[0]));
        }
        return false;
    }
}
