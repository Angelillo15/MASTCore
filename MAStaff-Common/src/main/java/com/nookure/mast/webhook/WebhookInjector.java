package com.nookure.mast.webhook;

import com.google.inject.AbstractModule;
import com.nookure.mast.webhook.json.WebhookLoader;

public class WebhookInjector extends AbstractModule {
  @Override
  protected void configure() {
    bind(WebhookLoader.class).asEagerSingleton();
  }
}
