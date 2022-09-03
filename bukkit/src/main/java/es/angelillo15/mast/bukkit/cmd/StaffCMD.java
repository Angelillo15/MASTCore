package es.angelillo15.mast.bukkit.cmd;

import es.angelillo15.mast.api.BStaffPlayer;
import es.angelillo15.mast.bukkit.MASTBukkitManager;
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
                BStaffPlayer bStaffPlayer;
                if(MASTBukkitManager.getInstance().containsStaffPlayer(p.getUniqueId())){
                    bStaffPlayer = MASTBukkitManager.getInstance().getSStaffPlayer(p.getUniqueId());
                }else {
                    bStaffPlayer = new BStaffPlayer(p);
                    MASTBukkitManager.getInstance().addStaffPlayer(bStaffPlayer);
                }
                if(bStaffPlayer.getStaffMode()){
                    bStaffPlayer.setStaffMode(false);
                    p.sendMessage("§cYou are no longer in staff mode");
                }else {
                    bStaffPlayer.setStaffMode(true);
                    p.sendMessage("§aYou are now in staff mode");
                }
                Bukkit.getConsoleSender().sendMessage("staff mode: " + bStaffPlayer.getStaffMode());
                return true;
            }else{
                return false;
            }



        }
        return false;
    }
}
