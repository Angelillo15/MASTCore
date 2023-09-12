package es.angelillo15.mast.vanish;

import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.factory.StaffPlayerFactory;
import es.angelillo15.mast.api.player.IVanishPlayer;
import es.angelillo15.mast.vanish.listeners.VanishListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class VanishPlayer implements IVanishPlayer, StaffPlayerFactory<VanishPlayer> {

  private IStaffPlayer player;

  @Override
  public void enableVanish() {
    Bukkit.getOnlinePlayers().forEach(p -> {
      if (p == player.getPlayer()) {
        return;
      }

      if (p.hasPermission("mast.vanish.see")) {
        return;
      }

      hide(p);
    });
  }

  @Override
  public void disableVanish() {
    Bukkit.getOnlinePlayers().forEach(p -> {
      if (p == player.getPlayer()) {
        return;
      }

      show(p);
    });
  }

  @Override
  public boolean isVanished() {
    return player.isVanished();
  }

  public void hide(Player player) {
    VanishListener.hide(this.player.getPlayer(), player);
  }

  public void show(Player player) {
    VanishListener.show(this.player.getPlayer(), player);
  }

  @Override
  public VanishPlayer createStaffPlayer(IStaffPlayer player) {
    this.player = player;
    return this;
  }
}
