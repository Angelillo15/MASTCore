package es.angelillo15.mast.bukkit;

import es.angelillo15.mast.bukkit.api.BStaffPlayer;
import es.angelillo15.mast.bukkit.api.item.types.StaffItem;
import es.angelillo15.mast.bukkit.cmd.FreezeCMD;
import es.angelillo15.mast.bukkit.cmd.MASTCoreCMD;
import es.angelillo15.mast.bukkit.cmd.StaffCMD;
import es.angelillo15.mast.bukkit.cmd.StaffChatCMD;
import es.angelillo15.mast.bukkit.config.ConfigLoader;
import es.angelillo15.mast.bukkit.listeners.*;
import es.angelillo15.mast.bukkit.utils.FreezeUtils;
import es.angelillo15.mast.bukkit.utils.Messages;
import es.angelillo15.mast.bukkit.utils.VanishUtils;
import es.angelillo15.mast.bukkit.utils.items.InternalModules;
import es.angelillo15.mast.bukkit.utils.scoreBoard.TeamManager;
import es.angelillo15.mast.database.PluginConnection;
import es.angelillo15.mast.database.SQLQueries;
import es.angelillo15.mast.utils.PLUtils;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.simpleyaml.configuration.file.YamlFile;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

public class MASTBukkitManager extends JavaPlugin {
    public static final int serverVersion = Integer.parseInt(Bukkit.getBukkitVersion().split("-")[0].split("\\.")[1]);
    private static Permission perms = null;
    private static MASTBukkitManager instance;
    private HashMap<UUID, BStaffPlayer> staffPlayers = new HashMap<UUID, BStaffPlayer>();
    private HashMap<UUID, BStaffPlayer> vanishedPlayers = new HashMap<UUID, BStaffPlayer>();
    private HashMap<UUID, ArrayList<StaffItem>> playersStaffItems = new HashMap<UUID, ArrayList<StaffItem>>();
    private HashMap<UUID, Player> freezePlayers = new HashMap<UUID, Player>();
    private static Logger Logger;
    private PluginDescriptionFile pdf = this.getDescription();
    private String version = pdf.getVersion();
    private ConfigLoader configLoader;
    private PluginConnection pluginConnection;
    private static ArrayList<StaffItem> internalModules;
    private static Integer onlinePlayers;
    private static TeamManager teamManager;

    public void drawLogo() {
        instance = this;
        Logger = this.getLogger();
        MASTBukkitManager plugin = this;
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', PLUtils.Logo.replace("{version}", version)));
    }

    public void loadConfig() {
        configLoader = new ConfigLoader(this);
        configLoader.load();
    }

    public void setupModules(){
        InternalModules modules = new InternalModules();
        internalModules = modules.getItems();
    }

    public void registerCommands() {
        this.getCommand("staff").setExecutor(new StaffCMD());
        this.getCommand("freeze").setExecutor(new FreezeCMD());
        this.getCommand("staffchat").setExecutor(new StaffChatCMD());
        this.getCommand("mastcore").setExecutor(new MASTCoreCMD());
    }

    public void setupPermissions() {
        if(Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return;
        }
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
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
        pm.registerEvents(new FreezeEvent(), this);
        pm.registerEvents(new StaffWorldChangeEvent(), this);
        FreezeUtils.setupMessageSender();
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

    public void reload(){
        SQLQueries.closeConnection(pluginConnection.getConnection());
        databaseConnection();
        configLoader.load();
        internalModules.clear();
        setupModules();
        Messages.reload();
    }

    public void initializeGlowSupport(){
        if(serverVersion >= 9){
            teamManager = new TeamManager();
            getLogger().severe("Glow support enabled!");
        }
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

    public void removeStaffPlayer(@NotNull BStaffPlayer staffPlayer) {
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

    public HashMap<UUID, Player> getFreezePlayers() {
        return freezePlayers;
    }
    public void addFreezePlayer(UUID uuid, Player player) {
        freezePlayers.put(uuid, player);
    }
    public void removeFreezePlayer(UUID uuid) {
        freezePlayers.remove(uuid);
    }
    public Player getFreezePlayer(UUID uuid) {
        return freezePlayers.get(uuid);
    }
    public boolean containsFreezePlayer(UUID uuid) {
        return freezePlayers.containsKey(uuid);
    }
    public static Integer getOnlinePlayers() {
        return onlinePlayers;
    }
    public static void setOnlinePlayers(Integer onlinePlayers) {
        MASTBukkitManager.onlinePlayers = onlinePlayers;
    }
    public static ArrayList<StaffItem> getInternalModules(){
        return internalModules;
    }
    public static Permission getPerms() {
        return perms;
    }
    public static TeamManager getTeamManager(){
        return teamManager;
    }
}
