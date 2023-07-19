package es.angelillo15.mast.bukkit.inject;

import com.google.inject.AbstractModule;
import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.managers.StaffManager;
import es.angelillo15.mast.bukkit.MAStaff;

public class BukkitInjector extends AbstractModule {
    @Override
    protected void configure() {
        bind(MAStaffInstance.class).toInstance(MAStaff.getPlugin());
        bind(StaffManager.class).asEagerSingleton();
    }
}
