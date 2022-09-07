package es.angelillo15.mast.bungee;

public class MASTBungee extends MASTBungeeManager {
    @Override
    public void onEnable() {
        drawLogo();
        registerEvents();
        registerConfig();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
