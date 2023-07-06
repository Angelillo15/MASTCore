package es.angelillo15.mast.vanish;

import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.player.IVanishPlayer;
import es.angelillo15.mast.api.vanish.VanishDataManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
@SuppressWarnings("deprecation")
public class VanishPlayer implements IVanishPlayer {
    private final IStaffPlayer player;

    public VanishPlayer(IStaffPlayer player) {
        this.player = player;
    }

    @Override
    public void enableVanish() {
        VanishDataManager.addVanishedPlayer(player);

        Bukkit.getOnlinePlayers().forEach(p -> {
            if (p.hasPermission("mast.vanish.see")) {
                return;
            }

            hide(player.getPlayer());
        });
    }

    @Override
    public void disableVanish() {
        VanishDataManager.removeVanishedPlayer(player);

        Bukkit.getOnlinePlayers().forEach(p -> {
            show(player.getPlayer());
        });
    }

    @Override
    public boolean isVanished() {
        return VanishDataManager.isVanished(player);
    }

    public void hide(Player player) {
        if (MAStaffInstance.version() > 12) {
            player.hidePlayer(MAStaffInstance.getInstance().getPluginInstance(), this.player.getPlayer());
        } else {
            player.hidePlayer(this.player.getPlayer());
        }
    }

    public void show(Player player) {
        if (MAStaffInstance.version() > 12) {
            player.showPlayer(MAStaffInstance.getInstance().getPluginInstance(), this.player.getPlayer());
        } else {
            player.showPlayer(this.player.getPlayer());
        }
    }
}
