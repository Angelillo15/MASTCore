package es.angelillo15.mast.bukkit.cmd;

import es.angelillo15.mast.bukkit.utils.Messages;
import es.angelillo15.mast.bukkit.utils.StaffUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChatCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(p.hasPermission("mast.staffchat")){

                StaffUtils.asyncStaffChatMessage(Messages.GET_STAFF_CHAT_FORMAT().replace("{player}", p.getDisplayName()).replace("{message}", String.join(" ", args)));
                return true;
            }else{
                p.sendMessage(Messages.GET_NO_PERMISSION_MESSAGE());
                return false;
            }
        }
        return false;
    }
}
