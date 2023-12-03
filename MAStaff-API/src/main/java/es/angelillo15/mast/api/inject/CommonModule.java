package es.angelillo15.mast.api.inject;

import com.google.inject.AbstractModule;
import com.nookure.mast.api.event.EventManager;
import es.angelillo15.mast.api.ILogger;
import es.angelillo15.mast.api.managers.PreviousServerManager;
import es.angelillo15.mast.api.managers.UserDataManager;
import es.angelillo15.mast.api.templates.managers.BanTemplatesManager;
import es.angelillo15.mast.api.templates.managers.WarnTemplateManager;

public class CommonModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(UserDataManager.class).asEagerSingleton();
    bind(ILogger.class).toInstance(ILogger.getInstance());
    bind(PreviousServerManager.class).asEagerSingleton();
    bind(WarnTemplateManager.class).asEagerSingleton();
    bind(BanTemplatesManager.class).asEagerSingleton();
    bind(EventManager.class).asEagerSingleton();
  }
}
