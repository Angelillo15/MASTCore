package es.angelillo15.mast.bukkit.cmd;

import com.nookure.mast.api.cmd.CommandData;
import com.nookure.mast.api.cmd.StaffCommand;
import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.bukkit.StaffPlayer;
import org.jetbrains.annotations.NotNull;

@CommandData(
    name= "vanish",
    permission = "mast.vanish",
    usage = "/vanish",
    description = "Vanish from the server",
    aliases =  {"v"}
)
public class VanishCMD extends StaffCommand {
  @Override
  public void onStaffCommand(@NotNull IStaffPlayer sender, @NotNull String label, @NotNull String[] args) {
    StaffPlayer staffPlayer = (StaffPlayer) sender;

    staffPlayer.setVanish(!sender.isVanished());
  }
}
