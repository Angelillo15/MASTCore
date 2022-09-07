package es.angelillo15.mast.bungee;

import es.angelillo15.mast.bungee.config.ConfigLoader;
import es.angelillo15.mast.bungee.listener.StaffChangeEvent;
import es.angelillo15.mast.utils.PLUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.logging.Logger;

public class MASTBungeeManager extends Plugin {
    private MASTBungeeManager instance;
    private static ConfigLoader configLoader;
    private static Logger logger;
    public void drawLogo(){
        getLogger().info(ChatColor.translateAlternateColorCodes('&' ,PLUtils.Logo.replace(
                "{version}", getDescription().getVersion()))
        );
        logger = this.getLogger();
        instance = this;
    }

    public void registerConfig(){
        configLoader = new ConfigLoader(this);
        configLoader.load();
    }

    public static MASTBungeeManager getInstance(){
        return instance;
    }

    public void registerEvents(){
        getProxy().getPluginManager().registerListener(this, new StaffChangeEvent());
    }

    public static Logger getPluginLogger(){
        return logger;
    }

}
