package es.angelillo15.mast.api.punishments.cmd.ban;

import es.angelillo15.mast.api.cmd.Command;
import es.angelillo15.mast.api.cmd.CommandData;
import es.angelillo15.mast.api.cmd.sender.CommandSender;
import es.angelillo15.mast.api.data.UserData;
import es.angelillo15.mast.api.models.BanModel;
import es.angelillo15.mast.api.punishments.data.DataManager;
import es.angelillo15.mast.api.punishments.utils.BanUtils;

import java.util.UUID;

@CommandData(
        name = "isbanned",
        permission = "mastaff.punishments.isbanned",
        aliases = {"isban", "isb"}
)
public class IsBannedCMD extends Command {
    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("Usage: /isbanned <player>");
            return;
        }

        UserData userData = BanUtils.getUserData(args[0]);

        BanModel model = DataManager.getDataManager().getBan(UUID.fromString(userData.getUUID()));

        if (model == null) {
            sender.sendMessage("Player not banned");
            return;
        }

        sender.sendMessage(model.toString());
    }
}
