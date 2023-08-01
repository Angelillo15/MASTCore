package es.angelillo15.mast.api.utils;

import es.angelillo15.mast.api.TextUtils;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeUtils {
    public static void setAudienceBungee(Plugin plugin) {
        TextUtils.setAudienceProvider(BungeeAudiences.create(plugin));
    }
}
