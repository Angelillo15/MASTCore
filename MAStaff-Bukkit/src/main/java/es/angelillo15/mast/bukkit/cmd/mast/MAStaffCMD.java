package es.angelillo15.mast.bukkit.cmd.mast;

import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.api.cmd.LegacySubCommand;
import es.angelillo15.mast.bukkit.config.Messages;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class MAStaffCMD implements CommandExecutor {
    @Getter
    private static ArrayList<LegacySubCommand> legacySubCommands = new ArrayList<>();

    public MAStaffCMD(){
        legacySubCommands.clear();
        legacySubCommands.add(new ReloadARG());
        legacySubCommands.add(new HelpARG());
        legacySubCommands.add(new DumpARG());
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0){
            sendHelp(sender);
            return true;
        }

        for (LegacySubCommand legacySubCommand : legacySubCommands) {
            if(args[0].equalsIgnoreCase(legacySubCommand.getName())){
                if(!sender.hasPermission(legacySubCommand.getPermission())){
                    sender.sendMessage(Messages.GET_NO_PERMISSION_MESSAGE());
                    return true;
                }
                legacySubCommand.execute(sender, args);
                return true;
            }
        }
        sendHelp(sender);
        return true;
    }

    public static void sendHelp(CommandSender sender) {
        sender.sendMessage(TextUtils.colorize("&6----------------MAStaff----------------"));
        sender.sendMessage(TextUtils.colorize("&bAvailable Commands:"));
        legacySubCommands.forEach(subCommand -> {
            if(sender.hasPermission(subCommand.getPermission())){
                sender.sendMessage(TextUtils.colorize("&b" + subCommand.getSyntax() + " &7- &f" + subCommand.getDescription()));
            }
        });

    }
}
