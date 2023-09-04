package es.angelillo15.mast.bukkit.cmd.mast;

import es.angelillo15.mast.api.cmd.LegacySubCommand;
import es.angelillo15.mast.api.config.bukkit.Messages;
import es.angelillo15.mast.bukkit.MAStaff;
import org.bukkit.command.CommandSender;

public class ReloadARG extends LegacySubCommand {
    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Reloads the plugin";
    }

    @Override
    public String getSyntax() {
        return "/mast reload";
    }

    @Override
    public String getPermission() {
        return "mast.reload";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        MAStaff.getPlugin().reload();
        sender.sendMessage(Messages.GET_RELOADED_MESSAGE());
    }
}
