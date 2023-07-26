package es.angelillo15.mast.bungee.inject;

import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.inject.CommonModule;
import es.angelillo15.mast.api.utils.MAStaffInject;
import es.angelillo15.mast.bungee.MAStaff;
import es.angelillo15.mast.bungee.utils.BungeeServerUtils;

public class BungeeInjector extends CommonModule {
    @Override
    protected void configure() {
        super.configure();
        bind(MAStaffInstance.class).toInstance(MAStaff.getInstance());
        bind(MAStaffInject.class).toInstance(MAStaff.getInstance());
        bind(MAStaff.class).toInstance(MAStaff.getInstance());
        bind(BungeeServerUtils.class).asEagerSingleton();
    }
}