package com.nookure.staff.api.config.bukkit.partials.messages;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class NoteMessages {
  @Setting
  private String successfullyCreated = "{prefix} <green>Successfully created a note for <red>{player.name}!</red>";
  @Setting
  private String savingData = "{prefix} <gray>Saving data, please wait ⌚...";
  @Setting
  private String loadingData = "{prefix} <gray>Loading data, please wait ⌚...";
  @Setting
  private String noteDisplayHeader = """
      🗡 User Note <red><u>#{note.id}</u></red> for <hover:show_text:'<red>Name:</red> {player.name}
      <red>UUID:</red> {player.uuid}
      '><red><u>{player.name}</u></red></hover>
      """;

  @Setting
  private String noteDisplayBody = """
      ⛏ <red>Active:</red> {note.active}
      ⛏ <red>Note:</red> {note.note}
      ⛏ <red>Show On Join:</red> {note.showOnJoin}
      """;

  @Setting
  private String noteDisplayBodyAdmin = """
      ⛏ <red>Active:</red> {note.active}
      ⛏ <red>Note:</red> {note.note}
      ⛏ <red>Show On Join:</red> {note.showOnJoin}
      ⛏ <red>Show Only To Administrators:</red> {note.showOnlyToAdministrators}
      """;

  @Setting
  private String noteDisplayFooter = """
      <b>⚡</b> <click:suggest_command:'/note remove {note.id}'><hover:show_text:'<red>Delete'><u><red>🗑</red></u></hover></click> <b>│</b> <click:run_command:'/note toggle-show {note.id}'><hover:show_text:'<yellow>Toggle Show on Join'><u><yellow>👁</yellow></u></hover></click>
      """;

  public String loadingData() {
    return loadingData;
  }

  public String noteDisplayHeader() {
    return noteDisplayHeader;
  }

  public String noteDisplayBody() {
    return noteDisplayBody;
  }

  public String noteDisplayBodyAdmin() {
    return noteDisplayBodyAdmin;
  }

  public String noteDisplayFooter() {
    return noteDisplayFooter;
  }

  public String savingData() {
    return savingData;
  }

  public String successfullyCreated() {
    return successfullyCreated;
  }
}
