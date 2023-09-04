package es.angelillo15.mast.glow;

import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.chat.api.ChatColor;
import es.angelillo15.mast.api.player.IGlowPlayer;
import es.angelillo15.mast.api.utils.PermsUtils;
import es.angelillo15.mast.glow.managers.GlowColorManager;
import es.angelillo15.mast.glow.managers.GlowManager;

public class GlowPlayer implements IGlowPlayer {
  private final IStaffPlayer staffPlayer;
  private ChatColor color = ChatColor.GREEN;

  public GlowPlayer(IStaffPlayer staffPlayer) {
    this.staffPlayer = staffPlayer;
  }

  @Override
  public void enableGlow() {
    try {
      color = GlowColorManager.getColor(PermsUtils.getGroup(staffPlayer.getPlayer()));
    } catch (Exception e) {
      color = ChatColor.GREEN;
    }

    staffPlayer.getPlayer().setGlowing(true);

    GlowManager.addPlayer(staffPlayer.getPlayer(), color);
  }

  @Override
  public void disableGlow() {
    GlowManager.removePlayer(staffPlayer.getPlayer());

    staffPlayer.getPlayer().setGlowing(false);
  }

  @Override
  public ChatColor getColor() {
    return color;
  }
}
