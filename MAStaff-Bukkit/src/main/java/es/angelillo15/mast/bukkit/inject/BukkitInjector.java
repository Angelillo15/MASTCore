package es.angelillo15.mast.bukkit.inject;

import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.inject.CommonModule;
import es.angelillo15.mast.bukkit.MAStaff;

public class BukkitInjector extends CommonModule {
    @Override
    protected void configure() {
        super.configure();
        bind(MAStaffInstance.class).toInstance(MAStaff.getPlugin());
        bind(MAStaff.class).toInstance(MAStaff.getPlugin());
    }

}
