package com.nookure.mast.webhook;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.nookure.mast.api.addons.AddonActions;
import com.nookure.mast.api.addons.annotations.Addon;
import com.nookure.mast.api.addons.annotations.AddonLogger;
import com.nookure.mast.api.config.ConfigurationContainer;
import com.nookure.mast.webhook.config.WebhookConfig;
import com.nookure.mast.webhook.json.WebhookLoader;
import es.angelillo15.mast.api.Constants;
import es.angelillo15.mast.api.ILogger;

import java.io.File;
import java.io.IOException;

@Addon(
    name = "Discord Webhooks",
    version = Constants.VERSION,
    description = "Sends messages to Discord Webhooks",
    author = "Nookure",
    loadOnScan = false
)
public class DiscordWebhooks implements AddonActions {
  @Inject
  private Injector injector;
  @Inject
  @AddonLogger
  private ILogger logger;
  @Inject
  private File dataFolder;
  private Injector addonInjector;
  private ConfigurationContainer<WebhookConfig> config;

  @Override
  public void onEnable() {
    logger.info("Loading Discord Webhooks...");
    loadConfig();

    addonInjector = injector.createChildInjector(new WebhookInjector(config.get()));
    loadWebhooksFiles();
  }

  public void loadWebhooksFiles() {
    addonInjector.getInstance(WebhookLoader.class).loadWebhooks();
  }

  public void loadConfig() {
    try {
      config = ConfigurationContainer.load(dataFolder.toPath(), WebhookConfig.class);
      config.reload();
    } catch (IOException e) {
      logger.error("Could not load config.conf file", e);
    }
  }
}
