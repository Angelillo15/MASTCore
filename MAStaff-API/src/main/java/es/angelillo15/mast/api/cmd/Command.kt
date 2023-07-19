package es.angelillo15.mast.api.cmd;

import es.angelillo15.mast.api.cmd.sender.CommandSender;

public abstract class Command {
    public abstract void onCommand(CommandSender sender, String label, String[] args);
}
