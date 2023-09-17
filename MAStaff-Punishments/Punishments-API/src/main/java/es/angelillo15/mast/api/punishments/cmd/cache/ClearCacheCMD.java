package es.angelillo15.mast.api.punishments.cmd.cache;

import com.nookure.mast.api.cmd.CommandData;
import com.nookure.mast.api.cmd.CommandParent;

@CommandData(name = "clearcache", permission = "mastaff.punishments.clearcache")
public class ClearCacheCMD extends CommandParent {
  @Override
  public void registerSubCommands() {
    this.registerSubCommand(new BansCache());
  }
}
