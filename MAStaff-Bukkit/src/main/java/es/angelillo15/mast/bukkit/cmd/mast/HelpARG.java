package es.angelillo15.mast.bukkit.cmd.mast;

import es.angelillo15.mast.api.cmd.LegacySubCommand;
import org.bukkit.command.CommandSender;

public class HelpARG extends LegacySubCommand {
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Shows the help";
    }

    @Override
    public String getSyntax() {
        return "/mast help";
    }

    @Override
    public String getPermission() {
        return "mast.help";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        MAStaffCMD.sendHelp(sender);
    }
}
