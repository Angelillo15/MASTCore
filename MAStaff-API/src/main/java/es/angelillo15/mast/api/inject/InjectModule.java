package es.angelillo15.mast.api.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;

public class InjectModule extends AbstractModule {
  private final Injector injector;

  public InjectModule(Injector injector) {
    this.injector = injector;
  }

  @Override
  protected void configure() {
    bind(Injector.class).toInstance(injector);
  }
}
