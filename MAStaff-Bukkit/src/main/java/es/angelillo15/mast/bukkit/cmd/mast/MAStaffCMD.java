package es.angelillo15.mast.bukkit.cmd.mast;

import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.api.cmd.SubCommand;
import es.angelillo15.mast.bukkit.config.Messages;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class MAStaffCMD implements CommandExecutor {
    @Getter
    private static ArrayList<SubCommand> subCommands = new ArrayList<>();

    public MAStaffCMD(){
        subCommands.add(new ReloadARG());
        subCommands.add(new HelpARG());
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0){
            sendHelp(sender);
            return true;
        }

        for (SubCommand subCommand : subCommands) {
            if(args[0].equalsIgnoreCase(subCommand.getName())){
                if(!sender.hasPermission(subCommand.getPermission())){
                    sender.sendMessage(Messages.GET_NO_PERMISSION_MESSAGE());
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
        sender.sendMessage(TextUtils.colorize("&6----------------MAStaff----------------"));
        sender.sendMessage(TextUtils.colorize("&bAvailable Commands:"));
        subCommands.forEach(subCommand -> {
            if(sender.hasPermission(subCommand.getPermission())){
                sender.sendMessage(TextUtils.colorize("&b" + subCommand.getSyntax() + " &7- &f" + subCommand.getDescription()));
            }
        });

    }
}
