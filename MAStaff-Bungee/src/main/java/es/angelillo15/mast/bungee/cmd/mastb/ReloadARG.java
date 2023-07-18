package es.angelillo15.mast.bungee.cmd.mastb;

import es.angelillo15.mast.api.cmd.SubCommand;
import es.angelillo15.mast.api.cmd.sender.CommandSender;
import es.angelillo15.mast.bungee.MAStaff;
import es.angelillo15.mast.bungee.config.Messages;
import net.md_5.bungee.api.chat.TextComponent;

public class ReloadARG extends SubCommand {
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
        return "/mastb reload";
    }

    @Override
    public String getPermission() {
        return "mastb.admin";
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (!sender.hasPermission(getPermission())) return;

        MAStaff.getInstance().reload();
        sender.sendMessage(Messages.GET_RELOADED_MESSAGE());
    }
}
