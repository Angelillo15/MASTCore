package es.angelillo15.mast.bukkit;

import es.angelillo15.mast.bukkit.api.BStaffPlayer;
import es.angelillo15.mast.bukkit.api.item.StaffItem;
import es.angelillo15.mast.bukkit.cmd.StaffCMD;
import es.angelillo15.mast.bukkit.config.ConfigLoader;
import es.angelillo15.mast.bukkit.listeners.*;
import es.angelillo15.mast.bukkit.utils.VanishUtils;
import es.angelillo15.mast.bukkit.utils.items.InternalModules;
import es.angelillo15.mast.database.PluginConnection;
import es.angelillo15.mast.database.SQLQueries;
import es.angelillo15.mast.utils.PLUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.simpleyaml.configuration.file.YamlFile;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

public class MASTBukkitManager extends JavaPlugin {
    private static MASTBukkitManager instance;
    private HashMap<UUID, BStaffPlayer> staffPlayers = new HashMap<UUID, BStaffPlayer>();
    private HashMap<UUID, BStaffPlayer> vanishedPlayers = new HashMap<UUID, BStaffPlayer>();
    private HashMap<UUID, ArrayList<StaffItem>> playersStaffItems = new HashMap<UUID, ArrayList<StaffItem>>();
    private static Logger Logger;
    private PluginDescriptionFile pdf = this.getDescription();
    private String version = pdf.getVersion();
    private ConfigLoader configLoader;
    private PluginConnection pluginConnection;
    private static ArrayList<StaffItem> internalModules;

    public void initLogger() {
        MASTBukkitManager.Logger = this.getLogger();
    }

    public void drawLogo() {
        Logger = this.getLogger();
        MASTBukkitManager plugin = this;
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', PLUtils.Logo.replace("{version}", version)));
    }

    public void loadConfig() {
        configLoader = new ConfigLoader(this);
        configLoader.load();
        instance = this;
    }

    public void setupModules(){
        InternalModules modules = new InternalModules();
        internalModules = modules.getItems();
    }

    public void registerCommands() {
        this.getCommand("staff").setExecutor(new StaffCMD());
    }

    public void registerEvents(){
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new VanishEvents(), this);
        VanishUtils.checkForStaffPlayers();
        pm.registerEvents(new ItemClickEvent(), this);
        pm.registerEvents(new ItemDropEvent(), this);
        pm.registerEvents(new VanishEvents(), this);
        pm.registerEvents(new StaffInventoryClickEvent(), this);
        pm.registerEvents(new StaffItemPickup(), this);
    }

    public static Logger getPluginLogger() {
        return Logger;
    }

    public void databaseConnection() {
        YamlFile config = ConfigLoader.getConfig().getConfig();
        String host = config.getString("Database.host");
        int port = Integer.parseInt(config.getString("Database.port"));
        String database = config.getString("Database.database");
        String user = config.getString("Database.user");
        String password = config.getString("Database.password");
        String type = config.getString("Database.type");

        pluginConnection = new PluginConnection(host, port, database, user, password, type, this.getDataFolder().getAbsolutePath(), this.getLogger());


        if (type.equalsIgnoreCase("SQLite")) {
            SQLQueries.createStaffDataTableSQLite(pluginConnection.getConnection());
        } else {
            if (!(SQLQueries.staffDataCreate(pluginConnection.getConnection()))) {
                this.getLogger().severe("StaffData table isn't created, creating....");
                SQLQueries.createStaffDataTableMySQL(pluginConnection.getConnection());
                this.getLogger().info("StaffData table created!");
            }
        }
        this.getLogger().info("Type: " + type);

        
    }

    public void setupMessenger(){
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    }

    public void disconnectDatabase() {
        SQLQueries.closeConnection(pluginConnection.getConnection());
    }

    public static MASTBukkitManager getInstance() {
        return instance;
    }

    public HashMap<UUID, BStaffPlayer> getStaffPlayers() {
        return staffPlayers;
    }

    public void addStaffPlayer(BStaffPlayer staffPlayer) {
        staffPlayers.put(staffPlayer.getPlayer().getUniqueId(), staffPlayer);
    }

    public void removeStaffPlayer(BStaffPlayer staffPlayer) {
        staffPlayers.remove(staffPlayer.getPlayer().getUniqueId());
    }

    public HashMap<UUID, BStaffPlayer> getVanishedPlayers() {
        return vanishedPlayers;
    }

    public void addVanishedPlayer(BStaffPlayer staffPlayer) {
        vanishedPlayers.put(staffPlayer.getPlayer().getUniqueId(), staffPlayer);
    }

    public void removeVanishedPlayer(BStaffPlayer staffPlayer) {
        vanishedPlayers.remove(staffPlayer.getPlayer().getUniqueId());
    }

    public BStaffPlayer getSStaffPlayer(UUID uuid) {
        return staffPlayers.get(uuid);
    }

    public boolean containsStaffPlayer(UUID uuid) {
        return staffPlayers.containsKey(uuid);
    }

    public PluginConnection getPluginConnection() {
        return pluginConnection;
    }

    public HashMap<UUID, ArrayList<StaffItem>> getPlayersStaffItems() {
        return playersStaffItems;
    }
    public void addPlayerStaffItems(UUID uuid, ArrayList<StaffItem> staffItems) {
        playersStaffItems.put(uuid, staffItems);
    }
    public void removePlayerStaffItems(UUID uuid) {
        playersStaffItems.remove(uuid);
    }
    public ArrayList<StaffItem> getPlayerStaffItems(UUID uuid) {
        return playersStaffItems.get(uuid);
    }
    public boolean containsPlayerStaffItems(UUID uuid) {
        return playersStaffItems.containsKey(uuid);
    }

    public static ArrayList<StaffItem> getInternalModules(){
        return internalModules;
    }
}
