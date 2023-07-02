package es.angelillo15.mast.vanish;

import com.comphenix.protocol.ProtocolLibrary;
import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.addons.MAStaffAddon;
import es.angelillo15.mast.api.utils.ServerUtils;
import es.angelillo15.mast.vanish.listeners.VanishListener;
import es.angelillo15.mast.vanish.packets.BukkitPacketUtils;
import es.angelillo15.mast.vanish.packets.IPacketUtils;
import es.angelillo15.mast.vanish.packets.ProtocolLibPacketUtils;
import es.angelillo15.mast.vanish.packets.custom.PlayerInfoPacketAdapter;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class MAStaffVanish extends MAStaffAddon<JavaPlugin> {
    @Getter
    private static boolean useProtocolLib;
    @Getter
    private static IPacketUtils packetUtils;

    @Override
    public void onEnable() {
        getLogger().info("Enabling MAStaff Vanish module...");
        useProtocolLib = ServerUtils.isProtocolLibInstalled();
        loadPackets();
        registerListeners();
    }

    private void loadPackets() {
        if (useProtocolLib) {
            packetUtils = new ProtocolLibPacketUtils();
            getLogger().debug("Using ProtocolLib packet utils");

            if (MAStaffInstance.version() >= 19) {
                ProtocolLibrary.getProtocolManager().addPacketListener(new PlayerInfoPacketAdapter());
                getMastaffInstance().getServer().getPluginManager().registerEvents(new VanishListener(), getMastaffInstance());
            }

        } else {
            packetUtils = new BukkitPacketUtils();
            getLogger().debug("Using Bukkit packet utils");
        }
    }

    void registerListeners() {
        getMastaffInstance().getServer().getPluginManager().registerEvents(new VanishListener(), getMastaffInstance());
    }
}
