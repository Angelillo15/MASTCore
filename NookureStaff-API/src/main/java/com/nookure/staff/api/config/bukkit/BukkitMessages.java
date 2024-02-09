package com.nookure.staff.api.config.bukkit;

import com.nookure.staff.api.config.bukkit.partials.messages.StaffModePartial;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
public class BukkitMessages {
  public final StaffModePartial staffMode = new StaffModePartial();
}
