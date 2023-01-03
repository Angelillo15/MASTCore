package es.angelillo15.mast.bukkit.cmd.staff;

import es.angelillo15.mast.api.Permissions;
import es.angelillo15.mast.api.cmd.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffHelpArg extends SubCommand {
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Shows the staff help";
    }

    @Override
    public String getSyntax() {
        return "/staff help";
    }

    @Override
    public String getPermission() {
        return Permissions.STAFF.getPermission();
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)) return;
        StaffCMD.sendHelp(sender);
    }
}
