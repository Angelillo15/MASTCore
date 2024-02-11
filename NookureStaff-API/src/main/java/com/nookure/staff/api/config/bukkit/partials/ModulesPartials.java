package com.nookure.staff.api.config.bukkit.partials;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class ModulesPartials {
  @Setting
  @Comment("""
      Enable or disable the staff mode.
      This include /staff command and the staff
      vault.
      """)
  private boolean staffMode = true;

  @Setting
  @Comment("""
      Enable or disable the freeze module.
      This include /freeze command
      """)
  private boolean freeze = true;

  @Setting
  @Comment("""
      Enable or disable the vanish module.
      This include /vanish command and the plugin vanish
      on StaffMode.
      """)
  private boolean vanish = true;

  @Setting
  @Comment("""
      Enable or disable the invsee module.
      This include /invsee command
      """)
  private boolean invsee = true;

  @Setting
  @Comment("""
      Enable or disable the StaffChat module.
      This include /staffchat command and
      the staff chat prefix.
      """)
  private boolean staffChat = true;

  public boolean isStaffMode() {
    return staffMode;
  }

  public boolean isFreeze() {
    return freeze;
  }

  public boolean isVanish() {
    return vanish;
  }

  public boolean isInvsee() {
    return invsee;
  }

  public boolean isStaffChat() {
    return staffChat;
  }
}
