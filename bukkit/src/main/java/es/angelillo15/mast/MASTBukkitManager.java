package es.angelillo15.mast;

import es.angelillo15.mast.utils.PLUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class MASTBukkitManager extends JavaPlugin {
    public void drawLogo(){
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', PLUtils.Logo));
    }

}
