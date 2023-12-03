package es.angelillo15.mast.bukkit;

import es.angelillo15.mast.api.managers.loader.ReflectionLoader;
import es.angelillo15.mast.api.thread.AsyncThreadKt;
import es.angelillo15.mast.bukkit.addons.AddonsLoader;
import es.angelillo15.mast.bukkit.utils.LibsLoader;
import es.angelillo15.mast.bukkit.utils.scheduler.Scheduler;
import io.papermc.lib.PaperLib;

public class MAStaffLoader extends MAStaff {
  @Override
  public void onEnable() {
    super.onEnable();
    LibsLoader.loadLibs();
    setupMiniMessage();
    drawLogo();
    inject();
    loadConfig();
    registerCommands();
    registerListeners();
    loadDatabase();
    loadModules();
    AddonsLoader.loadAddons();
    Scheduler.execute(this::checkUpdates);
    debugInfo();
    ReflectionLoader.loadAll();
    ReflectionLoader.loadBukkit();
    PaperLib.suggestPaper(this);
    registerPlaceholderAPI();
    AsyncThreadKt.start();
  }

  @Override
  public void onDisable() {
    AddonsLoader.disableAddons();
    super.onDisable();
    AsyncThreadKt.stop();
    disableAddons();
  }
}
