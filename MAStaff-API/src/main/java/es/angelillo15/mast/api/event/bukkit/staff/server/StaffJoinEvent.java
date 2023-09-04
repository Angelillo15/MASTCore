package es.angelillo15.mast.api.event.bukkit.staff.server;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class StaffJoinEvent extends Event {
  private static final HandlerList HANDLERS = new HandlerList();
  @Getter private Player player;

  public StaffJoinEvent(Player player) {
    this.player = player;
  }

  public static HandlerList getHandlerList() {
    return HANDLERS;
  }

  @Override
  public HandlerList getHandlers() {
    return null;
  }
}
