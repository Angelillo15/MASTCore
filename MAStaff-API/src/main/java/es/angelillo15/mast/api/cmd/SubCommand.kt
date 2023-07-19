package es.angelillo15.mast.api.cmd;

import es.angelillo15.mast.api.cmd.sender.CommandSender;

public abstract class SubCommand {
    public abstract String getName();

    public abstract String getDescription();

    public abstract String getSyntax();

    public abstract String getPermission();

    public abstract void onCommand(CommandSender sender, String label, String[] args);
}