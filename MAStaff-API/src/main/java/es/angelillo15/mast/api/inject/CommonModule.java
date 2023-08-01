package es.angelillo15.mast.api.inject;

import com.google.inject.AbstractModule;
import es.angelillo15.mast.api.ILogger;
import es.angelillo15.mast.api.managers.PreviousServerManager;
import es.angelillo15.mast.api.managers.UserDataManager;

public class CommonModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(UserDataManager.class).asEagerSingleton();
        bind(ILogger.class).toInstance(ILogger.getInstance());
        bind(PreviousServerManager.class).asEagerSingleton();
    }
}
