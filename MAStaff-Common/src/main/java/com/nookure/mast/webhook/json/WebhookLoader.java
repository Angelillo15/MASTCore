package com.nookure.mast.webhook.json;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nookure.mast.api.MAStaff;
import com.nookure.mast.api.addons.annotations.AddonLogger;
import com.nookure.mast.api.webhook.WebHookClient;
import com.nookure.mast.api.webhook.config.JsonConfig;
import com.nookure.mast.webhook.Webhooks;
import es.angelillo15.mast.api.ILogger;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Singleton
public class WebhookLoader {
  @Inject
  @AddonLogger
  private ILogger logger;
  @Inject
  private MAStaff plugin;
  @Inject
  private File dataFolder;

  private final Map<String, JsonConfig> webhooks = new ConcurrentHashMap<>();

  public void loadWebhooks() {
    webhooks.clear();
    Stream.of(
        new WebhookOrigin(
            "common/webhooks/staffchat.json",
            dataFolder.getAbsolutePath() + "/webhooks/staffchat.json",
            Webhooks.STAFF_CHAT
        ), new WebhookOrigin(
            "common/webhooks/freeze.json",
            dataFolder.getAbsolutePath() + "/webhooks/freeze.json",
            Webhooks.FREEZE
        ), new WebhookOrigin(
            "common/webhooks/unfreeze.json",
            dataFolder.getAbsolutePath() + "/webhooks/unfreeze.json",
            Webhooks.UNFREEZE
        ), new WebhookOrigin(
            "common/webhooks/staffjoin.json",
            dataFolder.getAbsolutePath() + "/webhooks/staffjoin.json",
            Webhooks.STAFF_JOIN
        ), new WebhookOrigin(
            "common/webhooks/staffleft.json",
            dataFolder.getAbsolutePath() + "/webhooks/staffleft.json",
            Webhooks.STAFF_LEFT
        ), new WebhookOrigin(
            "common/webhooks/serverswitch.json",
            dataFolder.getAbsolutePath() + "/webhooks/serverswitch.json",
            Webhooks.SERVER_SWITCH
        ), new WebhookOrigin(
            "common/webhooks/staffenabled.json",
            dataFolder.getAbsolutePath() + "/webhooks/staffenabled.json",
            Webhooks.STAFF_ENABLED
        ), new WebhookOrigin(
            "common/webhooks/staffdisabled.json",
            dataFolder.getAbsolutePath() + "/webhooks/staffdisabled.json",
            Webhooks.STAFF_DISABLED
        )
    ).forEach(jsonOrigin -> {
      try {
        long start = System.currentTimeMillis();
        JsonConfig jsonConfig = new JsonConfig(jsonOrigin.jar(), jsonOrigin.target(), plugin);

        webhooks.put(jsonOrigin.name(), jsonConfig);
        logger.debug("Loaded " + jsonOrigin.jar() + " to " + jsonOrigin.target() + " in " + (System.currentTimeMillis() - start) + "ms");
      } catch (Exception e) {
        logger.error("Error loading " + jsonOrigin.jar() + " to " + jsonOrigin.target());
        logger.error(e.getMessage());
      }
    });
  }

  public JsonConfig getWebhook(String name) {
    return webhooks.get(name);
  }
}
