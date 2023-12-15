package com.nookure.mast.webhook.handler.staff;

import com.google.inject.Inject;
import com.nookure.mast.api.event.MastSubscribe;
import com.nookure.mast.api.event.staff.StaffServerSwitchEvent;
import com.nookure.mast.api.webhook.WebHookClient;
import com.nookure.mast.webhook.Webhooks;
import com.nookure.mast.webhook.json.WebhookLoader;
import es.angelillo15.mast.api.factory.CommonFactory;

public class OnStaffSwitch implements CommonFactory<OnStaffSwitch, WebHookClient> {
  @Inject
  private WebhookLoader loader;
  private WebHookClient client;

  @MastSubscribe
  public void onStaffSwitch(StaffServerSwitchEvent event) {
    client.sendWebHook(
        loader.getWebhook(Webhooks.SERVER_SWITCH),
        new com.nookure.mast.api.webhook.Replacer("previousServer", event.fromServer()),
        new com.nookure.mast.api.webhook.Replacer("server", event.toServer()),
        new com.nookure.mast.api.webhook.Replacer("username", event.username())
    );
  }

  @Override
  public OnStaffSwitch create(WebHookClient factory) {
    client = factory;
    return this;
  }
}
