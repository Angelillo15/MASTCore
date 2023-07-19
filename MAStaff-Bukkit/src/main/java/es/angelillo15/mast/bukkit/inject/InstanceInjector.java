package es.angelillo15.mast.bukkit.inject;

import com.google.inject.AbstractModule;
import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.bukkit.MAStaff;

public class InstanceInjector extends AbstractModule {
    @Override
    protected void configure() {
        bind(MAStaffInstance.class).toInstance(MAStaff.getPlugin());
    }
}
