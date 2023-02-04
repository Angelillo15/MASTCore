package es.angelillo15.mast.bukkit.cmd;

import es.angelillo15.mast.api.event.bukkit.staff.StaffChatTalkEvent;
import es.angelillo15.mast.bukkit.config.Messages;
import es.angelillo15.mast.bukkit.utils.StaffUtils;
import org.bukkit.Bukkit;
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

                if(args.length < 1){
                    p.sendMessage(Messages.GET_STAFFCHAT_CORRECT_USE());
                    return true;
                }
                String message = Messages.GET_STAFF_CHAT_FORMAT().replace("{player}",
                        p.getDisplayName()).replace("{message}",
                        String.join(" ", args));

                StaffUtils.asyncStaffChatMessage(message);

                Bukkit.getPluginManager().callEvent(new StaffChatTalkEvent((Player) sender, String.join(" ", args)));

                return true;
            }else{
                p.sendMessage(Messages.GET_NO_PERMISSION_MESSAGE());
                return false;
            }
        }
        return false;
    }
}
