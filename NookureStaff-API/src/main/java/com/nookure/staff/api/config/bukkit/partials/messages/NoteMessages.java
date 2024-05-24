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
  private String deletingNote = "{prefix} <gray>Deleting note, please wait ⌚...";
  @Setting
  private String noteDeleted = "{prefix} <green>Successfully deleted the note with the id {note.id}.";
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

  @Setting
  private String noteNotFound = "{prefix} <red>Could not find the note with the id {note.id}.";

  @Setting
  private String userWithoutNotes = "{prefix} <red>{player.name} does not have any notes.";

  @Setting
  private String paginationPrevious = "<click:run_command:/note list {player.name} {prev_page}><hover:show_text:'<red>Previous Page'><red>«</red></hover></click> ";

  @Setting
  private String paginationNext = " <click:run_command:/note list {player.name} {next_page}><hover:show_text:'<red>Next Page'><red>»</red></hover></click>";

  @Setting
  private String paginationHeader = "<gray>Notes for <red>{player.name}</red> <gray>Page <red>{page}</red> <gray>of <red>{total_pages}</red>.";

  @Setting
  private String paginationFooterNumber = "<click:run_command:/note list {player.name} {page}><hover:show_text:'<red>Go to Page {page}'><red>{page}</red></hover></click>";

  private String currentPaginationNumber = "<u><red>{page}</red></u>";
  @Setting
  private String separator = "<gray> │ </gray>";

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

  public String noteNotFound() {
    return noteNotFound;
  }

  public String deletingNote() {
    return deletingNote;
  }

  public String noteDeleted() {
    return noteDeleted;
  }

  public String userWithoutNotes() {
    return userWithoutNotes;
  }

  public String paginationPrevious() {
    return paginationPrevious;
  }

  public String paginationNext() {
    return paginationNext;
  }

  public String paginationHeader() {
    return paginationHeader;
  }

  public String paginationFooterNumber() {
    return paginationFooterNumber;
  }

  public String separator() {
    return separator;
  }

  public String currentPaginationNumber() {
    return currentPaginationNumber;
  }
}
