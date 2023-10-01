package com.nookure.mast.webhook;

import com.google.inject.AbstractModule;
import com.nookure.mast.webhook.config.WebhookConfig;
import com.nookure.mast.webhook.json.WebhookLoader;

public class WebhookInjector extends AbstractModule {
  private final WebhookConfig config;

  public WebhookInjector(WebhookConfig config) {
    this.config = config;
  }

  @Override
  protected void configure() {
    bind(WebhookLoader.class).asEagerSingleton();
    bind(WebhookConfig.class).toInstance(config);
  }
}
