package es.angelillo15.mast.bungee.punishments;

import es.angelillo15.mast.api.TextUtils;

public class MAStaffPunishmentsLoader extends MAStaffPunishments {
    @Override
    public void onEnable() {
        super.onEnable();
        loadData();
        loadCommands();
        loadListeners();
        getLogger().info(TextUtils.simpleColorize("&aMAStaff-Punishments has been enabled!"));

    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void reload() {
        super.reload();
    }
}
