package com.nookure.mast.webhook.handler;

import com.google.inject.Inject;
import com.nookure.mast.api.event.MastSubscribe;
import com.nookure.mast.api.event.staff.StaffServerSwitchEvent;
import com.nookure.mast.api.webhook.WebHookClient;
import com.nookure.mast.webhook.Webhooks;
import com.nookure.mast.webhook.json.WebhookLoader;
import es.angelillo15.mast.api.factory.CommonFactory;

public class StaffSwitch implements CommonFactory<StaffSwitch, WebHookClient> {
  @Inject
  private WebhookLoader loader;
  private WebHookClient client;

  @MastSubscribe
  public void onStaffSwitch(StaffServerSwitchEvent event) {
    client.sendWebHook(
        loader.getWebhook(Webhooks.SERVER_SWITCH),
        new com.nookure.mast.api.webhook.Replacer("previousServer", event.getFromServer()),
        new com.nookure.mast.api.webhook.Replacer("server", event.getToServer()),
        new com.nookure.mast.api.webhook.Replacer("username", event.getUsername())
    );
  }

  @Override
  public StaffSwitch create(WebHookClient factory) {
    client = factory;
    return this;
  }
}