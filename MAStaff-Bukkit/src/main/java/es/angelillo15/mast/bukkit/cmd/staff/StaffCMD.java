package es.angelillo15.mast.bukkit.cmd.staff;

import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.cmd.SubCommand;
import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.api.managers.StaffPlayersManagers;
import es.angelillo15.mast.bukkit.config.Messages;
import es.angelillo15.mast.bukkit.gui.StaffListGUI;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class StaffCMD implements CommandExecutor {
    @Getter
    private static ArrayList<SubCommand> subCommands = new ArrayList<>();

    public StaffCMD(){
        subCommands.add(new StaffListArg());
        subCommands.add(new StaffHelpArg());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        if(args.length == 0){
            Player player = (Player) sender;
            IStaffPlayer staffPlayer = StaffPlayersManagers.getStaffPlayer(player);

            staffPlayer.toggleStaffMode();
            return true;
        }

        for (SubCommand subCommand : subCommands) {
            if(args[0].equalsIgnoreCase(subCommand.getName())){
                if(!sender.hasPermission(subCommand.getPermission())){
                    sender.sendMessage(TextUtils.colorize(Messages.GET_NO_PERMISSION_MESSAGE()));
                    return true;
                }
                subCommand.execute(sender, args);
                return true;
            } else {
                sendHelp(sender);
            }
        }

        return true;
    }

    public static void sendHelp(CommandSender sender) {
        sender.sendMessage(TextUtils.colorize("&6----------------Staff----------------"));
        sender.sendMessage(TextUtils.colorize("&bAvailable Commands:"));
        subCommands.forEach(subCommand -> {
            if(sender.hasPermission(subCommand.getPermission())){
                sender.sendMessage(TextUtils.colorize("&b" + subCommand.getSyntax() + " &7- &f" + subCommand.getDescription()));
            }
        });

    }
}
