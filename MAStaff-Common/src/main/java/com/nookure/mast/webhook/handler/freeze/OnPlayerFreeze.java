package com.nookure.mast.webhook.handler.freeze;

import com.google.inject.Inject;
import com.nookure.mast.api.event.MastSubscribe;
import com.nookure.mast.api.event.staff.freeze.PlayerFreezeEvent;
import com.nookure.mast.api.webhook.Replacer;
import com.nookure.mast.api.webhook.WebHookClient;
import com.nookure.mast.webhook.Webhooks;
import com.nookure.mast.webhook.json.WebhookLoader;
import es.angelillo15.mast.api.factory.CommonFactory;

public class OnPlayerFreeze implements CommonFactory<OnPlayerFreeze, WebHookClient> {
  @Inject
  private WebhookLoader loader;
  private WebHookClient client;

  @MastSubscribe
  public void onFreeze(PlayerFreezeEvent event) {
    client.sendWebHook(
        loader.getWebhook(Webhooks.FREEZE),
        new Replacer("staff", event.getStaffName()),
        new Replacer("username", event.getPlayerName())
    );
  }

  @Override
  public OnPlayerFreeze create(WebHookClient factory) {
    client = factory;
    return this;
  }
}