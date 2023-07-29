package es.angelillo15.mast.bukkit;

import es.angelillo15.mast.api.config.bukkit.ConfigLoader;
import es.angelillo15.mast.api.database.PluginConnection;
import es.angelillo15.mast.api.thread.AsyncThreadKt;
import es.angelillo15.mast.api.utils.PermsUtils;
import es.angelillo15.mast.bukkit.addons.AddonsLoader;
import es.angelillo15.mast.bukkit.legacy.BukkitLegacyLoader;
import es.angelillo15.mast.bukkit.loaders.ItemsLoader;
import es.angelillo15.mast.bukkit.utils.LibsLoader;
import es.angelillo15.mast.bukkit.utils.scheduler.Scheduler;
import io.papermc.lib.PaperLib;
import mc.obliviate.inventory.InventoryAPI;

public class LiteLoader extends MAStaff {
    @Override
    public void onEnable() {
        isFree = true;
        super.onEnable();
        LibsLoader.loadLibs();
        setupMiniMessage();
        drawLogo();
        inject();
        loadConfig();
        registerCommands();
        registerListeners();
        loadDatabase();
        loadDefaultAddons();
        loadModules();
        Scheduler.execute(this::checkUpdates);
        debugInfo();
        PaperLib.suggestPaper(this);
        registerPlaceholderAPI();
        AsyncThreadKt.start();
        getLogger().warning("[!] This is the free version of MAStaff");
        getLogger().warning("[!] If you want to get more features, consider buying the premium version");

    }

    @Override
    public void loadDatabase() {
        loadSqlite();
        PluginConnection.getQueries().createTables();

        super.getLogger().info("Connected to SQLite Database!");
    }

    @Override
    public void loadModules() {
        ItemsLoader.load();
        new InventoryAPI(this).init();

        if (version < 9) {
            super.getLogger().info("Loading legacy modules...");
            new BukkitLegacyLoader().load(this);
        }

        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return;
        }

        PermsUtils.setupPermissions();
    }

    @Override
    public void loadConfig() {
        new ConfigLoader(this, true).load();
    }

    public void loadDefaultAddons(){
        AddonsLoader.loadDefaultAddons(true);
    }
}
