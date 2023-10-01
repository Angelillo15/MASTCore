package com.nookure.mast.webhook.config;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@ConfigSerializable
public class WebhookConfig {
  @Comment("Enable/disable the StaffChat webhook.")
  private boolean enabled = true;
  private StaffChat staffChat = new StaffChat();

  public StaffChat staffChat() {
    return staffChat;
  }

  @ConfigSerializable
  public static class StaffChat {
    @Comment("Enable/disable the StaffChat webhook.")
    private boolean enabled = true;

    public boolean enabled() {
      return enabled;
    }

    @Comment("The webhook URL.")
    private String url = "https://discord.com/api/webhooks/1234567890/abcdefghijklmnopqrstuvwxyz";

    public String url() {
      return url;
    }
  }
}
