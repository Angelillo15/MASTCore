package es.angelillo15.mast.vanish;

import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.player.IVanishPlayer;
import es.angelillo15.mast.api.vanish.VanishDataManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
@SuppressWarnings("deprecation")
public class VanishPlayer implements IVanishPlayer {
    ArrayList<Player> vanishedFor = new ArrayList<>();
    private final IStaffPlayer player;

    public VanishPlayer(IStaffPlayer player) {
        this.player = player;
    }

    @Override
    public void enableVanish() {
        VanishDataManager.addVanishedPlayer(player);

        Bukkit.getOnlinePlayers().forEach(p -> {
            if (!p.hasPermission("mast.vanish.see")) {
                p.hidePlayer(player.getPlayer());
                vanishedFor.add(p);
            }
        });

        sendPlayerInfoChangeGameModePacket(true);

    }

    @Override
    public void disableVanish() {
        VanishDataManager.removeVanishedPlayer(player);

        vanishedFor.forEach(p -> {
            p.showPlayer(player.getPlayer());
        });

        sendPlayerInfoChangeGameModePacket(false);


        vanishedFor.clear();
    }

    @Override
    public boolean isVanished() {
        return VanishDataManager.isVanished(player);
    }

    @Override
    public boolean isVanishedFor(Player player) {
        return vanishedFor.contains(player);
    }

    @Override
    public void sendPlayerInfoChangeGameModePacket(boolean vanished) {
        Bukkit.getOnlinePlayers().forEach(p -> {
            if (isVanishedFor(p)) {
                return;
            }

            MAStaffVanish.getPacketUtils().sendPlayerInfoChangeGameModePacket(player.getPlayer(), p, vanished);
        });
    }

    @Override
    public void removeVanishedFor(Player player) {
        vanishedFor.remove(player);
    }

    @Override
    public void addVanishedFor(Player player) {
        vanishedFor.add(player);
    }
}
