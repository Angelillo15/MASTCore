package com.nookure.mast.webhook.handler.freeze;

import com.google.inject.Inject;
import com.nookure.mast.api.event.MastSubscribe;
import com.nookure.mast.api.event.staff.freeze.PlayerUnfreezeEvent;
import com.nookure.mast.api.webhook.Replacer;
import com.nookure.mast.api.webhook.WebHookClient;
import com.nookure.mast.webhook.Webhooks;
import com.nookure.mast.webhook.json.WebhookLoader;
import es.angelillo15.mast.api.factory.CommonFactory;

public class OnPlayerUnfreeze implements CommonFactory<OnPlayerUnfreeze, WebHookClient> {
  @Inject
  private WebhookLoader loader;
  private WebHookClient client;

  @MastSubscribe
  public void onUnFreeze(PlayerUnfreezeEvent event) {
    client.sendWebHook(
        loader.getWebhook(Webhooks.UNFREEZE),
        new Replacer("staff", event.staffName()),
        new Replacer("username", event.playerName())
    );
  }

  @Override
  public OnPlayerUnfreeze create(WebHookClient factory) {
    client = factory;
    return this;
  }
}
