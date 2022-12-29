package es.angelillo15.mast.bukkit;

public class MAStaffLoader extends MAStaff {
    @Override
    public void onEnable() {
        super.onEnable();
        drawLogo();
        loadConfig();
        registerCommands();
        registerListeners();
        loadDatabase();
        loadModules();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
