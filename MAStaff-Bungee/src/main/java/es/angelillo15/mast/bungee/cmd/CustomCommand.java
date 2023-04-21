package es.angelillo15.mast.bungee.cmd;

import es.angelillo15.mast.api.cmd.sender.BungeeConsoleCommandSender;
import es.angelillo15.mast.api.cmd.sender.CommandSender;
import es.angelillo15.mast.api.cmd.sender.ProxiedPlayerCommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CustomCommand extends Command {
    private final es.angelillo15.mast.api.cmd.Command command;
    public CustomCommand(String name, es.angelillo15.mast.api.cmd.Command command) {
        super(name);
        this.command = command;
    }

    public CustomCommand(String name, String permission, es.angelillo15.mast.api.cmd.Command command) {
        super(name, permission);
        this.command = command;
    }

    public CustomCommand(String name, String permission, es.angelillo15.mast.api.cmd.Command command, String... aliases) {
        super(name, permission, aliases);
        this.command = command;
    }

    public CustomCommand(String name, es.angelillo15.mast.api.cmd.Command command, String... aliases) {
        super(name, null, aliases);
        this.command = command;
    }

    @Override
    public void execute(net.md_5.bungee.api.CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            CommandSender commandSender = new ProxiedPlayerCommandSender((ProxiedPlayer) sender);

            command.onCommand(commandSender, getName(), args);
            return;
        }

        command.onCommand(new BungeeConsoleCommandSender(), getName(), args);
    }
}
