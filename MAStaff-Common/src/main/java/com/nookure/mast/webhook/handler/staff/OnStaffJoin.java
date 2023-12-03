package com.nookure.mast.webhook.handler.staff;

import com.google.inject.Inject;
import com.nookure.mast.api.event.MastSubscribe;
import com.nookure.mast.api.event.staff.StaffJoinEvent;
import com.nookure.mast.api.webhook.Replacer;
import com.nookure.mast.api.webhook.WebHookClient;
import com.nookure.mast.webhook.Webhooks;
import com.nookure.mast.webhook.json.WebhookLoader;
import es.angelillo15.mast.api.factory.CommonFactory;

public class OnStaffJoin implements CommonFactory<OnStaffJoin, WebHookClient> {
  @Inject
  private WebhookLoader loader;
  private WebHookClient client;

  @MastSubscribe
  public void onStaffJoin(StaffJoinEvent event) {
    client.sendWebHook(
        loader.getWebhook(Webhooks.STAFF_JOIN),
        new Replacer("username", event.getStaffName())
    );
  }

  @Override
  public OnStaffJoin create(WebHookClient factory) {
    client = factory;
    return this;
  }
}
