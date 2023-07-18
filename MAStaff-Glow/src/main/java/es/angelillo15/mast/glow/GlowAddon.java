package es.angelillo15.mast.glow;

import es.angelillo15.mast.api.addons.MAStaffAddon;
import es.angelillo15.mast.glow.extension.GlowPlaceholder;
import es.angelillo15.mast.glow.managers.GlowManager;
import es.angelillo15.mast.papi.PlaceholderManager;
import org.bukkit.plugin.java.JavaPlugin;

public class GlowAddon extends MAStaffAddon<JavaPlugin> {
    @Override
    public void onEnable() {
        GlowManager.setup();
        PlaceholderManager.registerPlaceholder(new GlowPlaceholder());
    }

    @Override
    public void reload() {
        super.reload();
    }
}
