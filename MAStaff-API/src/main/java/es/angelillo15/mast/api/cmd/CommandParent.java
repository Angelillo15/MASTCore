package es.angelillo15.mast.api.cmd;

import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.api.cmd.sender.CommandSender;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public abstract class CommandParent extends Command {
    @Getter
    private final Map<String, SubCommand> subCommands = new HashMap<>();
    @Getter
    @Setter
    private static String noPermission = "&cYou don't have permission to execute this command.";

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (args.length == 0) {
            sendHelp(sender);
            return;
        }

        SubCommand subCommand = getSubCommand(args[0]);
        if (subCommand == null) {
            sendHelp(sender);
            return;
        }

        if (!sender.hasPermission(subCommand.getPermission())) {
            sender.sendMessage(TextUtils.simpleColorize(noPermission));
            return;
        }

        subCommand.onCommand(sender, label, args);
    }

    public void sendHelp(CommandSender sender) {
        sender.sendMessage(TextUtils.simpleColorize("&6----------------MAStaff----------------"));
        sender.sendMessage(TextUtils.simpleColorize("&bAvailable Commands:"));
        for (SubCommand subCommand : subCommands.values()) {
            sender.sendMessage(TextUtils.simpleColorize("&b" + subCommand.getSyntax() + " &7- &f" + subCommand.getDescription()));
        }
    }

    public void registerSubCommand(SubCommand subCommand) {
        subCommands.put(subCommand.getName(), subCommand);
    }

    public void unregisterSubCommand(SubCommand subCommand) {
        subCommands.remove(subCommand.getName());
    }

    public void unregisterSubCommand(String name) {
        subCommands.remove(name);
    }

    public SubCommand getSubCommand(String name) {
        return subCommands.get(name);
    }

    public boolean hasSubCommand(String name) {
        return subCommands.containsKey(name);
    }
}
