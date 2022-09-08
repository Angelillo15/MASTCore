package es.angelillo15.mast.bukkit.cmd;

import es.angelillo15.mast.bukkit.api.BStaffPlayer;
import es.angelillo15.mast.bukkit.MASTBukkitManager;
import es.angelillo15.mast.bukkit.utils.Messages;
import es.angelillo15.mast.bukkit.utils.StaffUtils;
import es.angelillo15.mast.bukkit.utils.VanishUtils;
import org.bukkit.Bukkit;
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
                return false;
            }



        }
        return false;
    }
}
