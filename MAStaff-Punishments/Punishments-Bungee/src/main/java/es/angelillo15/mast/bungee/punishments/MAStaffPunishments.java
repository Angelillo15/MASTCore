package es.angelillo15.mast.bungee.punishments;

import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.addons.MAStaffAddon;
import es.angelillo15.mast.api.punishments.cmd.BanCMD;
import es.angelillo15.mast.api.punishments.cmd.IsBanned;
import es.angelillo15.mast.api.punishments.config.ConfigLoader;
import es.angelillo15.mast.api.punishments.data.DataManager;
import es.angelillo15.mast.api.punishments.cmd.TestCMD;
import es.angelillo15.mast.bungee.punishments.listeners.PlayerJoinListener;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Event;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

public class MAStaffPunishments extends MAStaffAddon<Plugin> {
    @Override
    public void onEnable() {
        super.onEnable();
    }

    public void loadListeners() {
        registerEvent(new PlayerJoinListener());
    }

    public void registerEvent(Listener listener) {
        ProxyServer.getInstance().getPluginManager().registerListener((Plugin) MAStaffInstance.getBungeeInstance(), listener);
    }

    public void loadAPI() {

    }

    public void loadCommands() {
        MAStaffInstance.getBungeeInstance().registerCommand(new TestCMD());
        MAStaffInstance.getBungeeInstance().registerCommand(new BanCMD());
        MAStaffInstance.getBungeeInstance().registerCommand(new IsBanned());
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