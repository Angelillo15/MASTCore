package es.angelillo15.mast.bukkit.cmd.staff;

import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.cmd.LegacySubCommand;
import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.api.managers.StaffPlayersManagers;
import es.angelillo15.mast.bukkit.config.ConfigLoader;
import es.angelillo15.mast.bukkit.config.Messages;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class StaffCMD implements CommandExecutor {
    @Getter
    private static ArrayList<LegacySubCommand> legacySubCommands = new ArrayList<>();

    public StaffCMD(){
        legacySubCommands.clear();
        legacySubCommands.add(new StaffListArg());
        legacySubCommands.add(new StaffHelpArg());
        if(ConfigLoader.getPunishmentsGUI().getConfig().getBoolean("Gui.enable")) legacySubCommands.add(new StaffPunishmentsGUIArg());
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

        for (LegacySubCommand legacySubCommand : legacySubCommands) {
            if(args[0].equalsIgnoreCase(legacySubCommand.getName())){
                if(!sender.hasPermission(legacySubCommand.getPermission())){
                    sender.sendMessage(TextUtils.colorize(Messages.GET_NO_PERMISSION_MESSAGE()));
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
        sender.sendMessage(TextUtils.colorize("&6----------------Staff----------------"));
        sender.sendMessage(TextUtils.colorize("&bAvailable Commands:"));
        legacySubCommands.forEach(subCommand -> {
            if(sender.hasPermission(subCommand.getPermission())){
                sender.sendMessage(TextUtils.colorize("&b" + subCommand.getSyntax() + " &7- &f" + subCommand.getDescription()));
            }
        });

    }
}
