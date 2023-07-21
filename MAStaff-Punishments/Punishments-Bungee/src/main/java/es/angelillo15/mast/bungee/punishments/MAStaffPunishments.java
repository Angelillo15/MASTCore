package es.angelillo15.mast.bungee.punishments;

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
        ProxyServer.getInstance().getPluginManager().registerListener((Plugin) getMaStaffInstance(), listener);
    }


    public void loadCommands() {
        getMaStaffInstance().registerCommand(new BanCMD());
        getMaStaffInstance().registerCommand(new IsBannedCMD());
        getMaStaffInstance().registerCommand(new ClearCacheCMD());
        getMaStaffInstance().registerCommand(new TempBanCMD());
        getMaStaffInstance().registerCommand(new UnBanCMD());
        getMaStaffInstance().registerCommand(new IPBanCMD());
        getMaStaffInstance().registerCommand(new TempIPBanCMD());
        getMaStaffInstance().registerCommand(new KickCMD());
        getMaStaffInstance().registerCommand(new WarnCMD());
        getMaStaffInstance().registerCommand(new UnWarnCMD());
    }

    public void loadConfig() {
        ConfigLoader.load(this);
    }

    public void loadData() {
        DataManager.load();
    }


}