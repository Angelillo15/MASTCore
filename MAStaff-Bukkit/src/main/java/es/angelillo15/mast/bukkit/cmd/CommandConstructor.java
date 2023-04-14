package es.angelillo15.mast.bukkit.cmd;

import es.angelillo15.mast.api.cmd.MainCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandConstructor implements CommandExecutor {
    private final MainCommand cmd;
    private final es.angelillo15.mast.api.cmd.Command command;
    public CommandConstructor(MainCommand cmd, es.angelillo15.mast.api.cmd.Command command) {
        this.cmd = cmd;
        this.command = command;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        cmd.onCommand(null, this.command, label, args);
        return true;
    }
}
