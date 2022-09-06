package es.angelillo15.mast.bungee;

import es.angelillo15.mast.utils.PLUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

public class MASTBungeeManager extends Plugin {
    public void drawLogo(){
        getLogger().info(ChatColor.translateAlternateColorCodes('&' ,PLUtils.Logo.replace(
                "{version}", getDescription().getVersion()))
        );
        ProxiedPlayer player = null;
    }

}
