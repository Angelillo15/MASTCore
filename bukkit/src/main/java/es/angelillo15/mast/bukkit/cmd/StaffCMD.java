package es.angelillo15.mast.bukkit.cmd;

import es.angelillo15.mast.bukkit.utils.Messages;
import es.angelillo15.mast.bukkit.utils.StaffUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(p.hasPermission("mast.staff")){
                StaffUtils.toggleStaff(p);
                return true;
            }else{
                p.sendMessage(Messages.GET_NO_PERMISSION_MESSAGE());
                return false;
            }
        }
        return false;
    }
}
