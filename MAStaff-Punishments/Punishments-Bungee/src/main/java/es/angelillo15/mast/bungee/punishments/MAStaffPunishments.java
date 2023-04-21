package es.angelillo15.mast.bungee.punishments;

import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.addons.MAStaffAddon;
import es.angelillo15.mast.api.punishments.cmd.BanCMD;
import es.angelillo15.mast.api.punishments.data.DataManager;
import es.angelillo15.mast.api.punishments.cmd.TestCMD;
import net.md_5.bungee.api.plugin.Plugin;

public class MAStaffPunishments extends MAStaffAddon<Plugin> {
    @Override
    public void onEnable() {
        super.onEnable();

    }

    public void loadListeners() {

    }

    public void loadAPI() {

    }

    public void loadCommands() {
        MAStaffInstance.getBungeeInstance().registerCommand(new TestCMD());
        MAStaffInstance.getBungeeInstance().registerCommand(new BanCMD());
    }

    public void loadConfig() {

    }

    public void loadMetrics() {

    }

    public void loadData() {
        DataManager.load();
    }


}