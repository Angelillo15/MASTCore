package com.nookure.mast.webhook.handler.staff;

import com.google.inject.Inject;
import com.nookure.mast.api.event.MastSubscribe;
import com.nookure.mast.api.event.staff.StaffLeaveEvent;
import com.nookure.mast.api.webhook.Replacer;
import com.nookure.mast.api.webhook.WebHookClient;
import com.nookure.mast.webhook.Webhooks;
import com.nookure.mast.webhook.json.WebhookLoader;
import es.angelillo15.mast.api.factory.CommonFactory;

public class OnStaffLeave implements CommonFactory<OnStaffLeave, WebHookClient> {
  @Inject
  private WebhookLoader loader;
  private WebHookClient client;

  @MastSubscribe
  public void onStaffLeave(StaffLeaveEvent event) {
    client.sendWebHook(
        loader.getWebhook(Webhooks.STAFF_LEFT),
        new Replacer("username", event.staffName())
    );
  }

  @Override
  public OnStaffLeave create(WebHookClient factory) {
    client = factory;
    return this;
  }
}
