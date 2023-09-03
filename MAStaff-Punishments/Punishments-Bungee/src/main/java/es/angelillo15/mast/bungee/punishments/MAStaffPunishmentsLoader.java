package es.angelillo15.mast.bungee.punishments;

import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.api.cmd.sender.BungeeConsoleCommandSender;
import es.angelillo15.mast.api.punishments.PunishPlayer;
import es.angelillo15.mast.api.punishments.PunishPlayersManager;
import lombok.Getter;

public class MAStaffPunishmentsLoader extends MAStaffPunishments {
  @Getter private static MAStaffPunishmentsLoader instance;

  @Override
  public void onEnable() {
    instance = this;
    super.onEnable();
    loadConfig();
    loadData();
    loadCommands();
    loadListeners();
    loadTemplates();
    getLogger().info(TextUtils.simpleColorize("&aMAStaff-Punishments has been enabled!"));
    PunishPlayersManager.addPlayer(
        instance
            .getMastaffInstance()
            .getInjector()
            .getInstance(PunishPlayer.class)
            .setPlayer(new BungeeConsoleCommandSender()));
  }

  @Override
  public void onDisable() {
    super.onDisable();
  }

  @Override
  public void reload() {
    super.reload();
  }
}
