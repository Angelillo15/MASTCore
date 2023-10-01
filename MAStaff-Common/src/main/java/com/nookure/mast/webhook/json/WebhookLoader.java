package com.nookure.mast.webhook.json;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nookure.mast.api.MAStaff;
import com.nookure.mast.api.addons.annotations.AddonLogger;
import com.nookure.mast.api.webhook.config.JsonConfig;
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
    Stream.of(new WebhookOrigin(
        "common/webhooks/example.json",
        dataFolder.getAbsolutePath() + "/webhooks/example.json",
        "example")
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
