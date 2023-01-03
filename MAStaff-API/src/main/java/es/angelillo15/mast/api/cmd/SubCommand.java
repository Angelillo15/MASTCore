package es.angelillo15.mast.api.cmd;

import org.bukkit.command.CommandSender;

public abstract class SubCommand {
    public abstract String getName();

    public abstract String getDescription();

    public abstract String getSyntax();

    public abstract String getPermission();

    public abstract void execute(CommandSender sender, String args[]);
}
