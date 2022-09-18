package es.angelillo15.mast.bukkit.cmd;

import es.angelillo15.mast.bukkit.MASTBukkitManager;
import es.angelillo15.mast.bukkit.utils.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MASTCoreCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(p.hasPermission("mast.admin")){
                if(args.length < 1){
                    p.sendMessage("§cUsage: /mast <reload>");
                    return false;
                }

                if(args[0].equalsIgnoreCase("reload")){
                    MASTBukkitManager.getInstance().reload();
                    p.sendMessage(Messages.GET_RELOADED_MESSAGE());
                    MASTBukkitManager.getInstance().getLogger().info(Messages.GET_RELOADED_MESSAGE());
                }
            }
        } else {
            if(args.length < 1){
                sender.sendMessage("§cUsage: /mast <reload>");
                return false;
            }

            if(args[0].equalsIgnoreCase("reload")){
                MASTBukkitManager.getInstance().reload();
                sender.sendMessage(Messages.GET_RELOADED_MESSAGE());
            }
        }
        return false;
    }
}
