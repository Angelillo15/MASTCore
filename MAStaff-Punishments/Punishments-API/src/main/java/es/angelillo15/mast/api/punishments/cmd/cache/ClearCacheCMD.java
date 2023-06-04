package es.angelillo15.mast.api.punishments.cmd.cache;

import es.angelillo15.mast.api.cmd.CommandData;
import es.angelillo15.mast.api.cmd.CommandParent;

@CommandData(
        name = "clearcache",
        permission = "mastaff.punishments.clearcache"
)
public class ClearCacheCMD extends CommandParent {
    public ClearCacheCMD() {
        this.registerSubCommand(new BansCache());
    }
}
