package com.nookure.staff.paper.extension.vanish;

import com.nookure.staff.api.StaffPlayerWrapper;
import com.nookure.staff.api.extension.VanishExtension;
import com.nookure.staff.paper.StaffPaperPlayerWrapper;
import de.myzelyam.api.vanish.VanishAPI;

public class SuperVanishExtension extends VanishExtension {
  private final StaffPaperPlayerWrapper player;
  public SuperVanishExtension(StaffPlayerWrapper player) {
    super(player);
    this.player = (StaffPaperPlayerWrapper) player;
  }

  @Override
  public void enableVanish(boolean silent) {
    VanishAPI.hidePlayer(player.getPlayer());
  }

  @Override
  public void disableVanish(boolean silent) {
    VanishAPI.showPlayer(player.getPlayer());
  }

  @Override
  public boolean isVanished() {
    return VanishAPI.isInvisible(player.getPlayer());
  }

  @Override
  public void setVanished(boolean vanished) {
  }
}
