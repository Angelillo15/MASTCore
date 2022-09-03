package es.angelillo15.mast.bukkit;

import es.angelillo15.mast.api.BStaffPlayer;
import es.angelillo15.mast.bukkit.cmd.StaffCMD;
import es.angelillo15.mast.bukkit.config.ConfigLoader;
import es.angelillo15.mast.database.PluginConnection;
import es.angelillo15.mast.database.SQLQueries;
import es.angelillo15.mast.utils.PLUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.simpleyaml.configuration.file.YamlFile;

import java.sql.Connection;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

public class MASTBukkitManager extends JavaPlugin {
    private static MASTBukkitManager instance;
    private HashMap<UUID, BStaffPlayer> staffPlayers = new HashMap<UUID, BStaffPlayer>();
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
        instance = this;
    }
    public void registerCommands(){
        this.getCommand("staff").setExecutor(new StaffCMD());
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

        if(!(SQLQueries.staffDataCreate(pluginConnection.getConnection()))){
            this.getLogger().severe("StaffData table isn't created, creating....");
            this.getLogger().info("Type: "+type);
            if(type.equalsIgnoreCase("SQLite")) {
                SQLQueries.createStaffDataTableSQLite(pluginConnection.getConnection());
                this.getLogger().info("StaffData table created!");
            }else {
                SQLQueries.createStaffDataTableMySQL(pluginConnection.getConnection());
                this.getLogger().info("StaffData table created!");
            }
        }
    }
    public static MASTBukkitManager getInstance() {
        return instance;
    }
    public HashMap<UUID, BStaffPlayer> getStaffPlayers() {
        return staffPlayers;
    }
    public void addStaffPlayer(BStaffPlayer staffPlayer){
        staffPlayers.put(staffPlayer.getPlayer().getUniqueId(), staffPlayer);
    }
    public void removeStaffPlayer(BStaffPlayer staffPlayer){
        staffPlayers.remove(staffPlayer.getPlayer().getUniqueId());
    }
    public BStaffPlayer getSStaffPlayer(UUID uuid){
        return staffPlayers.get(uuid);
    }
    public boolean containsStaffPlayer(UUID uuid){
        return staffPlayers.containsKey(uuid);
    }
}
