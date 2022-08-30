package es.angelillo15.mast.bukkit;

import es.angelillo15.mast.bukkit.config.ConfigLoader;
import es.angelillo15.mast.config.ConfigManager;
import es.angelillo15.mast.utils.PLUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class MASTBukkitManager extends JavaPlugin {
    private static Logger Logger;
    private PluginDescriptionFile pdf = this.getDescription();
    private String version = pdf.getVersion();

    public void initLogger(){
        MASTBukkitManager.Logger = this.getLogger();
    }
    public void drawLogo(){
        Logger = this.getLogger();
        MASTBukkitManager plugin = this;
        plugin.getDataFolder().getAbsolutePath();
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', PLUtils.Logo.replace("{version}", version)));
    }

    public void loadConfig(){
        ConfigLoader configLoader = new ConfigLoader(this);
        configLoader.load();
    }

    public static Logger getPluginLogger() {
        return Logger;
    }
}
