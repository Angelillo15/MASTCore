package es.angelillo15.mast.glow.extension;

import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.managers.StaffPlayersManagers;
import es.angelillo15.mast.glow.Placeholder;
import es.angelillo15.mast.glow.PlaceholderData;
import org.bukkit.entity.Player;

@PlaceholderData(key = "glow:color")
public class GlowPlaceholder extends Placeholder {
    @Override
    public String onPlaceholderRequest(Player player, String params) {
        if (player == null) return "";

        if (!StaffPlayersManagers.isStaffPlayer(player)) {
            return "";
        }

        IStaffPlayer staffPlayer = StaffPlayersManagers.getStaffPlayer(player);

        if (staffPlayer.isStaffMode()) {
            return staffPlayer.getGlowPlayer().getColor().toString();
        }

        return "";
    }
}
