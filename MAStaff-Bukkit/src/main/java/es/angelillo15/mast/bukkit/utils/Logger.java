package es.angelillo15.mast.bukkit.utils;

import es.angelillo15.mast.api.ILogger;
import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.bukkit.MAStaff;

public class Logger implements ILogger {
    @Override
    public void info(String message) {
        TextUtils.getAudienceProvider().console().sendMessage(TextUtils.toComponent("[MAStaff] "+message));
    }

    @Override
    public void warn(String message) {
        MAStaff.getPlugin().getLogger().warning(message);
    }

    @Override
    public void error(String message) {
        MAStaff.getPlugin().getLogger().severe(message);
    }

    @Override
    public void debug(String message) {
        if(MAStaff.getPlugin().isDebug()){
            MAStaff.getPlugin().getLogger().info("[DEBUG] " + message);
        }
    }
}
