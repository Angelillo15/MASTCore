package com.nookure.mast.webhook;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.nookure.mast.api.addons.AddonActions;
import com.nookure.mast.api.addons.annotations.Addon;
import com.nookure.mast.api.addons.annotations.AddonLogger;
import com.nookure.mast.webhook.json.WebhookLoader;
import es.angelillo15.mast.api.Constants;
import es.angelillo15.mast.api.ILogger;

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
  private Injector addonInjector;

  @Override
  public void onEnable() {
    logger.info("Loading Discord Webhooks...");
    addonInjector = injector.createChildInjector(new WebhookInjector());
    loadWebhooksFiles();
  }

  public void loadWebhooksFiles() {
    addonInjector.getInstance(WebhookLoader.class).loadWebhooks();
  }
}
