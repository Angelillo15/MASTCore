package es.angelillo15.mast.bungee.punishments;

import com.google.inject.Inject;
import com.google.inject.Injector;
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
    @Inject
    private MAStaffInstance instance;

    private Injector injector;
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
        injector = instance.getInjector();
    }

    public void loadListeners() {
        registerEvent(injector.getInstance(PlayerBanListener.class));
        registerEvent(injector.getInstance(BroadcastListener.class));
        registerEvent(injector.getInstance(PunishPlayerListener.class));
    }

    public void registerEvent(Listener listener) {
        ProxyServer.getInstance().getPluginManager().registerListener((Plugin) getMastaffInstance(), listener);
    }


    public void loadCommands() {
        getMastaffInstance().registerCommand(injector.getInstance(BanCMD.class));
        getMastaffInstance().registerCommand(injector.getInstance(IsBannedCMD.class));
        getMastaffInstance().registerCommand(injector.getInstance(ClearCacheCMD.class));
        getMastaffInstance().registerCommand(injector.getInstance(TempBanCMD.class));
        getMastaffInstance().registerCommand(injector.getInstance(UnBanCMD.class));
        getMastaffInstance().registerCommand(injector.getInstance(IPBanCMD.class));
        getMastaffInstance().registerCommand(injector.getInstance(TempIPBanCMD.class));
        getMastaffInstance().registerCommand(injector.getInstance(KickCMD.class));
        getMastaffInstance().registerCommand(injector.getInstance(WarnCMD.class));
        getMastaffInstance().registerCommand(injector.getInstance(UnWarnCMD.class));
    }

    public void loadConfig() {
        ConfigLoader.load(this);
    }

    public void loadData() {
        DataManager.load();
    }


}