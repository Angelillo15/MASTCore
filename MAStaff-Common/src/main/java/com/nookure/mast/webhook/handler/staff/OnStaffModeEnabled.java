package com.nookure.mast.webhook.handler.staff;

import com.google.inject.Inject;
import com.nookure.mast.api.event.MastSubscribe;
import com.nookure.mast.api.event.staff.mode.StaffModeEnabledEvent;
import com.nookure.mast.api.webhook.Replacer;
import com.nookure.mast.api.webhook.WebHookClient;
import com.nookure.mast.webhook.Webhooks;
import com.nookure.mast.webhook.json.WebhookLoader;
import es.angelillo15.mast.api.factory.CommonFactory;

public class OnStaffModeEnabled implements CommonFactory<OnStaffModeEnabled, WebHookClient> {
  @Inject
  private WebhookLoader loader;
  private WebHookClient client;

  @MastSubscribe
  public void onStaffModeEnable(StaffModeEnabledEvent event) {
    client.sendWebHook(
        loader.getWebhook(Webhooks.STAFF_ENABLED),
        new Replacer("username", event.staffPlayer().getName())
    );
  }

  @Override
  public OnStaffModeEnabled create(WebHookClient factory) {
    client = factory;
    return this;
  }
}
