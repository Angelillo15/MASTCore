package es.angelillo15.mast.api.event.bukkit.freeze;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Event called when a player gets frozen
 *
 * @author Angelillo15
 * @since 2.1.0
 */
public class FreezePlayerEvent extends Event {
  private static final HandlerList HANDLERS = new HandlerList();
  private Player frozenPlayer;
  private Player freezerPlayer;

  public FreezePlayerEvent(Player frozenPlayer, Player freezerPlayer) {
    this.frozenPlayer = frozenPlayer;
    this.freezerPlayer = freezerPlayer;
  }

  public static HandlerList getHandlerList() {
    return HANDLERS;
  }

  /**
   * Get the player who was frozen
   *
   * @return Player who was frozen
   */
  public Player getFrozenPlayer() {
    return frozenPlayer;
  }

  /**
   * Get the player who froze the player
   *
   * @return Player who froze the player
   */
  public Player getFreezerPlayer() {
    return freezerPlayer;
  }

  @Override
  public HandlerList getHandlers() {
    return HANDLERS;
  }
}