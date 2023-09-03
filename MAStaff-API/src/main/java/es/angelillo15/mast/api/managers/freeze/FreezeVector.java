package es.angelillo15.mast.api.managers.freeze;

import es.angelillo15.mast.api.IStaffPlayer;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.OfflinePlayer;

@Data
@AllArgsConstructor
public class FreezeVector {
  IStaffPlayer staffPlayer;
  OfflinePlayer target;
}
