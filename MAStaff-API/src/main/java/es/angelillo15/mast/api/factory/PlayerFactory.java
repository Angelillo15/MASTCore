package es.angelillo15.mast.api.factory;

import org.bukkit.entity.Player;

public interface PlayerFactory<T> {
  T createPlayer(Player player);
}
