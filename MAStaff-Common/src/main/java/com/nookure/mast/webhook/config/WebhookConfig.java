package com.nookure.mast.webhook.config;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@ConfigSerializable
public class WebhookConfig {
  @Comment("Enable/disable the webhook addon.")
  private boolean enabled = false;

  public boolean enabled() {
    return enabled;
  }

  public final StaffChat staffChat = new StaffChat();
  public final Freeze freeze = new Freeze();
  public final UnFreeze unfreeze = new UnFreeze();
  public final StaffJoin staffJoin = new StaffJoin();
  public final StaffLeft staffLeft = new StaffLeft();
  public final ServerSwitch serverSwitch = new ServerSwitch();
  public final StaffEnabled staffEnabled = new StaffEnabled();
  public final StaffDisabled staffDisabled = new StaffDisabled();


  @ConfigSerializable
  public static class Freeze {
    @Comment("Enable/disable the webhook.")
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

  @ConfigSerializable
  public static class ServerSwitch {
    @Comment("Enable/disable the webhook.")
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

  @ConfigSerializable
  public static class StaffChat {
    @Comment("Enable/disable the webhook.")
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

  @ConfigSerializable
  public static class StaffDisabled {
    @Comment("Enable/disable the webhook.")
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

  @ConfigSerializable
  public static class StaffEnabled {
    @Comment("Enable/disable the webhook.")
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

  @ConfigSerializable
  public static class StaffJoin {
    @Comment("Enable/disable the webhook.")
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

  @ConfigSerializable
  public static class StaffLeft {
    @Comment("Enable/disable the webhook.")
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

  @ConfigSerializable
  public static class UnFreeze {
    @Comment("Enable/disable the webhook.")
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
