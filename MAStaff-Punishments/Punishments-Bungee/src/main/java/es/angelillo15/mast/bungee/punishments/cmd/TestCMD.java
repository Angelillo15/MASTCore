package es.angelillo15.mast.bungee.punishments.cmd;

import es.angelillo15.mast.api.cmd.Command;
import es.angelillo15.mast.api.cmd.CommandData;
import es.angelillo15.mast.api.cmd.sender.CommandSender;

@CommandData(
        name = "test",
        aliases = {"t"},
        permission = "test.permission",
        usage = "/test",
        description = "Test command"
)
public class TestCMD extends Command {
    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        sender.sendMessage("Test command");
    }
}
