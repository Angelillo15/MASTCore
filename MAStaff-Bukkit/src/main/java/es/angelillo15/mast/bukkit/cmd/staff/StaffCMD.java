package es.angelillo15.mast.bukkit.cmd.staff;

import es.angelillo15.mast.api.cmd.SubCommand;
import es.angelillo15.mast.api.TextUtils;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class StaffCMD implements CommandExecutor {
    @Getter
    private static ArrayList<SubCommand> subCommands = new ArrayList<>();

    public StaffCMD(){
        subCommands.add(new StaffListArg());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return false;
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
