package es.angelillo15.mast.bungee.cmd.mastb;

import com.nookure.mast.api.cmd.CommandData;
import com.nookure.mast.api.cmd.CommandParent;
import com.nookure.mast.api.cmd.SubCommand;
import com.nookure.mast.api.cmd.sender.CommandSender;

@CommandData(
        name = "mastb",
        description = "MAStaff-Bungee commands",
        usage = "/mastb <subcommand>",
        permission = "mastb.admin",
        aliases = {"mab"}
)
public class MastParentCMD extends CommandParent {
    @Override
    public void registerSubCommands() {
        this.registerSubCommand(new ReloadARG());
        this.registerSubCommand(new DumpArg());
        this.registerSubCommand(new SubCommand() {
            @Override
            public String getName() {
                return "help";
            }

            @Override
            public String getDescription() {
                return "Shows the help page";
            }

            @Override
            public String getSyntax() {
                return "/mastb help";
            }

            @Override
            public String getPermission() {
                return "mastb.admin";
            }

            @Override
            public void onCommand(CommandSender sender, String label, String[] args) {
                sendHelp(sender);
            }
        });
    }
}
