package es.angelillo15.mast.bungee.punishments;

import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.addons.MAStaffAddon;
import es.angelillo15.mast.api.punishments.cmd.KickCMD;
import es.angelillo15.mast.api.punishments.cmd.ban.*;
import es.angelillo15.mast.api.punishments.cmd.cache.ClearCacheCMD;
import es.angelillo15.mast.api.config.punishments.ConfigLoader;
import es.angelillo15.mast.api.punishments.cmd.warn.UnWarnCMD;
import es.angelillo15.mast.api.punishments.cmd.warn.WarnCMD;
import es.angelillo15.mast.api.punishments.data.DataManager;
import es.angelillo15.mast.bungee.punishments.listeners.BroadcastListener;
import es.angelillo15.mast.bungee.punishments.listeners.PlayerBanListener;
import es.angelillo15.mast.bungee.punishments.listeners.PunishPlayerListener;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

public class MAStaffPunishments extends MAStaffAddon<Plugin> {
    @Override
    public void reload() {
        loadConfig();
        loadData();
        loadListeners();
        loadCommands();
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    public void loadListeners() {
        registerEvent(new PlayerBanListener());
        registerEvent(new BroadcastListener());
        registerEvent(new PunishPlayerListener());
    }

    public void registerEvent(Listener listener) {
        ProxyServer.getInstance().getPluginManager().registerListener((Plugin) MAStaffInstance.getBungeeInstance(), listener);
    }

    public void loadAPI() {

    }

    public void loadCommands() {
        MAStaffInstance.getBungeeInstance().registerCommand(new BanCMD());
        MAStaffInstance.getBungeeInstance().registerCommand(new IsBannedCMD());
        MAStaffInstance.getBungeeInstance().registerCommand(new ClearCacheCMD());
        MAStaffInstance.getBungeeInstance().registerCommand(new TempBanCMD());
        MAStaffInstance.getBungeeInstance().registerCommand(new UnBanCMD());
        MAStaffInstance.getBungeeInstance().registerCommand(new IPBanCMD());
        MAStaffInstance.getBungeeInstance().registerCommand(new TempIPBanCMD());
        MAStaffInstance.getBungeeInstance().registerCommand(new KickCMD());
        MAStaffInstance.getBungeeInstance().registerCommand(new WarnCMD());
        MAStaffInstance.getBungeeInstance().registerCommand(new UnWarnCMD());
    }

    public void loadConfig() {
        ConfigLoader.load(this);
    }

    public void loadMetrics() {

    }

    public void loadData() {
        DataManager.load();
    }


}