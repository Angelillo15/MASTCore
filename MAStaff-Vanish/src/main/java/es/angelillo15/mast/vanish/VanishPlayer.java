package es.angelillo15.mast.vanish;

import com.google.inject.Inject;
import es.angelillo15.mast.bukkit.nms.VersionSupport;
import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.factory.StaffPlayerFactory;
import es.angelillo15.mast.api.player.IVanishPlayer;
import es.angelillo15.mast.api.vanish.VanishDataManager;
import es.angelillo15.mast.vanish.listeners.VanishListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class VanishPlayer implements IVanishPlayer, StaffPlayerFactory<VanishPlayer> {
  @Inject
  private VersionSupport versionSupport;

  private IStaffPlayer player;

  @Override
  public void enableVanish() {
    VanishDataManager.addVanishedPlayer(player);

    for (Player p : Bukkit.getOnlinePlayers()) {
      if (p == player.getPlayer()) {
        continue;
      }
      
      if (p.hasPermission("mast.vanish.see")) {
        continue;
      }

      versionSupport.sendVanishOnPackets(player.getPlayer(), p);
    }
  }

  @Override
  public void disableVanish() {
    VanishDataManager.removeVanishedPlayer(player);

    Bukkit.getOnlinePlayers().forEach(p -> {
      if (p == player.getPlayer()) {
        return;
      }

      versionSupport.sendVanishOffPackets(player.getPlayer(), p);
    });
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

  @Override
  public VanishPlayer createStaffPlayer(IStaffPlayer player) {
    this.player = player;
    return this;
  }
}
