package es.angelillo15.mast.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import org.slf4j.Logger;

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

    @Inject
    public MAStaffLoader(ProxyServer server, Logger logger) {
        this.server = server;
        MAStaffLoader.logger = logger;

        onEnable();
        drawLogo();
    }
}
