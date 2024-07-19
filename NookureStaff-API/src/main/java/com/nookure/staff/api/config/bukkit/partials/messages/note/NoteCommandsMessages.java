package com.nookure.staff.api.config.bukkit.partials.messages.note;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class NoteCommandsMessages {
  @Setting
  @Comment("The command usage message for the /note add.")
  private final String addNoteUsage = "{prefix} <red>Usage: /note add <player> <show on join> <note> [show only to admins]";

  @Setting
  @Comment("The command usage message for the /note remove.")
  private final String removeNoteUsage = "{prefix} <red>Usage: /note remove <note id>";

  @Setting
  @Comment("The command usage message for the /note list.")
  private final String listNoteUsage = "{prefix} <red>Usage: /note list <player> [page]";

  @Setting
  @Comment("The command usage message for the /note toggle-show command.")
  private final String toggleShowUsage = "{prefix} <red>Usage: /note toggle-show <note id>";

  @Setting
  @Comment("The command usage message for the /note inventory command.")
  private final String inventoryUsage = "{prefix} <red>Usage: /note inventory <player>";

  public String getAddNoteUsage() {
    return addNoteUsage;
  }

  public String getRemoveNoteUsage() {
    return removeNoteUsage;
  }

  public String getListNoteUsage() {
    return listNoteUsage;
  }

  public String getToggleShowUsage() {
    return toggleShowUsage;
  }

  public String getInventoryUsage() {
    return inventoryUsage;
  }
}
