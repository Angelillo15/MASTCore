package es.angelillo15.mast.api.punishments.cmd.ban;

import es.angelillo15.mast.api.cmd.Command;
import es.angelillo15.mast.api.cmd.CommandData;
import es.angelillo15.mast.api.cmd.sender.CommandSender;

@CommandData(
    name = "isbanned",
    permission = "mastaff.punishments.isbanned",
    aliases = {"isban", "isb"})
public class IsBannedCMD extends Command {
  @Override
  public void onCommand(CommandSender sender, String label, String[] args) {}
}
