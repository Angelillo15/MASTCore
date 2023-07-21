package es.angelillo15.mast.bungee.punishments;

import com.google.inject.Injector;
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
    private final Injector injector = getMaStaffInstance().getInjector();
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
        registerEvent(injector.getInstance(PlayerBanListener.class));
        registerEvent(injector.getInstance(BroadcastListener.class));
        registerEvent(injector.getInstance(PunishPlayerListener.class));
    }

    public void registerEvent(Listener listener) {
        ProxyServer.getInstance().getPluginManager().registerListener((Plugin) getMaStaffInstance(), listener);
    }


    public void loadCommands() {
        getMaStaffInstance().registerCommand(injector.getInstance(BanCMD.class));
        getMaStaffInstance().registerCommand(injector.getInstance(IsBannedCMD.class));
        getMaStaffInstance().registerCommand(injector.getInstance(ClearCacheCMD.class));
        getMaStaffInstance().registerCommand(injector.getInstance(TempBanCMD.class));
        getMaStaffInstance().registerCommand(injector.getInstance(UnBanCMD.class));
        getMaStaffInstance().registerCommand(injector.getInstance(IPBanCMD.class));
        getMaStaffInstance().registerCommand(injector.getInstance(TempIPBanCMD.class));
        getMaStaffInstance().registerCommand(injector.getInstance(KickCMD.class));
        getMaStaffInstance().registerCommand(injector.getInstance(WarnCMD.class));
        getMaStaffInstance().registerCommand(injector.getInstance(UnWarnCMD.class));
    }

    public void loadConfig() {
        ConfigLoader.load(this);
    }

    public void loadData() {
        DataManager.load();
    }


}