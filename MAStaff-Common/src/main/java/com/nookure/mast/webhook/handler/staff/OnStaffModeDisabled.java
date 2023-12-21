package com.nookure.mast.webhook.handler.staff;

import com.google.inject.Inject;
import com.nookure.mast.api.event.MastSubscribe;
import com.nookure.mast.api.event.staff.mode.StaffModeDisabledEvent;
import com.nookure.mast.api.webhook.Replacer;
import com.nookure.mast.api.webhook.WebHookClient;
import com.nookure.mast.webhook.json.WebhookLoader;
import es.angelillo15.mast.api.factory.CommonFactory;

public class OnStaffModeDisabled implements CommonFactory<OnStaffModeDisabled, WebHookClient> {
  @Inject
  private WebhookLoader loader;
  private WebHookClient client;

  @MastSubscribe
  public void onStaffModeDisable(StaffModeDisabledEvent event) {
    client.sendWebHook(
        loader.getWebhook(com.nookure.mast.webhook.Webhooks.STAFF_DISABLED),
        new Replacer("username", event.staffPlayer().getName())
    );
  }

  @Override
  public OnStaffModeDisabled create(WebHookClient factory) {
    client = factory;
    return this;
  }
}
