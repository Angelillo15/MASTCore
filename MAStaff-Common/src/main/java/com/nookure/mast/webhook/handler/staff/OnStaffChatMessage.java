package com.nookure.mast.webhook.handler.staff;

import com.google.inject.Inject;
import com.nookure.mast.api.event.MastSubscribe;
import com.nookure.mast.api.event.staff.staffchat.StaffChatMessageSentEvent;
import com.nookure.mast.api.webhook.Replacer;
import com.nookure.mast.api.webhook.WebHookClient;
import com.nookure.mast.webhook.Webhooks;
import com.nookure.mast.webhook.json.WebhookLoader;
import es.angelillo15.mast.api.factory.CommonFactory;

public class OnStaffChatMessage implements CommonFactory<OnStaffChatMessage, WebHookClient> {
  @Inject
  private WebhookLoader loader;
  private WebHookClient client;

  @MastSubscribe
  public void onStaffChat(StaffChatMessageSentEvent event) {
    client.sendWebHook(
        loader.getWebhook(Webhooks.STAFF_CHAT),
        new Replacer("message", event.getMessage()),
        new Replacer("username", event.getUsername()),
        new Replacer("server", event.getServer())
    );
  }

  @Override
  public OnStaffChatMessage create(WebHookClient factory) {
    this.client = factory;
    return this;
  }
}
