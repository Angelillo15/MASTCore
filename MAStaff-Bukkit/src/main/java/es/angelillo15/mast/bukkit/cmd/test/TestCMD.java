package es.angelillo15.mast.bukkit.cmd.test;

import es.angelillo15.mast.api.player.IMastPlayer;
import es.angelillo15.mast.api.cmd.Command;
import es.angelillo15.mast.api.cmd.MainCommand;

@Command(name = "test",
        aliases = {"t"},
        permission = "mast.test",
        usage = "/test",
        description = "Test command",
        subcommands = {"test"}
)

public class TestCMD extends MainCommand {

    @Override
    public void onCommand(IMastPlayer sender, Command cmd, String label, String[] args) {
        sender.sendMessage("Test command");
    }
}
