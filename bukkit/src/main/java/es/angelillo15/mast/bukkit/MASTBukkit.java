package es.angelillo15.mast.bukkit;

public class MASTBukkit extends MASTBukkitManager {
    @Override
    public void onEnable() {
        drawLogo();
        loadConfig();
        databaseConnection();
    }
    @Override
    public void onDisable() {

    }
}
