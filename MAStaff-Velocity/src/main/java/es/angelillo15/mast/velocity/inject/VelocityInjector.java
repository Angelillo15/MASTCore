package es.angelillo15.mast.velocity.inject;

import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.config.common.CommonConfig;
import es.angelillo15.mast.api.config.velocity.VelocityConfig;
import es.angelillo15.mast.api.inject.CommonModule;
import es.angelillo15.mast.api.utils.MAStaffInject;
import es.angelillo15.mast.velocity.MAStaff;

public class VelocityInjector extends CommonModule {
    @Override
    protected void configure() {
        super.configure();
        bind(MAStaffInstance.class).toInstance(MAStaff.getInstance());
        bind(MAStaffInject.class).toInstance(MAStaff.getInstance());
        bind(MAStaff.class).toInstance(MAStaff.getInstance());
        bind(CommonConfig.class).asEagerSingleton();
        bind(VelocityConfig.class).asEagerSingleton();
    }
}
