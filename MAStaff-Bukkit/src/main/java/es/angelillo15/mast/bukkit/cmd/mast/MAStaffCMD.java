package es.angelillo15.mast.bukkit.cmd.mast;

import com.google.inject.Inject;
import es.angelillo15.mast.api.Constants;
import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.api.cmd.LegacySubCommand;
import es.angelillo15.mast.api.config.bukkit.Messages;
import es.angelillo15.mast.api.utils.MAStaffInject;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class MAStaffCMD implements CommandExecutor {
    @Getter
    private static ArrayList<LegacySubCommand> legacySubCommands = new ArrayList<>();
    @Inject
    private static MAStaffInject inject;

    public MAStaffCMD(){
        legacySubCommands.clear();
        legacySubCommands.add(inject.getInjector().getInstance(ReloadARG.class));
        legacySubCommands.add(inject.getInjector().getInstance(HelpARG.class));
        legacySubCommands.add(inject.getInjector().getInstance(DumpARG.class));
        legacySubCommands.add(inject.getInjector().getInstance(DebugARG.class));
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
        sender.sendMessage(TextUtils.colorize("&a&lMAS&r&ltaff &7- &fv" + Constants.VERSION));
        legacySubCommands.forEach(subCommand -> {
            if(sender.hasPermission(subCommand.getPermission())){
                sender.sendMessage(TextUtils.colorize("&a&l> &r" + subCommand.getSyntax() + " &7- &7" + subCommand.getDescription()));
            }
        });
    }
}
