package com.nookure.mast.api.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public interface BukkitListener<T> extends Listener {
  @EventHandler
  void handle(T event);
}
