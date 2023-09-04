package es.angelillo15.mast.api.event.bungee.ban;

import es.angelillo15.mast.api.models.BansTable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Event;

@AllArgsConstructor
public class BannedPlayerTriesToJoin extends Event {
  /**
   * The ban model of the banned player
   *
   * @return The ban model of the banned player
   * @see BansTable
   */
  @Getter private BansTable banModel;
  /**
   * The name of the banned player
   *
   * @return The name of the banned player
   */
  @Getter private String player;
}
