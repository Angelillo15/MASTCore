package es.angelillo15.mast.bungee;

public class MASTBungee extends MASTBungeeManager {
    @Override
    public void onEnable() {
        drawLogo();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
