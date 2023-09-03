package es.angelillo15.mast.vanish;

import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.player.IVanishPlayer;
import es.angelillo15.mast.api.vanish.VanishDataManager;
import es.angelillo15.mast.vanish.listeners.VanishListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class VanishPlayer implements IVanishPlayer {
  private final IStaffPlayer player;

  public VanishPlayer(IStaffPlayer player) {
    this.player = player;
  }

  @Override
  public void enableVanish() {
    VanishDataManager.addVanishedPlayer(player);

    for (Player p : Bukkit.getOnlinePlayers()) {
      if (p.hasPermission("mast.vanish.see")) {
        continue;
      }

      hide(p);
    }
  }

  @Override
  public void disableVanish() {
    VanishDataManager.removeVanishedPlayer(player);

    Bukkit.getOnlinePlayers().forEach(this::show);
  }

  @Override
  public boolean isVanished() {
    return VanishDataManager.isVanished(player);
  }

  public void hide(Player player) {
    VanishListener.hide(this.player.getPlayer(), player);
  }

  public void show(Player player) {
    VanishListener.show(this.player.getPlayer(), player);
  }
}
