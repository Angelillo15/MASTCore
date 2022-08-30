package es.angelillo15.mast.bukkit;

import es.angelillo15.mast.bukkit.config.ConfigLoader;
import es.angelillo15.mast.config.ConfigManager;
import es.angelillo15.mast.database.PluginConnection;
import es.angelillo15.mast.utils.PLUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.simpleyaml.configuration.file.YamlFile;

import java.sql.Connection;
import java.util.logging.Logger;

public class MASTBukkitManager extends JavaPlugin {
    private static Logger Logger;
    private PluginDescriptionFile pdf = this.getDescription();
    private String version = pdf.getVersion();
    private Connection connection;
    private ConfigLoader configLoader;

    public void initLogger(){
        MASTBukkitManager.Logger = this.getLogger();
    }
    public void drawLogo(){
        Logger = this.getLogger();
        MASTBukkitManager plugin = this;
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', PLUtils.Logo.replace("{version}", version)));
    }

    public void loadConfig(){
        configLoader = new ConfigLoader(this);
        configLoader.load();
    }

    public static Logger getPluginLogger() {
        return Logger;
    }

    public void databaseConnection(){
        YamlFile config = ConfigLoader.getConfig().getConfig();
        String host = config.getString("Database.host");
        int port = Integer.parseInt(config.getString("Database.port"));
        String database = config.getString("Database.database");
        String user = config.getString("Database.user");
        String password = config.getString("Database.password");
        String type = config.getString("Database.type");

        PluginConnection pluginConnection = new PluginConnection(host, port,database, user, password, type, this.getDataFolder().getAbsolutePath(), this.getLogger());
    }
}
