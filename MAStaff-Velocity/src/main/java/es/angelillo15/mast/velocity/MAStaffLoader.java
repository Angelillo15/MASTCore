package es.angelillo15.mast.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import es.angelillo15.mast.velocity.config.ConfigLoader;
import lombok.Getter;
import org.slf4j.Logger;

import java.nio.file.Path;

@Plugin(
        id = "mastaff",
        name = "MAStaff",
        version = Constants.VERSION,
        description = "A plugin for managing staff",
        authors = {
                "Angelillo15"
        }
)
public class MAStaffLoader extends MAStaff{
    private final ProxyServer server;
    @Getter
    private static Logger logger;
    @Getter
    @DataDirectory
    private Path dataFolder;
    @Getter
    private static MAStaffLoader plugin;

    @Inject
    public MAStaffLoader(ProxyServer server, Logger logger) {
        plugin = this;
        this.server = server;
        MAStaffLoader.logger = logger;

        onEnable();
        drawLogo();

        ConfigLoader.load();
    }
}
