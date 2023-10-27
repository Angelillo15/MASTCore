package com.nookure.mast.webhook;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.nookure.mast.api.addons.AddonActions;
import com.nookure.mast.api.addons.AddonContainer;
import com.nookure.mast.api.addons.AddonManager;
import com.nookure.mast.api.addons.annotations.Addon;
import com.nookure.mast.api.addons.annotations.AddonLogger;
import com.nookure.mast.api.config.ConfigurationContainer;
import com.nookure.mast.api.webhook.WebHookClient;
import com.nookure.mast.webhook.config.WebhookConfig;
import com.nookure.mast.webhook.handler.staff.OnStaffChatMessage;
import com.nookure.mast.webhook.handler.staff.OnStaffJoin;
import com.nookure.mast.webhook.handler.staff.OnStaffLeave;
import com.nookure.mast.webhook.handler.staff.OnStaffSwitch;
import com.nookure.mast.webhook.handler.freeze.OnPlayerFreeze;
import com.nookure.mast.webhook.handler.freeze.OnPlayerUnfreeze;
import com.nookure.mast.webhook.handler.staff.OnStaffModeDisabled;
import com.nookure.mast.webhook.handler.staff.OnStaffModeEnabled;
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
  @Inject
  private AddonManager addonManager;
  @Inject
  private AddonContainer addonContainer;
  private Injector addonInjector;
  private ConfigurationContainer<WebhookConfig> config;

  @Override
  public void onEnable() {
    logger.info("Loading Discord Webhooks...");
    loadConfig();

    if (!config.get().enabled()) {
      logger.info("Discord Webhooks are disabled.");
      addonManager.disableAddon(this);
      return;
    }

    addonInjector = injector.createChildInjector(new WebhookInjector(config.get()));
    loadWebhooksFiles();
    loadListeners();
  }

  public void loadListeners() {
    if (config.get().staffChat.enabled()) {
      addonManager.registerListener(addonInjector
          .getInstance(OnStaffChatMessage.class)
          .create(WebHookClient.fromURL(config.get().staffChat.url())), addonContainer
      );
    }

    if (config.get().freeze.enabled()) {
      addonManager.registerListener(addonInjector
          .getInstance(OnPlayerFreeze.class)
          .create(WebHookClient.fromURL(config.get().freeze.url())), addonContainer
      );
    }

    if (config.get().unfreeze.enabled()) {
      addonManager.registerListener(addonInjector
          .getInstance(OnPlayerUnfreeze.class)
          .create(WebHookClient.fromURL(config.get().unfreeze.url())), addonContainer
      );
    }

    if (config.get().serverSwitch.enabled()) {
      addonManager.registerListener(addonInjector
          .getInstance(OnStaffSwitch.class)
          .create(WebHookClient.fromURL(config.get().serverSwitch.url())), addonContainer
      );
    }

    if (config.get().staffDisabled.enabled()) {
      addonManager.registerListener(addonInjector
          .getInstance(OnStaffModeDisabled.class)
          .create(WebHookClient.fromURL(config.get().staffDisabled.url())), addonContainer
      );
    }

    if (config.get().staffEnabled.enabled()) {
      addonManager.registerListener(addonInjector
          .getInstance(OnStaffModeEnabled.class)
          .create(WebHookClient.fromURL(config.get().staffEnabled.url())), addonContainer
      );
    }

    if (config.get().staffJoin.enabled()) {
      addonManager.registerListener(addonInjector
          .getInstance(OnStaffJoin.class)
          .create(WebHookClient.fromURL(config.get().staffJoin.url())), addonContainer
      );
    }

    if (config.get().staffLeft.enabled()) {
      addonManager.registerListener(addonInjector
          .getInstance(OnStaffLeave.class)
          .create(WebHookClient.fromURL(config.get().staffLeft.url())), addonContainer
      );
    }
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

  @Override
  public void onReload() {
    addonManager.unregisterListeners(addonContainer);
    loadConfig();
    loadWebhooksFiles();
    loadListeners();
  }
}
